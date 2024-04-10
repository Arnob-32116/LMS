package com.example.loginpagemain;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.*;

public class OTP {

    static int OTP = 100000000;
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

        System.out.println(Thread.currentThread().getName()+"OTP");
        System.out.println(OTP);

    }

}
