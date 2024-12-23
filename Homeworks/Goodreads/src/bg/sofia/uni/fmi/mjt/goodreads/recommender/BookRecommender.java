package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class BookRecommender implements BookRecommenderAPI {
    private final Set<Book> initialBooks;
    private final SimilarityCalculator calculator;

    public BookRecommender(Set<Book> initialBooks, SimilarityCalculator calculator) {
        this.initialBooks = initialBooks;
        this.calculator = calculator;
    }

    @Override
    public SortedMap<Book, Double> recommendBooks(Book origin, int maxN) {
        if (origin == null) {
            throw new IllegalArgumentException("Origin book cannot be null!");
        }

        if (maxN <= 0) {
            throw new IllegalArgumentException("Cannot show 0 entries!");
        }

        SortedMap<Book, Double> similarBooks = new TreeMap<Book, Double>();

        for (Book book : initialBooks) {
            similarBooks.put(book, calculator.calculateSimilarity(origin, book));
        }

        int left = maxN;
        SortedMap<Book, Double> similarBooks2 = new TreeMap<Book, Double>();
        while (left > 0) {
            similarBooks2.put(similarBooks.firstEntry().getKey(), similarBooks.firstEntry().getValue());
            similarBooks.remove(similarBooks.firstKey());
            left--;
        }
        return similarBooks2;
    }

}