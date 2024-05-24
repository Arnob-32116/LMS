package com.example.loginpagemain;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public static int Current_message_count = 0 ;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String message;
    private int port ;
    public static  String username ;
    private int number_of_messages_in_this_client = 0 ;
    public static ArrayList<Pair<Integer,VBox>> current_message = new ArrayList<Pair<Integer, VBox>>();
    public Client(Socket socket, String username, int port) {
        try {
            this.socket = socket;
            this.port = port ;
            System.out.println("New Client Created of Port " + port);
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
                    System.out.println(msgFromGroupChat);
                    setMessage(msgFromGroupChat);
                }
            } catch (IOException exception) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            } catch (Exception e) {
                throw new RuntimeException(e);
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

    public String getMessage(){
        return message;
    }

    private void setMessage(String incomingmessage) throws Exception{
        VBox vBox = new VBox();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageLables.fxml"));
        VBox vbox = fxmlLoader.load();
        MessageLableController messageLableController = fxmlLoader.getController();
        String senderUsername ="" , msg="";
        int i = 0 ;
        int count = 0 ;
        while(i<incomingmessage.length()){
            if(incomingmessage.charAt(i)==' ')
                count++;
            if(count==0)
            senderUsername+= incomingmessage.charAt(i);
            else{
                msg+=incomingmessage.charAt(i);
            }
            i++;
        }
        messageLableController.SetMessageAndUsername(msg,senderUsername);
        messageLableController.setTextColorOfOthersSentMessage();
        if(!current_message.contains(vbox)){
            System.out.println("Added message");
        current_message.add(new Pair<>(port,vbox));
        }

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

