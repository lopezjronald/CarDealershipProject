package com.dealership;

import com.dealership.database.ConnectionUtil;
import com.dealership.model.DealershipUser;
import com.dealership.service.UserService;
import com.dealership.ui.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Driver {

    private final static Logger log = LoggerFactory.getLogger(Driver.class);

    public static void main(String[] args) throws SQLException {

        UserService userService = new UserService();
        ConnectionUtil connection = new ConnectionUtil();
        Connection dealershipDatabase = connection.getConnection();

        Scanner scan = new Scanner(System.in);
        log.info("Scanner created");


        Registration registration = new Registration();
        registration.greeting();
        String userResponse = registration.userLogin(scan);
        if (userResponse.equals("q")) {
            System.out.println("Visit us soon!");
        } else {
            DealershipUser user = registration.createUser(scan, userResponse);
            if (user.getFirstName() == null) {
                System.out.println("Visit Soon");
            } else {
                int result = userService.save(user, dealershipDatabase);
                System.out.println(result);
            }
        }
    }
}
