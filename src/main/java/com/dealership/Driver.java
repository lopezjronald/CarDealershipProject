package com.dealership;

import com.dealership.model.Customer;
import com.dealership.model.Employee;
import com.dealership.ui.Login;

import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Object [] users = new Object[2];
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
