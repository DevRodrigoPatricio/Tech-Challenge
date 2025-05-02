package com.fiap.techChallenge.adapters.outbound.payment;

import com.fiap.techChallenge.application.ports.PaymentProcessingPort;
import com.fiap.techChallenge.domain.PaymentRequest;
import com.fiap.techChallenge.domain.PaymentResponse;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoAdapter implements PaymentProcessingPort {

    private static final String ACCESS_TOKEN = "TEST-864228206834919-050211-c546c2c753924ddd2618b7d86074b096-328521796";

    static {
        MercadoPagoConfig.setAccessToken(ACCESS_TOKEN);
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        try {
            PaymentClient paymentClient = new PaymentClient();

            PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                    .transactionAmount(request.getAmount())
                    .description("Pedido " + request.getOrderId())
                    .paymentMethodId("pix")
                    .payer(PaymentPayerRequest.builder()
                            .email("rodrigopatricio19@gmail.com")  // Necessário para Pix
                            .firstName("Rodrigo")
                            .lastName("Patricio")
                            .build())
                    .build();

            Payment payment = paymentClient.create(paymentCreateRequest);

            String qrCode = payment.getPointOfInteraction().getTransactionData().getQrCode();
            String qrCodeBase64 = payment.getPointOfInteraction().getTransactionData().getQrCodeBase64();

            return new PaymentResponse("PENDING", qrCode, qrCodeBase64);

        } catch (MPApiException e) {
            System.err.println("Erro na API do Mercado Pago:");
            System.err.println("Status: " + e.getApiResponse().getStatusCode());
            System.err.println("Conteúdo: " + e.getApiResponse().getContent());
            throw new RuntimeException("Erro ao integrar com o Mercado Pago", e);
        } catch (MPException e) {
            throw new RuntimeException("Erro interno na SDK do Mercado Pago", e);
        }
    }
}
