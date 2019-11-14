package com.bank;

public abstract class FinancialProduct {


    public enum FinancialProductType {
        CREDIT_CARD,
        CHECKING_ACCOUNT,
        RRSP;


        @Override
        public String toString() {
            switch (this){
                case CREDIT_CARD: return "Credit Card";
                case CHECKING_ACCOUNT: return "Checking account";
                case RRSP: return "RRSP";
                default: throw new IllegalArgumentException();
            }
        }
    }

    static final int WELCOMING_BONUS = 50;
    static final double CHECKING_ACCOUNT_YEARLY_FEE = 99.99;

    abstract void depositMoneyToAccount(double amount);
    abstract void withdrawMoneyFromAccount(double amount);
    abstract void printTransactionList();



    Transaction createTransaction(double amount){
        return  new Transaction(amount);
    }





}
