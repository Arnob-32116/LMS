package com.example.loginpagemain;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class AreyousureController implements Initializable {
    @FXML
    private PasswordField sure_password_check ;
    @FXML
    private Label is_password_correct_lable ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    public void Confirm(javafx.event.ActionEvent event) {
        if(sure_password_check.getText().equals("admin")){
            AdminDatabase adminDatabase = new AdminDatabase();
            if(AdminController.is_semster_break==0) {
                AdminController.is_semster_break = 1;
                adminDatabase.change_semester_break_to_on();
            }
            else{
                AdminController.is_semster_break = 0 ;
                adminDatabase.change_semester_break_to_off();
            }
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        }
        else{
            is_password_correct_lable.setText("Incorrect Password");
        }
    }


}
