package com.example.loginpagemain;

import animatefx.animation.Shake;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class ForgetPasswordController implements Initializable {

    @FXML
    private TextField forget_pass_email_txt_field, forget_pass_id_txt_field;
    @FXML
    private Pane forget_pass_pane1;

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

            String to = forget_pass_email_txt_field.getText();
            // Sender's email ID and password needs to be mentioned
            String from = "roughuse32116@gmail.com";
            final String username = "roughuse32116";//change accordingly
            final String password = "omiludbuwfcdaegy";//change accordingly

            // Assuming you are sending email through relay.jangosmtp.net
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
                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(to));

                // Set Subject: header field
                message.setSubject("One Time Password From E-Learning Managment Software United International University");
                Random random = new Random();
                int OTP = random.nextInt(99999);

                // Now set the actual message
                message.setText("Your OTP is " + OTP + " .Please Type is correctly within 5 minutes. If this was not you don't reply to this mail");

                // Send message
                Transport.send(message);

                System.out.println("Sent message successfully....");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }


        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
