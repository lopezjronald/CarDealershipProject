package com.dealership;

import com.dealership.database.ConnectionUtil;
import com.dealership.model.DealershipUser;
import com.dealership.ui.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Driver {

    private final static Logger log = LoggerFactory.getLogger(Driver.class);


    public static void main(String[] args) throws SQLException {

        ConnectionUtil connection = new ConnectionUtil();
        Connection dealershipDatabase = connection.getConnection();
        DealershipUser user = new DealershipUser("ronald_lopez", "password", "Ronald", "Lopez", 2);
        int result = connection.save(user, dealershipDatabase);
        System.out.println(result);

        Scanner scan = new Scanner(System.in);
        log.info("Scanner created");


        Login login = new Login();
        login.greeting();
        login.userLogin(scan);

    }
}
