package part1.lesson10.server;

import part1.lesson10.ClientHandler;

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
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandler.start();
                clients.add(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

    public void sendMessagesToAllClients(String message) {
        clients.forEach(clientHandler -> {
            clientHandler.sendMessage(message);
        });
    }

    public static void removeFromChat(ClientHandler client) {
        clients.remove(client);
    }

    public void sendMessageToClient(ClientHandler client, String message) {
        client.sendMessage(message);
    }
}