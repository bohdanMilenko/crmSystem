package com.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class Customer {

     String name;
     String surname;
     int age;
     boolean canadianResident;
     boolean eligibleForCreditLine;
     boolean eligibleForPromotion;
     List<RelativeType> relativeTypes= new ArrayList<>();
     Map<FinancialProductType,FinancialProduct> financialProductList = new HashMap<>();

     abstract void openCheckingAccount(double amount);
     abstract void openCreditLine();
     abstract void applyForPromotion();

    int checkCreditLineEligibility(){
        CheckingAccount checkingAccount = (CheckingAccount) financialProductList.get(FinancialProductType.CHECKING_ACCOUNT);
        double checkingBalance = checkingAccount.getBalance();
        if(checkingBalance < 1000 && !canadianResident){
            System.out.println("Cannot apply for a credit card");
            eligibleForCreditLine = false;
            return 0;
        }else if(checkingBalance < 1000 && canadianResident){
            System.out.println("Eligible for $" + CreditCard.LOWEST_THRESHOLD);
            eligibleForCreditLine = true;
            return CreditCard.LOWEST_THRESHOLD;
        }else if(checkingBalance<5000 && !canadianResident){
            System.out.println("Eligible for $" + CreditCard.LOWEST_THRESHOLD);
            eligibleForCreditLine = true;
            return CreditCard.LOWEST_THRESHOLD;
        }else if(checkingBalance<5000 && canadianResident) {
            System.out.println("Eligible for $" + CreditCard.MIDDLE_THRESHOLD);
            eligibleForCreditLine = true;
            return CreditCard.MIDDLE_THRESHOLD;
        }else if(checkingBalance > 5000 && !canadianResident){
            System.out.println("Eligible for $" + CreditCard.MIDDLE_THRESHOLD);
            eligibleForCreditLine = true;
            return CreditCard.MIDDLE_THRESHOLD;
        }else {
            System.out.println("Eligible for $" + CreditCard.TOP_THRESHOLD);
            eligibleForCreditLine = true;
            return CreditCard.TOP_THRESHOLD;
        }
    }


    public enum RelativeType {
        PARENT,
        SIBLING,
        PARENTSSIBLING,
        GRANDPARENT;
    }

    public enum GENDER {
        MALE,
        FEMALE,
        UNKNOWN;
    }

    public enum FinancialProductType{
        CREDIT_CARD,
        CHECKING_ACCOUNT;
    }

}
