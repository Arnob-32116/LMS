package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Section implements Initializable {
    @FXML
    Label section_credit_lable, section_course_title_lable,section_course_code_lable;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(AdminController.credit);
        section_credit_lable.setText("Credit  " + AdminController.credit);
        section_course_code_lable.setText(AdminController.course_code);
        section_course_title_lable.setText(AdminController.course_name);
    }
}
