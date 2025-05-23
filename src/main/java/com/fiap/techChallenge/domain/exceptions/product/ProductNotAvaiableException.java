package com.fiap.techChallenge.domain.exceptions.product;

import com.fiap.techChallenge.domain.exceptions.DomainException;

public class ProductNotAvaiableException extends DomainException {

    public ProductNotAvaiableException() {
        super("O produto informado não está disponivel");
    }

}
