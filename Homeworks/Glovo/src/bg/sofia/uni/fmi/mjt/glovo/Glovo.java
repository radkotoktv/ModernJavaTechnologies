package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.ControlCenter;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.exception.NoAvailableDeliveryGuyException;

public class Glovo implements GlovoApi {
    private final ControlCenter controlCenter;

    public Glovo(char[][] mapLayout) {
        this.controlCenter = new ControlCenter(mapLayout);
    }

    @Override
    public Delivery getCheapestDelivery(MapEntity client, MapEntity restaurant, String foodItem) throws NoAvailableDeliveryGuyException {
        Location restaurantLocation = restaurant.getLocation();
        Location clientLocation = client.getLocation();
        DeliveryInfo cheapestDelivery = controlCenter.findOptimalDeliveryGuy(restaurantLocation, clientLocation, -1, -1, ShippingMethod.CHEAPEST);
        return new Delivery(clientLocation,
                            restaurantLocation,
                            cheapestDelivery.getDeliveryGuyLocation(),
                            foodItem,
                            cheapestDelivery.getPrice(),
                            cheapestDelivery.getEstimatedTime());
    }

    @Override
    public Delivery getFastestDelivery(MapEntity client, MapEntity restaurant, String foodItem) throws NoAvailableDeliveryGuyException {
        Location restaurantLocation = restaurant.getLocation();
        Location clientLocation = client.getLocation();
        DeliveryInfo cheapestDelivery = controlCenter.findOptimalDeliveryGuy(restaurantLocation, clientLocation, -1, -1, ShippingMethod.FASTEST);
        return new Delivery(clientLocation,
                restaurantLocation,
                cheapestDelivery.getDeliveryGuyLocation(),
                foodItem,
                cheapestDelivery.getPrice(),
                cheapestDelivery.getEstimatedTime());
    }

    @Override
    public Delivery getFastestDeliveryUnderPrice(MapEntity client, MapEntity restaurant, String foodItem, double maxPrice) throws NoAvailableDeliveryGuyException {
        Location restaurantLocation = restaurant.getLocation();
        Location clientLocation = client.getLocation();
        DeliveryInfo cheapestDelivery = controlCenter.findOptimalDeliveryGuy(restaurantLocation, clientLocation, maxPrice, -1, ShippingMethod.FASTEST);
        return new Delivery(clientLocation,
                restaurantLocation,
                cheapestDelivery.getDeliveryGuyLocation(),
                foodItem,
                cheapestDelivery.getPrice(),
                cheapestDelivery.getEstimatedTime());
    }

    @Override
    public Delivery getCheapestDeliveryWithinTimeLimit(MapEntity client, MapEntity restaurant, String foodItem, int maxTime) throws NoAvailableDeliveryGuyException {
        Location restaurantLocation = restaurant.getLocation();
        Location clientLocation = client.getLocation();
        DeliveryInfo cheapestDelivery = controlCenter.findOptimalDeliveryGuy(restaurantLocation, clientLocation, -1, maxTime, ShippingMethod.CHEAPEST);
        return new Delivery(clientLocation,
                restaurantLocation,
                cheapestDelivery.getDeliveryGuyLocation(),
                foodItem,
                cheapestDelivery.getPrice(),
                cheapestDelivery.getEstimatedTime());
    }
}
