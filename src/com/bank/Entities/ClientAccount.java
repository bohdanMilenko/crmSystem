package com.bank.Entities;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ClientAccount {

    private Customer customer;
    private FinancialClientsInfo financialClientsInfo;
    private Map<FinancialProduct.FinancialProductType, FinancialProduct> typeToFinancialProductMap;
    private int amountEligibleForCreditLine;
    private boolean eligibleForPromotion;

    public ClientAccount(Customer customer) {
        this.customer = customer;
        this.typeToFinancialProductMap = new HashMap<>();
        calculateInitialCreditLineEligibility(customer);
        this.eligibleForPromotion = false;
    }

    //Initializes variable should stay
    private void calculateInitialCreditLineEligibility(Customer customer){
        LocalDate minimalAge = LocalDate.of(18,1,1);
        boolean clientOldEnough =  customer.getDateOfBirth().compareTo(minimalAge)>0;
        if(customer.isCanadianResident()&& clientOldEnough) {
            this.amountEligibleForCreditLine = CreditCard.LOWEST_THRESHOLD;
        }else {this.amountEligibleForCreditLine = 0;}
    }


    public void setAmountEligibleForCreditLine(int amount){
        this.amountEligibleForCreditLine = amount;
    }

    public void setFinancialClientsInfo(FinancialClientsInfo financialClientsInfo){
        this.financialClientsInfo = financialClientsInfo;
    }

    public void checkPromotionEligibility() {
        CheckingAccount checkingAccount = (CheckingAccount) typeToFinancialProductMap.get(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT);
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



    public void viewAllFinancialProducts(){
        System.out.println(customer.getName() + " - this is list of your current financial products:");
        typeToFinancialProductMap.forEach((k, v) -> System.out.println(k.toString()));
    }

    public Customer getCustomer() {
        return customer;
    }

    public FinancialClientsInfo getFinancialClientsInfo() {
        return financialClientsInfo;
    }

    public Map<FinancialProduct.FinancialProductType, FinancialProduct> getTypeToFinancialProductMap() {
        return typeToFinancialProductMap;
    }

    public void addNewFinancialProduct(FinancialProduct.FinancialProductType financialProductType, FinancialProduct financialProduct){
        typeToFinancialProductMap.put(financialProductType, financialProduct);
    }



}
