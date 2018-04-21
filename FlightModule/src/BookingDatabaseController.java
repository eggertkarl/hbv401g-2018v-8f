import java.util.ArrayList;
import java.util.HashMap;

public class BookingDatabaseController extends DatabaseController {

    boolean isSeatReserved(Flight flight, Seat seat) {

        // Check if the seat in a particular flight has been reserved.
        //if(flight.getTotalSeatsCoach()+flight.getTotalSeatsFirstClass()<=flight.getReservedSeatsCoach()+flight.getReservedSeatsFirstClass()) {
        //    return false;
        //}
        String query = "SELECT * FROM Reservations WHERE FlightNumber = ?"
                + "AND DepartureTime = ? AND SeatRow = ?"
                + "AND SeatColumn = ?" ;
        ArrayList<Object> params = new ArrayList<>();
        params.add(flight.getFlightNumber());
        params.add(flight.getDepartureTimeString());
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

        // Check whether the user already has a reserved seat in a particular flight.
        String query = "SELECT * " +
                "FROM Reservations " +
                    "WHERE FlightNumber = ?" +
                        "AND DepartureTime = ? " +
                        "AND Name = ?" +
                        "AND PassportNumber = ?" ;
        ArrayList<Object> params = new ArrayList<>();
        params.add(flight.getFlightNumber());
        params.add(flight.getDepartureTimeString());
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

    boolean doesUserAlreadyExist(User user) {
        String query = "SELECT * FROM Users WHERE Name = ? AND PassportNumber = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(user.getName());
        params.add(user.getPassportNumber());
        ArrayList<User> users = executeQuery(query, params, userInitializer);
        if(users == null) {
            return false;
        }
        if(users.isEmpty()) {
            return false;
        }
        return true;
    }

    boolean addUser(User user) {
        String query = "INSERT INTO Users (Name, PassportNumber, isMinor)"
                + "VALUES (?, ?, ?);";
        ArrayList<Object> params = new ArrayList<>();
        params.add(user.getName());
        params.add(user.getPassportNumber());
        params.add(user.isMinor());
        return execute(query, params);
    }

    boolean reserveSeats(HashMap<String, Reservation> reservations) {

        String query = "INSERT INTO Reservations (FlightNumber,\n" +
                "  DepartureTime,\n" +
                "  Name,\n" +
                "  PassportNumber,\n" +
                "  AirplaneType, \n" +
                "  SeatRow,\n" +
                "  SeatColumn,\n" +
                "  Bags,\n" +
                "  HasVegeterianMeal)\n" +
                "  VALUES ";

        Reservation[] reservationArray = reservations.values().toArray(new Reservation[0]);

        SearchController sc = new SearchController();
        sc.filterSetDepartureTimeEqualTo(reservationArray[0].getDepartureTime());
        sc.filterSetFilterFlightNumberEqualTo(reservationArray[0].getFlightNumber());
        Flight flight = sc.searchForFlights().get(0);

        ArrayList<Object> params = new ArrayList<>();  // Setja alla parametra í réttri röð
        for(Reservation r: reservationArray) {
            query += "(?, ?, ?, ?, ?, ?, ?, ?, ?), ";
            params.add(r.getFlightNumber());
            params.add(r.getDepartureTimeString());
            params.add(r.getUser().getName());
            params.add(r.getUser().getPassportNumber());
            params.add(flight.getAirplaneType()); // airplanetype
            params.add(r.getSeat().getRow());
            params.add(r.getSeat().getColumn());
            params.add(r.getBags());
            params.add(r.hasVegeterianMeal()); // has veggie
        }
        query = query.substring(0, query.length()-2);
        query += ";";
        boolean success = false;
        success = execute(query, params); // Skilar true/false eftir success.

        return success;
    }

    ArrayList<Reservation> searchForReservations(String name, String passportNumber) {

        String query =
                  "SELECT DISTINCT " +
                "\n    Reservations.Name, " +
                "\n    Reservations.PassportNumber, " +
                "\n    Reservations.FlightNumber, " +
                "\n    Reservations.DepartureTime, " +
                "\n    Reservations.Bags, " +
                "\n    Reservations.HasVegeterianMeal, " +
                "\n    Users.IsMinor, " +
                "\n    FlightSeats.Row, " +
                "\n    FlightSeats.Column, " +
                "\n    FlightSeats.IsFirstClass, " +
                "\n        CASE WHEN Reservations.PassportNumber IS NULL " +
                "\n            THEN 1 " +
                "\n            ELSE 0 " +
                "\n        END " +
                "\n            AS IsAvailable " +
                "\nFROM " +
                "\n    ( " +
                "\n        Reservations " +
                "\n        LEFT JOIN Users " +
                "\n            ON Reservations.Name = Users.Name " +
                "\n                AND Reservations.PassportNumber = Users.PassportNumber " +
                "\n        LEFT JOIN FlightSeats " +
                "\n            ON FlightSeats.Row = Reservations.SeatRow " +
                "\n                AND FlightSeats.Column = Reservations.SeatColumn " +
                "\n                AND FlightSeats.AirplaneType = Reservations.AirplaneType " +
                "\n    ) " +
                "\nWHERE " +
                "\n    Reservations.Name = ? " +
                "\n        AND Reservations.PassportNumber = ?;";

        ArrayList<Object> params = new ArrayList<>();
        params.add(name);
        params.add(passportNumber);
        ArrayList<Reservation> reservations = executeQuery(query, params, reservationInitializer);
        return reservations;
    }

    boolean cancelReservation(Reservation reservation) {
        String query = "DELETE FROM Reservations " +
                "WHERE FlightNumber = ? " +
                    "AND DepartureTime = ? " +
                    "AND Name = ? " +
                    "AND PassportNumber = ?";
        ArrayList<Object> params = new ArrayList<>();
        params.add(reservation.getFlightNumber());
        params.add(reservation.getDepartureTimeString());
        params.add(reservation.getUser().getName());
        params.add(reservation.getUser().getPassportNumber());

        return execute(query, params);
    }

    boolean addReview(Reservation reservation, int score, String comment) {

        String query = "INSERT INTO Reviews (\n" +
                "  FlightNumber,\n" +
                "  DepartureTime,\n" +
                "  Name,\n" +
                "  PassportNumber,\n" +
                "  Rating,\n" +
                "  Comment)" +
                "  VALUES (?, ?, ?, ?, ?, ?)";
        ArrayList<Object> params = new ArrayList<>();
        params.add(reservation.getFlightNumber());
        params.add(reservation.getDepartureTimeString());
        params.add(reservation.getUser().getName());
        params.add(reservation.getUser().getPassportNumber());
        params.add(score);
        params.add(comment);

        return execute(query, params);
    }
}
