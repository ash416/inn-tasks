package part1.lesson10.way02.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static part1.lesson10.way02.server.utils.ChatMessagesUtil.*;

/**
 * The class is chat.
 * Clients can send message to all clients in the chat and to specific client
 *
 */

public class Server extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private boolean active;
    private InetAddress group;

    private DatagramSocket serverSocket;

    public final static Integer SERVER_PORT = 4000;
    public final static int MULTICAST_PORT = 3000;
    public final static String SERVER_ADDRESS = "127.0.0.1";
    public final static String MULTICAST_ADDRESS = "225.4.5.6";
    private static Map<String, ClientInfo> clientsByAddress = new HashMap<>();
    private static Map<String, ClientInfo> clientsByName = new HashMap<>();

    public Server() {
        active = true;
        try {
            serverSocket = new DatagramSocket(SERVER_PORT);
            group = InetAddress.getByName(MULTICAST_ADDRESS);
            LOGGER.info("Сервер запущен, ждет подключения клиентов");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (active) {
                DatagramPacket receivedPacket = getPacketFromClient();
                if (clientIsInChat(receivedPacket)) {
                    if (clientSentName(receivedPacket)) {
                        handleNewClientName(receivedPacket);
                        continue;
                    }
                    if (clientSentQuit(receivedPacket)) {
                        handleQuit(receivedPacket);
                        continue;
                    }
                    if (clientSentPrivateMessage(receivedPacket)) {
                        handlePrivateMessage(receivedPacket);
                        continue;
                    }
                    handleUsualMessage(receivedPacket);
                } else {
                    if (isEnterMessage(receivedPacket)) {
                        LOGGER.info("Клиент {} вошел в чат", createStringClientAddress(receivedPacket));
                        handleEnter(receivedPacket);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            serverSocket.close();
    }

}

    private DatagramPacket getPacketFromClient() throws IOException {
        byte[] message = new byte[1024];
        DatagramPacket receivedPacket = new DatagramPacket(message, message.length);
        serverSocket.receive(receivedPacket);
        return receivedPacket;
    }

    /**
     * Method handles new entered client, asks its name
     * @param packet
     * @throws IOException
     */
    private void handleEnter(DatagramPacket packet) throws IOException {
        clientsByAddress.put(createStringClientAddress(packet), new ClientInfo(packet.getPort(), packet.getAddress()));
        byte[] welcomeMessage = WELCOME_MESSAGE.getBytes();
        LOGGER.info("Клиенту {} отправлено сообщение: {}", createStringClientAddress(packet), WELCOME_MESSAGE);
        serverSocket.send(new DatagramPacket(welcomeMessage, welcomeMessage.length,
                packet.getAddress(), packet.getPort()));
        byte[] enterMessage = ENTER_CONDITION_MESSAGE.getBytes();
        LOGGER.info("Клиенту {} отправлено сообщение: {}", createStringClientAddress(packet), ENTER_CONDITION_MESSAGE);
        serverSocket.send(new DatagramPacket(enterMessage, enterMessage.length,
                packet.getAddress(), packet.getPort()));
    }

    /**
     * Method handles usual messages
     * @param packet
     * @throws IOException
     */
    private void handleUsualMessage(DatagramPacket packet) throws IOException {
        ClientInfo client = clientsByAddress.get(createStringClientAddress(packet));
        String message = format("%s: %s", client.getName(), convertMessageToString(packet.getData()));
        serverSocket.send(new DatagramPacket(message.getBytes(), message.getBytes().length,
                group, MULTICAST_PORT));
        LOGGER.info("Клиент {} написал сообщение в общий чат: {}", client.name, message);
    }

    /**
     * Method handles private messages, sends it to addressee
     * @param packet
     * @throws IOException
     */
    private void handlePrivateMessage(DatagramPacket packet) throws IOException {
        ClientInfo client = clientsByAddress.get(createStringClientAddress(packet));
        String packetData = convertMessageToString(packet.getData());
        String receiver = packetData.substring(packetData.indexOf('@') + 1, packetData.indexOf(' '));
        String message = packetData.substring(packetData.indexOf(' ') + 1);
        byte[] messageForReceiving;
        ClientInfo clientReceiver = clientsByName.get(receiver);
        if (clientReceiver == null) {
            messageForReceiving = ENTER_WRONG_NAME.getBytes();
            serverSocket.send(new DatagramPacket(messageForReceiving, messageForReceiving.length,
                    client.getAddress(), client.getPort()));
        } else {
            messageForReceiving = format(FROM_PRIVATE_MESSAGE, client.getName(), message).getBytes();
            serverSocket.send(new DatagramPacket(messageForReceiving, messageForReceiving.length,
                    clientReceiver.getAddress(), clientReceiver.getPort()));
            LOGGER.info("Клиент {} отправил личное сообщение {} : {}", client.getName(), clientReceiver.getName(), message);
        }
    }

    /**
     * Method creates address of client, that is key in map clients
     * @param packet
     * @return
     */
    private String createStringClientAddress(DatagramPacket packet) {
        return packet.getAddress() + ":" + packet.getPort();
    }

    /**
     * Method checks whether message is enter
     * @param packet
     * @return
     */
    private boolean isEnterMessage(DatagramPacket packet) {
        return ENTER_MESSAGE.equals(convertMessageToString(packet.getData()));
    }

    /**
     * Method checks whether message is private. Private message starts with '@'
     * @param packet
     * @return
     */
    private boolean clientSentPrivateMessage(DatagramPacket packet) {
        return convertMessageToString(packet.getData()).startsWith("@");
    }

    /**
     * Method checks whether client is in chat
     * @param packet
     * @return
     */
    private boolean clientIsInChat(DatagramPacket packet) {
        return clientsByAddress.get(createStringClientAddress(packet)) != null;
    }

    /**
     * Method checks whether client sent name
     * @param packet
     * @return
     */

    private boolean clientSentName(DatagramPacket packet) {
        ClientInfo client = clientsByAddress.get(createStringClientAddress(packet));
        return client.getName() == null;
    }

    /**
     * Method checks whether client left chat
     * @param packet
     * @return
     */
    private boolean clientSentQuit(DatagramPacket packet) {
        return QUIT_MESSAGE.equals(new String(packet.getData()).trim());
    }

    /**
     * Method handles client exit
     * @param packet
     * @throws IOException
     */

    private void handleQuit(DatagramPacket packet) throws IOException {
        ClientInfo client = clientsByAddress.remove(createStringClientAddress(packet));
        byte[] buyMessage = YOU_LEFT_CHAT.getBytes();
        serverSocket.send(new DatagramPacket(buyMessage, buyMessage.length,
                client.getAddress(), client.getPort()));
        byte[] userQuit = format(USER_LEFT_CHAT, client.getName()).getBytes();
        serverSocket.send(new DatagramPacket(userQuit, userQuit.length,
                group, MULTICAST_PORT));
    }

    /**
     * Method handles new client
     * @param packet
     * @throws IOException
     */

    private void handleNewClientName(DatagramPacket packet) throws IOException {
        ClientInfo client = clientsByAddress.get(createStringClientAddress(packet));
        String name = convertMessageToString(packet.getData());
        client.setName(name);
        clientsByName.put(name, client);
        LOGGER.info("Клиент {}:{} ввел имя {}", packet.getAddress().getHostAddress(), packet.getPort(), name);
        String multicastMessage = format(NEW_USER_MESSAGE, name);
        byte[] newClientMessage = multicastMessage.getBytes();
        serverSocket.send(new DatagramPacket(newClientMessage, newClientMessage.length,
                group, MULTICAST_PORT));
        LOGGER.info("Клиентам в группе отправлено сообщение: {}", multicastMessage);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
