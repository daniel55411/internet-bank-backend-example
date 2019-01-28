package com.zhenikhov.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class CardPayment implements BankClientPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Pattern(regexp = "^\\d{16}$")
    @NotNull
    private String cardNumber;

    @Min(1000)
    @Max(75000)
    @NotNull
    private Integer transferAmount;

    @NotNull
    private Integer bankClientId;

    @Pattern(regexp = "^\\d{2}/\\d{2}$")
    @NotNull
    private String cardDate;

    @Pattern(regexp = "^\\d{3}$")
    @NotNull
    private String cardCvc;

    @Size(max = 150)
    private String commentary;

    @Pattern(regexp = "^\\w{3,}@\\w{2,}\\.\\w{2,}$")
    @NotNull
    private String email;

    private Boolean unsafe = Boolean.FALSE;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Integer transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public String getCardCvc() {
        return cardCvc;
    }

    public void setCardCvc(String cardCvc) {
        this.cardCvc = cardCvc;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getUnsafe() {
        return unsafe;
    }

    public void setUnsafe(Boolean unsafe) {
        this.unsafe = unsafe;
    }

    @Override
    public Integer getBankClientId() {
        return bankClientId;
    }

    @Override
    public void setBankClientId(Integer bankClientId) {
        this.bankClientId = bankClientId;
    }
}
