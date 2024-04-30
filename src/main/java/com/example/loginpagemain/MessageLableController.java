package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageLableController implements Initializable {
    @FXML
    Text message_text;
    @FXML
    Label Message_Text_Username;

    String message , username;

    public void SetMessageAndUsername(String message , String username){
        this.Message_Text_Username.setText(username);
        this.message_text.setText(message);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
