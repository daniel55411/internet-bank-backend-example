package com.zhenikhov.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Vat {
    WITHOUT("Без НДС"),
    P10("НДС 10%"),
    P18("НДС 18%");

    private String name;

    Vat(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @JsonCreator
    public static Vat create(String value) {
        if(value == null) {
            throw new IllegalArgumentException();
        }
        for(Vat v : values()) {
            if(value.equals(v.toString())) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }
}
