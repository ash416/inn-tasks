package part1.lesson10.way02.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;

import static part1.lesson10.way01.server.utils.ChatMessagesUtil.YOU_LEFT_CHAT;
import static part1.lesson10.way02.server.utils.ChatMessagesUtil.convertMessageToString;

/**
 * The class receives messages from server and writes them on console.
 * If client quits from chat, the class stops its job
 */

public class Listener extends Thread {
    private DatagramSocket socket;
    private boolean active;
    private Client client;

    public Listener(DatagramSocket socket, Client client) {
        this.socket = socket;
        this.client = client;
        active = true;
    }

    @Override
    public void run() {
        while (active) {
            byte[] message = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(message, message.length);
            try {
                socket.receive(receivedPacket);
                String receivedMessage = convertMessageToString(receivedPacket.getData());
                System.out.println(receivedMessage);
                if (YOU_LEFT_CHAT.equals(receivedMessage)) {
                    client.quitFromChat();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void setActive(boolean value) {
        this.active = value;
    }
}
