package com.example.loginpagemain;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatController implements Initializable {

    Client c ;
    @FXML
    TextArea send_message_textarea;
    @FXML
    Button send_message_button;
    @FXML
    ScrollPane message_show_scrollpane;
    @FXML
    Label chat_section_label;
    ArrayList<VBox> curret_messages ;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    int port;
    public void SetPort(int port){
        this.port = port;
    }
    String username ;
    int Current_message_count = 0 ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.username = getUsername();
        scheduler.scheduleAtFixedRate(this::messages, 0, 1, TimeUnit.SECONDS);

    }

    String  getUsername(){
        LoginDatabase loginDatabase = new LoginDatabase();
        String username = loginDatabase.getUsername();
        return username;
    }
    void setLable(String section_name){
        chat_section_label.setText(section_name);
    }
    @FXML
    void startChatting() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                Server server = new Server(serverSocket);
                server.startServer();
            } catch (Exception e) {
                System.out.println("Server Already Open");
            }

        });

        executorService.execute(() -> {
            try {
                String username = getUsername();
                Socket socket = new Socket("localhost", port);
                Client client = new Client(socket, username , port );
                c = client;
                client.listenForMessage();
                System.out.println("Message" + client.getMessage());

            } catch (Exception e) {
                System.out.println("Error");
            }


        });
    }

    @FXML
    void SendMessage() throws IOException{
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            try {
                c.sendMessage(send_message_textarea.getText());
            }
            catch (Exception e){
                System.out.println("Sending Message Error");
            }
        });
//
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageLables.fxml"));
//        VBox vbox = fxmlLoader.load();
//        MessageLableController messageLableController = fxmlLoader.getController();
//        messageLableController.SetMessageAndUsername(send_message_textarea.getText() , username);
//        Client.current_message.add(vbox);
    }
//
//    @FXML
//    void messages(){
//        ArrayList<VBox> curret_messages = Client.current_message;
//        VBox parent = new VBox();
//        for(int i = 0 ; i < curret_messages.size() ; i++){
//            parent.getChildren().add(curret_messages.get(i));
//        }
//        if(Client.Current_message_count<=curret_messages.size())
//        Platform.runLater(()->message_show_scrollpane.setContent(parent));
//    }
    @FXML
    void messages(){
            ArrayList<Pair<Integer,VBox>>  curret_messages = Client.current_message;
          //  System.out.println("CMC" +curret_messages.size());
            VBox parent = new VBox();
            for (int i = 0; i < curret_messages.size(); i++) {
                if(curret_messages.get(i).getKey()==port){
                    parent.getChildren().add(curret_messages.get(i).getValue());
                }
            }
            Platform.runLater(() -> message_show_scrollpane.setContent(parent));
    }
}
