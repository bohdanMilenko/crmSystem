package com.bank;

import java.util.HashMap;
import java.util.Map;

public abstract class FinancialProduct {

    public enum TRANSACTION_TYPE{INCOME,EXPENSE}

    public static final int WELCOMING_BONUS = 50;
    Map<TRANSACTION_TYPE,Double> transactionList= new HashMap<>();

    abstract void increaseBalance(double incomingTransaction);
    abstract void decreaseBalance(double outgoingTransaction);
    abstract void updateTransactionList(TRANSACTION_TYPE transaction_type ,double transaction);




}
