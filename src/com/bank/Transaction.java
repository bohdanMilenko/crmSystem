package com.bank;

import java.time.LocalDateTime;

public class Transaction {

    private FinancialProduct.TRANSACTION_TYPE transaction_type;
    private double amount;
    private LocalDateTime dateTime;

    public Transaction(FinancialProduct.TRANSACTION_TYPE transaction_type, double amount) {
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    public FinancialProduct.TRANSACTION_TYPE getTransaction_type() {
        return transaction_type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
