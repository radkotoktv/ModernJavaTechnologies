package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Map;
import java.util.Set;

public class TFIDF implements SimilarityCalculator {
    private final Set<Book> books;
    private final TextTokenizer tokenizer;

    public TFIDF(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    @Override
    public double calculateSimilarity(Book first, Book second) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<String, Double> computeTFIDF(Book book) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<String, Double> computeTF(Book book) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Map<String, Double> computeIDF(Book book) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}