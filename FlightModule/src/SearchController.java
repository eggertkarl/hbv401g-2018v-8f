import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchController extends DatabaseController {


    //region Initializers
    //--------------------------------------------------------------------------------
    private static final Initializer<Seat> seatInitializer = new Initializer<Seat>() {
        @Override
        Seat create(HashMap<String, Object> map) {
            set(map);
            return new Seat(
                    getInt(SeatColumns.row),
                    getString(SeatColumns.column),
                    getBoolean(SeatColumns.isAvailable),
                    getBoolean(SeatColumns.isFirstClass)
            );
        }
    };

    private static final Initializer<User> userInitializer = new Initializer<User>() {
        @Override
        User create(HashMap<String, Object> map) {
            set(map);
            return new User(
                    getString(UserColumns.name),
                    getBoolean(UserColumns.isMinor),
                    getString(UserColumns.passportNumber));
        }
    };

    private static final Initializer<Flight> flightInitializer = new Initializer<Flight>() {
        @Override
        Flight create(HashMap<String, Object> map) {
            set(map);
            return new Flight(
                    getString(FlightColumns.flightNumber),
                    getString(FlightColumns.airline),
                    getString(FlightColumns.airplaneType),
                    getInt(FlightColumns.priceCoach),
                    getInt(FlightColumns.priceFirstClass),
                    getInt(FlightColumns.totalSeatsFirstClass),
                    getInt(FlightColumns.totalSeatsCoach),
                    getInt(FlightColumns.reservedSeatsFirstClass),
                    getInt(FlightColumns.reservedSeatsCoach),
                    getString(FlightColumns.departureLocation),
                    getString(FlightColumns.arrivalLocation),
                    getDateTime(FlightColumns.departureTime),
                    getDateTime(FlightColumns.arrivalTime),
                    getDouble(FlightColumns.averageRating),
                    getBoolean(FlightColumns.hasMeal),
                    getBoolean(FlightColumns.hasVegeterianMeal),
                    getBoolean(FlightColumns.hasEntertainment)
            );
        }
    };
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

        if(filters == null) {
            filters = new String[0];
        }
        if(params == null) {
            params = new Object[0];
        }

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
            + "\n	HasEntertainment, " + FlightColumns.averageRating + ", "
            + "\n" + FlightColumns.totalSeatsCoach + ", " + FlightColumns.totalSeatsFirstClass + ","
            + "\n	IFNULL(" + FlightColumns.reservedSeatsCoach + ", 0) " + FlightColumns.reservedSeatsCoach + ","
            + "\n	IFNULL(" + FlightColumns.reservedSeatsFirstClass + ", 0) " + FlightColumns.reservedSeatsFirstClass
            + "\n FROM ("
            + "\n	SELECT * FROM ("
            + "\n	"
            + "\n	SELECT * FROM ("
            + "\n		("
            + "\n			SELECT * FROM Flights"
            + "\n			-- WHERE <FILTERS>"
            + "\n		) F -- F is an alias for the table that selects all flights that match the filters"
            + "\n		LEFT JOIN"
            + "\n		("
            + "\n	   	 -- Counting seats (coach and first class) per airplane type."
            + "\n			SELECT"
            + "\n				AirplaneType,"
            + "\n				COUNT(CASE WHEN IsFirstClass = 0 THEN 1 END) AS " + FlightColumns.totalSeatsCoach + ","
            + "\n				COUNT(CASE WHEN IsFirstClass = 1 THEN 1 END) AS " + FlightColumns.totalSeatsFirstClass
            + "\n			FROM FlightSeats"
            + "\n			GROUP BY AirplaneType"
            + "\n		) S -- S is an alias for the table that counts the seats (coach and first class) per airplane type."
            + "\n		ON F.AirplaneType = S.AirplaneType"
            + "\n	) T"
            + "\n	LEFT JOIN "
            + "\n	("
            + "\n		SELECT Airline, AVG(Rating) AS " + FlightColumns.averageRating
            + "\n		FROM Reviews"
            + "\n	) D"
            + "\n	ON D.Airline = T.Airline"
            + "\n	) A "
            + "\n"
            + "\n	LEFT JOIN"
            + "\n"
            + "\n	("
            + "\n	  -- Counting reserved seats (coach and first class)"
            + "\n		SELECT FlightNumber, DepartureTime,"
            + "\n			COUNT(CASE WHEN IsFirstClass = 0 THEN 1 END) AS " + FlightColumns.reservedSeatsCoach + ","
            + "\n			COUNT(CASE WHEN IsFirstClass = 1 THEN 1 END) AS " + FlightColumns.reservedSeatsFirstClass
            + "\n		FROM ("
            + "\n			(SELECT FlightNumber, DepartureTime, SeatRow, SeatColumn, AirplaneType FROM Reservations) R -- alias"
            + "\n			LEFT JOIN"
            + "\n			(SELECT Row, Column, IsFirstClass, AirplaneType FROM FlightSeats) C"
            + "\n			-- C is an alias for a subset of the FlightSeats table"
            + "\n			ON R.SeatRow = C.Row"
            + "\n			AND R.SeatColumn = C.Column"
            + "\n			AND R.AirplaneType = C.AirplaneType"
            + "\n		)"
            + "\n	) B -- B is an alias for the table that is used to keep track of what seats have been reserved"
            + "\n	ON A.FlightNumber = B.FlightNumber"
            + "\n	AND A.DepartureTime = B.DepartureTime"
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
        static final String averageRating = "AverageRating";
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
