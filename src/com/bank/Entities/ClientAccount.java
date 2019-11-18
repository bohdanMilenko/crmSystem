package com.bank.Entities;


import com.bank.Service.FinancialProductService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ClientAccount {

    private Customer customer;
    private FinancialClientsInfo financialClientsInfo;
    private Map<FinancialProductService.FinancialProductType, FinancialProduct> typeToFinancialProductMap;
    private int amountEligibleForCreditLine;

    public ClientAccount(Customer customer) {
        this.customer = customer;
        this.typeToFinancialProductMap = new HashMap<>();
        calculateInitialCreditLineEligibility(customer);
    }

    private void calculateInitialCreditLineEligibility(Customer customer){
        LocalDate thresholdDate = LocalDate.now().minusYears(18);
        boolean clientOldEnough =  customer.getDateOfBirth().isBefore(thresholdDate);
        if(customer.isCanadianResident() && clientOldEnough) {
            this.amountEligibleForCreditLine = CreditCard.LOWEST_THRESHOLD;
        }else {this.amountEligibleForCreditLine = 0;}
    }


    public void setAmountEligibleForCreditLine(int amount){
        this.amountEligibleForCreditLine = amount;
    }

    public void setFinancialClientsInfo(FinancialClientsInfo financialClientsInfo){
        this.financialClientsInfo = financialClientsInfo;
    }

    public int getAmountEligibleForCreditLine() {
        return amountEligibleForCreditLine;
    }


    public void reviewCurrentFinancialProducts(){
        System.out.println(customer.getName() + " you have the following financial products:");
        typeToFinancialProductMap.forEach((k, v) -> System.out.println(k.toString()));
    }


    public Customer getCustomer() {
        return customer;
    }

    public FinancialClientsInfo getFinancialClientsInfo() {
        return financialClientsInfo;
    }

    public Map<FinancialProductService.FinancialProductType, FinancialProduct> getTypeToFinancialProductMap() {
        return Collections.unmodifiableMap(typeToFinancialProductMap);
    }

    public void addNewFinancialProduct(FinancialProductService.FinancialProductType financialProductType, FinancialProduct financialProduct){
        typeToFinancialProductMap.put(financialProductType, financialProduct);
    }



}
