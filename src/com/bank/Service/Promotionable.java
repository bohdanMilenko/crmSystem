package com.bank.Service;

public interface Promotionable {

    double CASH_BACK_BONUS = 0.01;
    double RRSP_YEARLY_BONUS_ROOM = 3000.00;
    double FEE_FREE_STUDENT_ACCOUNT = -99.99;


    //default method with
    //naming

    void viewEligibilityTerms();

    boolean checkIfEligibleForPromotion();

    void applyPromotion();
}
