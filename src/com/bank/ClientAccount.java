package com.bank;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClientAccount {

    private Customer customer;
    private FinancialClientsInfo financialClientsInfo;
    private Map<FinancialProduct.FinancialProductType, FinancialProduct> financialProductList;
    private int amountEligibleForCreditLine;
    private boolean eligibleForPromotion;

    private Scanner scanner = new Scanner(System.in);


    public ClientAccount(Customer customer) {
        this.customer = customer;
        this.financialProductList = new HashMap<>();
        calculateInitialCreditLineEligibility(customer);
        this.eligibleForPromotion = false;
    }

    private void calculateInitialCreditLineEligibility(Customer customer){
        LocalDate minimalAge = LocalDate.of(18,1,1);
        boolean clientOldEnough =  customer.getDateOfBirth().compareTo(minimalAge)>0;
        if(customer.isCanadianResident()&& clientOldEnough) {
            this.amountEligibleForCreditLine = CreditCard.LOWEST_THRESHOLD;
        }else {this.amountEligibleForCreditLine = 0;}
    }

    CheckingAccount openCheckingAccount(double amount) {
        if(!checkIfFinancialProductExists(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT)) {
            CheckingAccount checkingAccount = new CheckingAccount(amount);
            financialProductList.put(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT, checkingAccount);
            System.out.println("Successfully opened a checking account. You balance is: $" + amount);
            return checkingAccount;
        }
        return null;
    }


    CreditCard openCreditLine() {
        if(!checkIfFinancialProductExists(FinancialProduct.FinancialProductType.CREDIT_CARD)) {
            int availableCreditLine = checkCreditLineEligibility();
            CreditCard creditCard;
            if (availableCreditLine > 0) {
                creditCard = new CreditCard(availableCreditLine);
                financialProductList.put(FinancialProduct.FinancialProductType.CREDIT_CARD, creditCard);
                System.out.println("Successfully opened a credit line with $" + availableCreditLine + " available for credit");
                return creditCard;
            }
        }
        return null;
    }

    private int checkCreditLineEligibility() {
        CheckingAccount checkingAccount = (CheckingAccount) financialProductList.get(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT);
        double checkingBalance = checkingAccount.getBalance();
        if (checkingBalance < 1000 && !customer.isCanadianResident()) {
            System.out.println("Cannot apply for a credit card");
            amountEligibleForCreditLine = 0;
            return 0;
        } else if (checkingBalance < 1000 && customer.isCanadianResident()) {
            System.out.println("Eligible for $" + CreditCard.LOWEST_THRESHOLD);
            amountEligibleForCreditLine = CreditCard.LOWEST_THRESHOLD;
            return CreditCard.LOWEST_THRESHOLD;
        } else if (checkingBalance < 5000 && !customer.isCanadianResident()) {
            System.out.println("Eligible for $" + CreditCard.LOWEST_THRESHOLD);
            amountEligibleForCreditLine = CreditCard.LOWEST_THRESHOLD;
            return CreditCard.LOWEST_THRESHOLD;
        } else if (checkingBalance < 5000 && customer.isCanadianResident()) {
            System.out.println("Eligible for $" + CreditCard.MIDDLE_THRESHOLD);
            amountEligibleForCreditLine = CreditCard.MIDDLE_THRESHOLD;
            return CreditCard.MIDDLE_THRESHOLD;
        } else if (checkingBalance >= 5000 && !customer.isCanadianResident()) {
            System.out.println("Eligible for $" + CreditCard.MIDDLE_THRESHOLD);
            amountEligibleForCreditLine = CreditCard.MIDDLE_THRESHOLD;
            return CreditCard.MIDDLE_THRESHOLD;
        } else {
            System.out.println("Eligible for $" + CreditCard.TOP_THRESHOLD);
            amountEligibleForCreditLine = CreditCard.TOP_THRESHOLD;
            return CreditCard.TOP_THRESHOLD;
        }
    }
    public void checkPromotionEligibility(){
        CheckingAccount checkingAccount =  (CheckingAccount) financialProductList.get(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT);
        double amountSpentLastMonth = 0.00;
        if(checkingAccount!=null){
            amountSpentLastMonth = checkingAccount.getAmountSpentLastMonth();
            System.out.println("Amount spent last month is: $" + amountSpentLastMonth);
        }
        if(amountSpentLastMonth>5000){
            this.eligibleForPromotion = true;
            System.out.println("You are eligible for promotion");
        }else {
            this.eligibleForPromotion = false;
            System.out.println("You have to spend $" + (5000-amountSpentLastMonth) + " to be eligible.");
        }
    }

    public int getAmountEligibleForCreditLine() {
        return amountEligibleForCreditLine;
    }

    public boolean isEligibleForPromotion() {
        return eligibleForPromotion;
    }

    RRSP openRRSP(){
        FinancialClientsInfo financialClientsInfo;
        if(!checkIfFinancialProductExists(FinancialProduct.FinancialProductType.RRSP)){
            financialClientsInfo =  requestFinancialInfo();
            return new RRSP(financialClientsInfo);
        }
        return null;
    }

    private FinancialClientsInfo requestFinancialInfo(){
        String currentPosition;
        String previousPosition;
        int yearsOnCurrentPosition;
        int yearsOnPreviousPosition;
        Map<Integer, Double> salaryHistory = new HashMap<>();
        System.out.println("What is the name of the position you currently occupy?");
        currentPosition = scanner.nextLine();
        System.out.println("For how many years do you work at current position?");
        yearsOnCurrentPosition = scanner.nextInt();
        scanner.next();
        System.out.println("What is the name of the position you worked before?");
        previousPosition = scanner.nextLine();
        System.out.println("For how many years have you been working at your previous position?");
        yearsOnPreviousPosition = scanner.nextInt();
        scanner.next();
        System.out.println("Please enter the net income in 2017, 2018 and 2019:");
        salaryHistory.put(2017, scanner.nextDouble());
        scanner.next();
        salaryHistory.put(2018, scanner.nextDouble());
        scanner.next();
        salaryHistory.put(2019,scanner.nextDouble());
        scanner.next();
        return new FinancialClientsInfo(currentPosition,previousPosition,yearsOnCurrentPosition, yearsOnPreviousPosition, salaryHistory);
    }

    public void viewAllFinancialProducts(){
        financialProductList.forEach((k,v) -> System.out.println(k.toString()));
    }

    private boolean checkIfFinancialProductExists(FinancialProduct.FinancialProductType financialProductType){
        if(financialProductList.containsKey(financialProductType) ){
            System.out.println( financialProductType.toString() + " already exists!");
            return true;
        }
        return false;
    }

}
