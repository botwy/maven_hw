package com.botwy.chat_server;

import com.botwy.schoolchat_api.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase {
    private final HashMap<String,Socket> user_socket;
    private final HashMap<String,ObjectOutputStream> user_oos;
    private final HashMap<String,List<Message>> receiver_messages;

    public DataBase() {
        user_socket=new HashMap<>();
        user_oos=new HashMap<>();
        receiver_messages = new HashMap<String, List<Message>>();
    }

    public void addUser(String user_name, Socket socket, ObjectOutputStream oos) throws IOException {
        user_oos.put(user_name,oos);
        user_socket.put(user_name,socket);
    }

    public void addMessageToList(String receiver, Message msg) {
        if (!receiver_messages.containsKey(receiver))
            receiver_messages.put(receiver,new ArrayList<Message>());

        receiver_messages.get(receiver).add(msg);
    }

    public HashMap<String, Socket> getUser_socket() {
        return user_socket;
    }

    public HashMap<String, ObjectOutputStream> getUser_oos() {
        return user_oos;
    }

    public List<Message> getMessagesList(String receiver) {
        if (!receiver_messages.containsKey(receiver))
            receiver_messages.put(receiver,new ArrayList<Message>());

        return receiver_messages.get(receiver);
    }
}
