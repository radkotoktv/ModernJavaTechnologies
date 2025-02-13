package exception;

public class DisconnectedFromServerException extends RuntimeException {
    public DisconnectedFromServerException(String message) {
        super(message);
    }

    public DisconnectedFromServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
