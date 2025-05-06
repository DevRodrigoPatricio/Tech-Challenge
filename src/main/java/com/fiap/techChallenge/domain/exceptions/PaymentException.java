package com.fiap.techChallenge.domain.exceptions;

public class PaymentException extends DomainException {

    public PaymentException(String mensagem) {
        super(mensagem);
    }

    public PaymentException(String mensagem, Throwable causa) {
        super(mensagem);
        initCause(causa);
    }
}
