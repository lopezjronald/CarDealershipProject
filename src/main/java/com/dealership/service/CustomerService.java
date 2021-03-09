package com.dealership.service;

import com.dealership.model.DealershipUser;
import com.dealership.model.Offer;

import java.sql.Connection;
import java.util.Scanner;

public class CustomerService {

    private DealershipUser user;

    public CustomerService() {
    }

    public CustomerService(DealershipUser user) {
        this.user = user;
    }

    private final UserService userService = new UserService();

    public void runCustomerService(Scanner scanner, DealershipUser user, Connection connection) {
        while (true) {
            int userOption = showCustomerMenu(scanner, user);
            switch (userOption) {
                case 1:
                    getDealershipInventory(user, connection);
                    break;
                case 2:
                    getMyVehicles(userService, user, connection);
                    break;
                case 3:
                    scanner.nextLine();
                    Offer offer = userService.makeOffer(user, userService, scanner, connection);
                    System.out.println(offer.toString());
                    break;
                default:
                    System.out.println("I am the default case");
            }
            if (userOption == 3)
                break;
        }



    }

    private void customerGreeting(DealershipUser user) {
        System.out.println("Hello " + userService.capitalizeString(user.getFirstName()) + "!");
        System.out.println("Please choose from the following menu: ");
    }

    public int showCustomerMenu(Scanner scanner, DealershipUser user) {
        customerGreeting(user);
        while (true) {
            System.out.println("1. Show Dealership Inventory");
            System.out.println("2. Show Cars You Own");
            System.out.println("3. Make an Offer");
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("You have entered an invalid entry or press \"q\" to exit or any key to continue.");
                String option = scanner.nextLine();
                if (option.equalsIgnoreCase("q")) {
                    return -1;
                }else
                    continue;
            }
        }
    }

    public String[] getMyVehicles(UserService userService, DealershipUser user, Connection connection) {
        String [] myVehicles;
        myVehicles = userService.viewUserVehicles(user, connection);
        System.out.println("Cars Owned by " + userService.capitalizeString(user.getFirstName()));

        for (String eachVehicle: myVehicles){
            System.out.println(eachVehicle);
        }

        return myVehicles;
    }

    public String[] getDealershipInventory(DealershipUser user, Connection connection) {
        String[] dealershipInventory = userService.viewDealershipInventory(user, connection);
        System.out.println("Car Dealership Currently Has " + dealershipInventory[dealershipInventory.length-1] + " In Stock:");
        for (int i = 0; i < dealershipInventory.length - 1; i++) {

            System.out.println(dealershipInventory[i]);
        }
        return dealershipInventory;
    }

    public DealershipUser getUser() {
        return user;
    }

    public UserService getUserService() {
        return userService;
    }

}
