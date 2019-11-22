package tests;

import com.bank.Entities.ClientAccount;
import com.bank.Entities.CreditCard;
import com.bank.Entities.Customer;
import com.bank.Service.ClientAccountService;
import com.bank.Service.CreditCardService;
import com.bank.Service.FinancialProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardServiceTest  {

//    @BeforeEach
//    void setUp() {
//        Customer customer = new Customer("Bob", "Marley", LocalDate.of(1965, 10, 1), false, false);
//        ClientAccount clientAccount = new ClientAccount(customer);
//        ClientAccountService clientAccountService = new ClientAccountService(clientAccount);
//        CreditCardService creditCardService = clientAccountService.openCreditLine();
//        CreditCard creditCard = (CreditCard) clientAccount.getTypeToFinancialProductMap().get(FinancialProductService.FinancialProductType.CREDIT_CARD);
//    }

    @Test
    void testDepositMoneyToAccount() {
    }

    @Test
    void testWithdrawMoneyFromAccount() {
    }

    @Test
    void testReviewBalance() {
    }

    @Test
    void viewEligibilityTerms() {
    }

    @Test
    void checkIfEligibleForPromotion() {
    }

    @Test
    void applyPromotion() {
    }

    @Test
    void testPrintTransactionHistory() {
    }
}