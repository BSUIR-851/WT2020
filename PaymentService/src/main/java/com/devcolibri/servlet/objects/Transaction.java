package com.devcolibri.servlet.objects;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int scardId;
    private int dcardId;
    private float amount;
    private Timestamp datetime;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScardId() {
        return this.scardId;
    }

    public void setScardId(int scardId) {
        this.scardId = scardId;
    }

    public int getDcardId() {
        return this.dcardId;
    }

    public void setDcardId(int dcardId) {
        this.dcardId = dcardId;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Timestamp getDatetime() {
        return this.datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public Transaction (int scardId, int dcardId, float amount, Timestamp datetime) {
        this.scardId = scardId;
        this.dcardId = dcardId;
        this.amount = amount;
        this.datetime = datetime;
    }

    public Transaction (int id, int scardId, int dcardId, float amount, Timestamp datetime) {
        this.id = id;
        this.scardId = scardId;
        this.dcardId = dcardId;
        this.amount = amount;
        this.datetime = datetime;
    }
}
