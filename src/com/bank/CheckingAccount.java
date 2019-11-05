package com.bank;

public class CheckingAccount extends FinancialProduct {

    private double balance;

    public CheckingAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    void increaseBalance(double incomingTransaction) {
        balance+=incomingTransaction;

    }

    @Override
    void decreaseBalance(double outgoingTransaction) {

    }

    @Override
    void updateTransactionList(TRANSACTION_TYPE transaction_type, double transaction) {

    }
}
