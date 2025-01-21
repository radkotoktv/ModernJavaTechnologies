package bg.sofia.uni.fmi.mjt.newsfeed.exception;

public class ApiResponseException extends Exception {
    public ApiResponseException() {
        super();
    }

    public ApiResponseException(String message) {
        super(message);
    }

    public ApiResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiResponseException(Throwable cause) {
        super(cause);
    }
}
