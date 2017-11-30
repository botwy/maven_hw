package com.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

public class ChatClient {

    public static void main (String...args) {
        System.out.println("введите имя пользователя: ");
        String userName;
   //     try( BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));) {
    //       if (( userName = bufferedReader.readLine())!=null){
        Scanner scanner = new Scanner(System.in);
               userName=scanner.nextLine();
                ChatServer.login(userName);
      //      }
      //  } catch (IOException e) {
       //     e.printStackTrace();
       // }
    }
}
