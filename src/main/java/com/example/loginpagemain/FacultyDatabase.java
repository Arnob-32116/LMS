package com.example.loginpagemain;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class FacultyDatabase {

    public ArrayList<VBox> get_number_information(String Course_Code) throws Exception{
        ArrayList<VBox> evaluation_vbox = new ArrayList<VBox>();
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            String insertQuery = "Select * from "+Course_Code+"_Evaluation";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(insertQuery);
            while (resultSet.next()){
                ArrayList<Float> evaluations= new ArrayList<Float>();
                for(int i = 2; i <= 10 ; i++){
                    evaluations.add(resultSet.getFloat(i));
                }
                VBox vBox = new VBox();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Evaluation.fxml"));
                vBox = fxmlLoader.load();
                EvaluationController evaluationController = fxmlLoader.getController();
                evaluationController.setTextField(evaluations);
                evaluationController.setUserId(resultSet.getString(1));
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GiveResources.fxml"));
                vBox = fxmlLoader.load();
                GiveResources giveResources = fxmlLoader.getController();
                System.out.println(materials);
                giveResources.setCourseTitle(Course_Code);
                giveResources.SetExistingData(materials.get(1) , materials.get(0));
                giveResources.make_submit_unavailable();
                giveResources.setURL(materials.get(3));
                giveResources.make_addAttachment_unavailable();
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

    public void SubmitGrade(String Course_id , String student_id , ArrayList<Float>grades){
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
         //   String insertQuery = "INSERT INTO "+ Course_id+"_Evaluation VALUES ('WHERE " ++ "', 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 )";
            String insertQuery = "UPDATE "+Course_id+"_Evaluation"
           +" SET CT1 = "+grades.get(0)+", CT2 = "+grades.get(1)+", CT3 ="+grades.get(2)+", Best_CT_AVG = "+grades.get(3)+", Assignment1 = "+grades.get(4)+", Assignment2= "+grades.get(5)+", MID ="+grades.get(6)+", Final = "+grades.get(7) +", Final_grade = "+grades.get(8)
            +"WHERE Id = '"+student_id+"' " ;

            Statement statement = connection.createStatement();
             statement.execute(insertQuery);
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println(exception);
        }

    }

    public void submit_materials(String Text , String MaterialType , String Attachment_URL , String Course_Code){
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myDB",
                    "Arnob", "password_3306");
            //   String insertQuery = "INSERT INTO "+ Course_id+"_Evaluation VALUES ('WHERE " ++ "', 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 )";
            String insertQuery = "INSERT INTO "+Course_Code+"_Materials (Text , Attachment_URL , Material_type) VALUES( '"+Text+"' , '"+MaterialType+"' , '"+Attachment_URL+"')";

            Statement statement = connection.createStatement();
            statement.execute(insertQuery);
            connection.close();
            System.out.println("Conncetion done");
        } catch (Exception exception) {
            //System.out.println("AR");
            System.out.println(exception);
        }
    }
}
