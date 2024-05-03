package com.example.loginpagemain;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    public static Boolean isRunning = false;
    public int port ;
    public Server(ServerSocket serverSocket , int port) {
        this.serverSocket = serverSocket;
        isRunning = true;
        this.port = port ;
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New Client Has Connected");
                ClientHandler clientHandler = new ClientHandler(socket , port );
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




//    public static void main(String[] args) throws Exception {
//        ServerSocket serverSocket = new ServerSocket(1234);
//        Server server = new Server(serverSocket);
//        server.startServer();
//    }
}
