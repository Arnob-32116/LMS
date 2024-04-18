package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Post extends MainPage implements Initializable{
    @FXML
    Label username_lable,tag_lable,date_lable;
    @FXML
    Text post_text_text;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginDatabase loginDatabase = new LoginDatabase();
        username_lable.setText(loginDatabase.getUsername());
        post_text_text.setText(status_text_area.getText());
        tag_lable.setText("tag");
        date_lable.setText("date");
    }
}
