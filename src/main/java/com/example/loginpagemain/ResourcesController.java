package com.example.loginpagemain;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResourcesController implements Initializable {
    @FXML
    Label material_type_resources_lable , Date_type_resources_lable , course_title_resources_lable ;
    @FXML
    HBox attachment_hbox ;
    @FXML
    Text text_resources ;



    String url = "";
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public  void set_lables(String material , String Date , String Course_title){
        material_type_resources_lable.setText(material);
        Date_type_resources_lable.setText(Date);
        course_title_resources_lable.setText(Course_title);
    }

    public  void set_Text(String text ){
        text_resources.setText(text);
    }

    void  setURL(String url){
        this.url = url ;
    }

    @FXML
    public void Start() {
        if(!url.isEmpty()) {
            try {
                String pdfFilePath = url; // Replace with the actual file path
                ProcessBuilder processBuilder = new ProcessBuilder("xdg-open", pdfFilePath);
                processBuilder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
