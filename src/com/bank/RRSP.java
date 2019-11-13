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
           this.balance += amount;
           this.roomForContribution-=amount;
           System.out.println("You deposited: $" + amount + "\nYour balance is: $" + balance + ", and avaiable room to contribute: $" + roomForContribution);
       }else {
           System.out.println("Your available room for contribution: $" + roomForContribution);
       }
    }

    @Override
    void withdrawMoneyFromAccount(double amount) {
        if(amount<balance){
            balance-=amount;
            System.out.println("\nSuccessfully withdrew: $" + amount + "\nCurrent balance is: $" + balance);
        }else {
            System.out.println("\nNot enough funds! \nYou can withdraw only: $" + balance);
        }
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
        maximumContributionRoomYearly.add(new YearlyRoom(2017, 26010.00, 0.18));
        maximumContributionRoomYearly.add(new YearlyRoom(2018, 26230.00, 0.18));
        maximumContributionRoomYearly.add(new YearlyRoom(2019, 26500.00, 0.18));

    }

    private static class YearlyRoom{

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
