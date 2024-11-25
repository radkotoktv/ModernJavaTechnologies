package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.LocalDateTime;

public final class Car extends Vehicle {
    private final FuelType fueltype;
    private final int numberOfSeats;
    private final double pricePerWeek;
    private final double pricePerDay;
    private final double pricePerHour;
    public Car(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour)
    {
        super(id, model);
        this.fueltype = fuelType;
        this.numberOfSeats = numberOfSeats;
        this.pricePerWeek = pricePerWeek;
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

    public int getNumberOfSeats()
    {
        return this.numberOfSeats;
    }

    public double getPricePerHour()
    {
        return this.pricePerHour;
    }

    public double getPricePerDay()
    {
        return this.pricePerDay;
    }

    public double getPricePerWeek()
    {
        return this.pricePerWeek;
    }

    public FuelType getFueltype()
    {
        return this.fueltype;
    }

    /**
     * Used to calculate potential rental price without the vehicle to be rented.
     * The calculation is based on the type of the Vehicle (Car/Caravan/Bicycle).
     *
     * @param startOfRent the beginning of the rental
     * @param endOfRent the end of the rental
     * @return potential price for rent
     * @throws InvalidRentingPeriodException in case the vehicle cannot be rented for that period of time or
     * the period is not valid (end date is before start date)
     */
    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException
    {
        double price = 0.0;
        try
        {
            if(endOfRent.isBefore(startOfRent))
            {
                throw new InvalidRentingPeriodException("End of rent is before start of rent!");
            }
            int daysDifference = endOfRent.getDayOfYear() - startOfRent.getDayOfYear();
            int totalDaysDifference = daysDifference;
            int hoursDifference = endOfRent.getHour() - startOfRent.getHour();
            int weeksDifference = daysDifference / 7;
            daysDifference = daysDifference % 7;

            price = hoursDifference * getPricePerHour() + daysDifference * getPricePerDay() + weeksDifference * getPricePerWeek() + getNumberOfSeats() * 5 + totalDaysDifference * fueltype.getValue() + this.getTax();
        } catch(InvalidRentingPeriodException e)
        {
            System.out.println("Invalid renting period!");
        }
        return price;
    }
}
