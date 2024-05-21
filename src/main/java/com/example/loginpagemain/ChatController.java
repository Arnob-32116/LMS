package com.example.loginpagemain;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
    boolean running = false;
    Client c ;
    Server s;
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
        running = true;
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
                Server server = new Server(serverSocket , port);
                s = server;
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

    int prev_message_size = 0 , this_port_message_size = 0 ;



    @FXML
    void SendMessage() throws IOException{
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            try {
                c.sendMessage(send_message_textarea.getText());
                setSenderMessage(send_message_textarea.getText());
            }
            catch (Exception e){
                System.out.println("Sending Message Error");
            }
        });

    }

//    @FXML
//    void messages(){
//        ArrayList<Pair<Integer,VBox>>  curret_messages = Client.current_message;
//        VBox parent = new VBox();
//        for (int i = 0; i < curret_messages.size(); i++) {
//            if(curret_messages.get(i).getKey()==port){
//                this_port_message_size++;
//                parent.getChildren().add(curret_messages.get(i).getValue());
//            }
//        }
//        if(message_show_scrollpane.getContent()!=parent) {
//            Platform.runLater(() -> message_show_scrollpane.setContent(parent));
//            System.out.println("Yes Yes yes");
//        }
//        else{
//            System.out.println("Else else else");
//        }
//    }

    @FXML
    void messages() {
            ArrayList<Pair<Integer, VBox>> currentMessages = Client.current_message;
            VBox parent = new VBox();

            for (int i = 0; i < currentMessages.size(); i++) {
                if (currentMessages.get(i).getKey() == port) {
                    this_port_message_size++;
                    parent.getChildren().add(currentMessages.get(i).getValue());
                }
            }

            // Compare the visual content (number of children)
            if (message_show_scrollpane.getContent() instanceof VBox) {
                VBox currentContent = (VBox) message_show_scrollpane.getContent();
                if (currentContent.getChildren().size() != parent.getChildren().size()) {
                    Platform.runLater(() -> message_show_scrollpane.setContent(parent));
                    System.out.println("Content updated");
                } else {
                    System.out.println("Content already set");
                }
            } else {
                // If the current content is not a VBox, update it
                Platform.runLater(() -> message_show_scrollpane.setContent(parent));
                System.out.println("Content updated (different type)");
            }


    }

    private void setSenderMessage(String incomingmessage) throws Exception{
        VBox vBox = new VBox();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MessageLables.fxml"));
        VBox vbox = fxmlLoader.load();
        MessageLableController messageLableController = fxmlLoader.getController();
        LoginDatabase loginDatabase = new LoginDatabase();
        messageLableController.SetMessageAndUsername(incomingmessage,"");
        messageLableController.setTextColorOfUserSentMessage();
        vbox.setPadding(new Insets(0,0,0,490));
        if(!Client.current_message.contains(vbox)){
            Client.current_message.add(new Pair<>(port,vbox));
        }

    }
    @FXML
    void close() throws Exception{

    }




}