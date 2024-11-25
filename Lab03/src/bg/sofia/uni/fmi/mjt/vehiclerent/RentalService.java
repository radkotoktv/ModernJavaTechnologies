package bg.sofia.uni.fmi.mjt.vehiclerent;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleAlreadyRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.Objects;

public class RentalService {

    /**
     * Simulates renting of the vehicle. Makes all required validations and then the provided driver "rents" the provided
     * vehicle with start time @startOfRent
     * @throws IllegalArgumentException if any of the passed arguments are null
     * @throws VehicleAlreadyRentedException in case the provided vehicle is already rented
     * @param driver the designated driver of the vehicle
     * @param vehicle the chosen vehicle to be rented
     * @param startOfRent the start time of the rental
     */
    public void rentVehicle(Driver driver, Vehicle vehicle, LocalDateTime startOfRent) {
        try
        {
            if(driver == null) throw new IllegalArgumentException("Driver is null!");
            if(vehicle == null) throw new IllegalArgumentException("Vehicle is null!");
            if(startOfRent == null) throw new IllegalArgumentException("startOfRent is null!");
            vehicle.rent(driver, startOfRent);
        } catch(IllegalArgumentException e) {
            System.out.println("Illegal Arguments for renting a vehicle");
        }
    }

    /**
     * This method simulates rental return - it includes validation of the arguments that throw the listed exceptions
     * in case of errors. The method returns the expected total price for the rental - price for the vehicle plus
     * additional tax for the driver, if it is applicable
     * @param vehicle the rented vehicle
     * @param endOfRent the end time of the rental
     * @return price for the rental
     * @throws IllegalArgumentException in case @endOfRent or @vehicle is null
     * @throws VehicleNotRentedException in case the vehicle is not rented at all
     * @throws InvalidRentingPeriodException in case the endOfRent is before the start of rental, or the vehicle
     * does not allow the passed period for rental, e.g. Caravans must be rented for at least a day.
     */
    public double returnVehicle(Vehicle vehicle, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        double price = 0.0;
        try
        {
            if(vehicle == null) throw new IllegalArgumentException("Vehicle is null!");
            if(!vehicle.getRented()) throw new VehicleNotRentedException("Vehicle has not been rented!");
            vehicle.returnBack(endOfRent);
            price = vehicle.calculateRentalPrice(vehicle.getStartRentTime(), endOfRent);
        } catch(IllegalArgumentException e) {
            System.out.println("IllegalArgumentException caught");
        } catch(VehicleNotRentedException e) {
            System.out.println("VehicleNotRentedException caught");
        }
        return price;
    }
}
