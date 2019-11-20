package tests;

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

    private CheckingAccount checkingAccount;
    private CheckingAccountService checkingAccountService;


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Customer customer = new Customer("Bob", "Marley", LocalDate.of(1965, 10, 1), false, false);
        ClientAccount clientAccount = new ClientAccount(customer);
        ClientAccountService clientAccountService = new ClientAccountService(clientAccount);
        checkingAccountService = clientAccountService.openCheckingAccount(15000);
        checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);

    }

    @org.junit.jupiter.api.Test
    void testDepositMoneyToAccount() {

        checkingAccountService.depositMoneyToAccount(10000.00);
        assertEquals(25000 - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, checkingAccount.getBalance(), 0.000001);
    }

    @org.junit.jupiter.api.Test
    void testWithdrawMoneyFromAccountWithinLimits() {
        checkingAccountService.withdrawMoneyFromAccount(10000);
        assertEquals(5000 - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, checkingAccount.getBalance());

    }

    @org.junit.jupiter.api.Test
    void testWithdrawMoneyFromAccountOverLimit() {
        Customer customer = new Customer("Bob", "Marley", LocalDate.of(1965, 10, 1), false, false);
        ClientAccount clientAccount = new ClientAccount(customer);
        ClientAccountService clientAccountService = new ClientAccountService(clientAccount);
        CheckingAccountService checkingAccountService = clientAccountService.openCheckingAccount(15000);
        CheckingAccount checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);

        checkingAccountService.withdrawMoneyFromAccount(20000);
        assertEquals(15000 - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, checkingAccount.getBalance());

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
    void checkIfEligibleForPromotionNOoExpenses() {
        assertFalse(checkingAccount.isEligibleForPromotion());
    }

    @org.junit.jupiter.api.Test
    void checkIfEligibleForPromotionWithExpenses() {
        checkingAccountService.withdrawMoneyFromAccount(10000);
        assertTrue( checkingAccountService.checkIfEligibleForPromotion());
    }

    @org.junit.jupiter.api.Test
    void applyPromotion() {
    }
}