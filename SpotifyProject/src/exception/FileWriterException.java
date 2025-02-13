package exception;

public class FileWriterException extends RuntimeException {
    public FileWriterException(String message) {
        super(message);
    }

    public FileWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
