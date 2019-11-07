package com.bank;

import java.util.ArrayList;
import java.util.List;

public abstract class FinancialProduct {

    public enum TRANSACTION_TYPE{INCOME,EXPENSE}

    static final int WELCOMING_BONUS = 50;
    List<Transaction> transactionList= new ArrayList<>();

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
