package bg.sofia.uni.fmi.mjt.sentimentanalyzer.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

public class StopwordsFileReader implements FileReader {
    private final Reader reader;
    private final Set<String> stopwords = new HashSet<>();

    public StopwordsFileReader(Reader reader) throws IOException {
        this.reader = reader;
        this.read();
    }

    @Override
    public void read() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stopwords.add(line);
        }
    }

    public Set<String> stopwords() {
        return this.stopwords;
    }
}
