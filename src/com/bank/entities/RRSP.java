package com.bank.entities;

import java.util.ArrayList;
import java.util.List;

public class RRSP extends FinancialProduct {

    private ClientAccount clientAccount;
    private FinancialClientsInfo financialClientsInfo;
    private double roomForContribution;
    private double balance;

    //TODO Rethink the idea of maximum room!!
    private List<YearlyRoom> maximumContributionRoomYearly= new ArrayList<>();
    private List<Transaction> transactionHistory = new ArrayList<>();
    public static final double DEPOSIT_FEE_PERCENT = 0.01;

    public RRSP(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
    }

    {
        updateYearlyRooms();
        financialClientsInfo = clientAccount.getFinancialClientsInfo();
        roomForContribution = calculateRoom();
    }

    private void updateYearlyRooms() {
        maximumContributionRoomYearly.add(new YearlyRoom(2017, 26010.00, 0.18));
        maximumContributionRoomYearly.add(new YearlyRoom(2018, 26230.00, 0.18));
        maximumContributionRoomYearly.add(new YearlyRoom(2019, 26500.00, 0.18));
    }


    //TODO HERE IS A BUG! NEEDS A FIX
    private double calculateRoom() {
        double availableRoomForContribution = 0;
        double yearlySalary;
        int currentYear;
        System.out.println(maximumContributionRoomYearly.size());
        for (YearlyRoom yearlyRoom : maximumContributionRoomYearly) {
            currentYear = yearlyRoom.getYear();
            yearlySalary = financialClientsInfo.getSalaryHistory().get(currentYear);
            if (yearlySalary * yearlyRoom.getMaximumPercentageToContribute() < yearlyRoom.getMaximumAmountToContribute()) {
                availableRoomForContribution += yearlySalary * yearlyRoom.getMaximumPercentageToContribute();
            } else {
                availableRoomForContribution += yearlyRoom.maximumAmountToContribute;
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
        return new ArrayList<>(transactionHistory);
    }


    private class YearlyRoom {

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
