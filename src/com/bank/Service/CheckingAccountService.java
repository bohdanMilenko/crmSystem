package com.bank.Service;

import com.bank.Entities.CheckingAccount;
import com.bank.Entities.ClientAccount;
import com.bank.Entities.Transaction;

import java.time.LocalDate;
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
    public List<Transaction> printTransactionList(List<Transaction> transactionHistory) {
        return super.printTransactionList(transactionHistory);
    }

    @Override
    public void reviewBalance() {
        System.out.println("Your checking account balance is: $ " + checkingAccount.getBalance());
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
