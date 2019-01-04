package com.zhenikhov.entity;

public enum Vat {
    WITHOUT ("Без НДС"),
    P10 ("НДС 10%"),
    P18 ("НДС 18%");

    private String name;

    Vat(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
