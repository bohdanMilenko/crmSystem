package com.bank;

import java.util.HashMap;
import java.util.Map;


public class CreditCard extends FinancialProduct {

    private double balance;
    private  double creditLimit;
    private int overLimitCount;
    private boolean defaulted;
    private Map<TRANSACTION_TYPE,Double> creditCardTransactions = new HashMap<>();
    
     static final int LOWEST_THRESHOLD = 1000;
     static final int MIDDLE_THRESHOLD = 5000;
     static final int TOP_THRESHOLD = 10000;
    
    
    private static final double OVER_LIMIT_FEE = 29.99;

    public CreditCard(double creditLimit) {
        this.balance+= WELCOMING_BONUS;
        this.creditLimit = creditLimit* (-1);
        this.defaulted = false;
    }

    @Override
    void increaseBalance(double incomingTransaction) {
        balance+= incomingTransaction;
        updateTransactionList(TRANSACTION_TYPE.INCOME, incomingTransaction);
    }

    @Override
    void decreaseBalance(double outgoingTransaction) {
        if(creditLimit >= (balance - outgoingTransaction)){
            overLimitCount++;
            System.out.println("You used more funds than you credit line allows you!");
            updateTransactionList(TRANSACTION_TYPE.EXPENSE, OVER_LIMIT_FEE);
        }else {
            balance -= outgoingTransaction;
        }
        updateTransactionList(TRANSACTION_TYPE.EXPENSE, outgoingTransaction);
    }

    @Override
    void updateTransactionList(TRANSACTION_TYPE transaction_type, double transaction) {

        transactionList.put(transaction_type, transaction);
    }

    private void updateCreditCardTransactions(TRANSACTION_TYPE transaction_type, double transaction){
        creditCardTransactions.put(transaction_type,transaction);
    }

    public double getBalance() {
        return balance;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public int getOverLimitCount() {
        return overLimitCount;
    }

    public boolean isDefaulted() {
        return defaulted;
    }

    public Map<TRANSACTION_TYPE, Double> getCreditCardTransactions() {
        return creditCardTransactions;
    }
}
