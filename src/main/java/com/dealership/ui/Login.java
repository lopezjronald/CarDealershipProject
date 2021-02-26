package com.dealership.ui;

import java.util.Scanner;

public class Login {

    public void greeting(){
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
            } else {
                System.out.println("That is an invalid entry. Please choose one of the three options");
            }
        }
    }

}
