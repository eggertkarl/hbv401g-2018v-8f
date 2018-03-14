import java.time.LocalDateTime;
import java.util.ArrayList;

public class SearchController extends DatabaseController {

    private Filter flightFilter = null;

    public SearchController() {
        flightFilter = new Filter();
    }

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

    public ArrayList<Flight> searchForFlights() {
        if(this.flightFilter == null) {
            String query = getFlightQuery();
            return executeQuery(query, flightInitializer);
        }

        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParameters = this.flightFilter.getFiltersAndParameters();
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



    //region Filter functions
    //--------------------------------------------------------------------------------

    public void filterReset() {
        flightFilter = new Filter();
    }

    //region Set functions
    //--------------------------------------------------------------------------------
    public void filterSetFilterFlightNumberEqualTo(String flightNumber) {
        flightFilter.setEqualTo(FlightColumns.flightNumber, flightNumber);
    }

    public void filterSetAirlineEqualTo(String airline) {
        flightFilter.setEqualTo(FlightColumns.airline, airline);
    }

    public void filterSetAirplaneTypeEqualTo(String airplaneType) {
        flightFilter.setEqualTo(FlightColumns.airplaneType, airplaneType);
    }

    public void filterSetDepartureLocationEqualTo(String departureLocation) {
        flightFilter.setEqualTo(FlightColumns.departureLocation, departureLocation);
    }

    public void filterSetArrivalLocationEqualTo(String arrivalLocation) {
        flightFilter.setEqualTo(FlightColumns.arrivalLocation, arrivalLocation);
    }

    public void filterSetHasMealEqualTo(Boolean hasMeal) {
        flightFilter.setEqualTo(FlightColumns.hasMeal, hasMeal);
    }

    public void filterSetHasVegeterianMealEqualTo(Boolean hasVegeterianMeal) {
        flightFilter.setEqualTo(FlightColumns.hasVegeterianMeal, hasVegeterianMeal);
    }

    public void filterSetHasEntertainmentEqualTo(Boolean hasEntertainment) {
        flightFilter.setEqualTo(FlightColumns.hasEntertainment, hasEntertainment);
    }


    // Departure time functions:
    public void filterSetDepartureTimeEqualTo(LocalDateTime departureTime) {
        flightFilter.setEqualTo(FlightColumns.departureTime, Utilities.convertLocalDateTimeToString(departureTime));
    }

    public void filterSetDepartureTimeLowerThanOrEqualTo(LocalDateTime departureTime) {
        flightFilter.setLowerThanOrEqualTo(FlightColumns.departureTime, Utilities.convertLocalDateTimeToString(departureTime));
    }

    public void filterSetDepartureTimeGreaterThanOrEqualTo(LocalDateTime departureTime) {
        flightFilter.setGreaterThanOrEqualTo(FlightColumns.departureTime, Utilities.convertLocalDateTimeToString(departureTime));
    }

    public void filterSetDepartureTimeInterval(LocalDateTime departureTimeLower, LocalDateTime departureTimeUpper) {
        if(departureTimeLower != null && departureTimeUpper != null) {
            // If both have set times, we want to check if the user swapped the upper/lower bounds.
            if(departureTimeUpper.isBefore(departureTimeLower)) {
                flightFilter.setInterval(FlightColumns.departureTime,
                        Utilities.convertLocalDateTimeToString(departureTimeUpper),
                        Utilities.convertLocalDateTimeToString(departureTimeLower));
                return;
            }
        }
        flightFilter.setInterval(FlightColumns.departureTime,
                Utilities.convertLocalDateTimeToString(departureTimeLower),
                Utilities.convertLocalDateTimeToString(departureTimeUpper));
    }


    // Arrival time functions:
    public void filterSetArrivalTimeEqualTo(LocalDateTime arrivalTime) {
        flightFilter.setEqualTo(FlightColumns.arrivalTime, Utilities.convertLocalDateTimeToString(arrivalTime));
    }

    public void filterSetArrivalTimeLowerThanOrEqualTo(LocalDateTime arrivalTime) {
        flightFilter.setLowerThanOrEqualTo(FlightColumns.arrivalTime, Utilities.convertLocalDateTimeToString(arrivalTime));
    }

    public void filterSetArrivalTimeGreaterThanOrEqualTo(LocalDateTime arrivalTime) {
        flightFilter.setGreaterThanOrEqualTo(FlightColumns.arrivalTime, Utilities.convertLocalDateTimeToString(arrivalTime));
    }

    public void filterSetArrivalTimeInterval(LocalDateTime arrivalTimeLower, LocalDateTime arrivalTimeUpper) {
        if(arrivalTimeLower != null && arrivalTimeUpper != null) {
            // If both have set times, we want to check if the user swapped the upper/lower bounds.
            if(arrivalTimeUpper.isBefore(arrivalTimeLower)) {
                flightFilter.setInterval(FlightColumns.arrivalTime,
                        Utilities.convertLocalDateTimeToString(arrivalTimeUpper),
                        Utilities.convertLocalDateTimeToString(arrivalTimeLower));
                return;
            }
        }
        flightFilter.setInterval(FlightColumns.arrivalTime,
                Utilities.convertLocalDateTimeToString(arrivalTimeLower),
                Utilities.convertLocalDateTimeToString(arrivalTimeUpper));
    }


    // Price coach functions:
    public void filterSetPriceCoachEqualTo(Integer priceCoach) {
        flightFilter.setEqualTo(FlightColumns.priceCoach, priceCoach);
    }

    public void filterSetPriceCoachLowerThanOrEqualTo(Integer priceCoach) {
        flightFilter.setLowerThanOrEqualTo(FlightColumns.priceCoach, priceCoach);
    }

    public void filterSetPriceCoachGreaterThanOrEqualTo(Integer priceCoach) {
        flightFilter.setGreaterThanOrEqualTo(FlightColumns.priceCoach, priceCoach);
    }

    public void filterSetPriceCoachInterval(Integer priceCoachLower, Integer priceCoachUpper) {
        if(priceCoachLower != null && priceCoachUpper != null) {
            // If both have set times, we want to check if the user swapped the upper/lower bounds.
            if(priceCoachLower > priceCoachUpper) {
                flightFilter.setInterval(FlightColumns.priceCoach, priceCoachUpper, priceCoachLower);
                return;
            }
        }
        flightFilter.setInterval(FlightColumns.priceCoach, priceCoachLower, priceCoachUpper);
    }


    // Price first class functions:
    public void filterSetPriceFirstClassEqualTo(Integer priceFirstClass) {
        flightFilter.setEqualTo(FlightColumns.priceFirstClass, priceFirstClass);
    }

    public void filterSetPriceFirstClassLowerThanOrEqualTo(Integer priceFirstClass) {
        flightFilter.setLowerThanOrEqualTo(FlightColumns.priceFirstClass, priceFirstClass);
    }

    public void filterSetPriceFirstClassGreaterThanOrEqualTo(Integer priceFirstClass) {
        flightFilter.setGreaterThanOrEqualTo(FlightColumns.priceFirstClass, priceFirstClass);
    }

    public void filterSetPriceFirstClassInterval(Integer priceFirstClassLower, Integer priceFirstClassUpper) {
        if(priceFirstClassLower != null && priceFirstClassUpper != null) {
            // If both have set times, we want to check if the user swapped the upper/lower bounds.
            if(priceFirstClassLower > priceFirstClassUpper) {
                flightFilter.setInterval(FlightColumns.priceFirstClass, priceFirstClassUpper, priceFirstClassLower);
                return;
            }
        }
        flightFilter.setInterval(FlightColumns.priceFirstClass, priceFirstClassLower, priceFirstClassUpper);
    }
    //--------------------------------------------------------------------------------
    //endregion


    //region Remove functions
    //--------------------------------------------------------------------------------
    public void filterRemoveFlightNumber() {
        flightFilter.remove(FlightColumns.flightNumber);
    }

    public void filterRemoveAirline() {
        flightFilter.remove(FlightColumns.airline);
    }

    public void filterRemoveAirplaneType() {
        flightFilter.remove(FlightColumns.airplaneType);
    }

    public void filterRemoveDepartureLocation() {
        flightFilter.remove(FlightColumns.departureLocation);
    }

    public void filterRemoveArrivalLocation() {
        flightFilter.remove(FlightColumns.arrivalLocation);
    }

    public void filterRemoveHasMeal() {
        flightFilter.remove(FlightColumns.hasMeal);
    }

    public void filterRemoveHasVegeterianMeal() {
        flightFilter.remove(FlightColumns.hasVegeterianMeal);
    }

    public void filterRemoveHasEntertainment() {
        flightFilter.remove(FlightColumns.hasEntertainment);
    }

    public void filterRemoveDepartureTime() {
        flightFilter.remove(FlightColumns.departureTime);
    }

    public void filterRemoveArrivalTime() {
        flightFilter.remove(FlightColumns.arrivalTime);
    }

    public void filterRemovePriceCoach() {
        flightFilter.remove(FlightColumns.priceCoach);
    }

    public void filterRemovePriceFirstClass() {
        flightFilter.remove(FlightColumns.priceFirstClass);
    }
    //--------------------------------------------------------------------------------
    //endregion
    //--------------------------------------------------------------------------------
    //endregion
}
