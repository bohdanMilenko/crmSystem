package tests;

import com.bank.Entities.CheckingAccount;
import com.bank.Entities.ClientAccount;
import com.bank.Entities.Customer;
import com.bank.Service.CheckingAccountService;
import com.bank.Service.ClientAccountService;
import com.bank.Service.FinancialProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CheckingAccountServiceTest {

    private CheckingAccount checkingAccount;
    private ClientAccount clientAccount;

    private ClientAccountService clientAccountService = new ClientAccountService();
    private CheckingAccountService checkingAccountService = new CheckingAccountService();


    @BeforeEach
    void setUp() throws Exception {
        Customer customer = new Customer("Bob", "Marley", LocalDate.of(1965, 10, 1), false, false);
        clientAccount = new ClientAccount(customer);
        clientAccountService.openCheckingAccount(clientAccount, 10000);
        checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);
        System.out.println("Finished Initialization");
        System.out.println();

    }

    @Test
    void testDepositMoneyToAccount() throws NullPointerException {
        checkingAccountService.depositMoneyToAccount(clientAccount, 25000);
        assertEquals(35000 - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, checkingAccount.getBalance(), 0.000001);
    }

    @Test
    void testDepositMoneyToAccountNegativeSum() throws IllegalArgumentException {
        checkingAccountService.depositMoneyToAccount(clientAccount, 25000);
        assertEquals(35000 - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, checkingAccount.getBalance(), 0.000001);
    }

    @Test
    void testDepositMoneyToAccountBalanceCheck()  {
        checkingAccountService.depositMoneyToAccount(clientAccount, 25000);
        assertEquals(35000 - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, checkingAccount.getBalance(), 0.000001);
    }

    @Test
    void testWithdrawMoneyFromAccountWithinLimits() throws NullPointerException {
        assertThrows(IllegalArgumentException.class, ()-> checkingAccountService.depositMoneyToAccount(clientAccount,-500));

    }

    @Test
    void testWithdrawMoneyFromAccountOverLimit() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class, () -> checkingAccountService.withdrawMoneyFromAccount(clientAccount, 20000));

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
    void checkIfEligibleForPromotionNoExpenses() {
        assertFalse(checkingAccount.isEligibleForPromotion());
    }

    @Test
    void checkIfEligibleForPromotionNotEnoughExpenses() throws NullPointerException {
        checkingAccountService.withdrawMoneyFromAccount(clientAccount, 1000);
        assertFalse(checkingAccountService.checkIfEligibleForPromotion(clientAccount));
    }

    @Test
    void checkIfEligibleForPromotionEnoughExpenses() throws NullPointerException {
        checkingAccountService.withdrawMoneyFromAccount(clientAccount, 5000);
        assertTrue(checkingAccountService.checkIfEligibleForPromotion(clientAccount));
    }

    @Test
    void checkIfEligibleForPromotionIfCountsOnlyExpensesNotEnough() throws NullPointerException {
        checkingAccountService.withdrawMoneyFromAccount(clientAccount, 1000);
        checkingAccountService.depositMoneyToAccount(clientAccount, 2000);
        checkingAccountService.depositMoneyToAccount(clientAccount, 50000);
        checkingAccountService.withdrawMoneyFromAccount(clientAccount, 1000);
        assertFalse(checkingAccountService.checkIfEligibleForPromotion(clientAccount));
    }

    @Test
    void checkIfEligibleForPromotionIfCountsOnlyExpensesEnough() throws NullPointerException {
        checkingAccountService.withdrawMoneyFromAccount(clientAccount, 1000);
        checkingAccountService.depositMoneyToAccount(clientAccount, 2000);
        checkingAccountService.depositMoneyToAccount(clientAccount, 50000);
        checkingAccountService.withdrawMoneyFromAccount(clientAccount, 10000);
        assertTrue(checkingAccountService.checkIfEligibleForPromotion(clientAccount));
    }

    @Test
    void applyPromotion() {
    }
}