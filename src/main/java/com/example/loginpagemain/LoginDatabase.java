package com.example.loginpagemain;


import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;

public class LoginDatabase {
    String username,student_name,password,student_id,student_number,guardian_number,email;
    int acess_level;
    LoginDatabase( String username,String student_name,String password,String student_id,String student_number,String guardian_number,String email,int acess_level){
        this.username = username;
        this.password = password;
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_number = student_number;
        this.guardian_number = guardian_number;
        this.email = email;
        this.acess_level = acess_level;
    }
    LoginDatabase(){

    }
    void Login_Information_connection() {
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            String insertQuery = "insert into Student_Login_Informations(Username,Name,Password,Id,Student_Number,Guardian_Number,Acess_Level,Email) values (? , ? , ? , ? , ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery) ;
            // Set parameter values dynamically
            preparedStatement.setString(1, username); // username
            preparedStatement.setString(2, student_name); // student_name
            preparedStatement.setString(3, password); // password
            preparedStatement.setString(4, student_id); // student_id
            preparedStatement.setString(5,  student_number); // student_number
            preparedStatement.setString(6, guardian_number); // guardian_number
            preparedStatement.setInt(7, 0); // access_level (integer)
            preparedStatement.setString(8,email);
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

    ArrayList<String> get_all_emails() {
        ArrayList<String> emails = new ArrayList<String>();
        String get_string_command = "SELECT Email FROM Student_Login_Informations";
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            Statement statement = connection.createStatement();
           ResultSet resultSet =  statement.executeQuery(get_string_command);
           while (resultSet.next()){
               emails.add(resultSet.getString(1));
             //  System.out.println(resultSet.getString(1));
           }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emails;
    }

    ArrayList<String> get_all_passwords() {
        ArrayList<String> passwords = new ArrayList<String>();
        String get_string_command = "SELECT Password FROM Student_Login_Informations";
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(get_string_command);
            while (resultSet.next()){
                passwords.add(resultSet.getString(1));
                //  System.out.println(resultSet.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return passwords;
    }

    ArrayList<Pair<String,String>> get_all_login_info() {
        ArrayList<Pair<String,String>> login_info = new ArrayList<Pair<String,String>>();
        ArrayList<String> emails = new ArrayList<String>();
        ArrayList<String>passwords = new ArrayList<String>();
       // String get_password_command = "SELECT Password FROM Student_Login_Informations";
        emails = get_all_emails();
        passwords = get_all_passwords();

           for(int i = 0 ; i < emails.size() ; i++){
               login_info.add(new Pair<>(emails.get(i) , passwords.get(i)));
           }


        return login_info;
    }

    public String getUsername(){
        String search_email = LoginAndSignUpController.email;
        String temp_username="";
        String get_string_command = "SELECT Username FROM Student_Login_Informations WHERE Email = '" + search_email+"'";
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            Statement statement = connection.createStatement();
            ResultSet resultSet= statement.executeQuery(get_string_command);
            while (resultSet.next()){
                temp_username = resultSet.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(temp_username);
    return temp_username;
    }

    public String getStudent_ID(){
        String search_email = LoginAndSignUpController.email;
        String temp_ID="";
        String get_string_command = "SELECT ID FROM Student_Login_Informations WHERE Email = '" + search_email+"'";
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            Statement statement = connection.createStatement();
            ResultSet resultSet= statement.executeQuery(get_string_command);
            while (resultSet.next()){
                temp_ID = resultSet.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp_ID;
    }

    ArrayList<String> get_all_student_name() {
        ArrayList<String> all_student_name = new ArrayList<String>();
        String get_string_command = "SELECT Name FROM Student_Login_Informations";
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(get_string_command);
            while (resultSet.next()){
                all_student_name.add(resultSet.getString(1));
                //  System.out.println(resultSet.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return all_student_name;
    }

    ArrayList<String> get_all_student_ID() {
        ArrayList<String> all_student_id = new ArrayList<String>();
        String get_string_command = "SELECT ID FROM Student_Login_Informations";
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(get_string_command);
            while (resultSet.next()){
                all_student_id.add(resultSet.getString(1));
                //  System.out.println(resultSet.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return all_student_id;
    }

    ArrayList<String> get_all_acess_level() {
        ArrayList<String> all_acess_level = new ArrayList<String>();
        String get_string_command = "SELECT Acess_Level FROM Student_Login_Informations";
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery(get_string_command);
            while (resultSet.next()){
                all_acess_level.add(resultSet.getString(1));
                //  System.out.println(resultSet.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return all_acess_level;
    }

    void add_courseinfo_to_signup_user(){
        String get_string_command = "INSERT INTO Student_Course_Selection (Student_ID) VALUES ('"+student_id+"')";
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            Statement statement = connection.createStatement();
            statement.execute(get_string_command);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
