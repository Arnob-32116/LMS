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
import java.net.URL;
import java.util.*;

public class SignUpOTP extends OTP implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Stack<Pane> panes = new Stack<>();

    @FXML
    private Pane Only_OTP_Pane,Incorrect_OTP_pane;

    String username,student_name,password,student_id,student_number,guardian_number,email;

    @FXML
    private ComboBox<String> combobox ;


    SignUpOTP( String username,String student_name,String password,String student_id,String student_number,String guardian_number,String email){
        this.username = username;
        this.password = password;
        this.student_id = student_id;
        this.student_name = student_name;
        this.student_number = student_number;
        this.guardian_number = guardian_number;
        this.email = email;
    }






    @FXML
    public void  verify_OTP_Main(ActionEvent event) throws Exception{
        int user_otp =0;
        List<TextField> textFields = new ArrayList<>();
        for (Node node : Only_OTP_Pane.getChildren()) {
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
        if(OTP==user_otp){
            OpenLoginPage(event);
        }
        else{
            Only_OTP_Pane.setVisible(false);
            Only_OTP_Pane.toBack();
            Incorrect_OTP_pane.toFront();
        }

    }

    @FXML
    void Incorrect_case_btn_handeling(ActionEvent event) throws Exception{
        OpenSignupPage(event);
    }


    void OpenLoginPage(ActionEvent event) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    void OpenSignupPage(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private void back_functionality_1(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
