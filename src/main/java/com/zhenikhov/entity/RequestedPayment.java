package com.zhenikhov.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class RequestedPayment implements BankClientPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private Integer bankClientId;

    @Pattern(regexp = "^\\d{10}|\\d{12}$")
    @NotNull
    private String tin;

    @Pattern(regexp = "^\\d{9}$")
    @NotNull
    private String bic;

    @NotNull
    private Vat vat;

    @Min(1000)
    @Max(75000)
    @NotNull
    private Integer transferAmount;

    @Pattern(regexp = "^\\d{20}$")
    @NotNull
    private String accountNumber;

    @Pattern(regexp = "^(\\+\\d\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{2}[\\s.-]?\\d{2}$")
    @NotNull
    private String phoneNumber;

    @Pattern(regexp = "^\\w{3,}@\\w{2,}\\.\\w{2,}$")
    @NotNull
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public Vat getVat() {
        return vat;
    }

    public void setVat(Vat vat) {
        this.vat = vat;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Integer transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
