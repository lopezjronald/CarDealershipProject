package com.dealership.database;

import com.dealership.model.DealershipUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {
//    private static ConnectionUtil instance;

    public ConnectionUtil() {
    }

//    public static ConnectionUtil getInstance(){
//        if(instance == null){
//            instance = new ConnectionUtil();
//        }
//        return instance;
//    }

    public static void main(String[] args) throws SQLException {


    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://dealership.cnftml2dw6bt.us-east-2.rds.amazonaws.com:5432/postgres?currentSchema=public",
                "postgres",
                "password");
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


}
