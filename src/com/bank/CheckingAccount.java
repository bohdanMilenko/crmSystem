package com.bank;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CheckingAccount extends FinancialProduct {

    private double balance;
    private Map<LocalDate,Transaction> checkingAccountHistory;

    public CheckingAccount(double balance) {
        this.balance = balance;
        this.checkingAccountHistory = new HashMap<>();
    }

    public double getBalance() {
        return balance;
    }

    @Override
    void depositMoneyToAccount(double incomingTransaction) {
        balance+=incomingTransaction;
        checkingAccountHistory.put(LocalDate.now() ,super.createTransaction(incomingTransaction));
    }

    @Override
    void withdrawMoneyFromAccount(double outgoingTransaction) {
        balance-=outgoingTransaction;
        checkingAccountHistory.put( LocalDate.now() ,super.createTransaction(outgoingTransaction));
    }

    public Map<LocalDate,Transaction> getCheckingAccountHistory() {
        return checkingAccountHistory;
    }
}
