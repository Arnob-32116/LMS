package com.example.loginpagemain;

import animatefx.animation.FadeInRight;
import animatefx.animation.Shake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;


public class LoginAndSignUpController implements Initializable{
        private Stage stage;
        private Scene scene;
        private Parent root;
        private List<TextField> textFields = new ArrayList<>();

        private Stack<Pane> panes = new Stack<>();

    @FXML
        private Button signin_button;
        @FXML
        private Hyperlink signup_button,forget_pass_button;
        @FXML
        private BorderPane borderpane1;
        @FXML
        private Pane pane1,pane2,pane3,pane4,pane5;
        @FXML
        private Button next_button , back_button, back_button_page2;
        @FXML
        public void signin_button_control(ActionEvent event) throws Exception{
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
        public void sign_up_btn_control_method(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        new FadeInRight(root).play();
        }


        @FXML
        public void signup_next(ActionEvent event) throws Exception{
            Button button =(Button) event.getSource();

            if (button == next_button && panes.size()==0 ){
                panes.push(pane3);
                panes.push(pane5);
            }
            else if (button == next_button && panes.size()==1){
                panes.push(pane5);
            }
            else if(button==back_button || button==back_button_page2){
                if (panes.size()!=0)
                panes.pop();
               // System.out.println(panes.firstElement());
            }
            if(panes.empty()) {
                Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                new FadeInRight(root).play();
            }
            else {
                if (panes.size() >=2 && empty_text_field_shake(panes.get(panes.size() - 2))){
                    new FadeInRight(panes.peek()).play();
                    panes.peek().toFront();
                }
                else if (panes.size() ==1 && empty_text_field_shake(pane3)){
                    new FadeInRight(panes.peek()).play();
                    panes.peek().toFront();
                }
                else{
                    empty_text_field_shake(panes.peek());
                }
            }

        }


        @FXML
        public void unclick_textarea(MouseEvent event) throws Exception {
            Boolean check = false;
            Pane pane = (Pane) event.getSource();
            for (Node node : pane.getChildren()) {
                if (node instanceof TextField) {
                    textFields.add((TextField) node);
                }
            }

            for(TextField text_fields : textFields){
                if(text_fields.isFocused()){
                    pane.requestFocus();
                    break;
                }
            }

        }

        @FXML
        public void back_button_control(ActionEvent event){

        }

        @FXML
        public void number_constrains_for_text_fields(KeyEvent event){
                TextField numfield = (TextField) event.getSource();
                if(!event.getCharacter().matches("\\d")){
                    event.consume();
                    if(event.getCharacter().matches("\b") && numfield.getText().length() - 1 != 0){
                        String text = numfield.getText();
                        Boolean check = true;
                        for(int i = 1 ; i <= text.length() ; i++){
                            if(text.charAt(i-1) == '0' || text.charAt(i-1) == '1' || text.charAt(i-1) == '2'
                                    || text.charAt(i-1) == '3' || text.charAt(i-1) == '4' ||
                                    text.charAt(i-1) == '5' || text.charAt(i-1) == '6' ||
                                    text.charAt(i-1) == '7' || text.charAt(i-1) == '8' ||
                                    text.charAt(i-1) == '9') {
                                check = true;
                            }
                            else{
                                check = false;
                                break;
                            }
                        }
                        if (check)
                            numfield.setStyle("-fx-border-color: black");
                        else
                            numfield.setStyle("-fx-border-color: red");
                    }
                    else {
                        numfield.setStyle("-fx-border-color: red");
                    }
                }
                else {
                    String text = numfield.getText();
                    Boolean check = true;
                    for(int i = 1 ; i <= text.length() ; i++){
                        if(text.charAt(i-1) == '0' || text.charAt(i-1) == '1' || text.charAt(i-1) == '2'
                                || text.charAt(i-1) == '3' || text.charAt(i-1) == '4' ||
                                text.charAt(i-1) == '5' || text.charAt(i-1) == '6' ||
                                text.charAt(i-1) == '7' || text.charAt(i-1) == '8' ||
                                text.charAt(i-1) == '9') {
                            check = true;
                        }
                        else{
                            check = false;
                            break;
                        }
                    }
                    if (check)
                        numfield.setStyle("-fx-border-color: black");
                    else
                        numfield.setStyle("-fx-border-color: red");
                }
        }


        private Boolean empty_text_field_shake(Pane pane){
            System.out.println(pane);
            List<TextField> textFields = new ArrayList<>();
            for (Node node : pane.getChildren()) {
                if (node instanceof TextField) {
                    textFields.add((TextField) node);
                }
            }
            for(TextField text_fields : textFields){
                if(text_fields.getText().isEmpty()==true){
                    new Shake(text_fields).play();
                    return false;
                }
            }
            return  true;
        }







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}