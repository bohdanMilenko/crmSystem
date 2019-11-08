package com.bank;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

public class Main {

    public static void main(String[] args) {

      Customer customer = new Customer("Mark", "Twain", LocalDate.of(1984,8,24),false);
      ClientAccount MarkTwain = new ClientAccount(customer);

      MarkTwain.openCheckingAccount(5000);

      MarkTwain.openCreditLine();
        System.out.println(MarkTwain.getAmountEligibleForCreditLine());

        CreditCard creditCard =  MarkTwain.openCreditLine();
        System.out.println(creditCard.getBalance());

        creditCard.withdrawMoneyFromAccount(500.50);
        creditCard.withdrawMoneyFromAccount(900.00);
        System.out.println(creditCard.getBalance());

        List<Transaction> creditCardTransactions =  creditCard.getCreditCardTransactions();

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        creditCardTransactions.forEach(transaction ->
                System.out.println(formatter.format(transaction.getDateTime()) + " - " + transaction.getTransaction_type() + " " + transaction.getAmount() + " "));

        //creditCardTransactions.add(new Transaction(FinancialProduct.TRANSACTION_TYPE.EXPENSE, 400.00));

        creditCardTransactions.forEach(transaction ->
                System.out.println(formatter.format(transaction.getDateTime()) + " - " + transaction.getTransaction_type() + " " + transaction.getAmount() + " "));




        /*
        Main goal of the application:
            - provide painless process to create a new Customer and get all the required information for account creation
            - App holds Customer list in ArrayList - object Customer;
            - Crated Interface with basic methods and then add new account types: Student, RegularAdult, Senior
         */


    }
}
