package com.dealership.ui;

import com.dealership.model.DealershipUser;
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

    public DealershipUser loginInformation(Connection connection, Scanner scanner, UserService userService) {

        boolean continueToLogin = true;
        String response;
        DealershipUser userInfo;
        while (continueToLogin) {
            String username = askUsername(scanner);
            String password = askPassword(scanner);
            userInfo = userService.loginQuery(connection, username, password);

            if (userInfo.getFirstName() == null) {
                System.out.println("Sorry. You have entered an invalid username or password.");
                System.out.print("Press any key to continue, \"q\" to quit or \"r\" to register: ");
                response = scanner.nextLine();
                if (response.equalsIgnoreCase("q")) {
                    System.out.println("Visit us soon!");
                    return new DealershipUser();
                } else if (response.equalsIgnoreCase("r")) {
                    Registration registration = new Registration();
                    registration.registerNewUser(connection, scanner, userService);
                    return new DealershipUser();
                } else {
                    continue;
                }
            }
            return userInfo;
        }
        return new DealershipUser();
    }
}
