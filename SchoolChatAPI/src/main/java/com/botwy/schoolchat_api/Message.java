package com.botwy.schoolchat_api;

public class Message {
    private Command command;
    private String user_name;
    private String text;

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

    public String getText() {
        return text;
    }

    public enum Command {
        LOGIN,
        SEND,
        GET
    }


}
