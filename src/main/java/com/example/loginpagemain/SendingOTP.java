package com.example.loginpagemain;

public class SendingOTP implements Runnable {

    private String to;

    public SendingOTP(String to) {
        this.to = to;
    }

    @Override
    public void run() {
        otpsendingmethod();
    }

    public void otpsendingmethod(){
        OTP otp = new OTP();
        otp.send_OTP_email(to);
    }


}
