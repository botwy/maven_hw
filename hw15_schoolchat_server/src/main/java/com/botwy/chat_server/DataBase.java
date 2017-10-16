package com.botwy.chat_server;

import java.net.Socket;
import java.util.HashMap;

public class DataBase {
    private final HashMap<String,Socket> user_socket;

    public DataBase() {
        user_socket=new HashMap<>();
    }

    public void addUser(String user_name, Socket socket) {
        user_socket.put(user_name,socket);
    }

    public HashMap<String, Socket> getUser_socket() {
        return user_socket;
    }
}
