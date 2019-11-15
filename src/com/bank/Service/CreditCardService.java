package com.bank.Service;

import com.bank.Entities.ClientAccount;
import com.bank.Entities.CreditCard;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map;

public class CreditCardService extends FinancialProductService implements Promotion {

    private ClientAccount clientAccount;
    private Map<FinancialProductService.FinancialProductType, FinancialProductService> typeToFinancialProductMap = clientAccount.getTypeToFinancialProductMap();
    private CreditCard creditCard = typeToFinancialProductMap.get(FinancialProductType.CREDIT_CARD);
    public CreditCardService(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
    }

    @Override
    void depositMoneyToAccount(double incomingTransaction) {
        balance+= incomingTransaction;
        creditCardTransactions.add(super.createTransaction(incomingTransaction));
    }

    @Override
    void withdrawMoneyFromAccount(double outgoingTransaction) {
        if(creditLimit >= (balance - outgoingTransaction)){
            overLimitCount++;
            System.out.println("You used more funds than you credit line allows you!");
            creditCardTransactions.add(super.createTransaction(OVER_LIMIT_FEE));
        }

        balance -= outgoingTransaction;
        System.out.println("You withdrew $" + outgoingTransaction + " and you current balance is: $" + balance);
        creditCardTransactions.add(super.createTransaction(-outgoingTransaction));
    }

    @Override
    public void viewEligibilityTerms() {
        //Prints terms of the promotion to the console
    }

    @Override
    public boolean checkPromotionEligibility() {

        return false;
    }

    @Override
    public void applyPromotion() {

    }

    @Override
    void printTransactionList() {
        System.out.println("Your transaction list:");
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        creditCardTransactions.forEach(transaction ->
                System.out.println(formatter.format(transaction.getDateTime()) + " :" + transaction.getTransaction_type() + " $" + transaction.getAmount()));
    }

}
