package bg.sofia.uni.fmi.mjt.newsfeed.parseresult;

import bg.sofia.uni.fmi.mjt.newsfeed.article.Article;

import java.util.List;

public record OKResponse(String status, int totalResults, List<Article> articles) implements ApiResponse {
    @Override
    public String getStatus() {
        return status;
    }
}
