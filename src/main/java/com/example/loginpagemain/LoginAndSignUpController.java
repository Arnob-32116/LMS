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
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;


public class LoginAndSignUpController implements Initializable{
        static String username,student_name,password,student_id,student_number,guardian_number,email;
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
        private Pane pane1,pane2,pane3,pane4,pane5,forget_pass_pane1,signup_otp_pane;
        @FXML
        Pane forget_pass_pane2;
        @FXML
        private Button next_button , back_button, back_button_page2;
        @FXML
        private TextField signin_email_txt_field,signup_student_phonenumber_txt_field,gurdian_phone_number ,signup_student_id_txt_field,signup_email_txt_field,signin_password_txt_field;
        @FXML
        public void signin_button_control(ActionEvent event) throws Exception{
            LoginDatabase loginDatabase = new LoginDatabase();
            String loginemail,loginpassword;
            loginemail = signin_email_txt_field.getText();
            loginpassword = signin_password_txt_field.getText();
            String dummy = loginpassword;
            Hash hash = new Hash(loginpassword);
            loginpassword = hash.HashFunction();
            if(loginemail.equals("admin@gmail.com") && dummy.equals("admin"))
            {
                Parent root = FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                new FadeInRight(root).play();

            }
            else if(correct_email_check(signin_email_txt_field.getText()) && check_login_credentials(loginDatabase.get_all_login_info(),loginemail,loginpassword)){
                int acess_level = 0 ;
                acess_level = loginDatabase.get_individual_all_acess_level(signin_email_txt_field.getText());
                if(acess_level==0) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainPage.fxml"));
                    Parent root = fxmlLoader.load();
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    MainPage mainPage = fxmlLoader.getController();
                    mainPage.setPrimaryStage(stage);
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    new FadeInRight(root).play();
                }
                else if(acess_level == 1){
                    Parent root = FXMLLoader.load(getClass().getResource("Faculty.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    new FadeInRight(root).play();
                }
            }
            else{
                new Shake(signin_email_txt_field).play();
            }
        }
        Boolean check_login_credentials(ArrayList<Pair<String,String>> logininfos ,String login_mail ,String login_password){
            for(Pair<String,String> element : logininfos){
                if(element.getKey().equals(login_mail) && element.getValue().equals(login_password)) {
                    this.email = element.getKey();
                    return true;
                }
            }
            return false;
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
        ForgetPasswordController forgetPasswordController = new ForgetPasswordController();

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
                        if(all_numbers_textfield_check(signup_student_phonenumber_txt_field.getText()) && all_numbers_textfield_check(gurdian_phone_number.getText()) ){
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
        void setForget_pass_Pane2_front(){
            forget_pass_pane2.toFront();
        }


        @FXML
        public void final_signup_button(ActionEvent event) throws  Exception{
            ArrayList<String> emails = new ArrayList<String>();
            get_information();
            Hash hash = new Hash(password);
            password = hash.HashFunction();
            LoginDatabase loginDatabase = new LoginDatabase(username,student_name,password,student_id,student_number,guardian_number,email,0);
            emails = loginDatabase.get_all_emails();
            loginDatabase.add_courseinfo_to_signup_user();
            if(is_duplicate_email(emails)) {
                SendingOTP sendingOTP = new SendingOTP(email);
                Thread thread = new Thread(sendingOTP);
                thread.start();
                Parent root = FXMLLoader.load(getClass().getResource("OnlyOTP.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                loginDatabase.Login_Information_connection();
            }
            else{
                System.out.println("Email Already exists");
            }

        }


        void get_information(){
            for(Node node : pane3.getChildren()){
                if (node instanceof TextField) {
                    TextField tf = (TextField) node;
                    if(tf.getId().equals("signup_username_txt_field"))
                        username = tf.getText();
                    else if(tf.getId().equals("signup_email_txt_field"))
                        email = tf.getText();
                    else if(tf.getId().equals("signup_student_id_txt_field"))
                        student_id = tf.getText();
                    else if(tf.getId().equals("signup_name_txt_field"))
                        student_name = tf.getText();
                }
            }

            for(Node node : pane5.getChildren()){
                if (node instanceof TextField) {
                    TextField tf = (TextField) node;
                    if(tf.getId().equals("signup_student_phonenumber_txt_field"))
                        student_number = tf.getText();
                    else if(tf.getId().equals("signup_password_txt_field"))
                        password = tf.getText();
                    else if(tf.getId().equals("gurdian_phone_number"))
                        guardian_number = tf.getText();
                }
            }


        }

        Boolean is_duplicate_email(ArrayList<String> emails){
            for(String element : emails){
                if(element.equals(null))
                    continue;
                if(element.equals(email))
                    return false;
            }
            System.out.println("Failed");
            return true;
        }

        public String get_username(){
            return username;
        }

        public String getEmail(){
            return this.email;
        }















    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}