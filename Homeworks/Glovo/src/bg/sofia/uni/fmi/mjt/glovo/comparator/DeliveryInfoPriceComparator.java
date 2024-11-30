package bg.sofia.uni.fmi.mjt.glovo.comparator;

import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;

import java.util.Comparator;

public class DeliveryInfoPriceComparator implements Comparator<DeliveryInfo> {

    @Override
    public int compare(DeliveryInfo d1, DeliveryInfo d2) {
        int priceComparison = Double.compare(d1.getPrice(), d2.getPrice());
        if (priceComparison != 0) {
            return priceComparison;
        }

        return Integer.compare(d1.getEstimatedTime(), d2.getEstimatedTime());
    }
}