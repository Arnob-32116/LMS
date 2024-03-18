package com.example.loginpagemain;

public class SendingOTP implements Runnable{

    private String to = "";
    SendingOTP(String to){
        this.to = to ;
    }

    @Override
    public void run() {
        ForgetPasswordController forgetPasswordController = new ForgetPasswordController();
        forgetPasswordController.send_OTP_email(to);
    }
}
