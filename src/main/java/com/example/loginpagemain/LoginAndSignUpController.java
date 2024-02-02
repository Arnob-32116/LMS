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
        private TextField signin_email_txt_field,signup_username_txt_field1,gurdian_phone_number ,signup_student_id_txt_field,signup_email_txt_field;
        @FXML
        public void signin_button_control(ActionEvent event) throws Exception{
            if(correct_email_check(signin_email_txt_field.getText())) {
                new FadeInRight(signin_button).play();
                Parent root = FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                //root.setOnMouseClicked(new FadeIn(pane3).play());
                new FadeInRight(root).play();
            }
            else{
                new Shake(signin_email_txt_field).play();
            }
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
    public void forgetpassword_btn_control_method(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ForgetPassword.fxml"));
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
                    if(panes.peek() == pane3){
                        if(all_numbers_textfield_check(signup_username_txt_field1.getText()) && all_numbers_textfield_check(gurdian_phone_number.getText()) ){
                            new FadeInRight(panes.peek()).play();
                            panes.peek().toFront();
                        }
                        else{
                            empty_text_field_shake(panes.peek());
                        }
                    }
                    else if (panes.peek() == pane5){
                        if(all_numbers_textfield_check(signup_student_id_txt_field.getText()) && correct_email_check(signup_email_txt_field.getText())){
                            new FadeInRight(panes.peek()).play();
                            panes.peek().toFront();
                        }
                        else if (!all_numbers_textfield_check(signup_student_id_txt_field.getText())){
                            new Shake(signup_student_id_txt_field).play();
                            panes.pop();
                        }
                        else if (!correct_email_check(signup_email_txt_field.getText())){
                            new Shake(signup_email_txt_field).play();
                            panes.pop();
                        }
                    }

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
        public Boolean number_constrains_for_text_fields(KeyEvent event){
                TextField numfield = (TextField) event.getSource();
                if(!event.getCharacter().matches("\\d")){
                    event.consume();
                    if(event.getCharacter().matches("\b") && numfield.getText().length() - 1 != 0){
                        String text = numfield.getText();
                        Boolean check = true;
                        check = all_numbers_textfield_check(text);
                        if (check)
                            numfield.setStyle("-fx-border-color: black");
                        else
                            numfield.setStyle("-fx-border-color: red");
                        return check;
                    }
                    else {
                        numfield.setStyle("-fx-border-color: red");
                        return false;
                    }
                }
                else {
                    String text = numfield.getText();
                    Boolean check = all_numbers_textfield_check(text);
                    if (check)
                        numfield.setStyle("-fx-border-color: black");
                    else
                        numfield.setStyle("-fx-border-color: red");
                    return check;
                }

        }


        public Boolean empty_text_field_shake(Pane pane){
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
            System.out.println("TRUE");
            return  true;
        }

        public Boolean all_numbers_textfield_check(String text){
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
            return check;
        }

        public Boolean correct_email_check(String text){
            int count1 = 0 ,count2=0;
            for(int i = 0 ; i < text.length() ; i++ ){
                if(text.charAt(i) == '@')
                    count1++;
                if(text.charAt(i) == '.')
                    count2++;
            }

            if(count1 == 1 && count2>0)
                return true;
            else
                return false;
        }









    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}