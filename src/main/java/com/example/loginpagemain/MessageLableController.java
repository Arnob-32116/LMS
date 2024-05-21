package com.example.loginpagemain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageLableController implements Initializable {
    @FXML
    Text message_text;
    @FXML
    Label Message_Text_Username;
    @FXML
    HBox messageLableHBOX;


    String message , username;

    void SetMessageAndUsername(String message , String username){
        this.Message_Text_Username.setText(username);
        this.message_text.setText(message);
    }
    void setTextColorOfUserSentMessage(){
        messageLableHBOX.setStyle("-fx-background-color: #2769B4");

    }

    void setTextColorOfOthersSentMessage(){
       messageLableHBOX.setStyle("-fx-background-color:#EECC37");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
