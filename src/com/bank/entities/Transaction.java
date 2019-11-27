package com.bank.entities;

import java.time.LocalDateTime;

public class Transaction {

    private TRANSACTION_TYPE transaction_type;
    private final double amount;
    private final LocalDateTime dateTime;

    public enum TRANSACTION_TYPE{
        INCOME,
        EXPENSE
    }

    public Transaction( double amount) {
        //TODO THROW TRANSACTION IF = 0
        this.transaction_type = defineTransactionType(amount);
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    public TRANSACTION_TYPE getTransactionType() {
        return transaction_type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    private TRANSACTION_TYPE defineTransactionType(double amount){
        if(amount>0){
            return TRANSACTION_TYPE.INCOME;
        }else {
            return TRANSACTION_TYPE.EXPENSE;
        }
    }

    @Override
    public String toString() {
        if(amount>0 && this.transaction_type == TRANSACTION_TYPE.INCOME){
            return "Successfully deposited $" + amount;
        }else {
            return "Successfully withdrew $" + amount;
        }
    }
}
