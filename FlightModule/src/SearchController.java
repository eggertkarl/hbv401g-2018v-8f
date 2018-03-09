import java.time.LocalDateTime;
import java.util.ArrayList;

public class SearchController extends DatabaseController {


    //region Initializers
    //--------------------------------------------------------------------------------
    private static final Initializer<Seat> seatInitializer = (map -> {
        int row = (int) map.get(SeatColumns.row);
        String column = (String) map.get(SeatColumns.column);
        boolean isAvailable = (int) map.get(SeatColumns.isAvailable) == 1;
        boolean isFirstClass = (int) map.get(SeatColumns.isFirstClass) == 1;

        return new Seat(row, column, isAvailable, isFirstClass);
    });

    private static final Initializer<User> userInitializer = (map -> {
        String name = (String) map.get(UserColumns.name);
        boolean isMinor = (int) map.get(UserColumns.isMinor) == 1;
        String passportNumber = (String) map.get(UserColumns.passportNumber);

        return new User(name, isMinor, passportNumber);
    });

    private static final Initializer<Flight> flightInitializer = (map -> {
        String flightNumber = (String) map.get(FlightColumns.flightNumber);
        String airline = (String) map.get(FlightColumns.airline);
        String airplaneType = (String) map.get(FlightColumns.airplaneType);
        int priceCoach = (int) map.get(FlightColumns.priceCoach);
        int priceFirstClass = (int) map.get(FlightColumns.priceFirstClass);

        int totalSeatsFirstClass = (int) map.get(FlightColumns.totalSeatsFirstClass);
        int totalSeatsCoach = (int) map.get(FlightColumns.totalSeatsCoach);
        int reservedSeatsFirstClass = (int) map.get(FlightColumns.reservedSeatsFirstClass);
        int reservedSeatsCoach = (int) map.get(FlightColumns.reservedSeatsCoach);

        String departureLocation = (String) map.get(FlightColumns.departureLocation);
        String arrivalLocation = (String) map.get(FlightColumns.arrivalLocation);

        String arrivalTimeText = (String) map.get(FlightColumns.arrivalTime);
        String departureTimeText = (String) map.get(FlightColumns.departureTime);
        LocalDateTime arrivalTime = convertStringToLocalDateTime(arrivalTimeText);
        LocalDateTime departureTime = convertStringToLocalDateTime(departureTimeText);


        boolean hasMeal = (int) map.get(FlightColumns.hasMeal) == 1;
        boolean hasVegeterianMeal = (int) map.get(FlightColumns.hasVegeterianMeal) == 1;
        boolean hasEntertainment = (int) map.get(FlightColumns.hasEntertainment) == 1;

        return new Flight(flightNumber, airline, airplaneType, priceCoach, priceFirstClass,
                totalSeatsFirstClass, totalSeatsCoach, reservedSeatsFirstClass, reservedSeatsCoach,
                departureLocation, arrivalLocation, departureTime, arrivalTime, hasMeal,
                hasVegeterianMeal, hasEntertainment);
    });
    //--------------------------------------------------------------------------------
    //endregion


    //region Public functions
    //--------------------------------------------------------------------------------
    public boolean fetchSeats(Flight flight) {
        String flightNumber = flight.getFlightNumber();
        LocalDateTime departureTime = flight.getDepartureTime();
        String departureTimeText = convertLocalDateTimeToString(departureTime);
        String airplaneType = flight.getAirplaneType();

        String query =
                "SELECT Row, Column, IsFirstClass, CASE WHEN PassportNumber IS NULL THEN 1 ELSE 0 END AS IsAvailable FROM "
            + "\n("                                   // PassportNumber IS NULL  <=>  No user has reserved the seat
            + "\n	("
            + "\n		SELECT * FROM FlightSeats "
            + "\n		WHERE AirplaneType = ?"
            + "\n	) AS S"
            + "\n	LEFT JOIN "
            + "\n	("
            + "\n		SELECT * FROM Reservations "
            + "\n		WHERE FlightNumber = ? "
            + "\n		AND DepartureTime = ?"
            + "\n	) AS R"
            + "\n	ON S.Row = R.SeatRow"
            + "\n	AND S.Column = R.SeatColumn"
            + "\n);";

        ArrayList<Object> params = new ArrayList<>();
        params.add(airplaneType);
        params.add(flightNumber);
        params.add(departureTimeText);

        ArrayList<Seat> seats = executeQuery(query, params, seatInitializer);
        if(seats == null) {
            return false;
        }
        if(seats.isEmpty()) {
            return false;
        }
        flight.setSeats(seats);
        return true;
    }
    //--------------------------------------------------------------------------------
    //endregion


    //region Public Search functions
    //--------------------------------------------------------------------------------
    public ArrayList<Flight> searchForAllFlights() {
        return searchForFlights(null, null);
    }

    public ArrayList<Flight> searchForAllFlightsByDepartureInterval(LocalDateTime start, LocalDateTime end) {
        String[] filters = {
                FlightColumns.departureTime + " >= ?",
                FlightColumns.departureTime + " <= ?"
        };
        Object[] params = {
                convertLocalDateTimeToString(start),
                convertLocalDateTimeToString(end)
        };

        return searchForFlights(filters, params);
    }


    public ArrayList<Flight> searchForAllFlightsFilterByAirline(String airline) {

        // This query example selects all names that start with A.
        // SELECT * FROM Names WHERE Name LIKE 'A%';

        String[] filters = {
                FlightColumns.airline + " LIKE ?"
        };
        Object[] params = {airline + "%"};

        return searchForFlights(filters, params);
    }

