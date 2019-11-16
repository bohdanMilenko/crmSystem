package com.bank.Entities;

public class RRSP extends  FinancialProduct {

//    private FinancialClientsInfo financialClientsInfo;
//    private double roomForContribution;
//    private List<YearlyRoom> maximumContributionRoomYearly;
//    private double balance;
//    private List<Transaction> transactionHistory;
//    private double depositFeePercent = 0.01;
//
//    public RRSP(FinancialClientsInfo financialClientsInfo) {
//        updateYearlyRooms();
//        this.financialClientsInfo = financialClientsInfo;
//        this.roomForContribution = calculateRoom();
//        this.transactionHistory = new ArrayList<>();
//    }
//
//    private void updateYearlyRooms(){
//        this.maximumContributionRoomYearly = new ArrayList<>();
//        maximumContributionRoomYearly.add(new YearlyRoom(2017, 26010.00, 0.18));
//        maximumContributionRoomYearly.add(new YearlyRoom(2018, 26230.00, 0.18));
//        maximumContributionRoomYearly.add(new YearlyRoom(2019, 26500.00, 0.18));
//    }
//
//    private double calculateRoom(){
//        double availableRoomForContribution=0;
//        double yearlySalary;
//        int currentYear;
//        for(YearlyRoom yearlyRoom : maximumContributionRoomYearly){
//            currentYear = yearlyRoom.getYear();
//            yearlySalary = financialClientsInfo.getSalaryHistory().get(currentYear);
//            if(yearlySalary*yearlyRoom.getMaximumPercentageToContribute() < yearlyRoom.getMaximumAmountToContribute()){
//                availableRoomForContribution+=yearlySalary*yearlyRoom.getMaximumPercentageToContribute();
//            }else {
//                availableRoomForContribution+=yearlyRoom.maximumAmountToContribute;
//            }
//            System.out.println("In " + currentYear + " you can contribute: " + availableRoomForContribution);
//        }
//        return availableRoomForContribution;
//    }
//
//
//    @Override
//    void depositMoneyToAccount(double amount) {
//        checkPromotionEligibility();
//        double feeForDepositing = amount * depositFeePercent;
//       if(roomForContribution> (amount - feeForDepositing)) {
//           this.balance += amount- feeForDepositing;
//           this.roomForContribution-=amount;
//           transactionHistory.add(super.createTransaction(amount));
//           System.out.println("You deposited: $" + amount + "\nYour balance is: $" + balance + ", and available room to contribute: $" + roomForContribution);
//           System.out.println("The fee is: $" + feeForDepositing);
//       }else {
//           System.out.println("Your available room for contribution: $" + roomForContribution);
//       }
//    }
//
//    @Override
//    void withdrawMoneyFromAccount(double amount) {
//        if(amount<balance){
//            balance-=amount;
//            transactionHistory.add(super.createTransaction(-amount));
//            System.out.println("\nSuccessfully withdrew: $" + amount + "\nCurrent balance is: $" + balance);
//        }else {
//            System.out.println("\nNot enough funds! \nYou can withdraw only: $" + balance);
//        }
//    }
//
//    @Override
//    void printTransactionList() {
//        System.out.println("Yours RRSP history:");
//        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
//        transactionHistory.forEach(transaction ->
//                System.out.println(formatter.format(transaction.getDateTime()) + " :" + transaction.getTransaction_type() + " $" + transaction.getAmount()));
//    }
//
//
//    @Override
//    public void viewEligibilityTerms() {
//
//        //Will print terms of the promotion
//        //If Person deposits more than 10,000 CAD - he receives
//
//    }
//
//    @Override
//    public boolean checkPromotionEligibility() {
//        //Check sum of deposits this year. If deposits > 10,000 CAD set fee to 0%
//
//
//        return true;
//    }
//
//    @Override
//    public void applyPromotion() {
//
//    }
//
//    public double getRoomForContribution() {
//        return roomForContribution;
//    }
//
//    public double getBalance() {
//        return balance;
//    }
//
//
//
//    private static class YearlyRoom{
//
//        private int year;
//        private double maximumAmountToContribute;
//        private double maximumPercentageToContribute;
//
//        public YearlyRoom(int year, double maximumAmountToContribute, double maximumPercentageToContribute) {
//            this.year = year;
//            this.maximumAmountToContribute = maximumAmountToContribute;
//            this.maximumPercentageToContribute = maximumPercentageToContribute;
//        }
//
//        public int getYear() {
//            return year;
//        }
//
//        public double getMaximumAmountToContribute() {
//            return maximumAmountToContribute;
//        }
//
//        public double getMaximumPercentageToContribute() {
//            return maximumPercentageToContribute;
//        }
//    }

}
