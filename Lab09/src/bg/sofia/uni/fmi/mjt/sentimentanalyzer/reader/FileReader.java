package bg.sofia.uni.fmi.mjt.sentimentanalyzer.reader;

import java.io.IOException;

public interface FileReader {
    /**
     * Reads from a file
     * @throw IOException on error from reading from file
     */
    void read() throws IOException;
}
