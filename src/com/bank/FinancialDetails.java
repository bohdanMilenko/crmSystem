package com.bank;

import java.sql.SQLException;

public abstract class FinancialDetails {

    private boolean canadianCitizen;
    private boolean eligibleForCreditLine;
    private boolean promotionOpportunity;
    
    abstract double getBalance();

     boolean isCanadianCitizen() {
        return canadianCitizen;
    }

     void setCanadianCitizen(boolean canadianCitizen) {
        this.canadianCitizen = canadianCitizen;
    }

     boolean isEligibleForCreditLine() {
        return eligibleForCreditLine;
    }

     void setEligibleForCreditLine(boolean eligibleForCreditLine) {
        this.eligibleForCreditLine = eligibleForCreditLine;
    }

     boolean isPromotionOpportunity() {
        return promotionOpportunity;
    }

     void setPromotionOpportunity(boolean promotionOpportunity) {
        this.promotionOpportunity = promotionOpportunity;
    }
}
