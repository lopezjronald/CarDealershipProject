package com.dealership.service;

import com.dealership.model.DealershipUser;
import com.dealership.model.Offer;
import com.dealership.model.Vehicle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UserService {

    private final int EMPLOYEE_NUMBER = 1;
    private final int OWNER_NUMBER = 3;


    private Vehicle getVehicleInformation(Scanner scanner, DealershipUser user) {
        System.out.println("Please enter vehicle information:");
        System.out.println("VIN Number: ");
        String vin = scanner.nextLine();
        System.out.println("Vehicle Make: ");
        String vehicleMake = scanner.nextLine();
        System.out.println("Vehicle Model: ");
        String vehicleModel = scanner.nextLine();
        System.out.println("Vehicle Year: ");
        Integer vehicleYear;

        while (true) {
            try {
                vehicleYear = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Sorry, that is an invalid entry. Please enter the year for the vehicle.");
                scanner.nextLine();
                continue;
            }
            break;
        }

        Integer employeeId = user.getUserId();
        return new Vehicle(vin, vehicleMake, vehicleModel, vehicleYear, employeeId);
    }

    public String addVehicle(DealershipUser user, Connection connection, Scanner scanner) {
        if (checkUserType(user)) {
            Vehicle newVehicle = getVehicleInformation(scanner, user);

            try {
                String sql =
                        "INSERT INTO vehicle " +
                                "(vehicle_vin, vehicle_make, vehicle_model, vehicle_year, owner_id) " +
                                "VALUES " + "('" +
                                newVehicle.getVin() + "', '" +
                                newVehicle.getMake() + "', '" +
                                newVehicle.getModel() + "', '" +
                                newVehicle.getYear() + "', '" +
                                newVehicle.getOwnerId() + "')";

                Statement statement = connection.createStatement();
                int i = statement.executeUpdate(sql);
                System.out.println(capitalizeString(user.getFirstName()) + ", you have successfully added the " + capitalizeString(newVehicle.getMake()) + ".");
                return "Successful Entry";
            } catch (SQLException e) {
                e.printStackTrace();
                return "Something went wrong";
            }
        } else
            return "Sorry. Only workers can add new vehicles to the inventory";
    }

    public String askForVin(Scanner scanner) {
        System.out.print("Enter Vehicle VIN#: ");
        return scanner.nextLine();
    }

    public void removeVehicle(DealershipUser user, Connection connection, Scanner scanner) {
        if (checkUserType(user)) {
            String vin = askForVin(scanner);
            int vehicleUser = checkIfVehicleBelongsToCustomer(vin, connection);
            if (vehicleUser != 2) {
                try {
                    String sql =
                            "DELETE FROM vehicle WHERE vehicle_vin = '" + vin + "'";

                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    System.out.println(capitalizeString(user.getFirstName()) + ", you have successfully removed the vehicle with VIN #: " + vin + ".");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("VIN " + vin + " does not exist in the system");
                }
            } else
                System.out.println("You do not have permission to delete vehicles that belong to customers.");

        } else if (user.getUserType() == 2)
            System.out.println("Sorry. Only workers can remove vehicles from the inventory");
    }

    public String checkIfVehicleExists(DealershipUser user, Connection connection, Scanner scanner) {
        String vin = askForVin(scanner);
        int vehicleUser = checkIfVehicleBelongsToCustomer(vin, connection);
        if (vehicleUser == -1) {
            System.out.println("Sorry, vehicle with Vin # " + vin + " does not exist");
            return null;
        } else if (vehicleUser != 2) {
            return vin;
        } else if (user.getUserType() == 2)
            System.out.println("Sorry. This vehicle belongs to a customer.");
        return null;
    }

    public Offer makeOffer(DealershipUser user, UserService userService, Scanner scanner, Connection connection) {
        Offer newOffer;
        String vin = checkIfVehicleExists(user, connection, scanner);
        System.out.println("Hello " + userService.capitalizeString(user.getFirstName()) + ". Please enter your offer");
        while (true) {
            try {
                double offerAmount = scanner.nextDouble();
                newOffer = new Offer(offerAmount, user.getUserId(), vin);
                break;

            } catch (Exception e) {
                System.out.println("You have entered an invalid entry. Please enter your offer amount.");
                continue;
            }
        }
        addOfferIntoDatabase(user, newOffer, connection);
        return newOffer;
    }

    public void addOfferIntoDatabase(DealershipUser user, Offer offer, Connection connection) {
        try {
            String sql =
                    "INSERT INTO offer " +
                            "(offer_amount, user_id, vehicle_id) " +
                            "VALUES " + "('" +
                            offer.getOfferAmount() + "', '" +
                            offer.getUserId() + "', '" +
                            offer.getVehicleId() + "')";
            Statement statement = connection.createStatement();
            int i = statement.executeUpdate(sql);
            System.out.println(capitalizeString(user.getFirstName()) + ", you have successfully made an offer of $" + offer.getOfferAmount() + " for Vehicle with VIN # " + offer.getVehicleId() + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



//    public String retrieveVehicleByVin(Connection connection, String vin) {
//        try {
//            String sql = "SELECT vehicle_vin FROM vehicle WHERE vehicle_vin = '" + vin + "'";
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                return resultSet.getString(1);
//            }
//        } catch (SQLException e) {
//            System.out.println("Exception occurred");
//        }
//        return null;
//    }

//    public void addOffer(DealershipUser user, Connection connection, Scanner scanner) {
//        if (!checkUserType(user)) {
//            String vin = askForVin(scanner);
//            int inventoryCount = inventoryCount(user, connection);
//            String[] inventory = new String[inventoryCount + 1];
//            String sql =
//                    "SELECT vehicle_vin, vehicle_make, vehicle_model, vehicle_year " +
//                            "FROM vehicle " +
//                            "INNER JOIN dealership_user " +
//                            "ON dealership_user.id = vehicle.owner_id " +
//                            "INNER JOIN user_type " +
//                            "ON user_type.id = dealership_user.user_type " +
//                            "WHERE user_type.id = '" + EMPLOYEE_NUMBER +
//                            "' OR user_type.id ='" + OWNER_NUMBER + "'";
//            try {
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(sql);
//                int count = 0;
//                String description;
//                while (resultSet.next()) {
//                    description = resultSet.getString("vehicle_vin") +
//                            " | Make: " + resultSet.getString("vehicle_make") +
//                            " | Model: " + resultSet.getString("vehicle_model") +
//                            " | Year: " + resultSet.getString("vehicle_year");
//                    inventory[count++] = description;
//                }
//                inventory[count] = Integer.toString(inventoryCount);
//            } catch (SQLException e) {
//
//            }
//            int vehicleUser = checkIfVehicleBelongsToDealership(vin, connection);
//            if (vehicleUser != 2) {
//                try {
//                    String sql =
//                            "DELETE FROM vehicle WHERE vehicle_vin = '" + vin + "'";
//
//                    Statement statement = connection.createStatement();
//                    statement.executeUpdate(sql);
//                    System.out.println(capitalizeString(user.getFirstName()) + ", you have successfully entered an offer for vehicle with VIN #: " + vin + ".");
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                    System.out.println("VIN " + vin + " is not in the dealership lot.");
//                }
//            } else
//                System.out.println("You do not have permission to delete vehicles that belong to customers.");
//        }
//    }


    public int save(DealershipUser user, Connection connection) {
        try {
            String sql =
                    "insert into dealership_user " +
                            "(username, user_password, first_name, last_name, user_type) " +
                            "values " + "('" +
                            user.getUsername() + "', '" +
                            user.getUserPassword() + "', '" +
                            user.getFirstName() + "', '" +
                            user.getLastName() + "', '" +
                            user.getUserType() + "')";

            Statement statement = connection.createStatement();
            int i = statement.executeUpdate(sql);
            System.out.println(capitalizeString(user.getFirstName()) + ", you have successfully registered as " + (user.getUserType().equals("customer") ? "a customer." : "an employee."));
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public DealershipUser loginQuery(Connection connection, String username, String password) {
        try {
            String sql = "SELECT * FROM dealership_user WHERE username = '" +
                    username + "' AND user_password = '" +
                    password + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Integer userId = resultSet.getInt(1);
                // username;
                // password;
                String firstName = resultSet.getString(4);
                String lastName = resultSet.getString(5);
                Integer userTypeId = resultSet.getInt(6);
                return new DealershipUser(userId, username, password, firstName, lastName, userTypeId);

            }
        } catch (SQLException e) {
            return new DealershipUser();
        }
        return new DealershipUser();
    }

    private int inventoryCount(DealershipUser user, Connection connection) {
        int inventoryCount = 0;
        String sql =
                "SELECT COUNT(*) " +
                        "FROM vehicle " +
                        "INNER JOIN dealership_user " +
                        "ON dealership_user.id = vehicle.owner_id " +
                        "INNER JOIN user_type " +
                        "ON user_type.id = dealership_user.user_type " +
                        "WHERE user_type.id = '" + EMPLOYEE_NUMBER +
                        "' OR user_type.id ='" + OWNER_NUMBER + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("At this time, you have " + inventoryCount + " that you own.");
        }
        return inventoryCount;
    }

    public String[] viewDealershipInventory(DealershipUser user, Connection connection) {
        int inventoryCount = inventoryCount(user, connection);
        String[] inventory = new String[inventoryCount + 1];
        String sql =
                "SELECT vehicle_vin, vehicle_make, vehicle_model, vehicle_year " +
                        "FROM vehicle " +
                        "INNER JOIN dealership_user " +
                        "ON dealership_user.id = vehicle.owner_id " +
                        "INNER JOIN user_type " +
                        "ON user_type.id = dealership_user.user_type " +
                        "WHERE user_type.id = '" + EMPLOYEE_NUMBER +
                        "' OR user_type.id ='" + OWNER_NUMBER + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int count = 0;
            String description;
            while (resultSet.next()) {
                description = "Vehicle VIN #: " + resultSet.getString("vehicle_vin") +
                        " | Make: " + resultSet.getString("vehicle_make") +
                        " | Model: " + resultSet.getString("vehicle_model") +
                        " | Year: " + resultSet.getString("vehicle_year");
                inventory[count++] = description;
            }
            inventory[count] = Integer.toString(inventoryCount);
            return inventory;
        } catch (SQLException e) {
            return inventory;
        }
    }


    private int getUserVehicleCount(DealershipUser user, Connection connection) {
        int inventoryCount = 0;
        if (user.getUserType() == 1 || user.getUserType() == 3) {
            System.out.println("Sorry " + capitalizeString(user.getFirstName()) + ", you must be logged in as a customer to view your vehicles.");
            return 0;
        }
        try {
            String sql = "SELECT COUNT(*) FROM vehicle WHERE owner_id = " + user.getUserId();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("At this time, you have " + inventoryCount + " that you own.");
        }
        return inventoryCount;
    }

    public String[] viewUserVehicles(DealershipUser user, Connection connection) {
        String[] inventory = new String[getUserVehicleCount(user, connection)];
        if (inventory.length > 0) {
            try {
                String sql = "SELECT * FROM vehicle WHERE owner_id = " + user.getUserId();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                int count = 0;
                String description;
                while (resultSet.next()) {
                    description = "Vehicle VIN #: " + resultSet.getString("vehicle_vin") +
                            " | Make: " + resultSet.getString("vehicle_make") +
                            " | Model: " + resultSet.getString("vehicle_model") +
                            " | Year: " + resultSet.getString("vehicle_year");
                    inventory[count++] = description;
                }
                return inventory;
            } catch (SQLException e) {
                return inventory;
            }
        }
        return inventory;
    }

    public String capitalizeString(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public boolean checkUserType(DealershipUser user) {
        return (user.getUserType() == EMPLOYEE_NUMBER || user.getUserType() == OWNER_NUMBER);
    }

    public int checkIfVehicleBelongsToCustomer(String vin, Connection connection) {
        String sql =
                "SELECT dealership_user.user_type " +
                        "FROM vehicle " +
                        "INNER JOIN dealership_user " +
                        "ON dealership_user.id = vehicle.owner_id " +
                        "INNER JOIN user_type " +
                        "ON user_type.id = dealership_user.user_type " +
                        "WHERE vehicle_vin ='" + vin + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            return -1;
        }
        return -1;
    }


}
