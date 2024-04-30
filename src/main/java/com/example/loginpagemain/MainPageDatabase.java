package com.example.loginpagemain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MainPageDatabase {
    ArrayList<Integer> get_port(ArrayList<String> current_course){
        ArrayList<Integer>  port = new ArrayList<Integer>();
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            for (int i = 0 ; i < current_course.size() ; i++) {
                String insertQuery = "SELECT Port_Number FROM myDB.Port_Number_of_Courses WHERE Course_Code='" + current_course.get(i) + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(insertQuery);
                while (resultSet.next()) {
                    port.add(resultSet.getInt(1));
                }
            }
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println(exception);
        }
        return port;
    }
}
