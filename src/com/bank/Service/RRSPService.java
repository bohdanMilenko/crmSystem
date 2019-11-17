package com.bank.Service;

import com.bank.Entities.ClientAccount;
import com.bank.Entities.RRSP;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class RRSPService  extends  FinancialProductService implements  Promotion{


    private ClientAccount clientAccount;
    private RRSP rrsp;

    public RRSPService(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
        this.rrsp = (RRSP) clientAccount.getTypeToFinancialProductMap().get(FinancialProductType.RRSP);
    }

    @Override
    public void depositMoneyToAccount(double amount) {
        double feeForDepositing;
        if(checkIfEligibleForPromotion()) {
            feeForDepositing = 0;
        }else {
            feeForDepositing = amount * RRSP.DEPOSIT_FEE_PERCENT;
        }
        if(rrsp.getRoomForContribution() > (amount - feeForDepositing)) {
            this.balance += amount- feeForDepositing;
            this.roomForContribution-=amount;
            transactionHistory.add(super.createTransaction(amount));
            System.out.println("You deposited: $" + amount + "\nYour balance is: $" + balance + ", and available room to contribute: $" + roomForContribution);
            System.out.println("The fee is: $" + feeForDepositing);
        }else {
            System.out.println("Your available room for contribution: $" + roomForContribution);
        }
    }

    @Override
    void withdrawMoneyFromAccount(double amount) {
        if(amount<balance){
            balance-=amount;
            transactionHistory.add(super.createTransaction(-amount));
            System.out.println("\nSuccessfully withdrew: $" + amount + "\nCurrent balance is: $" + balance);
        }else {
            System.out.println("\nNot enough funds! \nYou can withdraw only: $" + balance);
        }
    }

    @Override
    void printTransactionList() {
        System.out.println("Yours RRSP history:");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        transactionHistory.forEach(transaction ->
                System.out.println(formatter.format(transaction.getDateTime()) + " :" + transaction.getTransaction_type() + " $" + transaction.getAmount()));
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


}
