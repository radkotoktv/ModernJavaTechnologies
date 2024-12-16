package bg.sofia.uni.fmi.mjt.goodreads;

import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounter implements Counter {
    private final TextTokenizer tokenizer;
    private final int totalWords;

    public WordCounter(TextTokenizer tokenizer, int totalWords) {
        this.tokenizer = tokenizer;
        this.totalWords = totalWords;
    }

    public int getTotalWords() {
        return this.totalWords;
    }

    @Override
    public Map<String, Double> count(String input) {
        List<String> tokenized = tokenizer.tokenize(input);
        int tokensAmount = tokenized.size();

        Map<String, Double> counted = new HashMap<>();
        for (String token : tokenized) {
            if (!counted.containsKey(token)) {
                counted.put(token, 1.0);
            } else {
                counted.put(token, counted.get(token) + 1);
            }
        }
        return counted;
    }
}
