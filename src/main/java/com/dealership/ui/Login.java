package com.dealership.ui;

import com.dealership.service.UserService;

import java.sql.Connection;

import java.util.Scanner;


public class Login {

    public Login() {
    }

    private String askUsername(Scanner scanner) {
        System.out.print("Please enter your username: ");
        String username = scanner.nextLine();
        return username.toLowerCase();
    }

    private String askPassword(Scanner scanner) {
        System.out.print("Please enter your password: ");
        return scanner.nextLine();
    }

    public String[] loginInformation(Connection connection, Scanner scanner, UserService userService) {

        boolean continueToLogin = true;
        String response;
        String[] userInfo = null;
        while (continueToLogin) {
            String username = askUsername(scanner);
            String password = askPassword(scanner);
            userInfo = userService.loginQuery(connection, username, password);

            if (userInfo[1] == null) {
                System.out.println("Sorry. You have entered an invalid username or password.");
                System.out.print("Press any key to continue, \"q\" to quit or \"r\" to register: ");
                response = scanner.nextLine();
                if (response.equalsIgnoreCase("q")) {
                    System.out.println("Visit us soon!");
                    return userInfo;
                } else if (response.equalsIgnoreCase("r")) {
                    Registration registration = new Registration();
                    registration.registerNewUser(connection, scanner, userService);
                    return userInfo;
                } else {
                    continue;
                }
            }
            continueToLogin = false;
        }
        return userInfo;

    }
}
