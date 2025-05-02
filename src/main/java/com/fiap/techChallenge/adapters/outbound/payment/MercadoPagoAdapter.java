package com.fiap.techChallenge.adapters.outbound.payment;

import com.fiap.techChallenge.application.ports.PaymentProcessingPort;
import com.fiap.techChallenge.domain.PaymentRequest;
import com.fiap.techChallenge.domain.PaymentResponse;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class MercadoPagoAdapter implements PaymentProcessingPort {

    static {
        MercadoPagoConfig.setAccessToken(System.getenv("MP_ACCESS_TOKEN"));
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        try {
            PreferenceClient preferenceClient = new PreferenceClient();

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title("Pedido " + request.getOrderId())
                    .quantity(1)
                    .unitPrice(request.getAmount())
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(Collections.singletonList(item))
                    .build();

            Preference preference = preferenceClient.create(preferenceRequest);

            return new PaymentResponse("PENDING", preference.getInitPoint());

        } catch (MPApiException | MPException e) {
            throw new RuntimeException("Erro ao integrar com o Mercado Pago", e);
        }
    }
}
