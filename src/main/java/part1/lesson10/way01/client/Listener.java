package part1.lesson10.way01.client;

import java.io.BufferedReader;
import java.io.IOException;

import static part1.lesson10.way01.server.utils.ChatMessagesUtil.YOU_LEFT_CHAT;

/**
 * The class receives messages from server and writes them on console.
 * If client quits from chat, the class stops its job
 */

public class Listener extends Thread {
    private BufferedReader reader;

    public Listener(BufferedReader reader) {
        this.reader = reader;
        start();
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
                if (message.equals(YOU_LEFT_CHAT)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
