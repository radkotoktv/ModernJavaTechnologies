import bg.sofia.uni.fmi.mjt.newsfeed.WebNewsApi;
import bg.sofia.uni.fmi.mjt.newsfeed.article.Article;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.ApiResponseException;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws ApiResponseException, IOException {
        WebNewsApi webNewsApi = new WebNewsApi("Trump us", "us", 20, null);
        List<Article> articles = webNewsApi.fetchArticles();

        for (Article article : articles) {
            System.out.println(article.title());
        }
    }
}