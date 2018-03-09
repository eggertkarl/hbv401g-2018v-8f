import java.time.LocalDateTime;
import java.util.ArrayList;

public class SearchController extends DatabaseController {


    //region Initializers
    //--------------------------------------------------------------------------------
    private static final Initializer<Seat> seatInitializer = (map -> {
        int row = (int) map.get("Row");
        String column = (String) map.get("Column");
        boolean isAvailable = (int) map.get("IsAvailable") == 1;
        boolean isFirstClass = (int) map.get("IsFirstClass") == 1;

        return new Seat(row, column, isAvailable, isFirstClass);
    });

    private static final Initializer<User> userInitializer = (map -> {
        String name = (String) map.get("Name");
        boolean isMinor = (int) map.get("IsMinor") == 1;
        String passportNumber = (String) map.get("PassportNumber");

        return new User(name, isMinor, passportNumber);
    });

    private static final Initializer<Flight> flightInitializer = (map -> {
        String flightNumber = (String) map.get("FlightNumber");
        String airline = (String) map.get("Airline");
        String airplaneType = (String) map.get("AirplaneType");
        int priceCoach = (int) map.get("PriceCoach");
        int priceFirstClass = (int) map.get("PriceFirstClass");

        int totalSeatsFirstClass = (int) map.get("TotalSeatsFirstClass");
        int totalSeatsCoach = (int) map.get("TotalSeatsCoach");
        int reservedSeatsFirstClass = (int) map.get("ReservedSeatsFirstClass");
        int reservedSeatsCoach = (int) map.get("ReservedSeatsCoach");

        String departureLocation = (String) map.get("DepartureLocation");
        String arrivalLocation = (String) map.get("ArrivalLocation");

        String arrivalTimeText = (String) map.get("ArrivalTime");
        String departureTimeText = (String) map.get("DepartureTime");
        LocalDateTime arrivalTime = convertStringToLocalDateTime(arrivalTimeText);
        LocalDateTime departureTime = convertStringToLocalDateTime(departureTimeText);


        boolean hasMeal = (int) map.get("HasMeal") == 1;
        boolean hasVegeterianMeal = (int) map.get("HasVegeterianMeal") == 1;
        boolean hasEntertainment = (int) map.get("HasEntertainment") == 1;

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
                "DepartureTime >= ?",
                "DepartureTime <= ?"
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

        String[] filters = {"Airline LIKE ?"};
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
            + "\n	HasEntertainment, TotalSeatsCoach, TotalSeatsFirstClass,"
            + "\n	IFNULL(ReservedSeatsCoach, 0) ReservedSeatsCoach,"
            + "\n	IFNULL(ReservedSeatsFirstClass, 0) ReservedSeatsFirstClass"
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
            + "\n			COUNT(CASE WHEN IsFirstClass = 0 THEN 1 END) AS TotalSeatsCoach,  "
            + "\n			COUNT(CASE WHEN IsFirstClass = 1 THEN 1 END) AS TotalSeatsFirstClass "
            + "\n		FROM FlightSeats"
            + "\n		GROUP BY AirplaneType"
            + "\n	) S"
            + "\n	ON F.AirplaneType = S.AirplaneType"
            + "\n	) T -- Total count"
            + "\n	LEFT JOIN "
            + "\n	("
            + "\n		SELECT FlightNumber, DepartureTime, "
            + "\n			COUNT(CASE WHEN IsFirstClass = 0 THEN 1 END) AS ReservedSeatsCoach, "
            + "\n			COUNT(CASE WHEN IsFirstClass = 1 THEN 1 END) AS ReservedSeatsFirstClass"
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

}
