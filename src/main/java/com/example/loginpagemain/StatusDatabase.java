package com.example.loginpagemain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatusDatabase {
    String status,date,tag,username;
    StatusDatabase(String status , String date, String tag , String username){
        this.username = username;
        this.status = status;
        this.tag = tag;
        this.date = date;
    }

    void status_information_connection() {
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            String insertQuery = "insert into Newsfeed(Username,Status,Tag,Date) values (? , ? , ? , ? )";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery) ;
            // Set parameter values dynamically
            preparedStatement.setString(1, username); // username
            preparedStatement.setString(2, status); // status
            preparedStatement.setString(3, tag); // tag
            preparedStatement.setString(4, date); // date
            try {
                preparedStatement.execute();
            }
            catch (SQLException exception){
                System.out.println(exception);
            }
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println(exception);
        }
    }
}
