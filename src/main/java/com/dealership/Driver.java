package com.dealership;

import com.dealership.database.ConnectionUtil;
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
        String [] userInfo = login.loginInformation(dealershipDatabase, scan, userService);


        for (String info: userInfo) {
            System.out.println(info);
        }




    }
}
