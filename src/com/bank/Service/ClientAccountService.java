package com.bank.Service;

import com.bank.Entities.*;

import java.util.HashMap;
import java.util.Map;

import static com.bank.Util.FinancialInfoUtil.getNumberFromCustomer;
import static com.bank.Util.FinancialInfoUtil.getStringFromCustomer;

public class ClientAccountService {


    public void openCheckingAccount(ClientAccount clientAccount, double amount) throws Exception {
        Customer customer = clientAccount.getCustomer();
        if (checkIfFinancialProductExists(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT, clientAccount)) {
            if (amount > 0 && customer.isStudent()) {
                createCheckingAccount(clientAccount, amount);
            } else if (!customer.isStudent() && amount > FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE) {
                System.out.println("The fee is: $" + FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE);
                double depositAmountWithFeeApplied = amount - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE;
                createCheckingAccount(clientAccount, depositAmountWithFeeApplied);
            } else {
                throw new Exception("Checking account already exists");
            }
        }
    }


    private void createCheckingAccount(ClientAccount clientAccount, double amount) {
        CheckingAccount checkingAccount = new CheckingAccount(amount);
        if(!clientAccount.getCustomer().isStudent()){
            checkingAccount.addTransactionToTransactionHistory(new Transaction(FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE));
        }
        clientAccount.addNewFinancialProduct(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT, checkingAccount);
        System.out.println("Successfully opened a checking account. You balance is: $" + amount);
        clientAccount.reviewCurrentFinancialProducts();
    }


    public void openCreditLine(ClientAccount clientAccount) throws Exception {
        Map<FinancialProductService.FinancialProductType, FinancialProduct> typeToFinancialProduct = clientAccount.getTypeToFinancialProductMap();

        if (!typeToFinancialProduct.containsKey(FinancialProductService.FinancialProductType.CREDIT_CARD) &&
                typeToFinancialProduct.containsKey(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT)) {
            int availableCreditLine = checkCreditLineEligibility(clientAccount);
            if (availableCreditLine > 0) {
                CreditCard creditCard = new CreditCard(availableCreditLine);
                clientAccount.addNewFinancialProduct(FinancialProductService.FinancialProductType.CREDIT_CARD, creditCard);
                System.out.println("Successfully opened a credit line with $" + availableCreditLine + " available for credit");
                clientAccount.reviewCurrentFinancialProducts();
            }
        } else {
            throw new Exception("Either credit card already exists or you don't have checking account yet!");
        }
    }

    private int checkCreditLineEligibility(ClientAccount clientAccount) {
        CheckingAccount checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().
                get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);
        Customer customer = clientAccount.getCustomer();
        if (checkingAccount != null) {
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
        }
        return -1;
    }

    public void openRRSP(ClientAccount clientAccount) {
        FinancialClientsInfo financialClientsInfo;
        Map<FinancialProductService.FinancialProductType, FinancialProduct> typeToFinancialProductMap = clientAccount.getTypeToFinancialProductMap();
        if (checkIfFinancialProductExists(FinancialProductService.FinancialProductType.RRSP, clientAccount)) {
            financialClientsInfo = requestFinancialInfo();
            clientAccount.setFinancialClientsInfo(financialClientsInfo);
            RRSP rrsp = new RRSP(clientAccount);
            typeToFinancialProductMap.put(FinancialProductService.FinancialProductType.RRSP, rrsp);
            clientAccount.reviewCurrentFinancialProducts();
        }
    }

    private FinancialClientsInfo requestFinancialInfo() {
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
        salaryHistory.put(2018, incomeIn2018);
        System.out.println("Please enter the net income in 2019: ");

        double incomeIn2019 = getNumberFromCustomer();
        salaryHistory.put(2019, incomeIn2019);
        return new FinancialClientsInfo(currentPosition, previousPosition, yearsOnCurrentPosition, yearsOnPreviousPosition, salaryHistory);
    }


    //TODO Move to FinancialProduct or clientAccount
    private boolean checkIfFinancialProductExists(FinancialProductService.FinancialProductType financialProductType, ClientAccount clientAccount) {
        Map financialProductList = clientAccount.getTypeToFinancialProductMap();
        if (!financialProductList.containsKey(financialProductType)) {
            return true;
        } else {
            System.out.println(financialProductType.toString() + " already exists!");
            return false;
        }
    }


}
