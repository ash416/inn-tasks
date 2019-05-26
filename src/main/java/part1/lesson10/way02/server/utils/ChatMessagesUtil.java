package part1.lesson10.way02.server.utils;

/**
 * The class provides chat service messages
 */

public class ChatMessagesUtil {
    public static final String ENTER_MESSAGE = "Enter";
    public static final String NEW_USER_MESSAGE = "В чат вошел %s.";
    public static final String ENTER_CONDITION_MESSAGE = "Введите ваше имя: ";
    public static final String WELCOME_MESSAGE = "Добро пожаловать в чат!";
    public static final String QUIT_MESSAGE = "quit";
    public static final String FROM_PRIVATE_MESSAGE = "Личное сообщение от %s: %s";
    public static final String YOU_LEFT_CHAT = "Вы вышли из чата.";
    public static final String USER_LEFT_CHAT = "%s вышел из чата.";
    public static final String ENTER_WRONG_NAME = "Вы ввели неверное имя. Данного пользователя нет в чате.";

    public static String convertMessageToString(byte[] bytes) {
        String string = new String(bytes);
        return string.substring(0, string.indexOf('\0'));
    }

}
