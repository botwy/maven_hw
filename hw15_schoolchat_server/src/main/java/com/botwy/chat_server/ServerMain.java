package com.botwy.chat_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * создаем ServerSocket слушатель порта 3000
 * затем в бесконечном цикле блокируем поток, ожидая подключения клиента socketListener.accept()
 * как только клиент подключился создаем задание Runnable, передавая ему сокет клиента и ссылку на объект DataBase
 * с данными чата
 * Это задание передаем на выполнение пулу потоков
 */
public class ServerMain {

    private static final DataBase dataBase=new DataBase();


    public static void main(String... args) {

        ExecutorService executor = Executors.newCachedThreadPool();
        try (ServerSocket socketListener = new ServerSocket(3000)) {

            while (!socketListener.isClosed()) {
                Socket client = socketListener.accept();
                ServerConnection connection = new ServerConnection(client, dataBase);
                executor.execute(connection);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
