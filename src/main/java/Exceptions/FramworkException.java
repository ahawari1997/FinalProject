package Exceptions;

public class FramworkException extends RuntimeException {
    public FramworkException(String message) {
        super(message);

    }

    public FramworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
