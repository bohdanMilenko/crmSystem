package com.bank;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ClientAccount {

    private Customer customer;
    private FinancialClientsInfo financialClientsInfo;
    private Map<FinancialProduct.FinancialProductType, FinancialProduct> financialProductList;
    private int amountEligibleForCreditLine;
    private boolean eligibleForPromotion;

    public static Scanner scanner = new Scanner(System.in);


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
        //contains key
        if(checkIfPossibleToAddNewFinancialProduct(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT)) {
            if(amount>0 && customer.isStudent()){
                createCheckingAccount(amount);
            }else if(!customer.isStudent() && amount > FinancialProduct.CHECKING_ACCOUNT_YEARLY_FEE) {
               createCheckingAccount(amount - FinancialProduct.CHECKING_ACCOUNT_YEARLY_FEE);
                System.out.println("The fee is: $" + FinancialProduct.CHECKING_ACCOUNT_YEARLY_FEE);
            }else {
                System.out.println("You need to pay yearly fee of $" + FinancialProduct.CHECKING_ACCOUNT_YEARLY_FEE);
            }
        }
        return null;
    }

    private CheckingAccount createCheckingAccount(double amount){
        CheckingAccount checkingAccount = new CheckingAccount(amount);
        financialProductList.put(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT, checkingAccount);
        System.out.println("Successfully opened a checking account. You balance is: $" + amount);
        return checkingAccount;
    }


    CreditCard openCreditLine() {
        if(checkIfPossibleToAddNewFinancialProduct(FinancialProduct.FinancialProductType.CREDIT_CARD)) {
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
        if(checkingAccount != null) {
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
        }else {
            System.out.println("Not eligible, please open checking account first!");
            return 0;
        }
    }
    public void checkPromotionEligibility() {
        CheckingAccount checkingAccount = (CheckingAccount) financialProductList.get(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT);
        double amountSpentLastMonth = 0.00;
        if (checkingAccount != null) {
            checkingAccount.checkPromotionEligibility();
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
        if(checkIfPossibleToAddNewFinancialProduct(FinancialProduct.FinancialProductType.RRSP)){
            financialClientsInfo =  requestFinancialInfo();
            RRSP rrsp = new RRSP(financialClientsInfo);
            financialProductList.put(FinancialProduct.FinancialProductType.RRSP, rrsp);
            return rrsp;
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
        currentPosition = getStringFromCustomer();
        System.out.println("For how many years do you work at current position?");
        yearsOnCurrentPosition = (int) getNumberFromCustomer();
        System.out.println("What is the name of the position you worked before?");
        previousPosition = getStringFromCustomer();
        System.out.println("For how many years have you been working at your previous position?");
        yearsOnPreviousPosition = (int) getNumberFromCustomer();

        System.out.println("Please enter the net income in 2017: ");
        double incomeIn2017 = getNumberFromCustomer();
        salaryHistory.put(2017, incomeIn2017);
        System.out.println("Please enter the net income in 2018: ");
        double incomeIn2018 = getNumberFromCustomer();
        salaryHistory.put(2018,incomeIn2018);
        System.out.println("Please enter the net income in 2019: ");
        double incomeIn2019 = getNumberFromCustomer();
        salaryHistory.put(2019,incomeIn2019);
        this.financialClientsInfo = new FinancialClientsInfo(currentPosition,previousPosition,yearsOnCurrentPosition, yearsOnPreviousPosition, salaryHistory);
        return this.financialClientsInfo;
    }

    public void viewAllFinancialProducts(){
        System.out.println(customer.getName() + " - this is list of your current financial products:");
        financialProductList.forEach((k,v) -> System.out.println(k.toString()));
    }

    private boolean checkIfPossibleToAddNewFinancialProduct(FinancialProduct.FinancialProductType financialProductType){
        //NAMING
        if(!financialProductList.containsKey(financialProductType) ){
            return true;
        }else {
            System.out.println(financialProductType.toString() + " already exists!");
            return false;
        }
    }

    public static double getNumberFromCustomer(){
        double numberToReturn = -1;
        try {
            do {
                numberToReturn = scanner.nextDouble();
                scanner.nextLine();
                if(numberToReturn < 0)   { System.out.println("Please enter a valid number >= 0!"); }
            } while (numberToReturn < 0);
            return numberToReturn;
        }catch (InputMismatchException e) {
            System.out.println("Please enter a valid number >= 0!");
            scanner.nextLine();
            getNumberFromCustomer();

        }
        return numberToReturn;
    }

    public static String getStringFromCustomer(){
        String returnString = "";
        int i = 0;
        try{
            returnString = scanner.nextLine();
            while ( !Pattern.matches("[a-zA-z]+",returnString)){
                System.out.println("Please enter a string!");
                returnString = scanner.nextLine();
                i++;
                if(i==3) throw new RuntimeException("Invalid input 3 times in a row!");
            }
        }catch (InputMismatchException e){
            System.out.println("Wrong input from customer");
        }catch (RuntimeException e){
            System.out.println("Unable to get user input");
        }
        return returnString;
    }

}
