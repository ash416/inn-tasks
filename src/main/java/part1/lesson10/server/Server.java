package part1.lesson10.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public final static Integer SERVER_PORT = 4000;
    static Map<String, ClientHandler> clients = new HashMap<>();

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                LOGGER.info("Сервер запущен, ждет подключения клиентов");
                Socket clientSocket = serverSocket.accept();
                LOGGER.info("Подключился клиент с порта {}", clientSocket.getPort());
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }

    public void sendMessagesToAllClients(String message) {
        clients.values().forEach(clientHandler -> {
            clientHandler.sendMessage(message);
        });
    }

    public void sendMessageToAllClientsExcept(String message, ClientHandler client) {
        clients.values().forEach(clientHandler -> {
            if (!client.equals(clientHandler)) {
                clientHandler.sendMessage(message);
            }
        });
        LOGGER.info(message);
    }

    public void removeFromChat(ClientHandler client) {
        clients.remove(client);
        LOGGER.info("Пользователь {} вышел из чата", client.getUserName());
    }

    public void sendMessageToClient(String message, ClientHandler client) {
        client.sendMessage(message);
    }
}
