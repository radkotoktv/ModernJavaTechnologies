package bg.sofia.uni.fmi.mjt.glovo.comparator;

import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;

import java.util.Comparator;

public class DeliveryInfoTimeComparator implements Comparator<DeliveryInfo> {

    @Override
    public int compare(DeliveryInfo d1, DeliveryInfo d2) {
        int timeComparison = Double.compare(d1.getEstimatedTime(), d2.getEstimatedTime());
        if (timeComparison != 0) {
            return timeComparison;
        }

        return Double.compare(d1.getPrice(), d2.getPrice());
    }
}