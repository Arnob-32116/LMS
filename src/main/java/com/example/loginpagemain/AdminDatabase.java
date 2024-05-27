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

    ArrayList<String> get_current_course_by_student(String Student_id){
        ArrayList<String> current_course = new ArrayList<String>();
        ArrayList<String> course_titles_list = new ArrayList<String>();
        ArrayList<String> courses_given_list = new ArrayList<String>();
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            String insertQuery1 = "SELECT COLUMN_NAME, ORDINAL_POSITION, DATA_TYPE\n" +
                    "FROM INFORMATION_SCHEMA.COLUMNS\n" +
                    "WHERE TABLE_NAME = 'Student_Course_Selection'\n" +
                    "ORDER BY 2;\n";
            String insertQuery = "Select * from Student_Course_Selection where Student_Id="+Student_id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(insertQuery);
            while (resultSet.next()){
                for(int i = 2 ; i < 19 ; i++) {
                        current_course.add(resultSet.getString(i));
                }
            }

            resultSet = statement.executeQuery(insertQuery1);

            while (resultSet.next()){
                course_titles_list.add(resultSet.getString(1));
            }
            connection.close();
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println(exception);
        }

        for(int i = 0 , j = 1 ; i<current_course.size() ; i++, j++ ){
            if(current_course.get(i) == null)
                continue;
            if(current_course.get(i).equals("Current")){
                courses_given_list.add(course_titles_list.get(j));
            }
        }

        return courses_given_list;
    }


    ArrayList<Integer> get_index_undone_course_by_student(String Student_id){
        ArrayList<String> current_course = new ArrayList<String>();
        ArrayList<String> course_titles_list = new ArrayList<String>();
        ArrayList<Integer> courses_given_index = new ArrayList<Integer>();
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            String insertQuery1 = "SELECT COLUMN_NAME, ORDINAL_POSITION, DATA_TYPE\n" +
                    "FROM INFORMATION_SCHEMA.COLUMNS\n" +
                    "WHERE TABLE_NAME = 'Student_Course_Selection'\n" +
                    "ORDER BY 2;\n";
            String insertQuery = "Select * from Student_Course_Selection where Student_Id="+Student_id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(insertQuery);
            while (resultSet.next()){
                for(int i = 2 ; i <= 19 ; i++) {
                    current_course.add(resultSet.getString(i));
                }
            }

            resultSet = statement.executeQuery(insertQuery1);

            while (resultSet.next()){
                course_titles_list.add(resultSet.getString(1));
            }
            connection.close();
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println(exception);
        }

        for(int i = 0 , j = 1 ; i< current_course.size() ; i++, j++ ){
            System.out.println(current_course.get(i));
            if(current_course.get(i) == null){
                courses_given_index.add(i);
            }
        }

        return courses_given_index;
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
                System.out.println("First loop");
                for(int j = 0 ; j < course_selected.size() ; j++) {
                    System.out.println("Second loop");
                    String insertQuery = "UPDATE Student_Course_Selection SET " +course_selected.get(j)+" ='Current' WHERE Student_ID= '"+student_id_selected.get(i)+"'";
                  //  System.out.println(insertQuery);
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

    public void add_evaluation (ArrayList<String>student_id_front_admin , ArrayList<String>course_from_admin){
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            for(int i = 0 ; i < course_from_admin.size() ; i++){
                for(int j = 0 ; j < student_id_front_admin.size() ; j++){
                    String insertQuery = "INSERT INTO "+ course_from_admin.get(i)+"_Evaluation VALUES ('" +student_id_front_admin.get(j)+ "', 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 )";
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
