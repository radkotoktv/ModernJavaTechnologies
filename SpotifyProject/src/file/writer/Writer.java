package file.writer;

public abstract class Writer<T> {
    protected final String filePath;

    protected Writer(String filePath) {
        this.filePath = filePath;
    }

    public abstract void writeToFile(T toAdd);
}
