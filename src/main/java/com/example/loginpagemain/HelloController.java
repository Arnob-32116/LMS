package com.example.loginpagemain;

import animatefx.animation.Bounce;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeInRight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable{
        private Stage stage;
        private Scene scene;
        private Parent root;

        @FXML
        private Button signin_button;
        @FXML
        private Hyperlink signup_button,forget_pass_button;
        @FXML
        private BorderPane borderpane1;
        @FXML
        private Pane pane1,pane2,pane3,pane4;
        @FXML
        public void button_animation(ActionEvent event) throws Exception{
            new FadeInRight(signin_button).play();
            Parent root = FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            //root.setOnMouseClicked(new FadeIn(pane3).play());
            new FadeInRight(root).play();
        }
        @FXML
        public void sign_up_method(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        new FadeInRight(root).play();
        }

        @FXML
        void unclick_textarea(ActionEvent event) throws Exception{
            
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}