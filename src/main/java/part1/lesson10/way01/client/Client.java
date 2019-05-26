package part1.lesson10.way01.client;

import part1.lesson10.way01.server.Server;

import java.io.*;
import java.net.Socket;

/**
 * The class is client in chat.
 * It has listener for receiving messages and Sender for sending messages from console.
 */

public class Client {

    public void start() {
        try (Socket socket = new Socket("127.0.0.1", Server.SERVER_PORT);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            Listener listener = new Listener(reader);
            Sender sender = new Sender(writer);
            listener.join();
            sender.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }


}
