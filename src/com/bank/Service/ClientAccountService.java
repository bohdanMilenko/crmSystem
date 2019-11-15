package com.bank.Service;

import com.bank.Entities.CheckingAccount;
import com.bank.Entities.ClientAccount;
import com.bank.Entities.Customer;
import com.bank.Entities.FinancialProduct;

public class ClientAccountService {

    private ClientAccount clientAccount;


    CheckingAccount openCheckingAccount(double amount) {
        //contains key
        Customer customer = clientAccount.getCustomer();
        if(checkIfPossibleToAddNewFinancialProduct(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT)) {
            if(amount>0 && customer.isStudent()){
                createCheckingAccount(amount);
            }else if(!customer.isStudent() && amount > FinancialProduct.CHECKING_ACCOUNT_YEARLY_FEE) {
                createCheckingAccount(amount - FinancialProduct.CHECKING_ACCOUNT_YEARLY_FEE);
                System.out.println("The fee is: $" + FinancialProduct.CHECKING_ACCOUNT_YEARLY_FEE);
            }else {
                System.out.println("You need to pay yearly fee of $" + FinancialProduct.CHECKING_ACCOUNT_YEARLY_FEE);
            }
        }
        return null;
    }

    private CheckingAccount createCheckingAccount(double amount){
        CheckingAccount checkingAccount = new CheckingAccount(amount);
        clientAccount.addNewFinancialProduct(FinancialProduct.FinancialProductType.CHECKING_ACCOUNT, checkingAccount);
        System.out.println("Successfully opened a checking account. You balance is: $" + amount);
        return checkingAccount;
    }

    private boolean checkIfPossibleToAddNewFinancialProduct(FinancialProduct.FinancialProductType financialProductType){
        //NAMING
        if(!financialProductList.containsKey(financialProductType) ){
            return true;
        }else {
            System.out.println(financialProductType.toString() + " already exists!");
            return false;
        }
    }


}
