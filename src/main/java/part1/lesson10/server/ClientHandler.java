package part1.lesson10.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

import static java.lang.String.format;
import static part1.lesson10.server.utils.ChatMessagesUtil.*;
import static part1.lesson10.server.Server.clients;

public class ClientHandler extends Thread{
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    private Socket clientSocker;
    private Server server;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String name = null;
    private static int countClients = 0;
;

    public ClientHandler(Socket clientSocket, Server server) {
        this.server = server;
        this.clientSocker = clientSocket;
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

    private void handleUserName() throws IOException {
        LOGGER.info("Сервер запрашивает имя у пользователя с порта {}", clientSocker.getPort());
        name = reader.readLine();
        LOGGER.info("Пользователь port = {} ввел имя {}", clientSocker.getPort(), name);
        server.sendMessageToClient(format(WELCOME_MESSAGE, name), this);
        server.sendMessageToAllClientsExcept(format(NEW_USER_MESSAGE, name), this);
        clients.put(name, this);
        countClients++;
    }


    private void handleQuit() {
        server.sendMessageToClient(YOU_LEFT_CHAT, this);
        server.removeFromChat(this);
        String quitMessage = format(USER_LEFT_CHAT, name);
        server.sendMessageToAllClientsExcept(quitMessage, this);
        LOGGER.info(quitMessage);
    }

    private void handleMessage(String message) {
        if (isPrivateMessage(message)) {
            handlePrivateMessage(message);
        } else {
            String namedMessage = name + ": " + message;
            System.out.println(namedMessage);
            server.sendMessageToAllClientsExcept(namedMessage, this);
        }
    }

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

    private boolean isPrivateMessage(String message) {
        return message.startsWith(PRIVATE_BEGIN_SIGNAL);
    }

    private String extractName(String message) {
        return message.substring(PRIVATE_BEGIN_SIGNAL.length(), message.indexOf(' '));
    }

    private String extractPrivateMessage(String message) {
        return message.substring(message.indexOf(' ') + 1);
    }

    String getUserName() {
        return this.name;
    }

    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
