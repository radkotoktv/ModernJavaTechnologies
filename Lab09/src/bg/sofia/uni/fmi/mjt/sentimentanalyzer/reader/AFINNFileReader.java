package bg.sofia.uni.fmi.mjt.sentimentanalyzer.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class AFINNFileReader implements FileReader {
    private static final int WORD_INDEX = 0;
    private static final int SCORE_INDEX = 1;

    private final Reader reader;
    private final Map<String, Integer> scores = new HashMap<>();

    public AFINNFileReader(Reader reader) throws IOException {
        this.reader = reader;
        this.read();
    }

    @Override
    public void read() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] tokens = line.split("\t");
            scores.put(tokens[WORD_INDEX], Integer.parseInt(tokens[SCORE_INDEX]));
        }
    }

    public Map<String, Integer> getScores() {
        return this.scores;
    }
}
