package com.kakaopay.investment.product;

public enum ProductStateType {
    OPEN("OPEN"),
    CLOSED("CLOSED");

    private final String value;

    private ProductStateType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}