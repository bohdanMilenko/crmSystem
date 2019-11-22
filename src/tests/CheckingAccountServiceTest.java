package tests;

import com.bank.Entities.CheckingAccount;
import com.bank.Entities.ClientAccount;
import com.bank.Entities.Customer;
import com.bank.Service.CheckingAccountService;
import com.bank.Service.ClientAccountService;

import java.time.LocalDate;

import com.bank.Service.FinancialProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckingAccountServiceTest {

    private CheckingAccount checkingAccount;
    private ClientAccount clientAccount;

    private ClientAccountService clientAccountService = new ClientAccountService();
    private CheckingAccountService checkingAccountService = new CheckingAccountService();


    @BeforeEach
    void setUp() {
        Customer customer = new Customer("Bob", "Marley", LocalDate.of(1965, 10, 1), false, false);
        clientAccount = new ClientAccount(customer);
        checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);

    }

    @Test
    void testDepositMoneyToAccount() {
        checkingAccountService.depositMoneyToAccount(clientAccount, 25000);
        assertEquals(25000 - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, checkingAccount.getBalance(), 0.000001);
    }

    @Test
    void testWithdrawMoneyFromAccountWithinLimits() throws Exception {
        checkingAccountService.withdrawMoneyFromAccount(clientAccount, 10000);
        assertEquals(5000 - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, checkingAccount.getBalance());

    }

    @Test
    void testWithdrawMoneyFromAccountOverLimit() throws Exception {

        checkingAccountService.withdrawMoneyFromAccount(clientAccount, 20000);
        assertEquals(15000 - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, checkingAccount.getBalance());

    }

    @Test
    void testPrintTransactionHistory() {
    }

    @Test
    void testReviewBalance() {
    }

    @Test
    void viewEligibilityTerms() {
    }

    @Test
    void checkIfEligibleForPromotionNOoExpenses() {
        assertFalse(checkingAccount.isEligibleForPromotion());
    }

    @Test
    void checkIfEligibleForPromotionWithExpenses() throws Exception{
        checkingAccountService.withdrawMoneyFromAccount(clientAccount,1000);
        assertTrue(checkingAccountService.checkIfEligibleForPromotion(clientAccount));
    }

    @Test
    void applyPromotion() {
    }
}