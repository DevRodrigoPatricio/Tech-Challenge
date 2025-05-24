package com.fiap.techChallenge.domain.exceptions.order;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.fiap.techChallenge.domain.enums.Category;
import com.fiap.techChallenge.domain.exceptions.DomainException;

public class WrongCategoryOrderException extends DomainException {

    public WrongCategoryOrderException() {
        super("Os produtos devem ser selecionados na seguinte ordem: " + getCategoryList());
    }

    private static String getCategoryList() {
        String categoryList = Arrays.stream(Category.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
        return categoryList;
    }

}
