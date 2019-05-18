package part1.lesson10.client;

import part1.lesson10.server.Server;

import java.io.*;
import java.net.Socket;

public class Client {

    private Listener listener;
    private Sender sender;


    public void start() {
        try (Socket socket = new Socket("127.0.0.1", Server.SERVER_PORT);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            listener = new Listener(reader);
            sender = new Sender(writer);
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
