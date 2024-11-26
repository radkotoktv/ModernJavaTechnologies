package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;
import bg.sofia.uni.fmi.mjt.glovo.exception.NoAvailableDeliveryGuyException;

public class Glovo implements GlovoApi {
    public Glovo(char[][] mapLayout) {};

    @Override
    public Delivery getCheapestDelivery(MapEntity client, MapEntity restaurant, String foodItem) throws NoAvailableDeliveryGuyException {
        return null;
    }

    @Override
    public Delivery getFastestDelivery(MapEntity client, MapEntity restaurant, String foodItem) throws NoAvailableDeliveryGuyException {
        return null;
    }

    @Override
    public Delivery getFastestDeliveryUnderPrice(MapEntity client, MapEntity restaurant, String foodItem, double maxPrice) throws NoAvailableDeliveryGuyException {
        return null;
    }

    @Override
    public Delivery getCheapestDeliveryWithinTimeLimit(MapEntity client, MapEntity restaurant, String foodItem, int maxTime) throws NoAvailableDeliveryGuyException {
        return null;
    }


}
