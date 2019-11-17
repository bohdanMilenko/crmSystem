package com.bank.Util;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FinancialInfoUtil {

    public static Scanner scanner = new Scanner(System.in);


    public static double getNumberFromCustomer(){
        double numberToReturn = -1;
        try {
            do {
                numberToReturn = scanner.nextDouble();
                scanner.nextLine();
                if(numberToReturn < 0)   { System.out.println("Please enter a valid number >= 0!"); }
            } while (numberToReturn < 0);
            return numberToReturn;
        }catch (InputMismatchException e) {
            System.out.println("Please enter a valid number >= 0!");
            scanner.nextLine();
            getNumberFromCustomer();

        }
        return numberToReturn;
    }

    public static String getStringFromCustomer(){
        String returnString = "";
        int i = 0;
        try{
            returnString = scanner.nextLine();
            while ( !Pattern.matches("[a-zA-z]+",returnString)){
                System.out.println("Please enter a string!");
                returnString = scanner.nextLine();
                i++;
                if(i==3) throw new RuntimeException("Invalid input 3 times in a row!");
            }
        }catch (InputMismatchException e){
            System.out.println("Wrong input from customer");
        }catch (RuntimeException e){
            System.out.println("Unable to get user input");
        }
        return returnString;
    }
}
