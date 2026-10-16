package com.pluralsight;

import java.util.Scanner;

public class FinancialCalc {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);

        int calcType = introduction(myScanner);

        switch (calcType) {
            case 1 -> mortgageCalc(myScanner);
            case 2 -> compoundInterestCalc(myScanner);
            case 3 -> annuityCalc(myScanner);
            default -> System.out.println("Error in switch statement");
        }


    }

    public static int introduction(Scanner scanner) {
        System.out.println("Welcome to the Financial Calculator");
        System.out.println("Which calculator would you like to use?");
        System.out.println("1. Mortgage Calculator");
        System.out.println("2. Compound Interest Calculator");
        System.out.println("3. Annuity Calculator");
        System.out.println("Enter 1, 2, or 3: ");
        int calcType = 0;
        while (calcType != 1 && calcType != 2 && calcType != 3) {
            // If user inputs valid number
            if (scanner.hasNextInt()) {
                calcType = scanner.nextInt();
                if (calcType != 1 && calcType != 2 && calcType != 3) {
                    System.out.println("Please enter a valid option");
                }
                // If user did not input a valid number
            } else {
                System.out.println("Please enter a valid option");
                // Eats the invalid input
                scanner.next();
            }
        }
        return calcType;
    }

    // Calculates monthly payment and total interest paid and outputs to console
    // Prompts the user for principal, interest rate, and loan length
    public static void mortgageCalc(Scanner scanner) {
        System.out.println("This is the Mortgage Calculator");
        System.out.println("It calculates your monthly payment and total interest paid at the end of your mortgage");
        System.out.println("You will need to input the principal value of the loan, the annual interest rate, and the loan length");
        double principal    = promptDouble(scanner, "principal in dollars");
        double interestRate = promptDouble(scanner, "annual interest rate as a percent");
        double years        = promptDouble(scanner, "loan length in years");

        double monthlyPayment = calcMonthlyPayment(principal, interestRate, years);
        double totalInterest = (monthlyPayment * years * 12) - principal;
        System.out.printf(
                "$%.2f loan at %f%% interest for %f years would have a $%.2f/mo payment with a total interest of $%.2f%n",
                principal, interestRate, years, monthlyPayment, totalInterest
        );
    }

    // Calculates compound interest and total interest gained and outputs to console
    // Prompts the user for deposit, interest rate, and years of growth
    public static void compoundInterestCalc(Scanner scanner) {
        System.out.println("This is the Compound Interest Calculator");
        System.out.println("It calculates the amount of interest you'll gain, and the total value of your account after interest");
        System.out.println("You will need to input the deposit amount, the annual interest rate, and the number of years of growth");
        double deposit      = promptDouble(scanner, "deposit");
        double interestRate = promptDouble(scanner, "annual interest rate as a percent");
        double years        = promptDouble(scanner, "number of years of growth");

        double value = calcEndingBalance(deposit, interestRate, years);
        double totalInterest = value - deposit;

        System.out.printf(
                "If you deposit $%.2f in a CD that earns %f%% interest and matures in %f years," +
                " your CD's ending balance will be $%.2f and you would have earned $%.2f in interest%n",
                deposit, interestRate, years, value, totalInterest
        );
    }

    // Calculates the required present value invested to pay a monthly annuity
    // Prompts the user for monthly payout amount, expected annual interest rate, and number of years of payouts
    public static void annuityCalc(Scanner scanner) {
        System.out.println("This is the Annuity Calculator");
        System.out.println("It calculates the required present value invested into an annuity");
        System.out.println("You will need to input the desired monthly payouts, expected annual interest rate, and number of years of payouts");
        double payout       = promptDouble(scanner, "monthly payout in dollars");
        double interestRate = promptDouble(scanner, "expected annual interest rate as a percent");
        double years        = promptDouble(scanner, "number of years the annuity pays out");

        double value = calcInvestAmount(payout, interestRate, years);

        System.out.printf(
                "To fund an annuity that pays $%.2f monthly for %f years and earns an expected %f%% interest," +
                " you would need to invest $%.2f today.",
                payout, years, interestRate, value
        );
    }

    // Calculates the monthly payment for mortgageCalc
    public static double calcMonthlyPayment(double principal, double rate, double years) {
        // interest rate converted to a decimal from percent
        // interest rate multiplied by number of months in a year
        double monthlyInterestRate = (rate * 12) / 100;
        double months = years * 12;

        // (1 + i)^n)
        double compoundInterest = Math.pow(1 + monthlyInterestRate, months);
        // M = P × (i × (1 + i)^n) / ((1 + i)^n - 1)
        return principal * ((monthlyInterestRate * compoundInterest) / (compoundInterest - 1));
    }


    // Calculates the total value after interest for compoundInterestCalc
    public static double calcEndingBalance(double deposit, double rate, double years) {
        // FV = P × (1 + (r / d))^n
        int daysInYear = 365;
        return deposit * Math.pow(( 1 + (rate / daysInYear)), years * daysInYear);
    }

    // Calculates the present value invested required for annuityCalc
    public static double calcInvestAmount(double payout, double rate, double years) {
        // PV = C * ( (1-(1+i)^-n)/ i )
        double numPayments = years * 12;
        return payout * ((1 - Math.pow(1 + rate, 0 - numPayments)) / rate);
    }

    // Prompts the user for a positive double
    // If user enters a string, will reprompt
    // If user enters a negative number, will reprompt
    // Returns the number inputted by user
    public static double promptDouble(Scanner scanner, String doublePrompt) {
        System.out.print("Enter the " + doublePrompt + ": ");
        double num = -1;
        while (num < 0) {
            // If user inputs valid number
            if (scanner.hasNextDouble()) {
                num = scanner.nextDouble();
                if (num < 0) {
                    System.out.println("Please enter a positive number");
                }
                // If user did not input a valid number
            } else {
                System.out.println("Please enter a valid number");
                // Eats the invalid input
                scanner.next();
            }
        }
        return num;
    }
}
