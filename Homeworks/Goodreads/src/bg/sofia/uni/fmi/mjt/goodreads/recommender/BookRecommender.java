package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Comparator;

import static java.lang.Math.min;

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
        int left = min(maxN, initialBooks.size());

        SortedMap<Book, Double> similarBooks = new TreeMap<>(Comparator.comparingDouble(
                (Book b) -> calculator.calculateSimilarity(origin, b))
                .thenComparing(Book::title)
                .reversed()
        );

        for (Book book : initialBooks) {
            similarBooks.put(book, calculator.calculateSimilarity(origin, book));
        }
        SortedMap<Book, Double> result = new TreeMap<>(Comparator.comparingDouble(
                        (Book b) -> calculator.calculateSimilarity(origin, b))
                .thenComparing(Book::title)
                .reversed()
        );
        while (left > 0) {
            result.put(similarBooks.firstKey(), similarBooks.get(similarBooks.firstKey()));
            similarBooks.remove(similarBooks.firstKey());
            left--;
        }
        return result;
    }
}