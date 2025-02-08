package exception;

public class DisconnectedFromServerException extends RuntimeException {
    public DisconnectedFromServerException(String message) {
        super(message);
    }
}
