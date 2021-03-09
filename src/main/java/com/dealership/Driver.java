package com.dealership;

import com.dealership.database.ConnectionUtil;
import com.dealership.model.DealershipUser;
import com.dealership.service.EmployeeService;
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
        ConnectionUtil connectionUtil = new ConnectionUtil();
        Connection connection = connectionUtil.getConnection();
        Scanner scanner = new Scanner(System.in);
        log.info("Scanner created");


        Login login = new Login();
        DealershipUser user = login.loginInformation(connection, scanner, userService);

        EmployeeService employeeService = new EmployeeService(userService, user);
        employeeService.displayOffers(connection, scanner, userService);


//        if (user.getFirstName() != null) {
//            CustomerService customerService = new CustomerService(user);
//            customerService.runCustomerService(scanner, user, connection);
//        }


//        userService.removeVehicle(userInfo, connection, scanner);
//        String result = userService.addVehicle(userInfo, connection, scanner);
//        System.out.println(result);
//        if (userInfo.getFirstName() != null) {
//            String[] customerInventory = userService.viewUserVehicles(userInfo, connection);
//            String[] dealershipInventory = userService.viewDealershipInventory(userInfo, connection);
//            System.out.println(userInfo.getFirstName() + " owns: ");
//            for (String eachVehicle: customerInventory) {
//                System.out.println(eachVehicle);
//            }
//            System.out.println("Car Dealership Currently Has " + dealershipInventory[dealershipInventory.length-1] + " In Stock:");
//            for (int i = 0; i < dealershipInventory.length - 1; i++) {
//                System.out.println(dealershipInventory[i]);
//            }

//        }


    }
}
