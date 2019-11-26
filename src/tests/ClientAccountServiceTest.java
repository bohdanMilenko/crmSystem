package tests;

import com.bank.entities.CheckingAccount;
import com.bank.entities.ClientAccount;
import com.bank.entities.Customer;
import com.bank.entities.Transaction;
import com.bank.service.ClientAccountService;
import com.bank.service.FinancialProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientAccountServiceTest {

    private static final double OPENING_ACCOUNT_BALANCE = 5000;
    private ClientAccount clientAccount;
    private ClientAccountService clientAccountService = new ClientAccountService();

    @BeforeEach
    void setUp() {
        Customer customer = new Customer("Bob", "Marley", LocalDate.of(1965, 10, 1));
        clientAccount = new ClientAccount(customer);
    }

    @Test
    void openCheckingAccount() throws Exception {
        clientAccountService.openCheckingAccount(clientAccount, OPENING_ACCOUNT_BALANCE);
        assertNotNull(clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT));
    }

    @Test
    void openCheckingAccountNegativeAmount() {
        assertThrows(Exception.class, () -> clientAccountService.openCheckingAccount(clientAccount, -OPENING_ACCOUNT_BALANCE));
    }

    @Test
    void openCheckingAccountCheckBalanceForStudent() throws Exception {
        Customer customer = clientAccount.getCustomer();
        customer.setStudentAccount();
        clientAccountService.openCheckingAccount(clientAccount, OPENING_ACCOUNT_BALANCE);
        CheckingAccount checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);
        assertEquals(OPENING_ACCOUNT_BALANCE, checkingAccount.getBalance());
    }

    @Test
    void openCheckingAccountCheckEmptyTransactionsForStudent() throws Exception {
        Customer customer = clientAccount.getCustomer();
        customer.setStudentAccount();
        clientAccountService.openCheckingAccount(clientAccount, OPENING_ACCOUNT_BALANCE);
        CheckingAccount checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);
        assertTrue(checkingAccount.getCheckingAccountHistory().isEmpty());
    }

    @Test
    void openCheckingAccountAmountLessThenFee() {
        assertThrows(Exception.class, () -> clientAccountService.openCheckingAccount(clientAccount, 50));
    }

    @Test
    void openCheckingAccountCheckBalanceAfterFeeApplied() throws Exception {
        clientAccountService.openCheckingAccount(clientAccount, OPENING_ACCOUNT_BALANCE);
        CheckingAccount checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);
        assertEquals(OPENING_ACCOUNT_BALANCE - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, checkingAccount.getBalance());
    }

    @Test
    void openCheckingAccountCheckTransactionForFee() throws Exception {
        clientAccountService.openCheckingAccount(clientAccount, OPENING_ACCOUNT_BALANCE);
        CheckingAccount checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);
        Transaction transaction = checkingAccount.getCheckingAccountHistory().get(0);
        assertEquals(-FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, transaction.getAmount());
    }


    @Test
    void openCreditLine() {
    }

    @Test
    void openRRSP() {
    }
}