package com.dealership.ui;

import com.dealership.model.DealershipUser;

import java.util.Scanner;

public class Login {

    final static String EMPLOYEE_VERIFICATION_CODE = "password";

    public void greeting() {
        System.out.println("Welcome to the Car Dealership");
    }

    public String userLogin(Scanner scan) {
        System.out.print("Would you like to sign in as a:\n");
        System.out.println("1. Customer\n2. Employee\n3. Exit");
        while (true) {
            String userChoice = scan.nextLine();
            if (userChoice.equals("1")) {
                return "c";
            } else if (userChoice.equals("2")) {
                return "e";
            } else if (userChoice.equals("3")) {
                return "Visit Us Soon!";
            } else {
                System.out.println("That is an invalid entry. Please choose one of the three options");
            }
        }
    }

    public DealershipUser createUser(Scanner scanner) {
        DealershipUser user = new DealershipUser();
        System.out.println("Please enter information");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        String userType = "1";
        while (true) {
            System.out.println("Are you an employee or customer?");
            System.out.println("1: Customer\n2: Employee");
            userType = scanner.nextLine();
            if (userType.equals("1"))
                break;
            else
                userType = isEmployee(scanner);
            if (userType.equals("q")) {
                return user;
            }
            break;
        }
        return new DealershipUser(username, password, firstName, lastName, Integer.parseInt(userType));
    }


    public String isEmployee(Scanner scanner) {
        System.out.println("Please enter employee verification code: ");
        while (true) {
            String verificationCode = scanner.nextLine();
            if (EMPLOYEE_VERIFICATION_CODE.equals(verificationCode)) {
                return "2";
            } else if (verificationCode.equals("1")) {
                break;
            } else if (verificationCode.equals("q")){
                return "q";
            } else {
                System.out.println("Invalid entry. Press \"q\" to Quit or \"1\" if you want to register as a customer.");
            }
        }
        return "1";
    }

}
