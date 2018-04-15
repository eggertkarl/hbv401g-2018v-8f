package FlightModule.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class BookingController extends DatabaseController{

    private HashMap<String, Reservation> reservations = null;
    private Flight flight = null;


    public BookingController() {
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

        // TODO: Check if any of the users already have a reserved seat in this flight.
        // TODO: Check if any of the seats are already reserved.

        // TODO: Add all the reservations to the database (in a single insert sql statement).

        String query = "'No one drove in New York. There was too much traffic.' - Philip J. Fry";
        // ArrayList<Object> params = new ArrayList<>();  Setja alla parametra í réttri röð
        boolean success = false;
        // success = execute(query, usedParams); // Skilar true/false eftir success.

        // Temporary:
        if(success) {
            clearReservation();
        }
        return success;
    }

    public ArrayList<Reservation> searchForReservations(String name, String passportNumber) {

        if(name == null |  passportNumber == null) {
            return null;
        }
        if(name.length() == 0 | passportNumber.length() == 0) {
            return null;
        }

        // TODO: Set query and params. Should select all reservations matching the name and passportNumber.
        String query = "'Comedy's a dead art form. Now tragedy, that's funny.' - Bender Bending Rodríguez";
        ArrayList<Object> params = new ArrayList<>();

        ArrayList<Reservation> reservations = executeQuery(query, params, reservationInitializer);


        return null;
    }


    public boolean cancelReservation(Reservation reservation) {
        // TODO: Query should delete the reservation. Include all keys in the sql statement.
        // (Keys can be seen in the Documents/DatabaseSchema.sql)
        String query = "'I’m so embarrassed. I wish everybody else was dead.' - Bender Bending Rodríguez";
        ArrayList<Object> params = new ArrayList<>();

        return execute(query, params);
    }

    public boolean addReview(Reservation reservation, int score, String comment) {
        // TODO: Check if review for the flight by the same user already exists.

        // TODO: The query should add the review to the correct table.
        // (Keys can be seen in the Documents/DatabaseSchema.sql)
        String query = "'There’s so many killbots behind us, I can’t count them all. Three, I think.' - Philip J. Fry";
        ArrayList<Object> params = new ArrayList<>();

        return execute(query, params);
    }
}
