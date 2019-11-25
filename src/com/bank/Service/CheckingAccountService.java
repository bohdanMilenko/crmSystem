package com.bank.Service;

import com.bank.Entities.*;

import java.time.LocalDate;
import java.util.List;

import static com.bank.Entities.CheckingAccount.PROMOTION_ELIGIBLE_EXPENSES;

public class CheckingAccountService extends FinancialProductService implements Promotionable {


    @Override
    public void depositMoneyToAccount(ClientAccount clientAccount, double incomingTransactionAmount) throws NullPointerException{
        if (incomingTransactionAmount < 0) {
            throw new IllegalArgumentException("Deposited negative sum");
        }
        CheckingAccount checkingAccount = checkIfFinProductExists(clientAccount);
        double balance = checkingAccount.getBalance();
        checkingAccount.setBalance(balance + incomingTransactionAmount);
        Transaction transaction = super.createTransaction(incomingTransactionAmount);
        checkingAccount.addTransactionToTransactionHistory(transaction);
    }

    @Override
    public void withdrawMoneyFromAccount(ClientAccount clientAccount, double outgoingTransactionAmount) throws IllegalArgumentException {
        if (outgoingTransactionAmount < 0) {
            throw new IllegalArgumentException();
        }
        CheckingAccount checkingAccount = checkIfFinProductExists(clientAccount);
        double balance = checkingAccount.getBalance();
        double remainingBalance = balance - outgoingTransactionAmount;
        if (remainingBalance >= 0) {
            checkingAccount.setBalance(balance - outgoingTransactionAmount);
            Transaction transaction = super.createTransaction(-outgoingTransactionAmount);
            checkingAccount.addTransactionToTransactionHistory(transaction);
        } else {
            throw new IllegalArgumentException("Not enough funds! \nYour balance is: $" + checkingAccount.getBalance());
        }
    }

    @Override
    public void printTransactionHistory(ClientAccount clientAccount) throws NullPointerException {
        CheckingAccount checkingAccount = checkIfFinProductExists(clientAccount);
        List<Transaction> checkingAccountHistory = checkingAccount.getCheckingAccountHistory();
        super.printTransactionList(checkingAccountHistory);
    }

    @Override
    public void reviewBalance(ClientAccount clientAccount) {
        CheckingAccount checkingAccount = checkIfFinProductExists(clientAccount);
        System.out.println("Your checking account balance is: $ " + checkingAccount.getBalance());
    }

    @Override
    public void viewEligibilityTerms() {
    }

    @Override
    public boolean checkIfEligibleForPromotion(ClientAccount clientAccount) {
        CheckingAccount checkingAccount = checkIfFinProductExists(clientAccount);
        double amountSpentLastMonth = getAmountSpentLastMonth(checkingAccount);
        System.out.println("Amount spent last month is: $" + amountSpentLastMonth);
        if (amountSpentLastMonth <= PROMOTION_ELIGIBLE_EXPENSES) {
            System.out.println("You are eligible for promotion, please apply for it!");
            return true;
        } else {
            checkingAccount.setEligibleForPromotion(false);
            System.out.println("You have to spend $" + (PROMOTION_ELIGIBLE_EXPENSES - amountSpentLastMonth) + " to be eligible.");
            return false;
        }
    }

    @Override
    public void applyPromotion(ClientAccount clientAccount) {
        CheckingAccount checkingAccount = checkIfFinProductExists(clientAccount);
        if(!checkingAccount.isEligibleForPromotion() && checkIfEligibleForPromotion(clientAccount)){
            checkingAccount.setEligibleForPromotion(true);
            double amountSpentLastMonth = getAmountSpentLastMonth(checkingAccount);
            Transaction promotionBonus = super.createTransaction(amountSpentLastMonth * (-0.01));
            checkingAccount.addTransactionToTransactionHistory(promotionBonus);
            System.out.println("Your bonus is: $" +  promotionBonus);
        }else {
            throw new IllegalStateException("Not eligible client attempts to apply promotion!");
        }

    }

    private double getAmountSpentLastMonth(CheckingAccount checkingAccount) {
        LocalDate monthBeforeNow = LocalDate.now().minusMonths(1);
        List<Transaction> checkingAccountHistory = checkingAccount.getCheckingAccountHistory();
        double amountSpentLastMonth = 0.0;
        for (Transaction transaction : checkingAccountHistory) {
            if (transaction.getDateTime().isAfter(monthBeforeNow.atStartOfDay()) &&
                    (transaction.getTransaction_type() == Transaction.TRANSACTION_TYPE.EXPENSE)) {
                amountSpentLastMonth += transaction.getAmount();
            }
        }
        return amountSpentLastMonth;
    }

    private CheckingAccount checkIfFinProductExists(ClientAccount clientAccount) throws NullPointerException {
        if (clientAccount.getTypeToFinancialProductMap().containsKey(FinancialProductType.CHECKING_ACCOUNT)) {
            return (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.CHECKING_ACCOUNT);
        } else {
            throw new NullPointerException(FinancialProductType.CHECKING_ACCOUNT.toString() + " does not exists");
        }
    }
}
