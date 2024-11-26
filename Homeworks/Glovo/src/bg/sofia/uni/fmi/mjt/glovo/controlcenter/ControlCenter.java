package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType.DELIVERY_GUY_BIKE;
import static bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType.DELIVERY_GUY_CAR;
import static bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType.BIKE;
import static bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType.CAR;

public class ControlCenter implements ControlCenterApi {
    private final char[][] mapLayout;

    public ControlCenter(char[][] mapLayout) {
        this.mapLayout = mapLayout;
    };

//    char[][] layout = {
//            {'#', '#', '#', '.', '#'},
//            {'#', '.', 'B', 'R', '.'},
//            {'.', '.', '#', '.', '#'},
//            {'#', 'C', '.', 'A', '.'},
//            {'#', '.', '#', '#', '#'}
//    };

    public ArrayList<DeliveryInfo> findAllDeliveryGuys(Location restaurantLocation, Location clientLocation, ShippingMethod shippingMethod) {
        boolean[][] visited = new boolean[mapLayout.length][mapLayout[0].length];
        for (int i = 0; i < mapLayout.length; i++) {
            for (int j = 0; j < mapLayout.length; j++) {
                if (mapLayout[i][j] == '#') {
                    visited[i][j] = true;
                }
            }
        }

        ArrayList<DeliveryInfo> allDeliveryGuys = bfs(visited, restaurantLocation, shippingMethod);
        allDeliveryGuys.addAll(bfs(visited, clientLocation, shippingMethod));

        return allDeliveryGuys;
    }

    public ArrayList<DeliveryInfo> bfs(boolean[][] visited, Location startLocation, ShippingMethod shippingMethod) {
        int[] horizontalDirection = {-1, 0, 1, 0};
        int[] verticalDirection = {0, 1, 0, -1};

        Queue<Location> spis = new LinkedList<>();
        ArrayList<DeliveryInfo> deliveries = new ArrayList<>();
        int counter = -1;
        int upCounter = 1;
        spis.add(startLocation);
        visited[startLocation.getX()][startLocation.getY()] = true;
        while (!spis.isEmpty()) {
            Location top = spis.poll();
            upCounter--;
            if (upCounter == 0) {
                counter++;
                upCounter = spis.size();
            }
            for (int i = 0; i < 4; i++) {
                int x = top.getX() + verticalDirection[i];
                int y = top.getY() + horizontalDirection[i];
                if (!visited[x][y]) {
                    if (mapLayout[x][y] == DELIVERY_GUY_CAR.getSymbol()) {
                        deliveries.add(new DeliveryInfo(new Location(x, y), counter * CAR.getPricePerKilometer(), counter * CAR.getTimePerKilometer(), CAR));
                    }
                    else if (mapLayout[x][y] == DELIVERY_GUY_BIKE.getSymbol()) {
                        deliveries.add(new DeliveryInfo(new Location(x, y), counter * BIKE.getPricePerKilometer(), counter * BIKE.getTimePerKilometer(), BIKE));
                    }
                }
            }
        }
        return deliveries;
    }

    @Override
    public DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation, double maxPrice, int maxTime, ShippingMethod shippingMethod) {
        ArrayList<DeliveryInfo> allDeliveries = this.findAllDeliveryGuys(restaurantLocation, clientLocation, shippingMethod);
        allDeliveries.sort(null);
        return allDeliveries.getFirst();
    }

    @Override
    public MapEntity[][] getLayout() {
        return new MapEntity[][]{};
    }
}
