package part1.lesson10.way02.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import static part1.lesson10.way02.server.Server.MULTICAST_ADDRESS;
import static part1.lesson10.way02.server.utils.ChatMessagesUtil.convertMessageToString;

/**
 * The class receives other clients messages from server and writes them on console.
 * If client quits from chat, the class stops its job
 */

public class MulticastListener extends Thread {
    private MulticastSocket socket;
    private boolean active;

    public MulticastListener(MulticastSocket socket) {
        this.socket = socket;
        active = true;
    }

    @Override
    public void run() {
        try {
            while (active) {
                byte[] message = new byte[256];
                DatagramPacket receivedPacket = new DatagramPacket(message, message.length);
                socket.receive(receivedPacket);
                String receivedMessage = convertMessageToString(receivedPacket.getData());
                System.out.println(receivedMessage);
            }
            socket.leaveGroup(InetAddress.getByName(MULTICAST_ADDRESS));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setActive(boolean value) {
        this.active = value;
    }
}
