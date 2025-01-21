package bg.sofia.uni.fmi.mjt.newsfeed;

import bg.sofia.uni.fmi.mjt.newsfeed.article.Article;
import bg.sofia.uni.fmi.mjt.newsfeed.article.Source;
import bg.sofia.uni.fmi.mjt.newsfeed.category.Category;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.ApiKeyException;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.IllegalPageSizeException;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.RateLimitedException;
import bg.sofia.uni.fmi.mjt.newsfeed.parseresult.ErrorResponse;
import bg.sofia.uni.fmi.mjt.newsfeed.parseresult.OKResponse;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class WebNewsApiTest {
    private static final String MOCK_API_KEY = "mock_api_key";

    @Before
    public void setup() {
        System.setProperty("API_KEY", MOCK_API_KEY);
    }

    @Test
    public void testQueryCreation() throws IllegalPageSizeException {
        WebNewsApi webNewsApi = new WebNewsApi("Trump us", "us", 20, null);
        String apiKey = webNewsApi.getApiKey();
        assertNotEquals(null, apiKey);

        StringBuilder query = webNewsApi.createQuery();
        assertEquals("/v2/top-headlines?q=Trump%20us&pageSize=20&apiKey=" + apiKey + "&country=us", query.toString());
    }

    @Test
    public void testErrorResponse() throws IOException {
        try(FileReader reader = new FileReader("test/SampleErrorData.json")) {
            Gson gson = new Gson();
            ErrorResponse fromJson = gson.fromJson(reader, ErrorResponse.class);

            assertEquals("error", fromJson.getStatus());
            assertEquals("apiKeyMissing", fromJson.code());
            assertEquals("Example error message", fromJson.message());
        }
    }

    @Test
    public void testGetArticles() throws Exception {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2025-01-19T23:32:00Z", DateTimeFormatter.ISO_DATE_TIME);
        Date date = Date.from(zonedDateTime.toInstant());

        List<Article> articles = List.of(
                new Article(new Source("id1", "name1"), "author1", "title1", "description1", "url1", "urlToImage1", date, "content1"),
                new Article(new Source("id2", "name2"), "author2", "title2", "description2", "url2", "urlToImage2", date, "content2")
        );

        try(FileReader reader = new FileReader("test/SampleOKData.json")) {
            Gson gson = new Gson();
            OKResponse fromJson = gson.fromJson(reader, OKResponse.class);

            assertEquals(fromJson.articles().size(), articles.size());
            for (int i = 0; i < articles.size(); i++) {
                assertEquals(articles.get(i), fromJson.articles().get(i));
            }
        }
    }

    @Test
    public void testGetElements() throws IOException {
        OKResponse response = new OKResponse("ok", 2, null);

        try(FileReader reader = new FileReader("test/SampleOKData.json")) {
            Gson gson = new Gson();
            OKResponse fromJson = gson.fromJson(reader, OKResponse.class);

            assertEquals(response.getStatus(), fromJson.getStatus());
            assertEquals(response.totalResults(), fromJson.totalResults());
        }
    }

    @Test
    public void testCreateQueryWithInvalidKeywords() {
        WebNewsApi api = new WebNewsApi("", "us", 10, Category.GENERAL);
        assertThrows(IllegalArgumentException.class, api::createQuery);
    }

    @Test
    public void testCreateQueryWithInvalidPageSize() {
        WebNewsApi api = new WebNewsApi("test", "us", 200, Category.GENERAL);
        assertThrows(IllegalPageSizeException.class, api::createQuery);
    }

    @Test
    public void testHandleErrorResponseApiKeyDisabled() {
        WebNewsApi api = new WebNewsApi("test", "us", 10, Category.GENERAL);

        ErrorResponse errorResponse = new ErrorResponse("error", "apiKeyDisabled", "API key is disabled");

        ApiKeyException exception = assertThrows(ApiKeyException.class,
                () -> api.handleErrorResponse(errorResponse));

        assertEquals("API key is disabled", exception.getMessage());
    }

    @Test
    public void testHandleErrorResponseRateLimited() {
        WebNewsApi api = new WebNewsApi("test", "us", 10, Category.GENERAL);

        ErrorResponse errorResponse = new ErrorResponse("error", "rateLimited", "Rate limit exceeded");

        RateLimitedException exception = assertThrows(RateLimitedException.class,
                () -> api.handleErrorResponse(errorResponse));

        assertEquals("Rate limit exceeded", exception.getMessage());
    }

    @Test
    public void testHandleResponseWithErrorResponse() {
        WebNewsApi api = new WebNewsApi("test", "us", 10, Category.GENERAL);
        StringBuilder mockResponse = new StringBuilder(
                "{\"status\":\"error\",\"code\":\"apiKeyDisabled\",\"message\":\"API key is disabled\"}"
        );

        ApiKeyException exception = assertThrows(ApiKeyException.class,
                () -> api.handleResponse(mockResponse));

        assertEquals("API key is disabled", exception.getMessage());
    }

    @Test
    public void testHandleResponseWithOkResponse() throws Exception {
        WebNewsApi api = new WebNewsApi("test", "us", 10, Category.GENERAL);
        StringBuilder mockResponse = new StringBuilder(
                "{\"status\":\"ok\",\"totalResults\":1,\"articles\":[]}"
        );

        OKResponse response = (OKResponse) api.handleResponse(mockResponse);

        assertNotEquals(null, response);
        assertEquals("ok", response.status());
        assertTrue(response.articles().isEmpty());
    }
}
