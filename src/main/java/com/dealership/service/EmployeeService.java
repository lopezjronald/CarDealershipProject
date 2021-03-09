package com.dealership.service;

import com.dealership.model.Offer;

import java.sql.Connection;
import java.util.Scanner;

public class EmployeeService {

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
    }


}
