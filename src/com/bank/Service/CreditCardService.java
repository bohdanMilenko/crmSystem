package com.bank.Service;

import com.bank.Entities.ClientAccount;
import com.bank.Entities.CreditCard;
import com.bank.Entities.Transaction;

import java.util.List;

public class CreditCardService extends FinancialProductService implements Promotion {

    private ClientAccount clientAccount;
    private CreditCard creditCard;

    public CreditCardService(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
        creditCard = (CreditCard) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.CREDIT_CARD);
    }

    @Override
    public void depositMoneyToAccount(double incomingTransaction) {
        double balance = creditCard.getBalance();
        creditCard.setBalance(balance + incomingTransaction);
        creditCard.addTransactionToTransactionHistory(super.createTransaction(incomingTransaction));
    }

    @Override
    public void withdrawMoneyFromAccount(double outgoingTransaction) {
        double balance = creditCard.getBalance();
        if(creditCard.getCreditLimit() >= (balance - outgoingTransaction)){
            creditCard.increaseOverLimitCount();
            System.out.println("You used more funds than you credit line allows you!");
            creditCard.addTransactionToTransactionHistory(super.createTransaction(CreditCard.OVER_LIMIT_FEE));
        }

        creditCard.setBalance(balance- outgoingTransaction);
        System.out.println("You withdrew $" + outgoingTransaction + " and you current balance is: $" + balance);
        creditCard.addTransactionToTransactionHistory(super.createTransaction(-outgoingTransaction));
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
    public boolean checkPromotionEligibility() {

        return false;
    }

    @Override
    public void applyPromotion() {

    }

    @Override
    public List<Transaction> printTransactionList(List<Transaction> transactionHistory) {
        return super.printTransactionList(transactionHistory);
    }
}
