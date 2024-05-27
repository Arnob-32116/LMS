package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EvaluationController implements Initializable {
    @FXML
    private VBox Evaluation_Vbox;
    @FXML
    private Label student_id_evaluation;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private ArrayList<TextField> getTextField(){
        ArrayList<TextField> numbers_and_cgpa_textfiled = new ArrayList<TextField>();
        for(Node node : Evaluation_Vbox.getChildren()){
            if(node instanceof HBox hBox){
                for(Node node1 : hBox.getChildren()){
                    if(node1 instanceof TextField textField){
                        numbers_and_cgpa_textfiled.add(textField);
                    }
                }
            }
        }
        return numbers_and_cgpa_textfiled;
    }

    @FXML
    public void setTextField(ArrayList<Float> input_numbers_from_database){
        ArrayList<TextField> numbers_and_cgpa_textfiled = getTextField();
        for (int i = 0 ; i < input_numbers_from_database.size() ; i++){
            numbers_and_cgpa_textfiled.get(i).setText(String.valueOf(input_numbers_from_database.get(i)));
        }
    }
    @FXML
    public void setUserId(String Id ){
        student_id_evaluation.setText(Id);
    }




}
