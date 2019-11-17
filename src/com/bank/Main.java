package com.bank;

import com.bank.Entities.ClientAccount;
import com.bank.Entities.Customer;
import com.bank.Service.CheckingAccountService;
import com.bank.Service.ClientAccountService;
import com.bank.Service.CreditCardService;
import com.bank.Service.RRSPService;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws UnsupportedOperationException {

        Customer customer = new Customer("Mark", "Twain", LocalDate.of(1984, 8, 24), false, true);
        ClientAccount MarkTwain = new ClientAccount(customer);
        ClientAccountService MarkTwainService = new ClientAccountService(MarkTwain);

        CheckingAccountService checkingAccountService =  MarkTwainService.openCheckingAccount(5000);

        checkingAccountService.depositMoneyToAccount(10000);
        checkingAccountService.withdrawMoneyFromAccount(5000);
        checkingAccountService.withdrawMoneyFromAccount(7000);
        checkingAccountService.depositMoneyToAccount(15000);

        checkingAccountService.reviewBalance();

        checkingAccountService.checkIfEligibleForPromotion();
        System.out.println(MarkTwain.getAmountEligibleForCreditLine());

        CreditCardService creditCardService =  MarkTwainService.openCreditLine();

        creditCardService.depositMoneyToAccount(900);
        creditCardService.depositMoneyToAccount(1500);
        creditCardService.withdrawMoneyFromAccount(5000);
        creditCardService.depositMoneyToAccount(5000);

        creditCardService.reviewBalance();

        creditCardService.printTransactionHistory();

        RRSPService MarkTwainRRSP =  MarkTwainService.openRRSP();

        MarkTwainRRSP.depositMoneyToAccount(40000);
        MarkTwainRRSP.depositMoneyToAccount(10000);
        MarkTwainRRSP.withdrawMoneyFromAccount(200000);
        MarkTwainRRSP.reviewBalance();

        Customer customer1 = new Customer("Fyodor", "Dostoyevsky",LocalDate.of(1821,11,11),false, false);





        /*
        Main goal of the application:
            - provide painless process to create a new Customer and get all the required information for account creation
            - App holds Customer list in ArrayList - object Customer;
            - Crated Interface with basic methods and then add new account types: Student, RegularAdult, Senior
         */


    }


}
