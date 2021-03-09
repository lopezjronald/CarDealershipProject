package com.dealership.service;

import com.dealership.model.DealershipUser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService {

    private final int EMPLOYEE_NUMBER = 1;
    private final int CUSTOMER_NUMBER = 2;
    private final int OWNER_NUMBER = 3;

    public String[] inventoryQuery(DealershipUser user, Connection connection) {
        int inventoryAmount = 0;
        String sql;
        String sqlCount;

        if (user.getUserType() == 2) {
            sqlCount = "SELECT COUNT (*) FROM vehicle WHERE owner_id ='" + CUSTOMER_NUMBER + "'";
            sql = "SELECT * FROM vehicle WHERE ower_id = '" + CUSTOMER_NUMBER + "'";
        } else {
            sqlCount = "SELECT COUNT (*) FROM vehicle WHERE ower_id = '" +
                    EMPLOYEE_NUMBER + "' OR owner_id = '" +
                    OWNER_NUMBER + "'";
            sql = "SELECT * FROM vehicle WHERE ower_id = '" +
                    EMPLOYEE_NUMBER + "' OR owner_id = '" +
                    OWNER_NUMBER + "'";
        }

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlCount);
            inventoryAmount = resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println("Currently own " + inventoryAmount + " vehicles.");
        }

        String[] inventory = new String[inventoryAmount];


        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int count = 0;
            while (resultSet.next()) {
                for (int i = count; i < inventory.length; i++) {
                    inventory[i] = resultSet.getString(1);
                }
                count++;
            }
            return inventory;
        } catch (SQLException e) {
            return inventory;
        }

    }

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

    public String[] loginQuery(Connection connection, String username, String password) {
        String[] userInfo = new String[6];
        try {
            String sql = "SELECT * FROM dealership_user WHERE username = '" +
                    username + "' AND user_password = '" +
                    password + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                userInfo[0] = Integer.toString(resultSet.getInt(1));
                userInfo[1] = username;
                userInfo[2] = password;
                userInfo[3] = resultSet.getString(4);
                userInfo[4] = resultSet.getString(5);
                userInfo[5] = Integer.toString(resultSet.getInt(6));

            }
        } catch (SQLException e) {
            return userInfo;
        }
        return userInfo;
    }

    public String getUsername(String[] userInfo) {
        return userInfo[1];
    }

}
