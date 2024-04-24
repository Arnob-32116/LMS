package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Section implements Initializable {
    @FXML
    Label section_credit_lable, section_course_title_lable,section_course_code_lable;
    @FXML
    HBox section_hbox;
    @FXML
    Button section_add_btn , section_remove_btn;

    int number_of_times_clicked = 0 ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(AdminController.credit);
        section_credit_lable.setText("Credit  " + AdminController.credit);
        section_course_code_lable.setText(AdminController.course_code);
        section_course_title_lable.setText(AdminController.course_name);
        section_hbox.setStyle("-fx-border-color: black ; -fx-border-width: 5px;");
        section_remove_btn.setVisible(false);
    }

    @FXML
    void when_selected(){
            section_hbox.setStyle("-fx-border-color: blue ; -fx-border-width: 5px;");
            section_remove_btn.setVisible(true);
            section_add_btn.setVisible(false);
    }


    @FXML
    void when_unselected(){
        section_hbox.setStyle("-fx-border-color: black ; -fx-border-width: 5px;");
        section_add_btn.setVisible(true);
        section_remove_btn.setVisible(false);
    }

}
