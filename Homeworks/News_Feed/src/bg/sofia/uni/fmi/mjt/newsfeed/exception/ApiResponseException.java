package bg.sofia.uni.fmi.mjt.newsfeed.exception;

public class ApiResponseException extends Exception {
    // Default constructor
    public ApiResponseException() {
        super();
    }

    // Constructor with a custom message
    public ApiResponseException(String message) {
        super(message);
    }

    // Constructor with a custom message and a cause
    public ApiResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with a cause
    public ApiResponseException(Throwable cause) {
        super(cause);
    }
}
