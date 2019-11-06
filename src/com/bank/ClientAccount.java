package com.bank;


public class ClientAccount extends  Customer{




    @Override
    void openCheckingAccount(double amount) {
        CheckingAccount checkingAccount = new CheckingAccount(amount);
        financialProductList.put(FinancialProductType.CHECKING_ACCOUNT,checkingAccount);
    }

    @Override
    void openCreditLine() {
    int availableCreditLine = checkCreditLineEligibility();
        if (availableCreditLine>0){
            CreditCard creditCard = new CreditCard(availableCreditLine);
            financialProductList.put(FinancialProductType.CREDIT_CARD, creditCard);
        }
    }


    @Override
    void applyForPromotion() {

    }
}
