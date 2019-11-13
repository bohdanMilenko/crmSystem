package com.bank;

import java.util.ArrayList;
import java.util.List;

public class RRSP extends FinancialProduct implements Promotion{

    private FinancialClientsInfo financialClientsInfo;
    private double roomForContribution;
    private List<YearlyRoom> maximumContributionRoomYearly;
    private double balance;

    public RRSP(FinancialClientsInfo financialClientsInfo) {
        updateYearlyRooms();
        this.financialClientsInfo = financialClientsInfo;
        this.roomForContribution = calculateRoom();
    }

    private double calculateRoom(){
        double availableRoomForContribution=0;
        double yearlySalary;
        int currentYear;
        for(YearlyRoom yearlyRoom : maximumContributionRoomYearly){
            currentYear = yearlyRoom.getYear();
            yearlySalary = financialClientsInfo.getSalaryHistory().get(currentYear);
            if(yearlySalary*yearlyRoom.getMaximumPercentageToContribute() < yearlyRoom.getMaximumAmountToContribute()){
                availableRoomForContribution+=yearlySalary*yearlyRoom.getMaximumPercentageToContribute();
            }else {
                availableRoomForContribution+=yearlyRoom.maximumAmountToContribute;
            }
            System.out.println("In " + currentYear + " you can contribute: " + availableRoomForContribution);
        }
        return availableRoomForContribution;
    }


    public double getRoomForContribution() {
        return roomForContribution;
    }

    public double getBalance() {
        return balance;
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

    private void updateYearlyRooms(){
        this.maximumContributionRoomYearly = new ArrayList<>();
        maximumContributionRoomYearly.add(new YearlyRoom(2017,26010.00,0.18));
        maximumContributionRoomYearly.add(new YearlyRoom(2018,26230.00,0.18));
        maximumContributionRoomYearly.add(new YearlyRoom(2019,26500.00,0.18));

    }

    class YearlyRoom{

        private int year;
        private double maximumAmountToContribute;
        private double maximumPercentageToContribute;

        public YearlyRoom(int year, double maximumAmountToContribute, double maximumPercentageToContribute) {
            this.year = year;
            this.maximumAmountToContribute = maximumAmountToContribute;
            this.maximumPercentageToContribute = maximumPercentageToContribute;
        }

        public int getYear() {
            return year;
        }

        public double getMaximumAmountToContribute() {
            return maximumAmountToContribute;
        }

        public double getMaximumPercentageToContribute() {
            return maximumPercentageToContribute;
        }
    }

}
