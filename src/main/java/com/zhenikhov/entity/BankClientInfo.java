package com.zhenikhov.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
public class BankClientInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private Integer bankClientId;

    @Pattern(regexp = "^\\w{3,}@\\w{2,}\\.\\w{2,}$")
    private String email;

    @Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$")
    private String phoneNumber;

    private String FIO;

    public Integer getBankClientId() {
        return bankClientId;
    }

    public void setBankClientId(Integer bankClientId) {
        this.bankClientId = bankClientId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
