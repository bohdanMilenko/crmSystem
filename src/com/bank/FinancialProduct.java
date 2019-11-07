package com.bank;

public abstract class FinancialProduct {

    public enum TRANSACTION_TYPE{INCOME,EXPENSE}
    public enum FinancialProductType {
        CREDIT_CARD,
        CHECKING_ACCOUNT;
    }

    static final int WELCOMING_BONUS = 50;

    abstract void depositMoneyToAccount(double amount);
    abstract void withdrawMoneyFromAccount(double amount);



    Transaction createTransaction(double amount){
        TRANSACTION_TYPE TYPE = defineTransactionType(amount);
        return  new Transaction(TYPE,amount);
    }

    private TRANSACTION_TYPE defineTransactionType(double amount){
        if(amount>0){
            return TRANSACTION_TYPE.INCOME;
        }else {
            return TRANSACTION_TYPE.EXPENSE;
        }
    }




}
