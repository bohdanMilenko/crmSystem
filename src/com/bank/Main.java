package com.bank;

import com.bank.entities.ClientAccount;
import com.bank.entities.Customer;
import com.bank.service.*;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws UnsupportedOperationException {

        Customer customer = new Customer("Mark", "Twain", LocalDate.of(1984, 8, 24));
        customer.setStudentAccount();
        ClientAccount MarkTwain = new ClientAccount(customer);

        Customer customer1 = new Customer("Fyodor", "Dostoyevsky", LocalDate.of(1821, 11, 11));
        ClientAccount FyodorDostoyevsky = new ClientAccount(customer1);

        ClientAccountService clientAccountService = new ClientAccountService();
        CheckingAccountService checkingAccountService = new CheckingAccountService();
        CreditLineService creditLineService = new CreditLineService();
        RRSPService rrspService = new RRSPService();

        try {
            clientAccountService.openCheckingAccount(MarkTwain, 10000);
        } catch (Exception e) {
            e.getMessage();
        }

        try {
            clientAccountService.openRRSP(MarkTwain);
        } catch (Exception e) {
            e.getMessage();
        }

        try {
            checkingAccountService.depositMoneyToAccount(MarkTwain, 5000);
        } catch (Exception e) {
            e.getMessage();
        }

        try {
            creditLineService.depositMoneyToAccount(MarkTwain, 15000);
            creditLineService.reviewBalance(MarkTwain);
        } catch (Exception e) {
            System.out.println("Credit card is not on the account");
        }


    }


}
