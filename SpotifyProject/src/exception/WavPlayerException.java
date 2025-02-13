package exception;

public class WavPlayerException extends RuntimeException {
    public WavPlayerException(String message) {
        super(message);
    }

    public WavPlayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
