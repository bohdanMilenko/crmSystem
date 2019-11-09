package com.bank;

import java.time.LocalDate;
import java.util.Map;

public class RRSP extends FinancialProduct implements Promotion{

    private FinancialClientsInfo financialClientsInfo;
    private double roomForContribution;
    private Map<LocalDate,Double > maximumContributionRoomYearly;

    public RRSP(FinancialClientsInfo financialClientsInfo) {
        this.financialClientsInfo = financialClientsInfo;
        this.roomForContribution = calculateRoom();
    }

    private double calculateRoom(){

    }

    @Override
    void depositMoneyToAccount(double amount) {

    }

    @Override
    void withdrawMoneyFromAccount(double amount) {

    }

    @Override
    void printTransactionList() {

    }

    @Override
    public void viewEligibilityTerms() {

    }

    @Override
    public void checkPromotionEligibility() {

    }

    @Override
    public void applyPromotion() {

    }
}
