package FlightModule.src;

import java.util.ArrayList;
import java.util.HashMap;

public class BookingDatabaseController extends DatabaseController {

    boolean isSeatReserved(Flight flight, Seat seat) {
        // TODO: Velja parametra
        // Check if the seat in a particular flight has been reserved.
        //if(flight.getTotalSeatsCoach()+flight.getTotalSeatsFirstClass()<=flight.getReservedSeatsCoach()+flight.getReservedSeatsFirstClass()) {
        //    return false;
        //}
        String query = "SELECT * FROM Reservations WHERE FlightNumber = ?"
                + "AND DepartureTime = ? AND SeatRow = ?"
                + "AND SeatColumn = ?" ;
        ArrayList<Object> params = new ArrayList<>();
        params.add(flight.getFlightNumber());
        params.add(flight.getDepartureTime());
        params.add(seat.getRow());
        params.add(seat.getColumn());
        ArrayList<Reservation> reservations = executeQuery(query, params, reservationInitializer);
        if(reservations == null) {
            return false;
        }
        if(reservations.isEmpty()) {
            return false;
        }
        return true;
    }

    boolean doesUserAlreadyHaveReservation(Flight flight, String name, String passportNumber) {
        // TODO: Velja parametra
        // Check whether the user already has a reserved seat in a particular flight.
        String query = "SELECT * FROM Reservations WHERE FlightNumber = ?"
                + "AND DepartureTime = ? AND Name = ?"
                + "AND PassportNumber = ?" ;
        ArrayList<Object> params = new ArrayList<>();
        params.add(flight.getFlightNumber());
        params.add(flight.getDepartureTime());
        params.add(name);
        params.add(passportNumber);
        ArrayList<Reservation> reservations = executeQuery(query, params, reservationInitializer);
        if(reservations == null) {
            return false;
        }
        if(reservations.isEmpty()) {
            return false;
        }
        return true;
    }

    boolean reserveSeats(HashMap<String, Reservation> reservations) {

        String query = "INSERT INTO Reservations (FlightNumber,\n" +
                "  DepartureTime,\n" +
                "  Name,\n" +
                "  PassportNumber,\n" +
                "  SeatRow INT,\n" +
                "  SeatColumn,\n" +
                "  Bags,\n" +
                "  VALUES ";
        String[] passportNumbers = reservations.keySet().toArray(new String[0]);
        Reservation[] reservationArray = reservations.values().toArray(new Reservation[0]);

        ArrayList<Object> params = new ArrayList<>();  // Setja alla parametra í réttri röð
        for(Reservation r: reservationArray) {
            query += "(?, ?, ?, ?, ?, ?, ?), ";
            params.add(r.getFlightNumber());
            params.add(r.getDepartureTime());
            params.add(r.getUser().getName());
            params.add(r.getUser().getPassportNumber());
            params.add(r.getSeat().getRow());
            params.add(r.getSeat().getColumn());
            params.add(r.getBags());
        }
        query = query.substring(0, query.length()-3);
        query += ";";
        boolean success = false;
        success = execute(query, params); // Skilar true/false eftir success.

        return success;
    }

    ArrayList<Reservation> searchForReservations(String name, String passportNumber) {
        // TODO: Set query and params. Should select all reservations matching the name and passportNumber.
        String query = "SELECT * FROM Reservations WHERE Name = ? AND PassportNumber = ?;" ;
        ArrayList<Object> params = new ArrayList<>();
        params.add(name);
        params.add(passportNumber);

        ArrayList<Reservation> reservations = executeQuery(query, params, reservationInitializer);
        return reservations;
    }

    boolean cancelReservation(Reservation reservation) {
        // TODO: Query should delete the reservation. Include all keys in the sql statement.
        // (Keys can be seen in the Documents/DatabaseSchema.sql)
        String query = "DELETE FROM Reservations WHERE FlightNumber = ? AND DepartureTime = ? AND Name = ? AND PassportNumber = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(reservation.getFlightNumber());
        params.add(reservation.getDepartureTime());
        params.add(reservation.getUser().getName());
        params.add(reservation.getUser().getPassportNumber());

        return execute(query, params);
    }

    boolean addReview(Reservation reservation, int score, String comment) {
        // TODO: Check if review for the flight by the same user already exists.

        // TODO: The query should add the review to the correct table.
        // (Keys can be seen in the Documents/DatabaseSchema.sql)
        String query = "INSERT INTO Reviews (\n" +
                "  FlightNumber,\n" +
                "  DepartureTime,\n" +
                "  Name,\n" +
                "  PassportNumber,\n" +
                "  Rating,\n" +
                "  Comment)" +
                "  VALUES (?, ?, ?, ?, ? ,?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(reservation.getFlightNumber());
        params.add(reservation.getDepartureTime());
        params.add(reservation.getUser().getName());
        params.add(reservation.getUser().getPassportNumber());
        params.add(score);
        params.add(comment);

        return execute(query, params);
    }
}
