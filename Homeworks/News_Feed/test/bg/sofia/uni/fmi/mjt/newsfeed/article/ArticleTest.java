package bg.sofia.uni.fmi.mjt.newsfeed.article;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ArticleTest {
    @Test
    public void testArticleConstructor() {
        Source source = new Source("id1", "Boredpanda.com");
        String author = "Renan Duarte";
        String title = "“No Sympathy”: UnitedHealth Loses $63 Billion In Value After Former CEO’s Assasination - Bored Panda";
        String description = "UnitedHealth Group has lost $63 billion in value following Brian Thompson's murder.";
        String url = "https://www.boredpanda.com/unitedhealth-loses-63-billion-value-following-ceo-assasination/";
        String urlToImage = "https://www.boredpanda.com/blog/wp-content/uploads/2025/01/fb_image_678d82d33541f.png";
        Date publishedAt = new Date();
        String content = "UnitedHealth Group is facing a staggering loss in its market value following the former CEO’s sudden murder.\r\nThe shocking assassination of Brian Thompson last December took the world by storm as all… [+4638 chars]";

        Article article = new Article(source, author, title, description, url, urlToImage, publishedAt, content);

        assertEquals(source, article.source());
        assertEquals(author, article.author());
        assertEquals(title, article.title());
        assertEquals(description, article.description());
        assertEquals(url, article.url());
        assertEquals(urlToImage, article.urlToImage());
        assertEquals(publishedAt, article.publishedAt());
        assertEquals(content, article.content());
    }
}