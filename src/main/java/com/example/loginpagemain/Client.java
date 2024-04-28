package com.example.loginpagemain;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.username = username;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException exception) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String message) {
        try {

            if(socket.isConnected()) {
                bufferedWriter.write(username + " " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception exception) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(() -> {
            try {
                String msgFromGroupChat;
                while (socket.isConnected()) {
                    msgFromGroupChat = bufferedReader.readLine();
                    System.out.println("Receive " + msgFromGroupChat);
                }
            } catch (IOException exception) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername(){
        return username;
    }

//    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your Username for the group chat:");
//        String username = scanner.nextLine();
//        Socket socket = new Socket("localhost", 1234);
//        Client client = new Client(socket, username);
//        client.listenForMessage();
//        client.sendMessage();
//
//    }
}

