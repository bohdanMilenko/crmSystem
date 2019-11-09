package com.bank;

import java.time.LocalDate;
import java.util.Map;

public class RRSP extends FinancialProduct implements Promotion{

    private FinancialClientsInfo financialClientsInfo;
    private double roomForContribution;
    private Map<LocalDate,Double > maximumContributionRoomYearly;
    private double balance;

    public RRSP(FinancialClientsInfo financialClientsInfo) {
        this.financialClientsInfo = financialClientsInfo;
        this.roomForContribution = calculateRoom();
    }

    private double calculateRoom(){

    }

    @Override
    void depositMoneyToAccount(double amount) {
       if(roomForContribution>amount) {
           balance += amount;
       }else {
           System.out.println("Your available room for contribution: $" + roomForContribution);
       }

    }

    @Override
    void withdrawMoneyFromAccount(double amount) {
        double netWithdrawalAmount;
        if(amount<balance){
            balance-=amount;
            netWithdrawalAmount = redeemTaxesFromGrossAmount(amount);
        }else {
            System.out.println("You can withdraw only: $" + balance);
        }
    }

    private double redeemTaxesFromGrossAmount(double amount){
        return amount*(1-0.18);
    }

    @Override
    void printTransactionList() {

    }

    @Override
    public void viewEligibilityTerms() {

    }

    @Override
    public void checkPromotionEligibility() {

    }

    @Override
    public void applyPromotion() {

    }
}
