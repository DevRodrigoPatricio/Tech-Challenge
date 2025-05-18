package com.fiap.techChallenge.domain.customer;

public class CPF {

    private String number;

    public CPF(String number) {
        setNumber(number);
    }

    public CPF() {}

    String getNumber() {
        return number;
    }

    void setNumber(String number) {
        if (number != null && !isValid(number)) {
            throw new IllegalArgumentException("Invalid CPF number.");
        }
        this.number = number;
    }

    private boolean isValid(String number) {
        return number.matches("\\d{11}");
    }
}
