package part1.lesson10.client;

import java.io.BufferedReader;
import java.io.IOException;

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
                if (message.equals("Вы вышли из чата")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
