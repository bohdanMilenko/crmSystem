package com.bank.Service;

import com.bank.Entities.*;

import java.util.HashMap;
import java.util.Map;

import static com.bank.Service.FinancialInfoUtil.getNumberFromCustomer;
import static com.bank.Service.FinancialInfoUtil.getStringFromCustomer;

public class ClientAccountService {

    private ClientAccount clientAccount;


    CheckingAccount openCheckingAccount(double amount) {
        //contains key
        Customer customer = clientAccount.getCustomer();
        if(checkIfPossibleToAddNewFinancialProduct(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT)) {
            if(amount>0 && customer.isStudent()){
                return createCheckingAccount(amount);
            }else if(!customer.isStudent() && amount > FinancialProduct.CHECKING_ACCOUNT_YEARLY_FEE) {
                System.out.println("The fee is: $" + FinancialProduct.CHECKING_ACCOUNT_YEARLY_FEE);
                return createCheckingAccount(amount - FinancialProduct.CHECKING_ACCOUNT_YEARLY_FEE);
            }else {
                System.out.println("You need to pay yearly fee of $" + FinancialProduct.CHECKING_ACCOUNT_YEARLY_FEE);
            }
        }
        return null;
    }

    private CheckingAccount createCheckingAccount(double amount){
        CheckingAccount checkingAccount = new CheckingAccount(amount);
        clientAccount.addNewFinancialProduct(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT, checkingAccount);
        System.out.println("Successfully opened a checking account. You balance is: $" + amount);
        return checkingAccount;
    }

    private boolean checkIfPossibleToAddNewFinancialProduct(FinancialProduct.FinancialProductType financialProductType){
        //NAMING
        Map financialProductList = clientAccount.getTypeToFinancialProductMap();
        if(!financialProductList.containsKey(financialProductType) ){
            return true;
        }else {
            System.out.println(financialProductType.toString() + " already exists!");
            return false;
        }
    }

    CreditCard openCreditLine() {
        Map<FinancialProduct.FinancialProductType, FinancialProduct> financialProductList = clientAccount.getTypeToFinancialProductMap();
        Customer customer = clientAccount.getCustomer();
        if(checkIfPossibleToAddNewFinancialProduct(FinancialProduct.FinancialProductType.CREDIT_CARD)) {
            int availableCreditLine = checkCreditLineEligibility(customer, financialProductList);
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

    private int checkCreditLineEligibility(Customer customer, Map<FinancialProduct.FinancialProductType, FinancialProduct> typeToFinancialProductMap) {
        CheckingAccount checkingAccount = (CheckingAccount) typeToFinancialProductMap.get(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT);
        if(checkingAccount != null) {
            double checkingBalance = checkingAccount.getBalance();
            if (checkingBalance < 1000 && !customer.isCanadianResident()) {
                System.out.println("Cannot apply for a credit card");
                clientAccount.setAmountEligibleForCreditLine(0);
                return 0;
            } else if (checkingBalance < 1000 && customer.isCanadianResident()) {
                System.out.println("Eligible for $" + CreditCard.LOWEST_THRESHOLD);
                clientAccount.setAmountEligibleForCreditLine(CreditCard.LOWEST_THRESHOLD);
                return CreditCard.LOWEST_THRESHOLD;
            } else if (checkingBalance < 5000 && !customer.isCanadianResident()) {
                System.out.println("Eligible for $" + CreditCard.LOWEST_THRESHOLD);
                clientAccount.setAmountEligibleForCreditLine(CreditCard.LOWEST_THRESHOLD);
                return CreditCard.LOWEST_THRESHOLD;
            } else if (checkingBalance < 5000 && customer.isCanadianResident()) {
                System.out.println("Eligible for $" + CreditCard.MIDDLE_THRESHOLD);
                clientAccount.setAmountEligibleForCreditLine(CreditCard.MIDDLE_THRESHOLD);
                return CreditCard.MIDDLE_THRESHOLD;
            } else if (checkingBalance >= 5000 && !customer.isCanadianResident()) {
                System.out.println("Eligible for $" + CreditCard.MIDDLE_THRESHOLD);
                clientAccount.setAmountEligibleForCreditLine(CreditCard.MIDDLE_THRESHOLD);
                return CreditCard.MIDDLE_THRESHOLD;
            } else {
                System.out.println("Eligible for $" + CreditCard.TOP_THRESHOLD);
                clientAccount.setAmountEligibleForCreditLine(CreditCard.TOP_THRESHOLD);
                return CreditCard.TOP_THRESHOLD;
            }
        }else {
            System.out.println("Not eligible, please open checking account first!");
            return 0;
        }
    }

    RRSP openRRSP(){
        FinancialClientsInfo financialClientsInfo;
        Map<FinancialProduct.FinancialProductType, FinancialProduct> typeToFinancialProductMap = clientAccount.getTypeToFinancialProductMap();
        if(checkIfPossibleToAddNewFinancialProduct(FinancialProduct.FinancialProductType.RRSP)){
            financialClientsInfo =  requestFinancialInfo();
            RRSP rrsp = new RRSP(financialClientsInfo);
            typeToFinancialProductMap.put(FinancialProduct.FinancialProductType.RRSP, rrsp);
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
        FinancialClientsInfo financialClientsInfo =new FinancialClientsInfo(currentPosition,previousPosition,yearsOnCurrentPosition, yearsOnPreviousPosition, salaryHistory);
        clientAccount.setFinancialClientsInfo(financialClientsInfo);
        return financialClientsInfo;
    }


}
