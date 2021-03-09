package com.dealership.service;

import com.dealership.model.DealershipUser;
import com.dealership.model.Offer;

import java.sql.Connection;
import java.util.Scanner;

public class EmployeeService {

    private UserService userService;
    private DealershipUser user;

    public EmployeeService(UserService userService, DealershipUser user) {
        this.userService = userService;
        this.user = user;
    }

    public void displayOffers(Connection connection, Scanner scanner, UserService userService) {
        Offer[] allOffers = userService.retrieveOffers(connection, scanner, userService);
        if (allOffers.length == 0) {
            System.out.println("There are no offers for the vehicle you requested.");
        } else {
            for (int i = 0; i < 1; i++) {
                System.out.println("Vehicle #: " + allOffers[i].getVehicleId());
            }
            for (Offer eachOffer : allOffers) {
                System.out.print("ID: " + eachOffer.getId());
                System.out.print(" | Offer Amount: $" + eachOffer.getOfferAmount());
                System.out.println(" | Customer #: " + eachOffer.getUserId());
            }
        }

        chooseFromOfferMenu(allOffers, scanner, user, connection);


    }

    public int offerMenu(Scanner scanner) {
        int userChoice = -1;
        System.out.println("Choose one of the following:");
        System.out.println("1: Accept an offer");
        System.out.println("2. Reject an offer");
        System.out.println("3. Exit");
        while (true) {
            try {
                String stringUserChoice = scanner.nextLine();
                userChoice = Integer.parseInt(stringUserChoice);
                break;
            } catch (Exception e) {
                System.out.println("That was an invalid entry. Please choose of the following.");
            }
        }
        return userChoice;
    }

    public void chooseFromOfferMenu(Offer[] allOffers, Scanner scanner, DealershipUser user, Connection connection) {
        int userChoice = offerMenu(scanner);
        switch (userChoice) {
            case 1:
                System.out.println("Accept Offer Function");
                break;
            case 2:
                userService.removeVehicleFromOffers(user, connection, scanner);
                break;
            case 3:
                System.out.println("Exit");
                break;
            default:
                System.out.println("Default");
        }
    }

}
