package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import static java.lang.Math.min;

public class GenresOverlapSimilarityCalculator implements SimilarityCalculator {

    @Override
    public double calculateSimilarity(Book first, Book second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Cannot compare null to a book!");
        }

        double enumerator = min(first.genres().size(), second.genres().size());
        return first.genres().stream()
                .filter(second.genres()::contains)
                .count()
                / enumerator;
    }
}