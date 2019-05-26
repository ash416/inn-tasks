package part1.lesson10.way01.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

import static part1.lesson10.way01.server.utils.ChatMessagesUtil.QUIT_MESSAGE;

/**
 * The class reads client's messages from the console and sends its to server.
 * If client writes 'quit', the class stops its job.
 */

public class Sender extends Thread {
    private BufferedWriter writer;

    Sender(BufferedWriter writer) {
        this.writer = writer;
        start();
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            String message;
            while (!(message = scanner.nextLine()).isEmpty()) {
                writer.write(message);
                writer.newLine();
                writer.flush();
                if (message.equals(QUIT_MESSAGE)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
