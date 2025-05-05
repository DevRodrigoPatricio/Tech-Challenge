package com.fiap.techChallenge.adapters.outbound.payment;

import com.fiap.techChallenge.application.ports.PaymentProcessingPort;
import com.fiap.techChallenge.domain.PaymentRequest;
import com.fiap.techChallenge.domain.PaymentResponse;
import com.fiap.techChallenge.domain.enums.PaymentStatus;
import com.fiap.techChallenge.domain.exceptions.PaymentException;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Component
public class MercadoPagoAdapter implements PaymentProcessingPort {

    private static final String ACCESS_TOKEN = "APP_USR-7885298402464176-050508-28625df9a6c9cbd4560bbfc22cb73b98-2416569013";
    private static final String COLLECTOR_ID = "2416569013";
    private static final String POS_ID = "SUC001POS001";

    private static final String QR_URL_TEMPLATE =
            "https://api.mercadopago.com/instore/orders/qr/seller/collectors/{collector_id}/pos/{pos_id}/qrs";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @Retryable(
            value = { Exception.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public PaymentResponse processPayment(PaymentRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(ACCESS_TOKEN);

            Map<String, Object> payload = buildPayload(request);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

            String url = QR_URL_TEMPLATE
                    .replace("{collector_id}", COLLECTOR_ID)
                    .replace("{pos_id}", POS_ID);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.CREATED || response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> body = response.getBody();
                String qrData = (String) body.get("qr_data");
                String externalRef = (String) body.get("external_reference");

                return new PaymentResponse("PENDING",request.getOrderId(), qrData);
            } else {
                throw new PaymentException("Erro ao gerar QR Code Mercado Pago: status " + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new PaymentException("Erro na integração com Mercado Pago", e);
        }
    }

    private Map<String, Object> buildPayload(PaymentRequest request) {
        Map<String, Object> payload = new HashMap<>();

        payload.put("external_reference", request.getOrderId());
        payload.put("title", "Pedido " + request.getOrderId());
        payload.put("description", "Compra no tech challenge ");
        payload.put("total_amount", request.getAmount());

        Map<String, Object> item = new HashMap<>();
        item.put("sku_number", "A123K9191938");
        item.put("category", "marketplace");
        item.put("title", "Compra no tech challenge");
        item.put("description", "Pedido realizado no tech challenge");
        item.put("unit_price", request.getAmount());
        item.put("quantity", 1);
        item.put("unit_measure", "unit");
        item.put("total_amount", request.getAmount());

        payload.put("items", List.of(item));

        Map<String, Object> sponsor = new HashMap<>();
        sponsor.put("id", 662208785);
        payload.put("sponsor", sponsor);

        Map<String, Object> cashOut = new HashMap<>();
        cashOut.put("amount", 0);
        payload.put("cash_out", cashOut);

        return payload;
    }

    @Override
    public PaymentStatus checkStatus(UUID orderId) {
        try {
            String url = "https://api.mercadopago.com/merchant_orders/search?external_reference=" + orderId;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(ACCESS_TOKEN);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> body = response.getBody();
                List<Map<String, Object>> elements = (List<Map<String, Object>>) body.get("elements");

                if (elements == null || elements.isEmpty()) {
                    return PaymentStatus.PENDING;
                }

                Map<String, Object> merchantOrder = elements.get(0);
                List<Map<String, Object>> payments = (List<Map<String, Object>>) merchantOrder.get("payments");

                if (payments == null || payments.isEmpty()) {
                    return PaymentStatus.PENDING;
                }

                // Pega o status do primeiro pagamento
                String status = (String) payments.get(0).get("status");
                return mapStatus(status);
            } else {
                throw new PaymentException("Erro ao consultar status do pedido: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new PaymentException("Erro ao consultar status do pedido", e);
        }
    }


    private PaymentStatus mapStatus(String mpStatus) {
        return switch (mpStatus) {
            case "approved" -> PaymentStatus.APPROVED;
            case "rejected", "cancelled" -> PaymentStatus.REJECTED;
            default -> PaymentStatus.PENDING;
        };
    }


    @Recover
    public PaymentResponse recoverProcessPayment(Exception e, PaymentRequest request) {
        System.err.println("Todas as tentativas falharam. Retornando fallback");
        return new PaymentResponse("FAILED",
                request.getOrderId(), null);
    }
}
