package com.bank.Service;

import com.bank.Entities.CheckingAccount;
import com.bank.Entities.ClientAccount;
import com.bank.Entities.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class CheckingAccountService extends FinancialProductService implements Promotion {

    private ClientAccount clientAccount;
    private CheckingAccount checkingAccount;


    public CheckingAccountService(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
        checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.CHECKING_ACCOUNT);
    }

    @Override
    public void depositMoneyToAccount(double incomingTransaction) {
        double balance = checkingAccount.getBalance();
        checkingAccount.setBalance( balance+incomingTransaction);
        checkingAccount.addTransactionToTransactionHistory(super.createTransaction(incomingTransaction));
    }

    @Override
    public void withdrawMoneyFromAccount(double outgoingTransaction) {
        double balance = checkingAccount.getBalance();
        checkingAccount.setBalance( balance-outgoingTransaction);
        checkingAccount.addTransactionToTransactionHistory(super.createTransaction(-outgoingTransaction));
    }

    @Override
    public void printTransactionList() {
        System.out.println("Your transaction list:");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        List<Transaction> checkingAccountHistory = checkingAccount.getCheckingAccountHistory();
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
            checkingAccount.setEligibleForPromotion(true);
            System.out.println("You are eligible for promotion");
        }else {
            checkingAccount.setEligibleForPromotion(false);
            System.out.println("You have to spend $" + (5000-amountSpentLastMonth) + " to be eligible.");
        }
        return false;
    }

    @Override
    public void applyPromotion() {

    }

    private double getAmountSpentLastMonth(){
        LocalDate monthBeforeNow = LocalDate.now().minusMonths(1);
        List<Transaction> checkingAccountHistory = checkingAccount.getCheckingAccountHistory();
        double amountSpentLastMonth = 0.0;
        for(Transaction transaction : checkingAccountHistory){
            if(transaction.getDateTime().isAfter(monthBeforeNow.atStartOfDay())){
                amountSpentLastMonth+=transaction.getAmount();
            }
        }
        return amountSpentLastMonth;
    }
}
