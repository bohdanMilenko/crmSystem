package com.bank.Service;

import com.bank.Entities.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class CheckingAccountService extends FinancialProductService implements Promotion {


    @Override
    void depositMoneyToAccount(double incomingTransaction) {
        balance+=incomingTransaction;
        checkingAccountHistory.add(super.createTransaction(incomingTransaction));
    }

    @Override
    void withdrawMoneyFromAccount(double outgoingTransaction) {
        balance-=outgoingTransaction;
        checkingAccountHistory.add(super.createTransaction(-outgoingTransaction));
    }

    @Override
    void printTransactionList() {
        System.out.println("Your transaction list:");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        checkingAccountHistory.forEach(transaction ->
                System.out.println(formatter.format(transaction.getDateTime()) + " :" + transaction.getTransaction_type() + " $" + transaction.getAmount()));
    }

    @Override
    public void viewEligibilityTerms() {
        //Prints terms of the promotion to the console
    }

    @Override
    public boolean checkPromotionEligibility() {
        double amountSpentLastMonth = getAmountSpentLastMonth();
        System.out.println("Amount spent last month is: $" + amountSpentLastMonth);
        if(amountSpentLastMonth>5000){
            this.eligibleForPromotion = true;
            System.out.println("You are eligible for promotion");
        }else {
            this.eligibleForPromotion = false;
            System.out.println("You have to spend $" + (5000-amountSpentLastMonth) + " to be eligible.");
        }
        return false;
    }

    @Override
    public void applyPromotion() {

    }

    private double getAmountSpentLastMonth(){
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
