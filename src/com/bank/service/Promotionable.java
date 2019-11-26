package com.bank.service;

import com.bank.entities.ClientAccount;

public interface Promotionable {

    double CASH_BACK_BONUS = 0.01;
    double RRSP_YEARLY_BONUS_ROOM = 3000.00;
    double FEE_FREE_STUDENT_ACCOUNT = -99.99;


    //default method with
    //naming

    void viewEligibilityTerms();

    boolean isPromotionEligible(ClientAccount clientAccountService) throws Exception;

    void applyPromotion(ClientAccount clientAccount) throws Exception;
}
