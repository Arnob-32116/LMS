package com.example.loginpagemain;

import java.sql.*;
import java.util.ArrayList;

public class AdminDatabase {

    String Student_Id;
    AdminDatabase(String Student_Id){
        this.Student_Id = Student_Id;
    }
    AdminDatabase(){

    }

    ArrayList<String> get_course_name(){
        ArrayList<String> course_name = new ArrayList<String>();
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            String insertQuery = "Select * from Course_info where Serial=1";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(insertQuery);
            while (resultSet.next()){
                for(int i = 2 ; i <= 19 ; i++) {
                    course_name.add(resultSet.getString(i));
                }
            }
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println(exception);
        }
        return course_name;
    }

    ArrayList<String> get_course_credit(){
        ArrayList<String> course_credit = new ArrayList<String>();
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            String insertQuery = "Select * from Course_info where Serial=2";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(insertQuery);
            while (resultSet.next()){
                for(int i = 2 ; i <= 19 ; i++) {
                    course_credit.add(resultSet.getString(i));
                }
            }
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println(exception);
        }
        return course_credit;
    }

    ArrayList<String> get_course_done_by_student(){
        ArrayList<String> course_done = new ArrayList<String>();
        Connection connection = null;

        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            String insertQuery = "Select * from Student_Course_Selection where Student_Id="+Student_Id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(insertQuery);
            while (resultSet.next()){
                for(int i = 2 ; i < 19 ; i++) {
                    course_done.add(resultSet.getString(i));
                }
            }
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println(exception);
        }
        return course_done;
    }

    ArrayList<String> get_course_title(){
        ArrayList<String> course_title = new ArrayList<String>();
        Connection connection = null;

        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            String insertQuery = "SELECT COLUMN_NAME, ORDINAL_POSITION, DATA_TYPE\n" +
                    "FROM INFORMATION_SCHEMA.COLUMNS\n" +
                    "WHERE TABLE_NAME = 'Course_info'\n" +
                    "ORDER BY 2;\n";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(insertQuery);
            while (resultSet.next()){
                    course_title.add(resultSet.getString(1));
            }
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println(exception);
        }
        return course_title;
    }


    void insert_admin_course_selection(ArrayList<String>student_id_selected , ArrayList<String> course_selected) {
        System.out.println("DONE DONE DONE");
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            for(int i = 0 ; i < student_id_selected.size() ; i++){
                for(int j = 0 ; j < course_selected.size() ; j++) {
                    String insertQuery = "UPDATE Student_Course_Selection SET " +course_selected.get(j)+" ='Current' WHERE Student_ID= '"+student_id_selected.get(i)+"'";
                    System.out.println(insertQuery);
                    Statement statement = connection.createStatement();
                    statement.execute(insertQuery);
                }
            }
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println(exception);
        }
    }

}
