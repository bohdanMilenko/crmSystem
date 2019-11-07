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
    void depositMoneyToAccount(double incomingTransaction) {
        balance+=incomingTransaction;
        checkingAccountHistory.add(super.createTransaction(incomingTransaction));
    }

    @Override
    void withdrawMoneyFromAccount(double outgoingTransaction) {
        balance-=outgoingTransaction;
        checkingAccountHistory.add(super.createTransaction(outgoingTransaction));
    }

    public List<Transaction> getCheckingAccountHistory() {
        return checkingAccountHistory;
    }
}
