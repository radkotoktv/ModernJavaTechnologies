package file.reader;

import java.util.ArrayList;

public abstract class Reader {
    protected final String filePath;

    protected Reader(String filePath) {
        this.filePath = filePath;
    }

    public abstract ArrayList<?> readFromFile();
}
