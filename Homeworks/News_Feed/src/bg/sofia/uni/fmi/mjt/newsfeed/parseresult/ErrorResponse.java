package bg.sofia.uni.fmi.mjt.newsfeed.parseresult;

public record ErrorResponse(String status, String code, String message) implements ApiResponse {
    @Override
    public String getStatus() {
        return status;
    }
}
