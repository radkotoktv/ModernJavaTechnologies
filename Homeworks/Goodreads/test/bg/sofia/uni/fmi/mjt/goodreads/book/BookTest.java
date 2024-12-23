package bg.sofia.uni.fmi.mjt.goodreads.book;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTest {
    @Test
    public void testBookMaking() {
        String[] tokens = {
                "1",
                "Effective Java",
                "Joshua Bloch",
                "A comprehensive guide, comma, to programming in Java.",
                "['Programming', 'Java']",
                "4.8",
                "12345",
                "http://example.com/effective-java"
        };

        Book book = Book.of(tokens);
        assertEquals(tokens[0], book.ID());
        assertEquals(tokens[1], book.title());
        assertEquals(tokens[2], book.author());
        assertEquals(tokens[3], book.description());

        List<String> genres = new ArrayList<>();
        genres.add("Programming");
        genres.add("Java");
        assertEquals(genres, book.genres());
        assertEquals(Double.parseDouble(tokens[5]), book.rating());
        assertEquals(Integer.parseInt(tokens[6]), book.ratingCount());
        assertEquals(tokens[7], book.URL());
    }
}
