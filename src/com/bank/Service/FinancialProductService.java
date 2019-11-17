package com.bank.Service;

import com.bank.Entities.Transaction;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public abstract class FinancialProductService {

    public enum FinancialProductType {
        CREDIT_CARD,
        CHECKING_ACCOUNT,
        RRSP;


        @Override
        public String toString() {
            switch (this){
                case CREDIT_CARD: return "Credit Card";
                case CHECKING_ACCOUNT: return "Checking account";
                case RRSP: return "RRSP";
                default: throw new IllegalArgumentException();
            }
        }
    }



    public static final double CHECKING_ACCOUNT_YEARLY_FEE = 99.99;

    public abstract void depositMoneyToAccount(double amount);
    public abstract void withdrawMoneyFromAccount(double amount);
    public abstract void reviewBalance();
    public abstract void printTransactionHistory();

    public  void printTransactionList(List<Transaction> transactionHistory){
        System.out.println("Your transaction list:");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        transactionHistory.forEach(transaction ->
                System.out.println(formatter.format(transaction.getDateTime()) + " :" + transaction.getTransaction_type() + " $" + transaction.getAmount()));
    }




    Transaction createTransaction(double amount){
        Transaction transaction = new Transaction(amount);
        System.out.println(transaction.toString());
        return transaction;
    }





}
