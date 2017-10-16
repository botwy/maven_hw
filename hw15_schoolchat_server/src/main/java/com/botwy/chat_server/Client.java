package com.botwy.chat_server;

import com.botwy.schoolchat_api.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;
        String user_name;


        try (Socket socket = new Socket("localhost", 3000)) {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message msg_out;

            final ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message msg_in;
                    try {
                        while (true) {
                            if ((msg_in = (Message) ois.readObject()) != null)
                                System.out.println(msg_in.getUser_name() + ">>" + msg_in.getText());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            System.out.println("enter user_name:");
            user_name = scanner.nextLine();
            msg_out = new Message(Message.Command.LOGIN, user_name);
            oos.writeObject(msg_out);
            oos.flush();

            while (true) {

                System.out.println("enter command:");
                command = scanner.nextLine();

                switch (command.toLowerCase()) {
                    case "send":
                        String msg_text;
                        String receiver_name;
                        System.out.println("enter message:");
                        msg_text = scanner.nextLine();
                        System.out.println("enter receiver:");
                        receiver_name = scanner.nextLine();
                        msg_out = new Message(Message.Command.SEND, user_name, receiver_name, msg_text);
                        oos.writeObject(msg_out);
                        oos.flush();
                        break;

                    case "get":
                        msg_out = new Message(Message.Command.GET, user_name);
                        oos.writeObject(msg_out);
                        oos.flush();
                        break;
                }

            }
//oos.close();
// ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
