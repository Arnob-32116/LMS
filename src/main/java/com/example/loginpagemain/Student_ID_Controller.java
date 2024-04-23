package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Student_ID_Controller implements Initializable {
    @FXML
    Label student_id_lable,full_name_lable;
    @FXML
    HBox admin_student_id_hbox;
    @FXML
    ScrollPane student_id_scrollpane;
    String prev_selected_id = "-100";
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        student_id_lable.setText(AdminController.static_student_id);
        full_name_lable.setText(AdminController.static_student_name);
    }

    @FXML
    void get_selected_student() {
        System.out.println(student_id_lable.getText());
        System.out.println(full_name_lable.getText());
    }

}
