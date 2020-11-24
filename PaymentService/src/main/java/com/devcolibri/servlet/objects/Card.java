package com.devcolibri.servlet.objects;

import java.sql.Date;

public class Card {
    private int id;
    private int userId;
    private int bankAccountId;
    private int number;
    private float balance;
    private String pinHash;
    private String cvvHash;
    private Date expireDate;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBankAccountId() {
        return this.bankAccountId;
    }

    public void setBankAccountId(int bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getBalance() {
        return this.balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getPinHash() {
        return this.pinHash;
    }

    public void setPinHash(String pinHash) {
        this.pinHash = pinHash;
    }

    public String getCvvHash() {
        return this.cvvHash;
    }

    public void setCvvHash(String cvvHash) {
        this.cvvHash = cvvHash;
    }

    public Date getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Card (int userId, int bankAccountId, int number, float balance, String pinHash, String cvvHash, Date expireDate) {
        this.userId = userId;
        this.bankAccountId = bankAccountId;
        this.number = number;
        this.balance = balance;
        this.pinHash = pinHash;
        this.cvvHash = cvvHash;
        this.expireDate = expireDate;
    }

    public Card (int id, int userId, int bankAccountId, int number, float balance, String pinHash, String cvvHash, Date expireDate) {
        this.id = id;
        this.userId = userId;
        this.bankAccountId = bankAccountId;
        this.number = number;
        this.balance = balance;
        this.pinHash = pinHash;
        this.cvvHash = cvvHash;
        this.expireDate = expireDate;
    }

}
