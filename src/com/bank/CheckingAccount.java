package com.bank;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

    @Override
    void printTransactionList() {
        System.out.println("Your transaction list:");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        checkingAccountHistory.forEach(transaction ->
                System.out.println(formatter.format(transaction.getDateTime()) + " :" + transaction.getTransaction_type() + " $" + transaction.getAmount()));
    }

    double getAmountSpentLastMonth(){
        LocalDate monthBeforeNow = LocalDate.now().minusMonths(1);
        double amountSpentLastMonth = 0.0;
        for(Transaction transaction : checkingAccountHistory){
            if(transaction.getDateTime().isAfter(monthBeforeNow.atStartOfDay())){
                amountSpentLastMonth+=transaction.getAmount();
            }
    }
        return amountSpentLastMonth;
    }
}
