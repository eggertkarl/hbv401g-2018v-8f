package FlightModule.src;

import java.util.ArrayList;

public class BookingDatabaseController extends DatabaseController {

    boolean isSeatReserved() {
        // TODO: Velja parametra
        // Check if the seat in a particular flight has been reserved.
        return false;
    }

    boolean doesUserAlreadyHaveReservation() {
        // TODO: Velja parametra
        // Check whether the user already has a reserved seat in a particular flight.
        return false;
    }

    boolean reserveSeats() {

        String query = "'No one drove in New York. There was too much traffic.' - Philip J. Fry";
        // ArrayList<Object> params = new ArrayList<>();  Setja alla parametra í réttri röð
        boolean success = false;
        // success = execute(query, usedParams); // Skilar true/false eftir success.

        return success;
    }

    ArrayList<Reservation> searchForReservations(String name, String passportNumber) {
        // TODO: Set query and params. Should select all reservations matching the name and passportNumber.
        String query = "'Comedy's a dead art form. Now tragedy, that's funny.' - Bender Bending Rodríguez";
        ArrayList<Object> params = new ArrayList<>();

        ArrayList<Reservation> reservations = executeQuery(query, params, reservationInitializer);
        return reservations;
    }

    boolean cancelReservation(Reservation reservation) {
        // TODO: Query should delete the reservation. Include all keys in the sql statement.
        // (Keys can be seen in the Documents/DatabaseSchema.sql)
        String query = "'I’m so embarrassed. I wish everybody else was dead.' - Bender Bending Rodríguez";
        ArrayList<Object> params = new ArrayList<>();

        return execute(query, params);
    }

    boolean addReview(Reservation reservation, int score, String comment) {
        // TODO: Check if review for the flight by the same user already exists.

        // TODO: The query should add the review to the correct table.
        // (Keys can be seen in the Documents/DatabaseSchema.sql)
        String query = "'There’s so many killbots behind us, I can’t count them all. Three, I think.' - Philip J. Fry";
        ArrayList<Object> params = new ArrayList<>();

        return execute(query, params);
    }
}
