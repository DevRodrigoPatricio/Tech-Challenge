package com.fiap.techChallenge.utils.exceptions;

public class WrongCategoryOrderException extends DomainException {

    public WrongCategoryOrderException(String categorysList) {
        super("Os produtos devem ser selecionados na seguinte ordem: " + categorysList);
    }

}
