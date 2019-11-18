package com.bank.Service;

import com.bank.Entities.ClientAccount;
import com.bank.Entities.CreditCard;

public class CreditCardService extends FinancialProductService implements Promotionable {

    private ClientAccount clientAccount;
    private CreditCard creditCard;

    public CreditCardService(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
        this.creditCard = (CreditCard) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.CREDIT_CARD);
    }


    @Override
    public void depositMoneyToAccount(double incomingTransactionAmount) {
        double balance = creditCard.getBalance();
        creditCard.setBalance(balance + incomingTransactionAmount);
        creditCard.addTransactionToTransactionHistory(super.createTransaction(incomingTransactionAmount));
    }

    @Override
    public void withdrawMoneyFromAccount(double outgoingTransactionAmount) {
        double balance = creditCard.getBalance();
        if (creditCard.getCreditLimit() >= (balance - outgoingTransactionAmount)) {
            creditCard.increaseOverLimitCount();
            System.out.println("You used more funds than you credit line allows you!");
            creditCard.addTransactionToTransactionHistory(super.createTransaction(CreditCard.OVER_LIMIT_FEE));
        }

        creditCard.setBalance(balance - outgoingTransactionAmount);
        System.out.println("You withdrew $" + outgoingTransactionAmount + " and you current balance is: $" + balance);
        creditCard.addTransactionToTransactionHistory(super.createTransaction(-outgoingTransactionAmount));
    }

    @Override
    public void reviewBalance() {
        System.out.println("Your credit account balance is: $ " + creditCard.getBalance());
    }

    @Override
    public void viewEligibilityTerms() {
        //Prints terms of the promotion to the console
    }

    @Override
    public boolean checkIfEligibleForPromotion() {

        return false;
    }

    @Override
    public void applyPromotion() {

    }

    @Override
    public void printTransactionHistory() {
        super.printTransactionList(creditCard.getCreditCardTransactions());
    }
}
