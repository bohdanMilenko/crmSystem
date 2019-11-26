package com.bank.service;

import com.bank.entities.*;

import java.util.HashMap;
import java.util.Map;

import static com.bank.util.FinancialInfoUtil.getNumberFromCustomer;
import static com.bank.util.FinancialInfoUtil.getStringFromCustomer;

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
        if (!clientAccount.getCustomer().isStudent()) {
            Transaction transaction = new Transaction(-FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE);
            checkingAccount.addTransactionToTransactionHistory(transaction);
        }
        clientAccount.addNewFinancialProduct(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT, checkingAccount);
        System.out.println("Successfully opened a checking account. You balance is: $" + amount);
        clientAccount.reviewCurrentFinancialProducts();
    }


    public void openCreditLine(ClientAccount clientAccount) throws Exception {
        Map<FinancialProductService.FinancialProductType, FinancialProduct> typeToFinancialProduct = clientAccount.getTypeToFinancialProductMap();

        if (!typeToFinancialProduct.containsKey(FinancialProductService.FinancialProductType.CREDIT_CARD) &&
                typeToFinancialProduct.containsKey(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT)) {
            int availableCreditLine = defineCreditLineAmount(clientAccount);
            if (availableCreditLine > 0) {
                CreditLine creditLine = new CreditLine(availableCreditLine);
                clientAccount.addNewFinancialProduct(FinancialProductService.FinancialProductType.CREDIT_CARD, creditLine);
                System.out.println("Successfully opened a credit line with $" + availableCreditLine + " available for credit");
                clientAccount.reviewCurrentFinancialProducts();
            }
        } else {
            throw new Exception("Either credit card already exists or you don't have checking account yet!");
        }
    }

    private int defineCreditLineAmount(ClientAccount clientAccount) {
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
                System.out.println("Eligible for $" + CreditLineService.LOWEST_THRESHOLD);
                clientAccount.setAmountEligibleForCreditLine(CreditLineService.LOWEST_THRESHOLD);
                return CreditLineService.LOWEST_THRESHOLD;
            } else if (checkingBalance < 5000 && !customer.isCanadianResident()) {
                System.out.println("Eligible for $" + CreditLineService.LOWEST_THRESHOLD);
                clientAccount.setAmountEligibleForCreditLine(CreditLineService.LOWEST_THRESHOLD);
                return CreditLineService.LOWEST_THRESHOLD;
            } else if (checkingBalance < 5000 && customer.isCanadianResident()) {
                System.out.println("Eligible for $" + CreditLineService.MIDDLE_THRESHOLD);
                clientAccount.setAmountEligibleForCreditLine(CreditLineService.MIDDLE_THRESHOLD);
                return CreditLineService.MIDDLE_THRESHOLD;
            } else if (checkingBalance >= 5000 && !customer.isCanadianResident()) {
                System.out.println("Eligible for $" + CreditLineService.MIDDLE_THRESHOLD);
                clientAccount.setAmountEligibleForCreditLine(CreditLineService.MIDDLE_THRESHOLD);
                return CreditLineService.MIDDLE_THRESHOLD;
            } else {
                System.out.println("Eligible for $" + CreditLineService.TOP_THRESHOLD);
                clientAccount.setAmountEligibleForCreditLine(CreditLineService.TOP_THRESHOLD);
                return CreditLineService.TOP_THRESHOLD;
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

        System.out.println("What is the name of the position you currently occupy?");
        String currentPosition = getStringFromCustomer();
        System.out.println("For how many years do you work at current position?");
        int yearsOnCurrentPosition = (int) getNumberFromCustomer();
        System.out.println("What is the name of the position you worked before?");
        String previousPosition = getStringFromCustomer();
        System.out.println("For how many years have you been working at your previous position?");
        int yearsOnPreviousPosition = (int) getNumberFromCustomer();

        Map<Integer, Double> salaryHistory = getSalaryHistoryFromCustomer();
        return new FinancialClientsInfo(currentPosition, previousPosition, yearsOnCurrentPosition, yearsOnPreviousPosition, salaryHistory);
    }

    private Map<Integer, Double> getSalaryHistoryFromCustomer(){
        Map<Integer, Double> salaryHistory = new HashMap<>();
        System.out.println("Please enter the net income in 2017: ");
        double incomeIn2017 = getNumberFromCustomer();
        salaryHistory.put(2017, incomeIn2017);
        System.out.println("Please enter the net income in 2018: ");
        double incomeIn2018 = getNumberFromCustomer();
        salaryHistory.put(2018, incomeIn2018);
        System.out.println("Please enter the net income in 2019: ");
        double incomeIn2019 = getNumberFromCustomer();
        salaryHistory.put(2019, incomeIn2019);
        return salaryHistory;
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
