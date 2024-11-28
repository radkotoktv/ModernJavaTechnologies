import bg.sofia.uni.fmi.mjt.glovo.Glovo;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;

public class Main {
    public static void main(String[] args) {
        char[][] layout = {
                {'#', '#', '#', '.', '#'},
                {'#', '.', 'B', 'R', '.'},
                {'.', '.', '#', '.', '#'},
                {'#', 'C', '.', 'A', '.'},
                {'#', 'A', '#', '#', '#'}
        };

        Glovo glovo = new Glovo(layout);
        Delivery cheapestDelivery = glovo.getCheapestDeliveryWithinTimeLimit(new MapEntity(new Location(3, 1), MapEntityType.RESTAURANT),
                                                             new MapEntity(new Location(1, 3), MapEntityType.CLIENT),
                                                     "Pizza", 30);
        System.out.println("Client Location: (" + cheapestDelivery.getClient().getX() + ", " + cheapestDelivery.getClient().getY() + ")");
        System.out.println("Restaurant Location: (" + cheapestDelivery.getRestaurant().getX() + ", " + cheapestDelivery.getRestaurant().getY() + ")");
        System.out.println("Delivery Guy Location: (" + cheapestDelivery.getDeliveryGuy().getX() + ", " + cheapestDelivery.getDeliveryGuy().getY() + ")");
        System.out.println("Food Item: " + cheapestDelivery.getFoodItem());
        System.out.println("Price: " + cheapestDelivery.getPrice());
        System.out.println("Estimated Time: " + cheapestDelivery.getEstimatedTime());
    }
}