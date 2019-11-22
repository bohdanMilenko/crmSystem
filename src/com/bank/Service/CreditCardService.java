package com.bank.Service;

import com.bank.Entities.ClientAccount;
import com.bank.Entities.CreditCard;
import com.bank.Entities.Transaction;

public class CreditCardService extends FinancialProductService implements Promotionable {


    @Override
    public void depositMoneyToAccount(ClientAccount clientAccount, double incomingTransactionAmount) throws Exception {
        if (clientAccount.getTypeToFinancialProductMap().containsKey(FinancialProductType.CREDIT_CARD)) {
            CreditCard creditCard = (CreditCard) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.CREDIT_CARD);
            double balance = creditCard.getBalance();
            creditCard.setBalance(balance + incomingTransactionAmount);
            creditCard.addTransactionToTransactionHistory(super.createTransaction(incomingTransactionAmount));
        } else {
            throw new Exception("No Credit Line opened");
        }
    }

    @Override
    public void withdrawMoneyFromAccount(ClientAccount clientAccount, double outgoingTransactionAmount) throws Exception {
        if (clientAccount.getTypeToFinancialProductMap().containsKey(FinancialProductType.CREDIT_CARD)) {
            CreditCard creditCard = (CreditCard) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.CREDIT_CARD);
            double balance = creditCard.getBalance();
            if (creditCard.getCreditLimit() >= (balance - outgoingTransactionAmount)) {
                creditCard.increaseOverLimitCount();
                System.out.println("You used more funds than you credit line allows you!");
                creditCard.addTransactionToTransactionHistory(super.createTransaction(CreditCard.OVER_LIMIT_FEE));
            }
            creditCard.setBalance(balance - outgoingTransactionAmount);
            System.out.println("You withdrew $" + outgoingTransactionAmount + " and you current balance is: $" + balance);
            Transaction transaction = super.createTransaction(-outgoingTransactionAmount);
            creditCard.addTransactionToTransactionHistory(transaction);
        } else {
            throw new Exception("No Credit Line opened");
        }
    }

    @Override
    public void reviewBalance(ClientAccount clientAccount) throws Exception {
        if (clientAccount.getTypeToFinancialProductMap().containsKey(FinancialProductType.CREDIT_CARD)) {
            CreditCard creditCard = (CreditCard) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.CREDIT_CARD);
            System.out.println("Your credit account balance is: $ " + creditCard.getBalance());
        } else {
            throw new Exception("No Credit Line opened");
        }
    }

    @Override
    public void viewEligibilityTerms() {
        //Prints terms of the promotion to the console
    }

    @Override
    public boolean checkIfEligibleForPromotion(ClientAccount clientAccount) {

        return false;
    }

    @Override
    public void applyPromotion() {

    }

    @Override
    public void printTransactionHistory(ClientAccount clientAccount) throws Exception {
        if (clientAccount.getTypeToFinancialProductMap().containsKey(FinancialProductType.CREDIT_CARD)) {
            CreditCard creditCard = (CreditCard) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.CREDIT_CARD);
            super.printTransactionList(creditCard.getCreditCardTransactions());
        } else {
            throw new Exception("No Credit Line opened");
        }
    }
}
