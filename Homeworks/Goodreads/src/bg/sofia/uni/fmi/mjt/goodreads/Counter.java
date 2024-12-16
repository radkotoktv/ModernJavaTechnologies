package bg.sofia.uni.fmi.mjt.goodreads;

import java.util.Map;

public interface Counter {
    /**
     * Counts the amount of something
     *
     * @param input the string in which the search should be realised
     * @throws IllegalArgumentException if the input is null or empty
     * @return a Map of the counted elements
     */
    Map<String, Double> count(String input);
}
