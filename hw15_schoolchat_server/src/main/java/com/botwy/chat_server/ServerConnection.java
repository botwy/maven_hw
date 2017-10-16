package com.botwy.chat_server;

import com.botwy.schoolchat_api.Message;

import java.io.*;
import java.net.Socket;

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

            Message message = null;
            while ((message = (Message) ois.readObject()) != null) {
                switch (message.getCommand()) {
                    case LOGIN:
                        dataBase.addUser(message.getUser_name(), client);
                        for (Socket curr_soc : dataBase.getUser_socket().values()
                                ) {
                            ObjectOutputStream oos = new ObjectOutputStream(curr_soc.getOutputStream());
                            oos.writeObject(new Message(Message.Command.LOGIN, message.getUser_name()));
                        }
                        break;
                    case SEND:
                        System.out.println(message.getUser_name() + ">>" + message.getText());
                        break;
                    case GET:

                        break;
                }
            }
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
