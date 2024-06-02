package com.example.loginpagemain;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
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
                String insertQuery = "SELECT Port_Number FROM Port_Number_of_Courses WHERE Course_Code='" + current_course.get(i) + "'";
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
            System.out.println("T4");
            System.out.println(exception);
        }
        return port;
    }

    String get_course_code_of_port(int port){
        String course_code = "";
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            String insertQuery = "SELECT Course_Code FROM Port_Number_of_Courses WHERE Port_Number='"+port +"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(insertQuery);
            while (resultSet.next()){
                course_code = resultSet.getString(1);
            }
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println("T3");
            System.out.println(exception);
        }
        return course_code;

    }

    void sendMessage (String course_code , String username , String message){
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            int message_count = get_last_messagecount(course_code);
            String insertQuery = "INSERT INTO " +course_code+"_Messages (Message_Count, Messages, SenderEmail) VALUES ("+message_count+", '" + username + "' , '"+message+"')";
            Statement statement = connection.createStatement();
            statement.execute(insertQuery);
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println("T21");
            System.out.println(exception);
        }

    }

    ArrayList<Pair<String , String >> getMessage (String course_code){
        ArrayList<Pair<String , String >> username_and_messages = new ArrayList<Pair<String , String >>();
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            LocalDate dateInGMT = LocalDate.now();
            String insertQuery = "SELECT * FROM "+course_code+"_Messages";
            System.out.println(course_code + "This is the course code");
            System.out.println(insertQuery);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(insertQuery);
            while (resultSet.next()){
                username_and_messages.add(new Pair<String , String >(resultSet.getString(2) , resultSet.getString(3)));
            }
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return username_and_messages ;
    }

    private int  get_last_messagecount(String course_code){
        Connection connection = null;
        int message_count = 0 ;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            String insertQuery = "SELECT Message_Count FROM "+course_code+"_Messages ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(insertQuery);
            while (resultSet.next()){
                message_count = resultSet.getInt(1);
            }
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println("T21");
            System.out.println(exception);
        }

        return message_count;

    }

    public ArrayList<VBox> get_materials_information(String Course_Code) throws Exception{
        ArrayList<VBox> evaluation_vbox = new ArrayList<VBox>();
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            String insertQuery = "Select * from "+Course_Code+"_Materials";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(insertQuery);
            while (resultSet.next()){
                ArrayList<String> materials = new ArrayList<String>();
                for(int i = 1; i <= 4 ; i++){
                    materials.add(resultSet.getString(i));
                }
                VBox vBox = new VBox();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Resources.fxml"));
                vBox = fxmlLoader.load();
                ResourcesController resources = fxmlLoader.getController();
                resources.set_lables(materials.get(1),materials.get(2),Course_Code);
                resources.setURL(materials.get(3));
                resources.set_Text(materials.get(0));
                evaluation_vbox.add(vBox);
            }
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println(exception);
        }
        return evaluation_vbox;
    }




}
