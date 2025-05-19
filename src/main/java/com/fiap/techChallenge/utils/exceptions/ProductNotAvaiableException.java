package com.fiap.techChallenge.utils.exceptions;

public class ProductNotAvaiableException extends DomainException {

    public ProductNotAvaiableException() {
        super("O produto informado não está disponivel");
    }

}
