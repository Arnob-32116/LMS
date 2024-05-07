package com.example.loginpagemain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MessageButtonController implements Initializable {
    @FXML
    Button message_button_of_Courses;
    @FXML
    VBox message_button_vbox_fxml;
    public void setMessage_button_of_Courses(String title){
        message_button_of_Courses.setText(title);
    }
    @FXML
    public void Message_Button_Handeling(ActionEvent event){
        Button button = (Button)(Node)event.getSource();
        MainPage.Current_Button_Message = button.getText();
        FacultyController.Current_Button_Message = button.getText();
    }

    public void SetBackgroundColor(){
        message_button_vbox_fxml.setStyle("-fx-background-color : #000000");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
