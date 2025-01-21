import bg.sofia.uni.fmi.mjt.newsfeed.WebNewsApi;
import bg.sofia.uni.fmi.mjt.newsfeed.article.Article;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.ApiResponseException;

import java.util.List;

public class Main {
    private static final int TEMP_PAGE_SIZE = 20;

    public static void main(String[] args) throws ApiResponseException {
        WebNewsApi webNewsApi = new WebNewsApi("Trump us", "us", TEMP_PAGE_SIZE, null);
        List<Article> articles = webNewsApi.fetchArticles();

        for (Article article : articles) {
            System.out.println(article.title());
        }
    }
}