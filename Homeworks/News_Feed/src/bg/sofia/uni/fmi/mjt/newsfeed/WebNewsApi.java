package bg.sofia.uni.fmi.mjt.newsfeed;

import bg.sofia.uni.fmi.mjt.newsfeed.article.Article;
import bg.sofia.uni.fmi.mjt.newsfeed.category.Category;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.ApiKeyException;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.ApiResponseException;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.IllegalPageSizeException;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.InvalidParameterException;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.RateLimitedException;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.SourceException;
import bg.sofia.uni.fmi.mjt.newsfeed.exception.UnexpectedErrorException;
import bg.sofia.uni.fmi.mjt.newsfeed.parseresult.ApiResponse;
import bg.sofia.uni.fmi.mjt.newsfeed.parseresult.ErrorResponse;
import bg.sofia.uni.fmi.mjt.newsfeed.parseresult.OKResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;

import java.lang.reflect.Type;
import java.net.Socket;

import java.util.List;

public class WebNewsApi {
    private static final String BASE_URL = "/v2/top-headlines";
    private static final String HOST = "newsapi.org";
    private static final int HTTP_PORT = 80;

    private static final int MIN_PAGE_SIZE = 1;
    private static final int MAX_PAGE_SIZE = 100;

    private final String keywords;
    private final String country;
    private final int pageSize;
    private final Category category;

    public WebNewsApi(String keywords, String country, int pageSize, Category category) {
        this.keywords = keywords;
        this.country = country;
        this.pageSize = pageSize;
        this.category = category;
    }

    /**
     * Get your API_KEY system variable.
     *
     * @return The API_KEY system variable.
     */
    public String getApiKey() {
        return System.getenv("API_KEY");
    }

    /**
     * Creates the query based on keywords, country, and page size.
     *
     * @return String representation of the GET query.
     */
    public StringBuilder createQuery() throws IllegalPageSizeException, IllegalArgumentException {
        if (keywords == null || keywords.isBlank()) {
            throw new IllegalArgumentException("Keywords must not be null or empty");
        }
        if (pageSize < MIN_PAGE_SIZE || pageSize > MAX_PAGE_SIZE) {
            throw new IllegalPageSizeException("Page size must be between 1 and 100");
        }
        StringBuilder query = new StringBuilder(BASE_URL)
                .append("?q=").append(keywords.replace(" ", "%20"))
                .append("&pageSize=").append(pageSize)
                .append("&apiKey=").append(getApiKey());

        if (country != null && !country.isBlank()) {
            query.append("&country=").append(country);
        }

        if (category != null) {
            query.append("&category=").append(category.getCategory());
        }

        return query;
    }

    /**
     * Creates the GET request based on keywords, country, and page size.
     *
     * @return String representation of the GET request.
     */
    public String createRequest() throws IllegalPageSizeException {
        return "GET " + createQuery() + " HTTP/1.1\r\n" +
                "Host: " + HOST + "\r\n" +
                "Connection: close\r\n\r\n";
    }

    public ApiResponse handleResponse(StringBuilder response) throws ApiResponseException {
        Gson gson = new Gson();
        if (response.toString().contains("status") && response.toString().contains("code")) {
            Type type = new TypeToken<ErrorResponse>() {
            }.getType();
            ErrorResponse result = gson.fromJson(response.toString(), type);
            handleErrorResponse(result);
        }

        Type type = new TypeToken<OKResponse>() {
        }.getType();
        return gson.<OKResponse>fromJson(response.toString(), type);
    }

    public void handleErrorResponse(ErrorResponse response) throws ApiResponseException {
        switch(response.code()) {
            case "apiKeyDisabled":
                throw new ApiKeyException("API key is disabled");
            case "apiKeyExhausted":
                throw new ApiKeyException("API key is exhausted");
            case "apiKeyInvalid":
                throw new ApiKeyException("API key is invalid");
            case "apiKeyMissing":
                throw new ApiKeyException("API key is missing");
            case "parameterInvalid":
                throw new InvalidParameterException("Parameter is invalid");
            case "parametersMissing":
                throw new InvalidParameterException("Parameters are missing");
            case "rateLimited":
                throw new RateLimitedException("Rate limit exceeded");
            case "sourcesTooMany":
                throw new SourceException("Too many sources");
            case "sourceDoesNotExist":
                throw new SourceException("Source does not exist");
            case "unexpectedError":
                throw new UnexpectedErrorException("Unexpected error");
            default:
                throw new RuntimeException("Unknown error");
        }
    }

    /**
     * Fetches articles based on keywords, country, and page size.
     *
     * @return List of articles matching the criteria.
     * @throws ApiResponseException If an error occurs during the request.
     */
    public List<Article> fetchArticles() throws ApiResponseException {
        try (Socket socket = new Socket(HOST, HTTP_PORT)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String request = createRequest();
            writer.println(request);

            StringBuilder response = new StringBuilder();
            String line;
            boolean isBody = false;
            while ((line = reader.readLine()) != null) {
                if (isBody) {
                    response.append(line).append("\n");
                }
                if (line.isEmpty()) {
                    isBody = true;
                }
            }

            return ((OKResponse) handleResponse(response)).articles();
        } catch (IOException e) {
            throw new RuntimeException("Could not access the website", e);
        }
    }
}