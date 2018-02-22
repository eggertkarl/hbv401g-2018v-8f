import java.util.ArrayList;

public class Flight {

    //region Attributes
    //--------------------------------------------------------------------------------
    // Flight metadata:
    private final String flightNumber;
    private final String airline;
    private final int priceCoach;
    private final int priceFirstClass;
    private final ArrayList<Seat> seats;
    private final int seatCountFirstClassAvailable;
    private final int seatCountCouchAvailable;

    // Flight location and datetime:
    private final String departureLocation;
    private final String arrivalLocation;
    // TODO: Figure out best way to store datetime.
    // private final String departureTime;
    // private final String arrivalTime;

    // Perks:
    private final boolean hasMeal;
    private final boolean hasVegeterianMeal;
    private final boolean hasEntertainment;
    //--------------------------------------------------------------------------------
    //endregion


    public Flight(String flightNumber, String airline, int priceCoach, int priceFirstClass,
                  int seatCountFirstClassAvailable, int seatCountCouchAvailable, String departureLocation,
                  String arrivalLocation, boolean hasMeal, boolean hasVegeterianMeal, boolean hasEntertainment) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.priceCoach = priceCoach;
        this.priceFirstClass = priceFirstClass;
        this.seats = new ArrayList<Seat>();
        this.seatCountFirstClassAvailable = seatCountFirstClassAvailable;
        this.seatCountCouchAvailable = seatCountCouchAvailable;
        this.departureLocation = departureLocation;

        // departureTime = null;
        // arrivalLocation = null;
        this.arrivalLocation = arrivalLocation;
        this.hasMeal = hasMeal;
        this.hasVegeterianMeal = hasVegeterianMeal;
        this.hasEntertainment = hasEntertainment;
    }


    //region Setters
    //--------------------------------------------------------------------------------
    public void setSeats(ArrayList<Seat> seats) {
        // TODO: Is it enough to set once? Should be.
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

    public int getSeatCountFirstClassAvailable() {
        return seatCountFirstClassAvailable;
    }

    public int getSeatCountCouchAvailable() {
        return seatCountCouchAvailable;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
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
    //--------------------------------------------------------------------------------
    //endregion
}
