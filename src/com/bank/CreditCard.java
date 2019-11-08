package com.bank;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;


public class CreditCard extends FinancialProduct {

    private double balance;
    private  double creditLimit;
    private int overLimitCount;
    private boolean defaulted;

    private List<Transaction> creditCardTransactions;

    static final int LOWEST_THRESHOLD = 1000;
    static final int MIDDLE_THRESHOLD = 5000;
    static final int TOP_THRESHOLD = 10000;
    private static final double OVER_LIMIT_FEE = -29.99;

    public CreditCard(double creditLimit) {
        this.balance+= WELCOMING_BONUS;
        this.creditLimit = creditLimit * (-1);
        this.defaulted = false;
        this.creditCardTransactions = new ArrayList<>();
    }

    @Override
    void depositMoneyToAccount(double incomingTransaction) {
        balance+= incomingTransaction;
        creditCardTransactions.add(super.createTransaction(incomingTransaction));
    }

    @Override
    void withdrawMoneyFromAccount(double outgoingTransaction) {
        if(creditLimit >= (balance - outgoingTransaction)){
            overLimitCount++;
            System.out.println("You used more funds than you credit line allows you!");
            creditCardTransactions.add(super.createTransaction(OVER_LIMIT_FEE));
        }

        balance -= outgoingTransaction;
        System.out.println("You withdrew $" + outgoingTransaction + " and you current balance is: $" + balance);
        creditCardTransactions.add(super.createTransaction(-outgoingTransaction));
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


    @Override
    void printTransactionList() {
        System.out.println("Your transaction list:");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        creditCardTransactions.forEach(transaction ->
                System.out.println(formatter.format(transaction.getDateTime()) + " :" + transaction.getTransaction_type() + " $" + transaction.getAmount()));
    }
}
