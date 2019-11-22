package com.bank;

import com.bank.Entities.ClientAccount;
import com.bank.Entities.CreditCard;
import com.bank.Entities.Customer;
import com.bank.Entities.RRSP;
import com.bank.Service.CheckingAccountService;
import com.bank.Service.ClientAccountService;
import com.bank.Service.CreditCardService;
import com.bank.Service.RRSPService;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws UnsupportedOperationException {

        Customer customer = new Customer("Mark", "Twain", LocalDate.of(1984, 8, 24), false, true);
        ClientAccount MarkTwain = new ClientAccount(customer);

        Customer customer1 = new Customer("Fyodor", "Dostoyevsky",LocalDate.of(1821,11,11),false, false);
        ClientAccount FyodorDostoyevsky = new ClientAccount(customer1);

        ClientAccountService clientAccountService = new ClientAccountService();
        CheckingAccountService checkingAccountService = new CheckingAccountService();
        CreditCardService creditCardService = new CreditCardService();
        RRSPService rrspService = new RRSPService();

        try {
            clientAccountService.openCheckingAccount(MarkTwain, 10000);
        }catch (Exception e){
            e.getMessage();
        }

        try{
            clientAccountService.openRRSP(MarkTwain);
        }catch (Exception e){
            e.getMessage();
        }

        try{
            checkingAccountService.depositMoneyToAccount(MarkTwain, 5000);
        }catch (Exception e){
            e.getMessage();
        }

        try{
            creditCardService.depositMoneyToAccount(MarkTwain,15000);
            creditCardService.reviewBalance(MarkTwain);
        }catch (Exception e){
            System.out.println("Credit card is not on the account");
        }

       





    }


}
