package com.bank;


import java.util.HashMap;
import java.util.Map;

public class ClientAccount {

    private Customer customer;
    private Map<Customer.FinancialProductType, FinancialProduct> financialProductList = new HashMap<>();
    private int amountEligibleForCreditLine;
    private boolean eligibleForPromotion;


    public ClientAccount(Customer customer) {
        this.customer = customer;
    }

    void openCheckingAccount(double amount) {
        CheckingAccount checkingAccount = new CheckingAccount(amount);
        financialProductList.put(Customer.FinancialProductType.CHECKING_ACCOUNT, checkingAccount);
    }


    void openCreditLine() {
        int availableCreditLine = checkCreditLineEligibility();
        if (availableCreditLine > 0) {
            CreditCard creditCard = new CreditCard(availableCreditLine);
            financialProductList.put(Customer.FinancialProductType.CREDIT_CARD, creditCard);
        }
    }

    private int checkCreditLineEligibility() {
        CheckingAccount checkingAccount = (CheckingAccount) financialProductList.get(Customer.FinancialProductType.CHECKING_ACCOUNT);
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

    public int getAmountEligibleForCreditLine() {
        return amountEligibleForCreditLine;
    }

    public boolean isEligibleForPromotion() {
        return eligibleForPromotion;
    }
}
