package part1.lesson10.way02.client;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static part1.lesson10.way02.server.Server.*;
import static part1.lesson10.way02.server.utils.ChatMessagesUtil.ENTER_MESSAGE;

/**
 * The class is client in the chat. Client has listener for server messages, listener for other clients messages
 * and sender
 */

public class Client {
    private MulticastListener multicastListener;
    private Listener listener;
    private Sender sender;

    public Client() {
        try {
            DatagramSocket socket = new DatagramSocket();
            MulticastSocket multiSocket = new MulticastSocket(MULTICAST_PORT);
            multiSocket.joinGroup(InetAddress.getByName(MULTICAST_ADDRESS));
            listener = new Listener(socket, this);
            multicastListener = new MulticastListener(multiSocket);
            sender = new Sender(socket);
            byte[] enterMessage = ENTER_MESSAGE.getBytes(StandardCharsets.UTF_8);
            socket.send(new DatagramPacket(enterMessage, enterMessage.length,
                    InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void quitFromChat() {
        listener.setActive(false);
        multicastListener.setActive(false);
        sender.setActive(false);
    }

    public void start() {
        try {
            multicastListener.start();
            listener.start();
            sender.start();
            multicastListener.join();
            listener.join();
            sender.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }


}
