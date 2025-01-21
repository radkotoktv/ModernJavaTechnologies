package bg.sofia.uni.fmi.mjt.newsfeed.exception;

public class UnexpectedErrorException extends ApiResponseException {
    public UnexpectedErrorException(String message) {
        super(message);
    }
}
