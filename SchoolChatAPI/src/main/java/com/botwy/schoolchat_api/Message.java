package com.botwy.schoolchat_api;

import java.io.Serializable;

public class Message implements Serializable{
    private Command command;
    private String user_name;
    private String receiver_name;
    private String text;

    public Message(Command command, String user_name, String receiver_name, String text) {
        this.command = command;
        this.user_name = user_name;
        this.receiver_name = receiver_name;
        this.text = text;
    }

    public Message(Command command, String user_name, String text) {
        this.command = command;
        this.user_name = user_name;
        this.text = text;
    }

    public Message(Command command, String user_name) {
        this.command = command;
        this.user_name = user_name;
    }


    public Command getCommand() {
        return command;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getText() {
        return text;
    }

    public enum Command {
        LOGIN,
        SEND,
        GET
    }


}
