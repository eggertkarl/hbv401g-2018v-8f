import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class BookingController extends ColumnNames{

    private BookingDatabaseController databaseController = null;
    private HashMap<String, Reservation> reservations = null;
    private Flight flight = null;


    public BookingController() {
        databaseController = new BookingDatabaseController();
        reservations = new HashMap<>();
    }


    public boolean addSeat(String name, String passportNumber, Seat seat, int bags) {
        return addSeat(name, passportNumber, seat, bags, false, false);
    }


    public boolean addSeat(String name, String passportNumber, Seat seat, int bags, boolean isMinor, boolean hasVegeterianMeal) {
        // If the user or seat is already registered, or the seat is unavailable, then return false.

        // TODO: Better error handling? Something to tell the user what went wrong.
        if(reservations.containsKey(passportNumber)) {
            return false;
        }

        if(flight == null) {
            return false;
        }

        if(databaseController.isSeatReserved()) { // TODO: Parameters missing
            return false;
        }
        if(databaseController.doesUserAlreadyHaveReservation()) { // TODO: Parameters missing
            return false;
        }

        for(Reservation reservation : reservations.values()) {
            Seat tmpSeat = reservation.getSeat();
            if(Objects.equals(tmpSeat.getColumn(), seat.getColumn()) & Objects.equals(tmpSeat.getRow(), seat.getRow())) {
                return false;
            }
        }

        for(Seat flightSeat : flight.getSeats()) {
            if(Objects.equals(flightSeat.getColumn(), seat.getColumn()) & Objects.equals(flightSeat.getRow(), seat.getRow())) {
                if(flightSeat.isAvailable()) {
                    break;
                }
                else {
                    // If the seat is unavailable.
                    return false;
                }
            }
        }

        User user = new User(name, passportNumber, isMinor);
        Reservation reservation = new Reservation(flight.getFlightNumber(), flight.getDepartureTime(), user, seat, bags, hasVegeterianMeal);

        reservations.put(passportNumber, reservation);
        return true;
    }

    public void selectFlight(Flight flight) {
        this.flight = flight;
    }

    public void clearReservation() {
        this.flight = null;
        this.reservations = new HashMap<>();
    }

    public boolean confirmReservation() {
        if(this.flight == null) {
            return false;
        }
        if(this.reservations.isEmpty()) {
            return false;
        }

        if(databaseController.isSeatReserved()) { // TODO: Parameters missing
            return false;
        }
        if(databaseController.doesUserAlreadyHaveReservation()) { // TODO: Parameters missing
            return false;
        }

        return databaseController.reserveSeats(); // TODO: Parameters missing
    }

    public ArrayList<Reservation> searchForReservations(String name, String passportNumber) {

        if(name == null |  passportNumber == null) {
            return null;
        }
        if(name.length() == 0 | passportNumber.length() == 0) {
            return null;
        }

        return databaseController.searchForReservations(name, passportNumber);
    }


    public boolean cancelReservation(Reservation reservation) {
        return databaseController.cancelReservation(reservation);
    }

    public boolean addReview(Reservation reservation, int score, String comment) {
        return databaseController.addReview(reservation, score, comment);
    }
}
