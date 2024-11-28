package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType.DELIVERY_GUY_BIKE;
import static bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType.DELIVERY_GUY_CAR;
import static bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType.BIKE;
import static bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType.CAR;

public class ControlCenter implements ControlCenterApi {
    private final char[][] mapLayout;

    public ControlCenter(char[][] mapLayout) {
        this.mapLayout = mapLayout;
    }

    public ArrayList<DeliveryInfo> findAllDeliveryGuys(Location restaurantLocation) {
        boolean[][] visited = new boolean[mapLayout.length][mapLayout[0].length];
        for (int i = 0; i < mapLayout.length; i++) {
            for (int j = 0; j < mapLayout.length; j++) {
                if (mapLayout[i][j] == '#') {
                    visited[i][j] = true;
                }
            }
        }
        return bfs(visited, restaurantLocation);
    }

    public ArrayList<DeliveryInfo> bfs(boolean[][] visited, Location startLocation) {
        int[] horizontalDirection = {-1, 0, 1, 0};
        int[] verticalDirection = {0, 1, 0, -1};

        Queue<Location> spis = new LinkedList<>();
        ArrayList<DeliveryInfo> deliveries = new ArrayList<>();
        int level = 1;
        int remaining = 1;
        spis.add(startLocation);
        visited[startLocation.getX()][startLocation.getY()] = true;
        while (!spis.isEmpty()) {
            Location top = spis.poll();
            remaining--;
            for (int i = 0; i < 4; i++) {
                int x = top.getX() + verticalDirection[i];
                int y = top.getY() + horizontalDirection[i];
                if (x < 0 || x >= mapLayout.length || y < 0 || y >= mapLayout[0].length) {
                    continue;
                }

                if (!visited[x][y]) {
                    if (mapLayout[x][y] != '.' && mapLayout[x][y] != 'R' && mapLayout[x][y] != 'C') {
                        for (DeliveryType type : DeliveryType.values()) {
                            if (mapLayout[x][y] == type.getSymbol()) {
                                deliveries.add(new DeliveryInfo(new Location(x, y), level * type.getPricePerKilometer(), level * type.getTimePerKilometer(), type));
                            }
                        }
                    }
                    visited[x][y] = true;
                    spis.add(new Location(x, y));
                }
            }

            if (remaining == 0) {
                level++;
                remaining = spis.size();
            }
        }
        return deliveries;
    }

    @Override
    public DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation, double maxPrice, int maxTime, ShippingMethod shippingMethod) {
        return null;
    }

    @Override
    public MapEntity[][] getLayout() {
        MapEntity[][] mapEntities = new MapEntity[mapLayout.length][mapLayout[0].length];
        for (int i = 0; i < mapLayout.length; i++) {
            for (int j = 0; j < mapLayout[0].length; j++) {
                for (MapEntityType type : MapEntityType.values()) {
                    if (mapLayout[i][j] == type.getSymbol()) {
                        mapEntities[i][j] = new MapEntity(new Location(i, j), type);
                    }
                }
            }
        }
        return mapEntities;
    }
}
