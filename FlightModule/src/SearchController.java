import java.time.LocalDateTime;
import java.util.ArrayList;

public class SearchController extends ColumnNames {

    private Filter flightFilter = null;
    private SearchDatabaseController databaseController = null;

    public SearchController() {
        flightFilter = new Filter();
        databaseController = new SearchDatabaseController();
    }

    //region Public functions
    //--------------------------------------------------------------------------------
    public boolean fetchSeats(Flight flight) {
        return databaseController.fetchSeats(flight);
    }

    public ArrayList<Flight> searchForFlights() {
        return databaseController.searchForFlights(flightFilter);
    }


    // TODO: List of airlines?

    // TODO: List of airplane types
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
