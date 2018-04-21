import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class BookingController {

    private BookingDatabaseController databaseController = null;
    private HashMap<String, Reservation> reservations = null;
    private Flight flight = null;


    public BookingController() {
        databaseController = new BookingDatabaseController();
        reservations = new HashMap<>();
    }


    public boolean addSeat(String name, String passportNumber, Seat seat, Integer bags) {
        return addSeat(name, passportNumber, seat, bags, false, false);
    }


    public boolean addSeat(String name, String passportNumber, Seat seat, Integer bags, Boolean isMinor, Boolean hasVegeterianMeal) {
        // If the user or seat is already registered, or the seat is unavailable, then return false.

        if(reservations.containsKey(passportNumber)) {
            return false;
        }

        if(flight == null) {
            return false;
        }

        if(databaseController.isSeatReserved(flight, seat)) {
            return false;
        }
        if(databaseController.doesUserAlreadyHaveReservation(flight, name, passportNumber)) {
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
        //String[] passportNumbers = this.reservations.keySet().toArray(new String[0]);
        Reservation[] reservationArray = this.reservations.values().toArray(new Reservation[0]);
        for(Reservation r: reservationArray) {
            if(databaseController.isSeatReserved(this.flight, r.getSeat())) {
                return false;
            }
            if(databaseController.doesUserAlreadyHaveReservation(flight, r.getUser().getName(), r.getUser().getPassportNumber())) {
                return false;
            }
        }

        for(Reservation r: reservationArray) {
            if(!databaseController.doesUserAlreadyExist(r.getUser())) {
                databaseController.addUser(r.getUser());
            }
        }

        return databaseController.reserveSeats(this.reservations);
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
