package com.bank.Util;

import com.bank.Entities.CheckingAccount;
import com.bank.Entities.ClientAccount;
import com.bank.Entities.Customer;
import com.bank.Service.CheckingAccountService;
import com.bank.Service.ClientAccountService;

import java.time.LocalDate;

import com.bank.Service.FinancialProductService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckingAccountServiceTest {


    @org.junit.jupiter.api.Test
    void testDepositMoneyToAccount() {
        Customer customer = new Customer("Bob", "Marley", LocalDate.of(1965, 10, 1), false, false);
        ClientAccount clientAccount = new ClientAccount(customer);
        ClientAccountService clientAccountService = new ClientAccountService(clientAccount);
        CheckingAccountService checkingAccountService = clientAccountService.openCheckingAccount(15000);
        checkingAccountService.depositMoneyToAccount(10000.00);
        CheckingAccount checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);
        assertEquals(25000 - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, checkingAccount.getBalance(), 0.000001);

    }

    @org.junit.jupiter.api.Test
    void testWithdrawMoneyFromAccount() {
    }

    @org.junit.jupiter.api.Test
    void testPrintTransactionHistory() {
    }

    @org.junit.jupiter.api.Test
    void testReviewBalance() {
    }

    @org.junit.jupiter.api.Test
    void viewEligibilityTerms() {
    }

    @org.junit.jupiter.api.Test
    void checkIfEligibleForPromotion() {
    }

    @org.junit.jupiter.api.Test
    void applyPromotion() {
    }
}