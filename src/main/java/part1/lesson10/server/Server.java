package part1.lesson10.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * The class is chat. It has clients handler list.
 * It can send messages to client.
 */


public class Server {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public final static Integer SERVER_PORT = 4000;
    static Map<String, ClientHandler> clients = new HashMap<>();

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            LOGGER.info("Сервер запущен, ждет подключения клиентов");
            while (true) {
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

    /**
     * the method sends messages to all clients is chat
     * @param message
     */
    public void sendMessagesToAllClients(String message) {
        clients.values().forEach(clientHandler -> {
            clientHandler.sendMessage(message);
        });
    }

    /**
     * the method sends messages to all clients from chat excepts clients who has written this message
     * @param message
     * @param client
     */
    public void sendMessageToAllClientsExcept(String message, ClientHandler client) {
        clients.values().forEach(clientHandler -> {
            if (!client.equals(clientHandler)) {
                clientHandler.sendMessage(message);
            }
        });
        LOGGER.info(message);
    }

    /**
     * the method for removing client from chat
     * @param client
     */
    public void removeFromChat(ClientHandler client) {
        clients.remove(client.getClientName());
    }

    /**
     * the method sends message to special client
     * @param message
     * @param client
     */

    public void sendMessageToClient(String message, ClientHandler client) {
        client.sendMessage(message);
    }
}
