package com.fiap.techChallenge.domain.user;

public class CPF {

    private String number;

    public CPF(String number) {
        setNumber(number);
    }

    public CPF() {}

    void setNumber(String number) {
        if (number != null && !(isValidAsUnformatted(number) || isValidAsFormatted(number))) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        this.number = number;
    }

    String getFormattedNumber() {
        return String.format("%s.%s.%s-%s",
                number.substring(0, 3),
                number.substring(3, 6),
                number.substring(6, 9),
                number.substring(9, 11));
    }

    String getUnformattedNumber() {
        return number.replaceAll("\\D", "");
    }

    private boolean isValidAsFormatted(String number) {
        return number.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    }

    private boolean isValidAsUnformatted(String number) {
        return number.matches("\\d{11}");
    }
}
