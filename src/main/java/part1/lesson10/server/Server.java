package part1.lesson10.server;

import part1.lesson10.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public final static Integer SERVER_PORT = 4000;
    private static List<ClientHandler> clients = new ArrayList<>();

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(new ClientHandler(clientSocket, this));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
