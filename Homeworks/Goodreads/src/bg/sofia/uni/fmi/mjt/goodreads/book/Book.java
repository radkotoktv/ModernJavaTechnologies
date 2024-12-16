package bg.sofia.uni.fmi.mjt.goodreads.book;

import java.util.Arrays;
import java.util.List;

public record Book(
        String ID,
        String title,
        String author,
        String description,
        List<String> genres,
        double rating,
        int ratingCount,
        String URL
) {
    static final int ID_INDEX = 0;
    static final int TITLE_INDEX = 1;
    static final int AUTHOR_INDEX = 2;
    static final int DESCRIPTION_INDEX = 3;
    static final int GENRES_INDEX = 4;
    static final int RATING_INDEX = 5;
    static final int RATINGCOUNT_INDEX = 6;
    static final int URL_INDEX = 7;
    static final int TOKENS_SIZE = 8;

    public static Book of(String[] tokens) {
        if (tokens == null || tokens.length != TOKENS_SIZE) {
            throw new IllegalArgumentException("Invalid input array. Expected an array of 8 elements.");
        }

        String id = tokens[ID_INDEX];
        String title = tokens[TITLE_INDEX];
        String author = tokens[AUTHOR_INDEX];
        String description = tokens[DESCRIPTION_INDEX];

        String genresRaw = tokens[GENRES_INDEX];
        List<String> genres = Arrays.stream(genresRaw.substring(1, genresRaw.length() - 1).split(","))
                .map(String::trim)
                .map(s -> s.replaceAll("^'|'$", ""))
                .toList();

        double rating = Double.parseDouble(tokens[RATING_INDEX]);
        int ratingCount = Integer.parseInt(tokens[RATINGCOUNT_INDEX].replace(",", "")); // Remove commas in the number
        String url = tokens[URL_INDEX];

        return new Book(id, title, author, description, genres, rating, ratingCount, url);
    }
}
