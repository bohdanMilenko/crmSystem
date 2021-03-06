package com.bank.service;

import com.bank.entities.ClientAccount;
import com.bank.entities.RRSP;
import com.bank.entities.Transaction;

public class RRSPService extends FinancialProductService implements Promotionable {


    @Override
    public void depositMoneyToAccount(ClientAccount clientAccount, double incomingTransactionAmount) throws NullPointerException {
        RRSP rrsp = getFinancialProductIfExists(clientAccount);
        double feeForDepositing;
        double balance = rrsp.getBalance();
        if (isPromotionEligible(clientAccount)) {
            feeForDepositing = 0;
        } else {
            feeForDepositing = incomingTransactionAmount * RRSP.DEPOSIT_FEE_PERCENT;
        }
        double netSumToBeDeposited = incomingTransactionAmount - feeForDepositing;
        if (rrsp.getRoomForContribution() > (netSumToBeDeposited)) {
            rrsp.setBalance(balance + netSumToBeDeposited);
            rrsp.setRoomForContribution(rrsp.getRoomForContribution() - netSumToBeDeposited);
            Transaction transaction = super.createTransaction(incomingTransactionAmount);
            rrsp.addTransactionToTransactionHistory(transaction);
            transaction = super.createTransaction(-feeForDepositing);
            rrsp.addTransactionToTransactionHistory(transaction);
            System.out.println("You deposited: $" + incomingTransactionAmount + "\nYour balance is: $" + balance + ", and available room to contribute: $" + rrsp.getRoomForContribution());
            System.out.println("The fee is: $" + feeForDepositing);
        } else {
            System.out.println("Your available room for contribution: $" + rrsp.getRoomForContribution());
            throw new IllegalArgumentException();
        }
    }


    @Override
    public void withdrawMoneyFromAccount(ClientAccount clientAccount, double outgoingTransactionAmount) throws NullPointerException {
        RRSP rrsp = getFinancialProductIfExists(clientAccount);
        double balance = rrsp.getBalance();
        if (outgoingTransactionAmount < balance) {
            rrsp.setBalance(balance - outgoingTransactionAmount);
            rrsp.addTransactionToTransactionHistory(super.createTransaction(-outgoingTransactionAmount));
            System.out.println("\nSuccessfully withdrew: $" + outgoingTransactionAmount + "\nCurrent balance is: $" + balance);
        } else {
            System.out.println("\nNot enough funds! \nYou can withdraw only: $" + balance);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void printTransactionHistory(ClientAccount clientAccount) throws NullPointerException {
        RRSP rrsp = getFinancialProductIfExists(clientAccount);
        super.printTransactionList(rrsp.getTransactionHistory());
    }

    @Override
    public void viewEligibilityTerms() {

        //Will print terms of the promotion
        //If Person deposits more than 10,000 CAD - he receives

    }

    //TODO COMPLETE THIS METHOD
    @Override
    public boolean isPromotionEligible(ClientAccount clientAccount) throws NullPointerException {
        RRSP rrsp = getFinancialProductIfExists(clientAccount);

        return true;
    }

    @Override
    public void applyPromotion(ClientAccount clientAccount) {

    }

    @Override
    public void reviewBalance(ClientAccount clientAccount) throws NullPointerException {
        RRSP rrsp = getFinancialProductIfExists(clientAccount);
        System.out.println("Your RRSP account balance is: $ " + rrsp.getBalance());
    }

    //TODO Throw different exception! NPE is too vague
    private RRSP getFinancialProductIfExists(ClientAccount clientAccount) throws NullPointerException {
        if (clientAccount.getTypeToFinancialProductMap().containsKey(FinancialProductType.RRSP)) {
            return (RRSP) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.RRSP);
        } else {
            throw new NullPointerException(FinancialProductType.RRSP.toString() + " does not exists");
        }
    }

}
