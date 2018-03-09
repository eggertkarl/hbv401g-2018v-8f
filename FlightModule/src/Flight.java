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
    private final int priceFirstClass;
    private final int priceCoach;
    private final ArrayList<Seat> seats;

    private final int totalSeatsFirstClass;
    private final int totalSeatsCoach;
    private final int reservedSeatsFirstClass;
    private final int reservedSeatsCoach;

    // Flight location and datetime:
    private final String departureLocation;
    private final String arrivalLocation;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;

    // Perks:
    private final boolean hasMeal;
    private final boolean hasVegeterianMeal;
    private final boolean hasEntertainment;
    //--------------------------------------------------------------------------------
    //endregion



    public Flight(String flightNumber, String airline, String airplaneType, int priceCoach, int priceFirstClass,
                  int totalSeatsFirstClass, int totalSeatsCoach, int reservedSeatsFirstClass, int reservedSeatsCoach,
                  String departureLocation, String arrivalLocation, LocalDateTime departureTime,
                  LocalDateTime arrivalTime, boolean hasMeal, boolean hasVegeterianMeal, boolean hasEntertainment) {
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

    public int getPriceCoach() {
        return priceCoach;
    }

    public int getPriceFirstClass() {
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

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public boolean hasMeal() {
        return hasMeal;
    }

    public boolean hasVegeterianMeal() {
        return hasVegeterianMeal;
    }

    public boolean hasEntertainment() {
        return hasEntertainment;
    }

    public String getAirplaneType() {
        return airplaneType;
    }

    public int getTotalSeatsFirstClass() {
        return totalSeatsFirstClass;
    }

    public int getTotalSeatsCoach() {
        return totalSeatsCoach;
    }

    public int getReservedSeatsFirstClass() {
        return reservedSeatsFirstClass;
    }

    public int getReservedSeatsCoach() {
        return reservedSeatsCoach;
    }

    //--------------------------------------------------------------------------------
    //endregion
}
