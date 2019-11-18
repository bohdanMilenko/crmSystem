package com.bank.Service;

import com.bank.Entities.ClientAccount;
import com.bank.Entities.RRSP;

import java.util.Objects;

public class RRSPService  extends  FinancialProductService implements Promotionable {


    private ClientAccount clientAccount;
    private RRSP rrsp;

    public RRSPService(ClientAccount clientAccount) {
        this.clientAccount = Objects.requireNonNull(clientAccount, "Client account is null");
        this.rrsp = (RRSP) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.RRSP);
    }


    @Override
    public void depositMoneyToAccount(double incomingTransactionAmount) {
        double feeForDepositing;
        double balance = rrsp.getBalance();
        if(checkIfEligibleForPromotion()) {
            feeForDepositing = 0;
        }else {
            feeForDepositing = incomingTransactionAmount * RRSP.DEPOSIT_FEE_PERCENT;
        }
        if(rrsp.getRoomForContribution() > (incomingTransactionAmount - feeForDepositing)) {
            rrsp.setBalance(balance + incomingTransactionAmount- feeForDepositing);
            rrsp.setRoomForContribution(rrsp.getRoomForContribution() - incomingTransactionAmount + feeForDepositing);
            rrsp.addTransactionToTransactionHistory(super.createTransaction(incomingTransactionAmount));
            System.out.println("You deposited: $" + incomingTransactionAmount + "\nYour balance is: $" + balance + ", and available room to contribute: $" + rrsp.getRoomForContribution());
            System.out.println("The fee is: $" + feeForDepositing);
        }else {
            System.out.println("Your available room for contribution: $" + rrsp.getRoomForContribution());
        }
    }

    @Override
    public void withdrawMoneyFromAccount(double outgoingTransactionAmount) {
        double balance = rrsp.getBalance();
        if(outgoingTransactionAmount < balance){
            rrsp.setBalance(balance - outgoingTransactionAmount);
            rrsp.addTransactionToTransactionHistory(super.createTransaction(-outgoingTransactionAmount));
            System.out.println("\nSuccessfully withdrew: $" + outgoingTransactionAmount + "\nCurrent balance is: $" + balance);
        }else {
            System.out.println("\nNot enough funds! \nYou can withdraw only: $" + balance);
        }
    }

    @Override
    public void printTransactionHistory() {
        super.printTransactionList(rrsp.getTransactionHistory());
    }

    @Override
    public void viewEligibilityTerms() {

        //Will print terms of the promotion
        //If Person deposits more than 10,000 CAD - he receives

    }

    @Override
    public boolean checkIfEligibleForPromotion() {
        //Check sum of deposits this year. If deposits > 10,000 CAD set fee to 0%


        return true;
    }

    @Override
    public void applyPromotion() {

    }

    @Override
    public void reviewBalance() {
        System.out.println("Your RRSP account balance is: $ " + rrsp.getBalance());
    }
}
