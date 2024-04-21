package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Post implements Initializable{
    @FXML
    Label username_lable,tag_lable,date_lable;
    @FXML
    Text post_text_text;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginDatabase loginDatabase = new LoginDatabase();
        username_lable.setText(MainPage.status_username);
        post_text_text.setText(MainPage.status);
        tag_lable.setText(MainPage.post_tag);
        date_lable.setText(MainPage.post_date);
    }
}
