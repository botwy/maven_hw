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
        String msg_text;

        try (Socket socket = new Socket("localhost", 3000)) {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            System.out.println("enter user_name:");
            user_name = scanner.nextLine();
            Message msg = new Message(Message.Command.LOGIN, user_name);
            oos.writeObject(msg);
            while (true) {
                Message in_msg;
                if ((msg = (Message) ois.readObject()) != null)
                    System.out.println(msg.getUser_name() + ">>" + msg.getText());
                System.out.println("enter command:");
                command = scanner.nextLine();
                System.out.println("enter message:");
                msg_text = scanner.nextLine();
                switch (command.toLowerCase()) {
                    case "send":
                        msg = new Message(Message.Command.SEND, user_name, msg_text);
                        oos.writeObject(msg);
                        break;

                    case "get":
                        msg = new Message(Message.Command.GET, user_name);
                        oos.writeObject(msg);
                        break;
                }

            }
//oos.close();
// ois.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
