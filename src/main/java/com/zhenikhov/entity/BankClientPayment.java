package com.zhenikhov.entity;


public abstract class BankClientPayment {
    private Integer bankClientId;

    public Integer getBankClientId() {
        return bankClientId;
    }

    public void setBankClientId(Integer bankClientId) {
        this.bankClientId = bankClientId;
    }
}
