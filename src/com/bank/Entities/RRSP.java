package com.bank.Entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RRSP extends  FinancialProduct {

    private FinancialClientsInfo financialClientsInfo;
    private double roomForContribution;

    //TODO Rethink the idea of maximum room!!
    private List<YearlyRoom> maximumContributionRoomYearly;
    private double balance;
    private List<Transaction> transactionHistory;
    public static final double DEPOSIT_FEE_PERCENT = 0.01;

    public RRSP(FinancialClientsInfo financialClientsInfo) {
        updateYearlyRooms();
        this.financialClientsInfo = financialClientsInfo;
        this.roomForContribution = calculateRoom();
        this.transactionHistory = new ArrayList<>();
    }

    private void updateYearlyRooms(){
        this.maximumContributionRoomYearly = new ArrayList<>();
        maximumContributionRoomYearly.add(new YearlyRoom(2017, 26010.00, 0.18));
        maximumContributionRoomYearly.add(new YearlyRoom(2018, 26230.00, 0.18));
        maximumContributionRoomYearly.add(new YearlyRoom(2019, 26500.00, 0.18));
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

    public void setRoomForContribution(double roomForContribution) {
        this.roomForContribution = roomForContribution;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addTransactionToTransactionHistory(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
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
