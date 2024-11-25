package bg.sofia.uni.fmi.mjt.vehiclerent.driver;

import bg.sofia.uni.fmi.mjt.vehiclerent.vehicle.Vehicle;

public class Driver {
    private AgeGroup group;
    private Vehicle rentedVehicle;
    public Driver(AgeGroup group)
    {
        this.group = group;
    }

    public void setRentedVehicle(Vehicle rentedVehicle) {
        this.rentedVehicle = rentedVehicle;
    }

    public Vehicle getRentedVehicle()
    {
        return this.rentedVehicle;
    }

    public AgeGroup getGroup()
    {
        return this.group;
    }
}
