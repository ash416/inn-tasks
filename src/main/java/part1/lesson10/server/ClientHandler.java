package part1.lesson10.server;

import java.io.*;
import java.net.Socket;

import static java.lang.String.format;

public class ClientHandler extends Thread{
    private static final String NEW_USER_MESSAGE = "В чат вошел %s";
    public static final String QUIT_MESSAGE = "quit";
    private Socket socket;

    private Server server;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String name = null;
    private static int countClients = 0;

    public ClientHandler(Socket clientSocket, Server server) {
        this.server = server;
        this.socket = clientSocket;
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
        try {
            server.sendMessageToClient("Введите ваше имя: ", this);
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
        name = reader.readLine();
        server.sendMessagesToAllClients(format(NEW_USER_MESSAGE, name));
        System.out.println(format(NEW_USER_MESSAGE, name));
    }

    synchronized private void handleQuit() {
        server.sendMessageToClient("Вы вышли из чата", this);
        server.removeFromChat(this);
        String quitMessage = name + " вышел из чата!";
        server.sendMessageToAllClientsExcept(quitMessage, this);
        System.out.println(quitMessage);
    }

    private void handleMessage(String message) {
        String namedMessage = name + ": " + message;
        System.out.println(namedMessage);
        server.sendMessageToAllClientsExcept(namedMessage, this);
    }

    private String getClientName() {
        return name;
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
