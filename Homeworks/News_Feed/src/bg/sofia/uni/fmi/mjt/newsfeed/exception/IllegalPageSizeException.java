package bg.sofia.uni.fmi.mjt.newsfeed.exception;

public class IllegalPageSizeException extends ApiResponseException {
    public IllegalPageSizeException(String message) {
        super(message);
    }
}
