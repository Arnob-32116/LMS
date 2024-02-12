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

public class ForgetPasswordController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Stack<Pane> panes = new Stack<>();

    @FXML
    private TextField forget_pass_email_txt_field, forget_pass_id_txt_field;
    @FXML
    private Pane forget_pass_pane1,forget_pass_pane2;


    private int OTP = 100000000;

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



    public void send_OTP_email(String to){
        String from = "roughuse32116@gmail.com";
        final String username = "roughuse32116";
        final String password = "omiludbuwfcdaegy";

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            message.setSubject("One Time Password From E-Learning Managment Software United International University");
            Random random = new Random();
            OTP = random.nextInt(10000,99999);

            message.setText("Your OTP is " + OTP + " .Please Type is correctly within 5 minutes. If this was not you don't reply to this mail");

            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Thread.currentThread().getName());

    }


    @FXML
    public void verify_OTP(ActionEvent event ) throws Exception{
        int user_otp =0;
        List<TextField> textFields = new ArrayList<>();
        for (Node node : forget_pass_pane2.getChildren()) {
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

        if(OTP==user_otp)
            System.out.println("YES");
        else
            System.out.println("NO");
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
