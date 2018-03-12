import java.time.LocalDateTime;
import java.util.ArrayList;

public class SearchController extends DatabaseController {


    //region Public functions
    //--------------------------------------------------------------------------------
    public boolean fetchSeats(Flight flight) {
        String flightNumber = flight.getFlightNumber();
        LocalDateTime departureTime = flight.getDepartureTime();
        String departureTimeText = Utilities.convertLocalDateTimeToString(departureTime);
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

    public ArrayList<Flight> searchForFlights(Filter.Flight filter) {
        if(filter == null) {
            String query = getFlightQuery();
            return executeQuery(query, flightInitializer);
        }

        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParameters = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParameters.valueLower;
        ArrayList<Object> params = filtersAndParameters.valueUpper;

        String query = getFlightQuery(filters);
        return executeQuery(query, params, flightInitializer);
    }

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


}
