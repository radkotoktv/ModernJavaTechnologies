package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.LocalDateTime;

public final class Bicycle extends Vehicle {
    private final double pricePerDay;
    private final double pricePerHour;
    public Bicycle(String id, String model, double pricePerDay, double pricePerHour)
    {
        super(id, model);
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

    public double getPricePerHour()
    {
        return this.pricePerHour;
    }

    public double getPricePerDay()
    {
        return this.pricePerDay;
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
        boolean isMoreThanAWeek = startOfRent.plusDays(7).isBefore(endOfRent);
        try
        {
            if(endOfRent.isBefore(startOfRent))
            {
                throw new InvalidRentingPeriodException("End of rent is before start of rent!");
            }
            if(isMoreThanAWeek)
            {
                throw new InvalidRentingPeriodException("Cannot rent a bicycle for more than a week!");
            }
            int daysDifference = endOfRent.getDayOfYear() - startOfRent.getDayOfYear();
            int hoursDifference = endOfRent.getHour() - startOfRent.getHour();
            price = hoursDifference * getPricePerHour() + daysDifference * getPricePerDay();
        } catch(InvalidRentingPeriodException e)
        {
            System.out.println("Invalid renting period!");
        }
        return price;
    }
}
