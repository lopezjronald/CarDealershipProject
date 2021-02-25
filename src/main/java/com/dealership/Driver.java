package com.dealership;

import com.dealership.model.Customer;
import com.dealership.model.Employee;

public class Driver {

    public static void main(String[] args) {

        Object [] users = new Object[2];
        Customer customer = new Customer();
        Employee employee = new Employee();
        users[0] = customer;
        users[1] = employee;
        for (Object eachUser: users) {
            System.out.println(eachUser.getClass().getSimpleName());
        }
    }
}
