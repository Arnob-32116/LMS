package com.example.loginpagemain;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

 class Hash {
    String inputString;
    Hash(String Password){
        this.inputString = Password;
    }
    String HashFunction() {

        try {
            // Get an instance of the MD5 digest
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Convert the input string to bytes using UTF-8 encoding
            byte[] bytesOfMessage = inputString.getBytes(StandardCharsets.UTF_8);

            // Calculate the MD5 hash
            byte[] md5Digest = md.digest(bytesOfMessage);

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : md5Digest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            inputString = hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return inputString;
    }

}

