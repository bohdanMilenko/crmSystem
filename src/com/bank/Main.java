package com.bank;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

      Customer customer = new Customer("Mark", "Twain", LocalDate.of(1984,8,24),false);
      ClientAccount MarkTwain = new ClientAccount(customer);

      MarkTwain.openCheckingAccount(5000);

      MarkTwain.openCreditLine();
        System.out.println(MarkTwain.getAmountEligibleForCreditLine());

        /*
        Main goal of the application:
            - provide painless process to create a new Customer and get all the required information for account creation
            - App holds Customer list in ArrayList - object Customer;
            - Crated Interface with basic methods and then add new account types: Student, RegularAdult, Senior
         */


    }
}
