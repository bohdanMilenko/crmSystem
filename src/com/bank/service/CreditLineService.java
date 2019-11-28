package com.bank.service;

import com.bank.entities.ClientAccount;
import com.bank.entities.CreditLine;
import com.bank.entities.Transaction;

public class CreditLineService extends FinancialProductService implements Promotionable {

    public static final int LOWEST_THRESHOLD = 1000;
    static final int MIDDLE_THRESHOLD = 5000;
    static final int TOP_THRESHOLD = 10000;
    private static final double OVER_LIMIT_FEE = -29.99;

    @Override
    public void depositMoneyToAccount(ClientAccount clientAccount, double incomingTransactionAmount) {
        CreditLine creditLine = getFinancialProductIfExists(clientAccount);
        double balance = creditLine.getBalance();
        creditLine.setBalance(balance + incomingTransactionAmount);
        creditLine.addTransactionToTransactionHistory(super.createTransaction(incomingTransactionAmount));
    }

    @Override
    public void withdrawMoneyFromAccount(ClientAccount clientAccount, double outgoingTransactionAmount)  {
        CreditLine creditLine = getFinancialProductIfExists(clientAccount);
        double balance = creditLine.getBalance();
        if (creditLine.getCreditLimit() >= (balance - outgoingTransactionAmount)) {
            creditLine.increaseOverLimitCount();
            System.out.println("You used more funds than you credit line allows you!");
            creditLine.addTransactionToTransactionHistory(super.createTransaction(OVER_LIMIT_FEE));
        }
        creditLine.setBalance(balance - outgoingTransactionAmount);
        System.out.println("You withdrew $" + outgoingTransactionAmount + " and you current balance is: $" + balance);
        Transaction transaction = super.createTransaction(-outgoingTransactionAmount);
        creditLine.addTransactionToTransactionHistory(transaction);
    }

    @Override
    public void reviewBalance(ClientAccount clientAccount) {
        CreditLine creditLine = getFinancialProductIfExists(clientAccount);
        System.out.println("Your credit account balance is: $ " + creditLine.getBalance());
    }

    @Override
    public void viewEligibilityTerms() {
        //Prints terms of the promotion to the console
    }

    @Override
    public boolean isPromotionEligible(ClientAccount clientAccount) {

        return false;
    }

    @Override
    public void applyPromotion(ClientAccount clientAccount) {

    }

    @Override
    public void printTransactionHistory(ClientAccount clientAccount)  {
        CreditLine creditLine = getFinancialProductIfExists(clientAccount);
        super.printTransactionList(creditLine.getCreditCardTransactions());
    }

    private CreditLine getFinancialProductIfExists(ClientAccount clientAccount) {
        if (clientAccount.getTypeToFinancialProductMap().containsKey(FinancialProductType.CREDIT_LINE)) {
            return (CreditLine) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.CREDIT_LINE);
        } else {
            throw new NullPointerException(FinancialProductType.CREDIT_LINE.toString() + " does not exists");
        }
    }
}

