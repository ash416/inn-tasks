package part1.lesson10.client;

import part1.lesson10.server.Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Integer port;

    Client(Integer port) {
        this.port = port;
    }

    public void start() {

        try (Socket socket = new Socket("127.0.0.1", Server.SERVER_PORT);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            Listener listener = new Listener(socket);
            listener.start();
            Scanner scanner = new Scanner(System.in);
            String message;
            while (!(message = scanner.nextLine()).isEmpty()) {
                writer.write(message);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client(1234);
        client.start();
    }


}