package file.reader;

import java.util.List;

public abstract class Reader {
    protected final String filePath;

    protected Reader(String filePath) {
        this.filePath = filePath;
    }

    public abstract List<?> readFromFile();
}
