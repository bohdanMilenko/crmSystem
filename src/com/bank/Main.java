package com.bank;

public class Main {

    public static void main(String[] args) {

        Customer customer = new RegularAccount();
        customer.openCheckingAccount(6000);
        customer.checkCreditLineEligibility();
        System.out.println(customer.eligibleForCreditLine);

        /*
        Main goal of the application:
            - provide painless process to create a new Customer and get all the required information for account creation
            - App holds Customer list in ArrayList - object Customer;
            - Crated Interface with basic methods and then add new account types: Student, RegularAdult, Senior
         */


    }
}
