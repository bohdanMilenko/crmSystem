package com.bank.Entities;

import java.time.LocalDateTime;

public class Transaction {

    private TRANSACTION_TYPE transaction_type;
    private double amount;
    private LocalDateTime dateTime;
    public enum TRANSACTION_TYPE{INCOME,EXPENSE}

    public Transaction( double amount) {
        this.transaction_type = defineTransactionType(amount);
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    public TRANSACTION_TYPE getTransaction_type() {
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
}
