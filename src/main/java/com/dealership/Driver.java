package com.dealership;

import com.dealership.database.ConnectionUtil;
import com.dealership.model.DealershipUser;
import com.dealership.model.Payment;
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
        userService.lookUpCustomerPaymentHistory(connection, scanner);


//        EmployeeService employeeService = new EmployeeService(userService, user);
//        employeeService.displayOffers(connection, scanner, userService);
//        UserService test = new UserService();
//        Payment paymentTest = userService.acceptOffer(user, scanner, connection);
//        System.out.println(paymentTest.toString());

    }
}
