public class Reservation {

    //region Attributes
    //--------------------------------------------------------------------------------
    private String flightNumber;
    // private String departureTime;
    private User user;
    private Seat seat;
    private int bags;
    private boolean hasVegeterianMeal;
    //--------------------------------------------------------------------------------
    //endregion


    public Reservation(String flightNumber, User user, Seat seat, int bags, boolean hasVegeterianMeal) {
        this.flightNumber = flightNumber;
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
    //--------------------------------------------------------------------------------
    //endregion
}
