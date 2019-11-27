package com.bank.service;

import com.bank.entities.ClientAccount;
import com.bank.entities.Transaction;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public abstract class FinancialProductService {

    public enum FinancialProductType {
        CREDIT_LINE,
        CHECKING_ACCOUNT,
        RRSP;


        @Override
        public String toString() {
            switch (this) {
                case CREDIT_LINE:
                    return "Credit Card";
                case CHECKING_ACCOUNT:
                    return "Checking account";
                case RRSP:
                    return "RRSP";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }


    public static final double CHECKING_ACCOUNT_YEARLY_FEE = 99.99;

    public abstract void depositMoneyToAccount(ClientAccount clientAccount, double amount) throws NullPointerException;

    public abstract void withdrawMoneyFromAccount(ClientAccount clientAccount, double amount) throws NullPointerException;

    public abstract void reviewBalance(ClientAccount clientAccount) throws NullPointerException;

    public abstract void printTransactionHistory(ClientAccount clientAccount) throws NullPointerException;

    void printTransactionList(List<Transaction> transactionHistory) throws NullPointerException{
        System.out.println("Your transaction list:");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        transactionHistory.forEach(transaction ->
                System.out.println(formatter.format(transaction.getDateTime()) + " :" + transaction.getTransactionType() + " $" + transaction.getAmount()));
    }


    Transaction createTransaction(double amount) {
        Transaction transaction = new Transaction(amount);
        System.out.println(transaction.toString());
        return transaction;
    }


}
