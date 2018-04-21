package FlightModule.src;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Flight {

    //region Attributes
    //--------------------------------------------------------------------------------
    // Flight metadata:
    private final String flightNumber;
    private final String airline;
    private final String airplaneType;
    private final Integer priceFirstClass;
    private final Integer priceCoach;
    private final ArrayList<Seat> seats;

    private final Integer totalSeatsFirstClass;
    private final Integer totalSeatsCoach;
    private final Integer reservedSeatsFirstClass;
    private final Integer reservedSeatsCoach;

    // Flight location and datetime:
    private final String departureLocation;
    private final String arrivalLocation;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;
    private final Double averageRating;

    // Perks:
    private final Boolean hasMeal;
    private final Boolean hasVegeterianMeal;
    private final Boolean hasEntertainment;
    //--------------------------------------------------------------------------------
    //endregion



    public Flight(String flightNumber, String airline, String airplaneType, Integer priceCoach, Integer priceFirstClass,
                  Integer totalSeatsFirstClass, Integer totalSeatsCoach, Integer reservedSeatsFirstClass,
                  Integer reservedSeatsCoach, String departureLocation, String arrivalLocation,
                  LocalDateTime departureTime, LocalDateTime arrivalTime, Double averageRating, Boolean hasMeal,
                  Boolean hasVegeterianMeal, Boolean hasEntertainment) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.airplaneType = airplaneType;
        this.priceCoach = priceCoach;
        this.priceFirstClass = priceFirstClass;
        this.seats = new ArrayList<>();
        this.totalSeatsFirstClass = totalSeatsFirstClass;
        this.totalSeatsCoach = totalSeatsCoach;
        this.reservedSeatsFirstClass = reservedSeatsFirstClass;
        this.reservedSeatsCoach = reservedSeatsCoach;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.hasMeal = hasMeal;
        this.hasVegeterianMeal = hasVegeterianMeal;
        this.hasEntertainment = hasEntertainment;
        this.averageRating = averageRating;
    }


    //region Setters
    //--------------------------------------------------------------------------------
    public void setSeats(ArrayList<Seat> seats) {
        // Can only be set once.
        if(this.seats.size() == 0) {
            for(Seat seat : seats) {
                this.seats.add(seat);
            }
        }
    }
    //--------------------------------------------------------------------------------
    //endregion


    //region Getters
    //--------------------------------------------------------------------------------
    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public Integer getPriceCoach() {
        return priceCoach;
    }

    public Integer getPriceFirstClass() {
        return priceFirstClass;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    //public String getDepartureTimeString() { return departureTime.toString().substring(0,10) + " " + departureTime.toString().substring(11, 16);}

    public String getDepartureTimeString() { return Utilities.convertLocalDateTimeToString(departureTime);}

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public Boolean hasMeal() {
        return hasMeal;
    }

    public Boolean hasVegeterianMeal() {
        return hasVegeterianMeal;
    }

    public Boolean hasEntertainment() {
        return hasEntertainment;
    }

    public String getAirplaneType() {
        return airplaneType;
    }

    public Integer getTotalSeatsFirstClass() {
        return totalSeatsFirstClass;
    }

    public Integer getTotalSeatsCoach() {
        return totalSeatsCoach;
    }

    public Integer getReservedSeatsFirstClass() {
        return reservedSeatsFirstClass;
    }

    public Integer getReservedSeatsCoach() {
        return reservedSeatsCoach;
    }

    public Double getAverageRating() {
        return averageRating;
    }
    
    @Override
    public String toString(){
        String displayString = "";
        displayString += getDepartureLocation() + " - " + getArrivalLocation();
        displayString += " (" + getDepartureTime() + ") ";

        return displayString;
    }
    
    //--------------------------------------------------------------------------------
    //endregion
}
