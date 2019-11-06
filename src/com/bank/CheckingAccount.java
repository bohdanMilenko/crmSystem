package com.bank;

import java.util.ArrayList;
import java.util.List;

public class CheckingAccount extends FinancialProduct {

    private double balance;
    private List<Transaction> checkingAccountHistory;

    public CheckingAccount(double balance) {
        this.balance = balance;
        this.checkingAccountHistory = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    @Override
    void increaseBalance(double incomingTransaction) {
        balance+=incomingTransaction;
        checkingAccountHistory.add(super.createTransaction(incomingTransaction));
    }

    @Override
    void decreaseBalance(double outgoingTransaction) {
        balance-=outgoingTransaction;
        checkingAccountHistory.add(super.createTransaction(outgoingTransaction));
    }

}
