package part1.lesson10.client;

import part1.lesson10.server.Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket clientSocket;
    private static Server server;
    private BufferedReader reader;
    private BufferedWriter writer;
    private static int countClients = 0;

    public ClientHandler(Socket clientSocket, Server server) {
        countClients++;
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }

    public void sendMessageToClient(String message) {
        try {
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
