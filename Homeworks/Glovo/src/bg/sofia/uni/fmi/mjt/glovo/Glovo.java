package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.ControlCenter;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.exception.IllegalConstraintsException;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidOrderException;
import bg.sofia.uni.fmi.mjt.glovo.exception.NoAvailableDeliveryGuyException;

public class Glovo implements GlovoApi {
    private final ControlCenter controlCenter;

    public Glovo(char[][] mapLayout) {
        this.controlCenter = new ControlCenter(mapLayout);
    }

    void checkExceptions(MapEntity client, MapEntity restaurant) {
        Location restaurantLocation = restaurant.getLocation();
        Location clientLocation = client.getLocation();
        int height = controlCenter.getLayout().length;
        int width = controlCenter.getLayout()[0].length;
        try {
            if (clientLocation.getX() > height    ||
                    clientLocation.getY() > width ||
                    clientLocation.getX() < 0     ||
                    clientLocation.getY() < 0)
                throw new InvalidOrderException("Client location is outside the map's defined boundaries!");

            if (restaurantLocation.getX() > height    ||
                    restaurantLocation.getY() > width ||
                    restaurantLocation.getX() < 0     ||
                    restaurantLocation.getY() < 0)
                throw new InvalidOrderException("Restaurant location is outside the map's defined boundaries!");

            if (controlCenter.getLayout()[clientLocation.getX()][clientLocation.getY()] != client)
                throw new InvalidOrderException("No Client at that location!");

            if (controlCenter.getLayout()[restaurantLocation.getX()][restaurantLocation.getY()] != restaurant)
                throw new InvalidOrderException("No Restaurant at that location!");
        } catch (InvalidOrderException e) {
            throw e;
        }
    }

    @Override
    public Delivery getCheapestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
            throws NoAvailableDeliveryGuyException {
        checkExceptions(client, restaurant);

        Location restaurantLocation = restaurant.getLocation();
        Location clientLocation = client.getLocation();

        DeliveryInfo cheapestDelivery = controlCenter.findOptimalDeliveryGuy(restaurantLocation,
                                                                             clientLocation,
                                                                    -1,
                                                                    -1,
                                                                            ShippingMethod.CHEAPEST);
        return new Delivery(clientLocation,
                            restaurantLocation,
                            cheapestDelivery.getDeliveryGuyLocation(),
                            foodItem,
                            cheapestDelivery.getPrice(),
                            cheapestDelivery.getEstimatedTime());
    }

    @Override
    public Delivery getFastestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
            throws NoAvailableDeliveryGuyException {
        checkExceptions(client, restaurant);

        Location restaurantLocation = restaurant.getLocation();
        Location clientLocation = client.getLocation();
        DeliveryInfo cheapestDelivery = controlCenter.findOptimalDeliveryGuy(restaurantLocation,
                                                                             clientLocation,
                                                                    -1,
                                                                    -1,
                                                                            ShippingMethod.FASTEST);
        return new Delivery(clientLocation,
                restaurantLocation,
                cheapestDelivery.getDeliveryGuyLocation(),
                foodItem,
                cheapestDelivery.getPrice(),
                cheapestDelivery.getEstimatedTime());
    }

    @Override
    public Delivery getFastestDeliveryUnderPrice(MapEntity client, MapEntity restaurant, String foodItem, double maxPrice)
            throws NoAvailableDeliveryGuyException {
        checkExceptions(client, restaurant);
        try {
            if (maxPrice < -1 || maxPrice == 0)
                throw new IllegalConstraintsException("Price cannot be negative!");
        } catch (IllegalArgumentException e) {
            throw e;
        }

        Location restaurantLocation = restaurant.getLocation();
        Location clientLocation = client.getLocation();
        DeliveryInfo cheapestDelivery = controlCenter.findOptimalDeliveryGuy(restaurantLocation,
                                                                             clientLocation,
                                                                             maxPrice,
                                                                    -1,
                                                                            ShippingMethod.FASTEST);
        return new Delivery(clientLocation,
                restaurantLocation,
                cheapestDelivery.getDeliveryGuyLocation(),
                foodItem,
                cheapestDelivery.getPrice(),
                cheapestDelivery.getEstimatedTime());
    }

    @Override
    public Delivery getCheapestDeliveryWithinTimeLimit(MapEntity client, MapEntity restaurant, String foodItem, int maxTime)
            throws NoAvailableDeliveryGuyException {
        checkExceptions(client, restaurant);
        try {
            if (maxTime < -1 || maxTime == 0)
                throw new IllegalConstraintsException("Time cannot be negative!");
        } catch (IllegalArgumentException e) {
            throw e;
        }

        Location restaurantLocation = restaurant.getLocation();
        Location clientLocation = client.getLocation();
        DeliveryInfo cheapestDelivery = controlCenter.findOptimalDeliveryGuy(restaurantLocation,
                                                                             clientLocation,
                                                                    -1,
                                                                            maxTime,
                                                                            ShippingMethod.CHEAPEST);
        return new Delivery(clientLocation,
                restaurantLocation,
                cheapestDelivery.getDeliveryGuyLocation(),
                foodItem,
                cheapestDelivery.getPrice(),
                cheapestDelivery.getEstimatedTime());
    }
}
