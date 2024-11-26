package bg.sofia.uni.fmi.mjt.glovo.delivery;

public enum DeliveryType {
    CAR(5, 3),
    BIKE(3, 5);

    private final int pricePerKilometer;
    private final int timePerKilometer;

    DeliveryType(int pricePerKilometer, int timePerKilometer) {
        this.pricePerKilometer = pricePerKilometer;
        this.timePerKilometer = timePerKilometer;
    }

    public int getPricePerKilometer() {
        return this.pricePerKilometer;
    }

    public int getTimePerKilometer() {
        return this.timePerKilometer;
    }
}
