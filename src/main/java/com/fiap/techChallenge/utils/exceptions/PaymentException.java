package com.fiap.techChallenge.utils.exceptions;

public class PaymentException extends DomainException {

    public PaymentException(String mensagem) {
        super(mensagem);
    }

    public PaymentException(String mensagem, Throwable causa) {
        super(mensagem);
        initCause(causa);
    }
}
