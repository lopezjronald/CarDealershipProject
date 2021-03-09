package com.dealership.service;

import com.dealership.model.DealershipUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService {

    private final int EMPLOYEE_NUMBER = 1;
    private final int OWNER_NUMBER = 3;

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
            System.out.println("The number of updated rows were " + i);
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
        String sql;
        if (user.getUserType() == EMPLOYEE_NUMBER || user.getUserType() == OWNER_NUMBER) {
            sql = "SELECT COUNT(*) FROM vehicle WHERE owner_id = " +
                    EMPLOYEE_NUMBER + " OR owner_id =" + OWNER_NUMBER;
        } else {
            sql = "SELECT COUNT(*) FROM vehicle WHERE owner_id = " +
                    user.getUserId();
        }
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


    public String[] retrieveInventory(DealershipUser user, Connection connection) {
        String[] inventory = new String[inventoryCount(user, connection)];
        String sql;
        if (user.getUserType() == EMPLOYEE_NUMBER || user.getUserType() == OWNER_NUMBER) {
            sql = "SELECT * FROM vehicle WHERE owner_id = '" +
                    EMPLOYEE_NUMBER + "' OR owner_id ='" + OWNER_NUMBER + "'";
        } else {
            sql = "SELECT * FROM vehicle WHERE owner_id = " + user.getUserId();
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int count = 0;
            String description;
            while (resultSet.next()) {
                description =   "Vehicle VIN #: " + resultSet.getString("vehicle_vin") +
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

}
