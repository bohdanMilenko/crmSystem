package com.bank.Service;

import com.bank.Entities.CheckingAccount;
import com.bank.Entities.ClientAccount;
import com.bank.Entities.Transaction;

import java.time.LocalDate;
import java.util.List;

public class CheckingAccountService extends FinancialProductService implements Promotionable {


    @Override
    public void depositMoneyToAccount(ClientAccount clientAccountService, double incomingTransactionAmount) {
        if (incomingTransactionAmount < 0) {
            throw new IllegalArgumentException("Deposited negative sum");
        }
        CheckingAccount checkingAccount = (CheckingAccount) clientAccountService.getTypeToFinancialProductMap().get(FinancialProductType.CHECKING_ACCOUNT);
        double balance = checkingAccount.getBalance();
        checkingAccount.setBalance(balance + incomingTransactionAmount);
        Transaction transaction = super.createTransaction(incomingTransactionAmount);
        checkingAccount.addTransactionToTransactionHistory(transaction);
    }

    @Override
    public void withdrawMoneyFromAccount(ClientAccount clientAccountService, double outgoingTransactionAmount) throws Exception {
        CheckingAccount checkingAccount = (CheckingAccount) clientAccountService.getTypeToFinancialProductMap().get(FinancialProductType.CHECKING_ACCOUNT);
        if (outgoingTransactionAmount < 0) {
            throw new IllegalArgumentException();
        }
        double balance = checkingAccount.getBalance();
        double remainingBalance = balance - outgoingTransactionAmount;
        if (remainingBalance >= 0) {
            checkingAccount.setBalance(balance - outgoingTransactionAmount);
            Transaction transaction = super.createTransaction(-outgoingTransactionAmount);
            checkingAccount.addTransactionToTransactionHistory(transaction);
        } else {
            throw new Exception("Not enough funds! \nYour balance is: $" + checkingAccount.getBalance());
        }
    }

    @Override
    public void printTransactionHistory(ClientAccount clientAccountService) {
        CheckingAccount checkingAccount = (CheckingAccount) clientAccountService.getTypeToFinancialProductMap().get(FinancialProductType.CHECKING_ACCOUNT);
        List<Transaction> checkingAccountHistory = checkingAccount.getCheckingAccountHistory();
        super.printTransactionList(checkingAccountHistory);
    }

    @Override
    public void reviewBalance(ClientAccount clientAccountService) {
        CheckingAccount checkingAccount = (CheckingAccount) clientAccountService.getTypeToFinancialProductMap().get(FinancialProductType.CHECKING_ACCOUNT);
        System.out.println("Your checking account balance is: $ " + checkingAccount.getBalance());
    }

    @Override
    public void viewEligibilityTerms() {
    }

    @Override
    public boolean checkIfEligibleForPromotion(ClientAccount clientAccountService) {
        CheckingAccount checkingAccount = (CheckingAccount) clientAccountService.getTypeToFinancialProductMap().get(FinancialProductType.CHECKING_ACCOUNT);
        double amountSpentLastMonth = getAmountSpentLastMonth(checkingAccount);
        System.out.println("Amount spent last month is: $" + amountSpentLastMonth);
        if (amountSpentLastMonth < -5000) {
            checkingAccount.setEligibleForPromotion(true);
            System.out.println("You are eligible for promotion");
            return true;
        } else {
            checkingAccount.setEligibleForPromotion(false);
            System.out.println("You have to spend $" + (5000 - amountSpentLastMonth) + " to be eligible.");
            return false;
        }

    }

    @Override
    public void applyPromotion() {

    }

    private double getAmountSpentLastMonth(CheckingAccount checkingAccount) {
        LocalDate monthBeforeNow = LocalDate.now().minusMonths(1);
        List<Transaction> checkingAccountHistory = checkingAccount.getCheckingAccountHistory();
        double amountSpentLastMonth = 0.0;
        for (Transaction transaction : checkingAccountHistory) {
            if (transaction.getDateTime().isAfter(monthBeforeNow.atStartOfDay())) {
                amountSpentLastMonth += transaction.getAmount();
            }
        }
        return amountSpentLastMonth;
    }
}
