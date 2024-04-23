package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    Pane admin_section_pane;
    @FXML
    static ScrollPane available_Courses_scrollpane , student_id_scrollpane;
    @FXML
    TextField search_student_id_textfield;
    public  static String credit , course_code, course_name , static_student_id , static_student_name;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            admin_get_allsection();
            admin_get_student_info();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    void admin_get_allsection() throws Exception {
        ArrayList<String> course_credit = new ArrayList<String>();
        ArrayList<String> course_nameall = new ArrayList<String>();
        ArrayList<String> course_title = new ArrayList<String>();
        AdminDatabase adminDatabase = new AdminDatabase();
        course_credit = adminDatabase.get_course_credit();
        course_nameall = adminDatabase.get_course_name();
        course_title = adminDatabase.get_course_title();
        VBox parent = new VBox();
        for (int i = 0; i < course_title.size() - 1; i++) {
            credit = course_credit.get(i);
            course_code = course_title.get(i);
            course_name = course_nameall.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("section.fxml"));
            HBox hBox = fxmlLoader.load();
            parent.getChildren().add(hBox);
        }
        available_Courses_scrollpane.setContent(parent);
    }

    void admin_get_student_info() throws Exception{
        ArrayList<String> student_name = new ArrayList<String>();
        ArrayList<String> student_id = new ArrayList<String>();
        ArrayList<String> acess_level = new ArrayList<String>();
        LoginDatabase loginDatabase = new LoginDatabase();
        student_name = loginDatabase.get_all_student_name();
        student_id = loginDatabase.get_all_student_ID();
        acess_level = loginDatabase.get_all_acess_level();
        VBox parent = new VBox();
        for(int i = 0 ; i < student_name.size() ; i++){
            if(!acess_level.get(i).equals("0"))
                continue;
            static_student_name = student_name.get(i);
            static_student_id = student_id.get(i);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Student_ID.fxml"));
            HBox hbox = fxmlLoader.load();
            parent.getChildren().add(hbox);
        }
        student_id_scrollpane.setContent(parent);
    }

    @FXML
    void admin_get_search_student_info() throws Exception{
        ArrayList<String> student_name = new ArrayList<String>();
        ArrayList<String> student_id = new ArrayList<String>();
        ArrayList<String> acess_level = new ArrayList<String>();
        LoginDatabase loginDatabase = new LoginDatabase();
        student_name = loginDatabase.get_all_student_name();
        student_id = loginDatabase.get_all_student_ID();
        acess_level = loginDatabase.get_all_acess_level();
        String search =search_student_id_textfield.getText();
        VBox parent = new VBox();
        student_id_scrollpane.setContent(parent);
        for(int i = 0 ; i < student_name.size() ; i++){
            if(!acess_level.get(i).equals("0"))
                continue;
            static_student_name = student_name.get(i);
            static_student_id = student_id.get(i);
            if(static_student_id.contains(search))
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Student_ID.fxml"));
                HBox hbox = fxmlLoader.load();
                parent.getChildren().add(hbox);
            }
        }
        student_id_scrollpane.setContent(parent);
    }





}
