package tests;

import com.bank.entities.*;
import com.bank.service.CheckingAccountService;
import com.bank.service.ClientAccountService;
import com.bank.service.CreditLineService;
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
    void openCheckingAccount() throws IllegalStateException {
        clientAccountService.openCheckingAccount(clientAccount, OPENING_ACCOUNT_BALANCE);
        assertNotNull(clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT));
    }

    @Test
    void openCheckingAccountNegativeAmount() {
        assertThrows(IllegalStateException.class, () -> clientAccountService.openCheckingAccount(clientAccount, -OPENING_ACCOUNT_BALANCE));
    }

    @Test
    void openCheckingAccountCheckBalanceForStudent() throws IllegalStateException {
        Customer customer = clientAccount.getCustomer();
        customer.setStudentAccount();
        clientAccountService.openCheckingAccount(clientAccount, OPENING_ACCOUNT_BALANCE);
        CheckingAccount checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);
        assertEquals(OPENING_ACCOUNT_BALANCE, checkingAccount.getBalance());
    }

    @Test
    void openCheckingAccountCheckEmptyTransactionsForStudent() throws IllegalStateException {
        Customer customer = clientAccount.getCustomer();
        customer.setStudentAccount();
        clientAccountService.openCheckingAccount(clientAccount, OPENING_ACCOUNT_BALANCE);
        CheckingAccount checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);
        assertTrue(checkingAccount.getCheckingAccountHistory().isEmpty());
    }

    @Test
    void openCheckingAccountAmountLessThenFee() {
        assertThrows(IllegalStateException.class, () -> clientAccountService.openCheckingAccount(clientAccount, 50));
    }

    @Test
    void openCheckingAccountCheckBalanceAfterFeeApplied() throws IllegalStateException {
        clientAccountService.openCheckingAccount(clientAccount, OPENING_ACCOUNT_BALANCE);
        CheckingAccount checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);
        assertEquals(OPENING_ACCOUNT_BALANCE - FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, checkingAccount.getBalance());
    }

    @Test
    void openCheckingAccountCheckTransactionForFee() throws IllegalStateException {
        clientAccountService.openCheckingAccount(clientAccount, OPENING_ACCOUNT_BALANCE);
        CheckingAccount checkingAccount = (CheckingAccount) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT);
        Transaction transaction = checkingAccount.getCheckingAccountHistory().get(0);
        assertEquals(-FinancialProductService.CHECKING_ACCOUNT_YEARLY_FEE, transaction.getAmount());
    }


    @Test
    void openCreditLineWithoutCheckingAccount() {
        assertThrows(IllegalStateException.class, () -> clientAccountService.openCreditLine(clientAccount));
    }

    @Test
    void openCreditLineWithAlreadyOpenCreditLine() {
        CheckingAccount checkingAccount = new CheckingAccount(OPENING_ACCOUNT_BALANCE);
        CreditLine creditLine = new CreditLine(CreditLineService.LOWEST_THRESHOLD);
        clientAccount.addNewFinancialProduct(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT, checkingAccount);
        clientAccount.addNewFinancialProduct(FinancialProductService.FinancialProductType.CREDIT_LINE, creditLine);
        assertThrows(IllegalStateException.class, () -> clientAccountService.openCreditLine(clientAccount));
    }

    @Test
    void openCreditLineCheckAvailableCreditLine() {
        CheckingAccount checkingAccount = new CheckingAccount(500);
        clientAccount.addNewFinancialProduct(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT, checkingAccount);
        assertEquals(0, clientAccount.getAmountEligibleForCreditLine());
    }

    @Test
    void openCreditLineNotEnoughFunds() {
        CheckingAccount checkingAccount = new CheckingAccount(500);
        clientAccount.addNewFinancialProduct(FinancialProductService.FinancialProductType.CHECKING_ACCOUNT, checkingAccount);
        assertThrows(IllegalStateException.class, () -> clientAccountService.openCreditLine(clientAccount));
    }

    @Test
    void openCreditLineNotEnoughFundsButCanadianResident() {
        clientAccountService.openCheckingAccount(clientAccount, 500);
        Customer customer = clientAccount.getCustomer();
        customer.setCanadianResident();
        clientAccountService.openCreditLine(clientAccount);
        assertEquals(1000, clientAccount.getAmountEligibleForCreditLine());
    }

    @Test
    void openCreditLineCanadianResidentMaximumLine() {
        clientAccountService.openCheckingAccount(clientAccount, 10000);
        Customer customer = clientAccount.getCustomer();
        customer.setCanadianResident();
        clientAccountService.openCreditLine(clientAccount);
        assertEquals(10000, clientAccount.getAmountEligibleForCreditLine());
    }

    @Test
    void openCreditLine() {
        //Ask a question: I can create = new CheckingAccount() and put it to Map using clientAccount.addNewFinancialProduct,
        // and this is different from openCheckingAccount(). Does it require a fix?
        clientAccountService.openCheckingAccount(clientAccount, OPENING_ACCOUNT_BALANCE + CheckingAccountService.CHECKING_ACCOUNT_YEARLY_FEE);
        clientAccountService.openCreditLine(clientAccount);
        assertEquals(5000, clientAccount.getAmountEligibleForCreditLine());
    }

    @Test
    void openCreditLineCheckWelcomingBonus() {
        clientAccountService.openCheckingAccount(clientAccount, OPENING_ACCOUNT_BALANCE + CheckingAccountService.CHECKING_ACCOUNT_YEARLY_FEE);
        clientAccountService.openCreditLine(clientAccount);
        CreditLine creditLine = (CreditLine) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CREDIT_LINE);
        assertEquals(50, creditLine.getBalance());
    }

    @Test
    void openRRSP() {
        RRSP rrsp = new RRSP(clientAccount);
        clientAccount.addNewFinancialProduct(FinancialProductService.FinancialProductType.RRSP, rrsp);
        assertThrows(IllegalStateException.class, () -> clientAccountService.openRRSP(clientAccount));
    }
}