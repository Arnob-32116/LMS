package com.example.loginpagemain;

import animatefx.animation.BounceInUp;
import animatefx.animation.Shake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ForgetPasswordController extends OTP implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Stack<Pane> panes = new Stack<>();

    @FXML
    private TextField forget_pass_email_txt_field, forget_pass_id_txt_field;
    @FXML
    private Pane forget_pass_pane1,forget_pass_pane2;


    @FXML
    private ComboBox<String> combobox ;

    @FXML
    public void unclick_textarea(MouseEvent event) throws Exception {
        LoginAndSignUpController loginAndSignUpController = new LoginAndSignUpController();
        loginAndSignUpController.unclick_textarea(event);
    }

    @FXML
    public Boolean number_constrains_for_text_fields(KeyEvent event){
        LoginAndSignUpController loginAndSignUpController = new LoginAndSignUpController();
        Boolean check = loginAndSignUpController.number_constrains_for_text_fields(event);
        return check;
    }

    @FXML
    public void send_verification_code(ActionEvent event) throws Exception{
        // Set the recipient's email address

        LoginAndSignUpController loginAndSignUpController = new LoginAndSignUpController();
        if (!loginAndSignUpController.empty_text_field_shake(forget_pass_pane1) ){

        }
        else if (loginAndSignUpController.correct_email_check(forget_pass_email_txt_field.getText()) == false){
            new Shake(forget_pass_email_txt_field).play();
        }
        else {
            SendingOTP sendingOTP = new SendingOTP(forget_pass_email_txt_field.getText());
            Thread thread = new Thread(sendingOTP);
            thread.start();
            forget_pass_pane2.toFront();
            System.out.println(Thread.currentThread().getName());
            panes.push(forget_pass_pane1);
        }
    }




    @FXML
    public void verify_OTP() throws Exception{
        verify_OTP_Main(forget_pass_pane2);
    }




    public Boolean verify_OTP_Main(Pane pane) throws Exception{
        int user_otp =0;
        List<TextField> textFields = new ArrayList<>();
        for (Node node : pane.getChildren()) {
            if (node instanceof TextField) {
                textFields.add((TextField) node);
            }
        }
        for(TextField text_fields : textFields){
            if(text_fields.getText().isEmpty()==true){
                new BounceInUp(text_fields).play();
                break;
            }
            else{
                user_otp = user_otp * 10;
                String c = text_fields.getText();
                user_otp+=Integer.parseInt(c);
            }
        }
        System.out.println(OTP + "    "+user_otp);
        if(OTP==user_otp)
            return true;
        else
            return false;
    }

    public int getOTP(){
        return OTP;
    }

    @FXML
    private void back_functionality_1(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void back_functionality_2(ActionEvent event) throws Exception {
        forget_pass_pane1.toFront();
    }
    @FXML
    void setForget_pass_Pane2_front(){
        forget_pass_pane2.toFront();
    }







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
