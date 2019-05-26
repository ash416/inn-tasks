package part1.lesson10.way01.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

import static java.lang.String.format;
import static part1.lesson10.way01.server.utils.ChatMessagesUtil.*;
import static part1.lesson10.way01.server.Server.clients;

/**
 * The class handles client's messages.
 * It added new clients to clients list, receives messages from clients
 * and sends messages to clients using Server
 */

public class ClientHandler extends Thread{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    private Socket clientSocket;
    private Server server;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String name = null;
    private static int countClients = 0;
;

    public ClientHandler(Socket clientSocket, Server server) {
        this.server = server;
        this.clientSocket = clientSocket;
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            server.sendMessageToClient(ENTER_MESSAGE, this);
            handleUserName();
            String message;
            while ((message = reader.readLine()) != null) {
                if (message.equals(QUIT_MESSAGE)) {
                    handleQuit();
                    break;
                }
                handleMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The methods makes client to write his name, added client to clients list,
     * notifies other clients about new client entering
     * @throws IOException
     */
    private void handleUserName() throws IOException {
        LOGGER.info("Сервер запрашивает имя у пользователя с порта {}", clientSocket.getPort());
        name = reader.readLine();
        LOGGER.info("Пользователь port = {} ввел имя {}", clientSocket.getPort(), name);
        server.sendMessageToClient(format(WELCOME_MESSAGE, name), this);
        server.sendMessageToAllClientsExcept(format(NEW_USER_MESSAGE, name), this);
        clients.put(name, this);
        countClients++;
    }

    /**
     * The method handles quit messages. It removes client from clients list and
     * inform other clients about the client's exit
     */
    private void handleQuit() {
        server.sendMessageToClient(YOU_LEFT_CHAT, this);
        server.removeFromChat(this);
        server.sendMessageToAllClientsExcept(format(USER_LEFT_CHAT, name), this);
    }

    /**
     * The method handles message from client
     * @param message
     */

    private void handleMessage(String message) {
        if (isPrivateMessage(message)) {
            handlePrivateMessage(message);
        } else {
            String namedMessage = name + ": " + message;
            server.sendMessageToAllClientsExcept(namedMessage, this);
        }
    }

    /**
     * The method handles private message.
     * @param message
     */
    private void handlePrivateMessage(String message) {
        String receiverName = extractName(message);
        String privateMessage = extractPrivateMessage(message);
        String privateNamedMessage = format(FROM_PRIVATE_MESSAGE, name) + privateMessage;
        ClientHandler receiver = clients.get(receiverName);
        if (receiver == null) {
            server.sendMessageToClient(ENTER_WRONG_NAME, this);
        } else {
            server.sendMessageToClient(privateNamedMessage, receiver);
            LOGGER.info(format(TO_FROM_PRIVATE_MESSAGE, receiverName, name) + privateMessage);
        }
    }

    /**
     * The method determines whether message is private by message structure
     * @param message
     * @return
     */
    private boolean isPrivateMessage(String message) {
        return message.startsWith(PRIVATE_BEGIN_SIGNAL);
    }

    /**
     * The method extracts receiver's name from private message
     * @param message
     * @return
     */

    private String extractName(String message) {
        return message.substring(PRIVATE_BEGIN_SIGNAL.length(), message.indexOf(' '));
    }

    /**
     * The method extract message from private message
     * @param message
     * @return
     */

    private String extractPrivateMessage(String message) {
        return message.substring(message.indexOf(' ') + 1);
    }

    /**
     * The method is for sending messages to client.
     * @param message
     */

    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * the method returns client Name
     * @return
     */
    public String getClientName() {
        return name;
    }
}
