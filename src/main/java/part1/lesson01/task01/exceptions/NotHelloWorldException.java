package part1.lesson01.task01.exceptions;

public class NotHelloWorldException extends RuntimeException {
    public NotHelloWorldException(String message) {
        super(message);
    }
}
