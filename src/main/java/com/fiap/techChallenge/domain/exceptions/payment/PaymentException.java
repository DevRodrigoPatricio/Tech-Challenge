package com.fiap.techChallenge.domain.exceptions.payment;

import com.fiap.techChallenge.domain.exceptions.DomainException;

public class PaymentException extends DomainException {

    public PaymentException(String mensagem) {
        super(mensagem);
    }

    public PaymentException(String mensagem, Throwable causa) {
        super(mensagem);
        initCause(causa);
    }
}
