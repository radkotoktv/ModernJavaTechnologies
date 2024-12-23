package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.BookRecommender;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres.GenresOverlapSimilarityCalculator;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookRecommenderTest {
    @Test
    public void testRecommendBooks() {
        final double rating1 = 3.92;
        final int ratingCount1 = 20378;
        String[] genres1 = {"Nonfiction", "Sexuality", "Relationships", "Polyamory", "Feminism", "Self Help", "Psychology"};
        Book book1 = new Book("1",
                "The Ethical Slut: A Guide to Infinite Sexual Possibilities",
                "Dossie Easton",
                "The essential guide for singles and couples who want to explore polyamory in ways that are ethically and emotionally sustainable.For anyone who has ever dreamed of love, sex, and companionship beyond the limits of traditional monogamy, this groundbreaking guide navigates the infinite possibilities that open relationships can offer. Experienced ethical sluts Dossie Easton and Janet W. Hardy dispel myths and cover all the skills necessary to maintain a successful and responsible polyamorous lifestyle--from self-reflection and honest communication to practicing safe sex and raising a family. Individuals and their partners will learn how to discuss and honor boundaries, resolve conflicts, and to define relationships on their own terms. \"I couldn't stop reading it, and I for one identify as an ethical slut. This is a book for anyone interested in creating more pleasure in their lives . . . a complete guide to improving any style of relating, from going steady to having an extended family of sexual friends.\" --Betty Dodson, PhD, author of Sex for One",
                Arrays.stream(genres1).toList(),
                rating1,
                ratingCount1,
                "https://www.goodreads.com/book/show/54944.The_Ethical_Slut");

        final double rating2 = 4.04;
        final int ratingCount2 = 5208;
        String[] genres2 = {"Nonfiction", "Sexuality", "Relationships", "Polyamory", "Help"};
        Book book2 = new Book("2",
                "Kiss Me Like This (The Morrisons, #1)",
                "Bella Andre",
                "Sean Morrison, one of six siblings and the top college baseball player in the country, is reeling from a heartbreakingly painful loss. Nothing seems to matter anymore...until the night Serena Britten unexpectedly ends up in his arms. Serena is a world-famous model who has only ever wanted to be normal, even though her mother has always pushed her to become a superstar. Though it isn't easy to try to leave everyone and everything she knows behind, Serena is determined to enroll in college. More than anything, she wants to turn her love for books into a new career that she actually loves. Only, she never expected to meet someone like Sean on campus—or to be instantly consumed by their incredible chemistry and connection. But when the pressures of her high-profile modeling career only get bigger and more demanding, will it make living a normal life as a college student—and falling in love with the hottest guy on campus—impossible?",
                Arrays.stream(genres2).toList(),
                rating2,
                ratingCount2,
                "https://www.goodreads.com/book/show/21248483-kiss-me-like-this");

        Set<Book> books = new HashSet<>();
        books.add(book1);
        books.add(book2);

        SimilarityCalculator calculator = new GenresOverlapSimilarityCalculator();

        BookRecommender bookRecommender = new BookRecommender(books, calculator);

        assertEquals(Set.of(book1, book2), bookRecommender.recommendBooks(book1, 2).keySet());
        assertEquals(1.0, bookRecommender.recommendBooks(book1, 2).get(book1));
        assertEquals(0.8, bookRecommender.recommendBooks(book1, 2).get(book2));
    }
}
