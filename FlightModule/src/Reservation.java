package FlightModule.src;

import java.time.LocalDateTime;

public class Reservation {

    //region Attributes
    //--------------------------------------------------------------------------------
    private String flightNumber;
    private LocalDateTime departureTime;
    private User user;
    private Seat seat;
    private int bags;
    private boolean hasVegeterianMeal;
    //--------------------------------------------------------------------------------
    //endregion


    // TODO: Implement generic constructor
    /* public Reservation(HashMap<String, Object> args) {
        this.bags = (int) args.get("???");
        ...
    } */

    public Reservation(String flightNumber, LocalDateTime departureTime, User user, Seat seat, int bags, boolean hasVegeterianMeal) {
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.user = user;
        this.seat = seat;
        this.bags = bags;
        this.hasVegeterianMeal = hasVegeterianMeal;
    }


    //region Setters
    //--------------------------------------------------------------------------------
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public void setBags(int bags) {
        this.bags = bags;
    }

    public void setHasVegeterianMeal(boolean hasVegeterianMeal) {
        this.hasVegeterianMeal = hasVegeterianMeal;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
    //--------------------------------------------------------------------------------
    //endregion


    //region Getters
    //--------------------------------------------------------------------------------
    public String getFlightNumber() {
        return flightNumber;
    }

    public User getUser() {
        return user;
    }

    public Seat getSeat() {
        return seat;
    }

    public int getBags() {
        return bags;
    }

    public boolean hasVegeterianMeal() {
        return hasVegeterianMeal;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    //--------------------------------------------------------------------------------
    //endregion
}
