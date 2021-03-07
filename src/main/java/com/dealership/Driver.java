package com.dealership;

import com.dealership.model.Customer;
import com.dealership.model.Employee;
import com.dealership.ui.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Driver {

    private final static Logger log = LoggerFactory.getLogger(Driver.class);



    public static void main(String[] args) {


        Scanner scan = new Scanner(System.in);
        log.info("Scanner created");

        Object [] users = new Object[2];
        log.debug("Users area created");
        Customer customer = new Customer();
        Employee employee = new Employee();
        users[0] = customer;
        users[1] = employee;
        for (Object eachUser: users) {
            System.out.println(eachUser.getClass().getSimpleName());
        }

        Login login = new Login();
        login.greeting();
        String userChoice = login.userLogin(scan);
        System.out.println(userChoice);

    }
}
