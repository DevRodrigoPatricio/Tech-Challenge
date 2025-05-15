package com.fiap.techChallenge.utils.exceptions;

public class CategoryHasProductsException extends DomainException {

    public CategoryHasProductsException() {
        super(String.format("Existem produtos cadastrados nessa categoria"));
    }
}
