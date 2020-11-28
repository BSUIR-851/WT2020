package com.devcolibri.servlet.objects;

public class BankAccount {
    private int id;
    private int userId;
    private float balance;
    private String number;

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

    public float getBalance() {
        return this.balance;
    }

    public void setEmail(float balance) {
        this.balance = balance;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BankAccount (int userId, float balance, String number) {
        this.userId = userId;
        this.balance = balance;
        this.number = number;
    }

    public BankAccount (int id, int userId, float balance, String number) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.number = number;
    }
}