    // TODO: Implement more functions with filters.

    //--------------------------------------------------------------------------------
    //endregion


    //region Private utilities
    //--------------------------------------------------------------------------------
    private ArrayList<Flight> searchForFlights(String[] filters, Object[] params) {

        ArrayList<String> usedFilters = new ArrayList<>();
        ArrayList<Object> usedParams = new ArrayList<>();

        int m = Math.min(filters.length, params.length);

        for(int i = 0; i < m; i++) {
            if(params[i] != null) {
                usedFilters.add(filters[i]);
                usedParams.add(params[i]);
            }
        }

        String query = getFlightQuery(usedFilters);
        return executeQuery(query, usedParams, flightInitializer);
    }



    private String getFlightQuery() {
        return getFlightQuery(null);
    }

    private String getFlightQuery(ArrayList<String> filters) {
        String query =
              "SELECT FlightNumber, Airline, AirplaneType, DepartureLocation, ArrivalLocation,"
            + "\n	DepartureTime, ArrivalTime, PriceCoach, PriceFirstClass, HasMeal, HasVegeterianMeal,"
            + "\n	HasEntertainment, " + FlightColumns.totalSeatsCoach + "," + FlightColumns.totalSeatsFirstClass + ","
            + "\n	IFNULL(" + FlightColumns.reservedSeatsCoach + ", 0) " + FlightColumns.reservedSeatsCoach + ","
            + "\n	IFNULL(" + FlightColumns.reservedSeatsFirstClass + ", 0) " + FlightColumns.reservedSeatsFirstClass
            + "\n FROM ("
            + "\n	SELECT * FROM ("
            + "\n	("
            + "\n		SELECT * FROM Flights"
            + "\n     -- WHERE <FILTERS>" // This is replaced with filters if necessary.
            + "\n	) F"
            + "\n	LEFT JOIN "
            + "\n	("
            + "\n		SELECT "
            + "\n			AirplaneType, "
            + "\n			COUNT(CASE WHEN IsFirstClass = 0 THEN 1 END) AS " + FlightColumns.totalSeatsCoach + ",  "
            + "\n			COUNT(CASE WHEN IsFirstClass = 1 THEN 1 END) AS " + FlightColumns.totalSeatsFirstClass
            + "\n		FROM FlightSeats"
            + "\n		GROUP BY AirplaneType"
            + "\n	) S"
            + "\n	ON F.AirplaneType = S.AirplaneType"
            + "\n	) T -- Total count"
            + "\n	LEFT JOIN "
            + "\n	("
            + "\n		SELECT FlightNumber, DepartureTime, "
            + "\n			COUNT(CASE WHEN IsFirstClass = 0 THEN 1 END) AS " + FlightColumns.reservedSeatsCoach + ", "
            + "\n			COUNT(CASE WHEN IsFirstClass = 1 THEN 1 END) AS " + FlightColumns.reservedSeatsFirstClass
            + "\n		FROM ("
            + "\n			(SELECT FlightNumber, DepartureTime, SeatRow, SeatColumn, AirplaneType FROM Reservations) R"
            + "\n			LEFT JOIN "
            + "\n			(SELECT Row, Column, IsFirstClass, AirplaneType FROM FlightSeats) S"
            + "\n			ON R.SeatRow = S.Row"
            + "\n			AND R.SeatColumn = S.Column"
            + "\n			AND R.AirplaneType = S.AirplaneType"
            + "\n		)"
            + "\n	) B -- Booked count"
            + "\n	ON T.FlightNumber = B.FlightNumber"
            + "\n	AND T.DepartureTime = B.DepartureTime"
            + "\n);";
        if(filters == null) {
            return query;
        }
        if(filters.isEmpty()) {
            return query;
        }
        String filter = "WHERE " + String.join(" AND ", filters);
        return query.replace("-- WHERE <FILTERS>", filter);
    }
    //--------------------------------------------------------------------------------
    //endregion

    //region Column names
    //--------------------------------------------------------------------------------
    private static class FlightColumns {
        static final String flightNumber = "FlightNumber";
        static final String airline = "Airline";
        static final String airplaneType = "AirplaneType";
        static final String departureLocation = "DepartureLocation";
        static final String arrivalLocation = "ArrivalLocation";
        static final String departureTime = "DepartureTime";
        static final String arrivalTime = "ArrivalTime";
        static final String priceCoach = "PriceCoach";
        static final String priceFirstClass = "PriceFirstClass";
        static final String hasMeal = "HasMeal";
        static final String hasVegeterianMeal = "HasVegeterianMeal";
        static final String hasEntertainment = "HasEntertainment";

        static final String totalSeatsFirstClass = "TotalSeatsFirstClass";
        static final String totalSeatsCoach = "TotalSeatsCoach";
        static final String reservedSeatsFirstClass = "ReservedSeatsFirstClass";
        static final String reservedSeatsCoach = "ReservedSeatsCoach";
    }

    private static class SeatColumns {
        static final String row = "Row";
        static final String column = "Column";
        static final String isFirstClass = "IsFirstClass";
        static final String isAvailable = "IsAvailable";
    }

    private static class UserColumns {
        static final String name = "Name";
        static final String isMinor = "IsMinor";
        static final String passportNumber = "PassportNumber";
    }
    //--------------------------------------------------------------------------------
    //endregion
}
