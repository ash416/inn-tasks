package part1.lesson10;

import part1.lesson10.server.Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Server server;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String name = null;
    private static int countClients = 0;

    public ClientHandler(Socket clientSocket, Server server) {
        this.server = server;
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
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                if (name == null) {
                    name = message;
                }
                if (message.equals("quit")) {
                    server.sendMessageToClient(this, "Вы вышли из чата");
                    String quitMessage = name + " вышел из чата!";
                    server.sendMessagesToAllClients(quitMessage);
                    System.out.println(quitMessage);
                    break;
                }
                System.out.println(message);
                server.sendMessagesToAllClients(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getClientName() {
        return name;
    }

    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}