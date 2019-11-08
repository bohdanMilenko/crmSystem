package com.bank;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws UnsupportedOperationException {

        Customer customer = new Customer("Mark", "Twain", LocalDate.of(1984, 8, 24), false);
        ClientAccount MarkTwain = new ClientAccount(customer);

        MarkTwain.openCheckingAccount(5000);

        MarkTwain.openCreditLine();
        System.out.println(MarkTwain.getAmountEligibleForCreditLine());

        CreditCard creditCard = MarkTwain.openCreditLine();

        creditCard.withdrawMoneyFromAccount(500.50);
        creditCard.withdrawMoneyFromAccount(900.00);


        creditCard.printTransactionList();
        creditCard.withdrawMoneyFromAccount(15000.00);
        creditCard.printTransactionList();

        CheckingAccount checkingAccount = MarkTwain.openCheckingAccount(9000);
        checkingAccount.depositMoneyToAccount(500);
        checkingAccount.withdrawMoneyFromAccount(2000);
        checkingAccount.depositMoneyToAccount(15550);

        MarkTwain.checkPromotionEligibility();
        MarkTwain.viewAllFinancialProducts();

        FinancialProduct financialProduct = new CreditCard(4000);




        /*
        Main goal of the application:
            - provide painless process to create a new Customer and get all the required information for account creation
            - App holds Customer list in ArrayList - object Customer;
            - Crated Interface with basic methods and then add new account types: Student, RegularAdult, Senior
         */


    }


}
