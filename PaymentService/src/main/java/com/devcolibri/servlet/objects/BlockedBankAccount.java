package com.devcolibri.servlet.objects;

public class BlockedBankAccount {
    private int id;
    private int bankAccountId;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBankAccountId() {
        return this.bankAccountId;
    }

    public void setBankAccountId(int bankAccountId) {
        this.bankAccountId = bankAccountId;
    }


    public BlockedBankAccount (int bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public BlockedBankAccount (int id, int bankAccountId) {
        this.id = id;
        this.bankAccountId = bankAccountId;
    }
}
