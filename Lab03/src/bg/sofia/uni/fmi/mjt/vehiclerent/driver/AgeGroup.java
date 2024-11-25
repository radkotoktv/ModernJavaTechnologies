package bg.sofia.uni.fmi.mjt.vehiclerent.driver;

public enum AgeGroup {
    JUNIOR(10),
    EXPERIENCED(0),
    SENIOR(15);

    private final int value;
    AgeGroup(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}