package bg.sofia.uni.fmi.mjt.newsfeed.article;

import java.util.Date;

public record Article(Source source,
                      String author,
                      String title,
                      String description,
                      String url,
                      String urlToImage,
                      Date publishedAt,
                      String content) {
}
