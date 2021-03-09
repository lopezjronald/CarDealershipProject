package com.dealership;

import com.dealership.database.ConnectionUtil;
import com.dealership.model.DealershipUser;
import com.dealership.service.UserService;
import com.dealership.ui.Login;
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



        Login login = new Login();
        DealershipUser userInfo = login.loginInformation(dealershipDatabase, scan, userService);
        String result = userService.addVehicle(userInfo, dealershipDatabase, scan);
        System.out.println(result);
        if (userInfo.getFirstName() != null) {
            String[] customerInventory = userService.viewUserVehicles(userInfo, dealershipDatabase);
            String[] dealershipInventory = userService.viewDealershipInventory(userInfo, dealershipDatabase);
            System.out.println(userInfo.getFirstName() + " owns: ");
            for (String eachVehicle: customerInventory) {
                System.out.println(eachVehicle);
            }
            System.out.println("Car Dealership Currently Has " + dealershipInventory[dealershipInventory.length-1] + " In Stock:");
            for (int i = 0; i < dealershipInventory.length - 1; i++) {
                System.out.println(dealershipInventory[i]);
            }

        }



    }
}
