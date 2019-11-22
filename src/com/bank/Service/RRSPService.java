package com.bank.Service;

import com.bank.Entities.ClientAccount;
import com.bank.Entities.RRSP;

public class RRSPService extends FinancialProductService implements Promotionable {


    @Override
    public void depositMoneyToAccount(ClientAccount clientAccount, double incomingTransactionAmount) throws Exception {
        RRSP rrsp = checkIfFinProductExists(clientAccount);
        double feeForDepositing;
        double balance = rrsp.getBalance();
        if (checkIfEligibleForPromotion(clientAccount)) {
            feeForDepositing = 0;
        } else {
            feeForDepositing = incomingTransactionAmount * RRSP.DEPOSIT_FEE_PERCENT;
        }
        if (rrsp.getRoomForContribution() > (incomingTransactionAmount - feeForDepositing)) {
            rrsp.setBalance(balance + incomingTransactionAmount - feeForDepositing);
            rrsp.setRoomForContribution(rrsp.getRoomForContribution() - incomingTransactionAmount + feeForDepositing);
            rrsp.addTransactionToTransactionHistory(super.createTransaction(incomingTransactionAmount));
            System.out.println("You deposited: $" + incomingTransactionAmount + "\nYour balance is: $" + balance + ", and available room to contribute: $" + rrsp.getRoomForContribution());
            System.out.println("The fee is: $" + feeForDepositing);
        } else {
            System.out.println("Your available room for contribution: $" + rrsp.getRoomForContribution());
        }
    }


    @Override
    public void withdrawMoneyFromAccount(ClientAccount clientAccount, double outgoingTransactionAmount) throws Exception {
        RRSP rrsp = checkIfFinProductExists(clientAccount);
        double balance = rrsp.getBalance();
        if (outgoingTransactionAmount < balance) {
            rrsp.setBalance(balance - outgoingTransactionAmount);
            rrsp.addTransactionToTransactionHistory(super.createTransaction(-outgoingTransactionAmount));
            System.out.println("\nSuccessfully withdrew: $" + outgoingTransactionAmount + "\nCurrent balance is: $" + balance);
        } else {
            System.out.println("\nNot enough funds! \nYou can withdraw only: $" + balance);
        }
    }

    @Override
    public void printTransactionHistory(ClientAccount clientAccount) throws Exception {
        RRSP rrsp = checkIfFinProductExists(clientAccount);
        super.printTransactionList(rrsp.getTransactionHistory());
    }

    @Override
    public void viewEligibilityTerms() {

        //Will print terms of the promotion
        //If Person deposits more than 10,000 CAD - he receives

    }

    @Override
    public boolean checkIfEligibleForPromotion(ClientAccount clientAccount) throws Exception {
        RRSP rrsp = checkIfFinProductExists(clientAccount);
        //Check sum of deposits this year. If deposits > 10,000 CAD set fee to 0%


        return true;
    }

    @Override
    public void applyPromotion() {

    }

    @Override
    public void reviewBalance(ClientAccount clientAccount) throws Exception {
        RRSP rrsp = checkIfFinProductExists(clientAccount);
        System.out.println("Your RRSP account balance is: $ " + rrsp.getBalance());
    }

    private RRSP checkIfFinProductExists(ClientAccount clientAccount) throws Exception {
        if (clientAccount.getTypeToFinancialProductMap().containsKey(FinancialProductType.RRSP)) {
            return (RRSP) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.RRSP);
        } else {
            throw new Exception("RRSP does not exists");
        }
    }
}
