package com.example.loginpagemain;


import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    public static ArrayList<Pair<Integer , ClientHandler>> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientName;
    public int port ;

    public ClientHandler(Socket socket , int port ) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientName = Client.username;
            this.port = port;
            clientHandlers.add(new Pair<Integer,ClientHandler>(port,this));
        } catch (Exception exception) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                System.out.println(messageFromClient);
                broadcastMessage(messageFromClient, this);
            } catch (IOException exception) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String clientMessage, ClientHandler sender) {
        try {
            for (Pair<Integer,ClientHandler> client: clientHandlers) {
                if(client.getKey()==port) {
                    client.getValue().bufferedWriter.write(clientMessage);
                    client.getValue().bufferedWriter.newLine();
                    client.getValue().bufferedWriter.flush();
                    System.out.println(clientName + " " + clientMessage);
                }
            }
        } catch (IOException exception) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(new Pair<>(port ,this));
        broadcastMessage("SERVER " + clientName + " LEFT THE CHAT", this);
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
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


}
