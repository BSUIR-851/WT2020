package com.devcolibri.servlet.objects;

public class RequestForUnblock {
    private int id;
    private int blockedBankAccountId;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlockedBankAccountId() {
        return this.blockedBankAccountId;
    }

    public void setBlockedBankAccountId(int blockedBankAccountId) {
        this.blockedBankAccountId = blockedBankAccountId;
    }


    public RequestForUnblock (int blockedBankAccountId) {
        this.blockedBankAccountId = blockedBankAccountId;
    }

    public RequestForUnblock (int id, int blockedBankAccountId) {
        this.id = id;
        this.blockedBankAccountId = blockedBankAccountId;
    }
}
