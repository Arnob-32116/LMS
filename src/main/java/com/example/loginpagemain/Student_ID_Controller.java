package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Student_ID_Controller implements Initializable {
    @FXML
    private Label student_id_lable,full_name_lable;
    @FXML
    private HBox admin_student_id_hbox;
    @FXML
    private ScrollPane student_id_scrollpane;
    private int number_of_times_clicked =  0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        student_id_lable.setText(AdminController.static_student_id);
        full_name_lable.setText(AdminController.static_student_name);
        admin_student_id_hbox.setStyle("-fx-border-color: #E1FCFD; -fx-border-width : 5px");
//      //  admin_student_id_hbox.setBorder(new Border(new BorderStroke(Color.#E1FCFD;,BorderStrokeStyle.SOLID,null,new BorderWidths(5))));

    }

    @FXML
    void get_selected_student() throws Exception{
        number_of_times_clicked++;
        if(number_of_times_clicked%2==1)
            admin_student_id_hbox.setStyle("-fx-border-color: black; -fx-border-width : 5px");
        else
            admin_student_id_hbox.setStyle("-fx-border-color: #E1FCFD; -fx-border-width : 5px");


    }



}

//
//
//AdminController adminController = new AdminController();
//VBox all = new VBox() ;
//        all.getChildren().add(adminController.get_parent_vbox());
//        System.out.println("Test");
//        for(Node node : all.getChildren()){
//        System.out.println("Test2");
//            if(node instanceof VBox){
//VBox vBox = (VBox) node;
//                for(Node node1 : vBox.getChildren()){
//        if(node1 instanceof HBox){
//HBox hBox = (HBox) node1;
//                            for(Node node2 : hBox.getChildren()){
//        if (node2 instanceof Label){
//Label label = (Label) node2;
//String s1 = label.getText();
//                                        if(label.getId().equals("student_id_lable") && s1.equals(student_id_lable.getText())){
//        admin_student_id_hbox.setBorder(new Border(new BorderStroke(Color.BLUE,BorderStrokeStyle.SOLID,null,new BorderWidths(5))));
//        System.out.println("Condition FullFIlling");
//                                        }
//                                                else{
//
//                                                }
//                                                }
//                                                }
//                                                System.out.println("test");
//                    }
//                            }
//                            }