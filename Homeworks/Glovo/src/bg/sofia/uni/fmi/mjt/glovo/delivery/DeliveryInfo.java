package bg.sofia.uni.fmi.mjt.glovo.delivery;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;

import java.util.Objects;

public class DeliveryInfo {
    private Location deliveryGuyLocation;
    private double price;
    private int estimatedTime;
    private DeliveryType deliveryType;

    public DeliveryInfo(Location deliveryGuyLocation, double price, int estimatedTime, DeliveryType deliveryType) {
        this.deliveryGuyLocation = deliveryGuyLocation;
        this.price = price;
        this.estimatedTime = estimatedTime;
        this.deliveryType = deliveryType;
    }

    public void setDeliveryGuyLocation(Location deliveryGuyLocation) {
        this.deliveryGuyLocation = deliveryGuyLocation;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Location getDeliveryGuyLocation() {
        return deliveryGuyLocation;
    }

    public double getPrice() {
        return price;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryInfo that = (DeliveryInfo) o;
        return Double.compare(price, that.price) == 0 && estimatedTime == that.estimatedTime && Objects.equals(deliveryGuyLocation, that.deliveryGuyLocation) && deliveryType == that.deliveryType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryGuyLocation, price, estimatedTime, deliveryType);
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }
}
