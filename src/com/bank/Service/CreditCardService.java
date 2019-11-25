package com.bank.Service;

import com.bank.Entities.CheckingAccount;
import com.bank.Entities.ClientAccount;
import com.bank.Entities.CreditCard;
import com.bank.Entities.Transaction;

public class CreditCardService extends FinancialProductService implements Promotionable {


    @Override
    public void depositMoneyToAccount(ClientAccount clientAccount, double incomingTransactionAmount) throws NullPointerException {
        CreditCard creditCard = checkIfFinProductExists(clientAccount);
        double balance = creditCard.getBalance();
        creditCard.setBalance(balance + incomingTransactionAmount);
        creditCard.addTransactionToTransactionHistory(super.createTransaction(incomingTransactionAmount));
    }

    @Override
    public void withdrawMoneyFromAccount(ClientAccount clientAccount, double outgoingTransactionAmount) throws NullPointerException {
        CreditCard creditCard = checkIfFinProductExists(clientAccount);
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
    }

    @Override
    public void reviewBalance(ClientAccount clientAccount) throws NullPointerException {
        CreditCard creditCard = checkIfFinProductExists(clientAccount);
        System.out.println("Your credit account balance is: $ " + creditCard.getBalance());
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
    public void applyPromotion(ClientAccount clientAccount) {

    }

    @Override
    public void printTransactionHistory(ClientAccount clientAccount) throws NullPointerException {
        CreditCard creditCard = checkIfFinProductExists(clientAccount);
        super.printTransactionList(creditCard.getCreditCardTransactions());
    }

    private CreditCard checkIfFinProductExists(ClientAccount clientAccount) throws NullPointerException {
        if (clientAccount.getTypeToFinancialProductMap().containsKey(FinancialProductType.CREDIT_CARD)) {
            return (CreditCard) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.CREDIT_CARD);
        } else {
            throw new NullPointerException(FinancialProductType.CREDIT_CARD.toString() + " does not exists");
        }
    }
}

