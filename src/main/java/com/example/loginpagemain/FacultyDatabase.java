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
}
