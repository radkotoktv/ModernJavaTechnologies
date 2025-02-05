package constants;

public enum Constant {
    PORT("7777"),
    BUFFER_SIZE("1024");

    private final String value;

    Constant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
