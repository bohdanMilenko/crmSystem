package com.bank;

import com.bank.Entities.*;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws UnsupportedOperationException {

        Customer customer = new Customer("Mark", "Twain", LocalDate.of(1984, 8, 24), false, true);
        ClientAccount MarkTwain = new ClientAccount(customer);

        System.out.println(MarkTwain.getAmountEligibleForCreditLine());

        CheckingAccount checkingAccount = MarkTwain.openCheckingAccount(9000);
        checkingAccount.depositMoneyToAccount(500);
        checkingAccount.withdrawMoneyFromAccount(2000);
        checkingAccount.depositMoneyToAccount(15550);

        MarkTwain.checkPromotionEligibility();
        MarkTwain.viewAllFinancialProducts();

        CreditCard creditCard = MarkTwain.openCreditLine();

        creditCard.withdrawMoneyFromAccount(500.50);
        creditCard.withdrawMoneyFromAccount(900.00);


        creditCard.printTransactionList();
        creditCard.withdrawMoneyFromAccount(15000.00);
        creditCard.printTransactionList();

        FinancialProduct financialProduct = new CreditCard(4000);

//        RRSP rrsp =  MarkTwain.openRRSP();
//        RRSP rrsp2 =  MarkTwain.openRRSP();

        Customer customer1 = new Customer("Fyodor", "Dostoyevsky",LocalDate.of(1821,11,11),false, false);

        ClientAccount Fyodor = new ClientAccount(customer1);
        RRSP fyodorsRRSP =  Fyodor.openRRSP();
        Fyodor.viewAllFinancialProducts();

        System.out.println(fyodorsRRSP.getRoomForContribution());
        fyodorsRRSP.depositMoneyToAccount(30000);

        fyodorsRRSP.withdrawMoneyFromAccount(2000);
        fyodorsRRSP.withdrawMoneyFromAccount(30000);
        fyodorsRRSP.depositMoneyToAccount(5000);

        fyodorsRRSP.printTransactionList();



        /*
        Main goal of the application:
            - provide painless process to create a new Customer and get all the required information for account creation
            - App holds Customer list in ArrayList - object Customer;
            - Crated Interface with basic methods and then add new account types: Student, RegularAdult, Senior
         */


    }


}
