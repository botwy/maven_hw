package com.botwy.chat_server;

import com.botwy.schoolchat_api.Message;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerConnection implements Runnable {
    private final Socket client;
    private final DataBase dataBase;


    public ServerConnection(Socket client, DataBase dataBase) {
        this.client = client;
        this.dataBase = dataBase;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());

            Message message_in;
            Message message_out;
            while ((message_in = (Message) ois.readObject()) != null) {
                switch (message_in.getCommand()) {
                    case LOGIN:
                        dataBase.addUser(message_in.getUser_name(), client, oos);
                      message_out = new Message(Message.Command.LOGIN,"    - system", message_in.getUser_name()+" enter to chat");
                        System.out.println("system" + ">>" + message_in.getUser_name()+" enter to chat");
                       for (ObjectOutputStream curr_oos : dataBase.getUser_oos().values()) {
                            curr_oos.writeObject(message_out);
                            curr_oos.flush();
                        }
                        break;
                    case SEND:
                        ObjectOutputStream reseiver_oos = dataBase.getUser_oos().get(message_in.getReceiver_name());
                        if (reseiver_oos!=null) {
                            message_out = new Message(Message.Command.SEND,"    - "+message_in.getUser_name(),message_in.getText());
                            System.out.println(message_in.getUser_name() + ">>" + message_in.getText());
                            reseiver_oos.writeObject(message_out);
                            reseiver_oos.flush();
                            dataBase.addMessageToList(message_in.getReceiver_name(),message_out);
                        }
                        break;
                    case GET:
                        List<Message> list_msg = dataBase.getMessagesList(message_in.getUser_name());
                        for (Message msg:list_msg) {
                            oos.writeObject(msg);
                            oos.flush();
                        }
                        break;
                }


            }
            ois.close();
            oos.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
