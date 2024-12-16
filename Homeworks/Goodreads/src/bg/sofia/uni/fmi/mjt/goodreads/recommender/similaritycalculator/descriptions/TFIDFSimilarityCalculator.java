package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.WordCounter;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collection;
import java.util.List;

public class TFIDFSimilarityCalculator implements SimilarityCalculator {
    private final Set<Book> books;
    private final TextTokenizer tokenizer;

    public TFIDFSimilarityCalculator(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    @Override
    public double calculateSimilarity(Book first, Book second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Cannot compare book to null!");
        }

        Map<String, Double> tfIdfScoresFirst = computeTFIDF(first);
        Map<String, Double> tfIdfScoresSecond = computeTFIDF(second);

        return cosineSimilarity(tfIdfScoresFirst, tfIdfScoresSecond);
    }

    private double cosineSimilarity(Map<String, Double> first, Map<String, Double> second) {
        double magnitudeFirst = magnitude(first.values());
        double magnitudeSecond = magnitude(second.values());

        return dotProduct(first, second) / (magnitudeFirst * magnitudeSecond);
    }

    private double dotProduct(Map<String, Double> first, Map<String, Double> second) {
        Set<String> commonKeys = new HashSet<>(first.keySet());
        commonKeys.retainAll(second.keySet());

        return commonKeys.stream()
                .mapToDouble(word -> first.get(word) * second.get(word))
                .sum();
    }

    private double magnitude(Collection<Double> input) {
        double squaredMagnitude = input.stream()
                .map(v -> v * v)
                .reduce(0.0, Double::sum);

        return Math.sqrt(squaredMagnitude);
    }

    public Map<String, Double> computeTFIDF(Book book) {
        Map<String, Double> tfidfs = new HashMap<>();
        Map<String, Double> tfs = computeTF(book);
        Map<String, Double> idfs = computeIDF(book);

        for (String token : idfs.keySet()) {
            tfidfs.put(token, tfs.get(token) * idfs.get(token));
        }
        return tfidfs;
    }

    public Map<String, Double> computeTF(Book book) {
        Map<String, Double> tfs = new HashMap<>();

        List<String> tokenizedDescription = tokenizer.tokenize(book.description());
        WordCounter wordCounter = new WordCounter(tokenizer, tokenizedDescription.size());
        Map<String, Double> counts = wordCounter.count(tokenizedDescription.toString());
        for (String token : counts.keySet()) {
            // Formula could need to be changed to
            // 0.5 + 0.5 * (f / max(f))
            tfs.put(token, counts.get(token) / (double)wordCounter.getTotalWords());
        }
        return tfs;
    }

    public Map<String, Double> computeIDF(Book book) {
        Map<String, Double> idfs = new HashMap<>();

        List<String> tokenizedDescription = tokenizer.tokenize(book.description());
        WordCounter wordCounter = new WordCounter(tokenizer, tokenizedDescription.size());
        Map<String, Double> counts = wordCounter.count(tokenizedDescription.toString());

        for (String token : counts.keySet()) {
            idfs.put(token, Math.log(((double)wordCounter.getTotalWords() + 1) / (counts.get(token) + 1)));
        }
        return idfs;
    }

}