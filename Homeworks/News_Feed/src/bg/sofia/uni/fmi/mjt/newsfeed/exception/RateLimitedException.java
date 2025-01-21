package bg.sofia.uni.fmi.mjt.newsfeed.exception;

public class RateLimitedException extends ApiResponseException {
    public RateLimitedException(String message) {
        super(message);
    }
}
