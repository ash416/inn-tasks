package part1.lesson10.way02.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import static part1.lesson10.way02.server.Server.SERVER_ADDRESS;
import static part1.lesson10.way02.server.Server.SERVER_PORT;

/**
 * The class reads client's messages from the console and sends its to server.
 */

public class Sender extends Thread {
    private DatagramSocket socket;
    private boolean active;

    Sender(DatagramSocket socket) {
        this.socket = socket;
        active = true;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            String message;
            while (active && !(message = scanner.nextLine()).isEmpty()) {
                DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length,
                        InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
                socket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setActive(boolean value) {
        this.active = value;
    }
}
