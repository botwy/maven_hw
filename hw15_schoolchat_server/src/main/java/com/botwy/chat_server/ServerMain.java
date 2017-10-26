package com.botwy.chat_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    private final DataBase dataBase;

    public ServerMain() {
        dataBase = new DataBase();
    }

    public void start() {

        ExecutorService executor = Executors.newCachedThreadPool();
        try (ServerSocket socketListener = new ServerSocket(3000)) {

            while (!socketListener.isClosed()) {
                Socket client = socketListener.accept();
                ServerConnection connection = new ServerConnection(client, dataBase);
                executor.execute(connection);
            }

            socketListener.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
