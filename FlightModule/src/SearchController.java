package FlightModule.src;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

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


    public ArrayList<String> getListOfLocations() {
        return databaseController.getListOfLocations();
    }

    public ArrayList<String> getListOfAirlines() {
        return databaseController.getListOfAirlines();
    }

    public ArrayList<String> getListOfAirplaneTypes() {
        return databaseController.getListOfAirplaneTypes();
    }

    public ArrayList<Integer> getMinMaxPriceCoach() {
        return databaseController.getMinMaxPriceCoach();
    }

    public ArrayList<Integer> getMinMaxPriceFirstClass() {
        return databaseController.getMinMaxPriceFirstClass();
    }

    //--------------------------------------------------------------------------------
    //endregion

    //region sort functions
    //--------------------------------------------------------------------------------
    public void orderByPriceCoach(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getPriceCoach().compareTo(f2.getPriceCoach()));
    }
    public void orderByPriceFirstClass(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getPriceFirstClass().compareTo(f2.getPriceFirstClass()));
    }
    public void orderByFlightNumber(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getFlightNumber().compareTo(f2.getFlightNumber()));
    }
    public void orderByAirline(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getAirline().compareTo(f2.getAirline()));
    }
    public void orderByAirplaneType(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getAirplaneType().compareTo(f2.getAirplaneType()));
    }
    public void orderByTotalSeatsFirstClass(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getTotalSeatsFirstClass().compareTo(f2.getTotalSeatsFirstClass()));
    }
    public void orderByTotalSeatsCoach(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getTotalSeatsCoach().compareTo(f2.getTotalSeatsCoach()));
    }
    public void orderByReservedSeatsCoach(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getReservedSeatsCoach().compareTo(f2.getReservedSeatsCoach()));
    }
    public void orderByReservedSeatsFirstClass(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getReservedSeatsFirstClass().compareTo(f2.getReservedSeatsFirstClass()));
    }
    public void orderByDepartureLocation(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getDepartureLocation().compareTo(f2.getDepartureLocation()));
    }
    public void orderByArrivalLocation(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getArrivalLocation().compareTo(f2.getArrivalLocation()));
    }
    public void orderByDepartureTime(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getDepartureTime().compareTo(f2.getDepartureTime()));
    }
    public void orderByArrivalTime(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getArrivalTime().compareTo(f2.getArrivalTime()));
    }
    public void orderByAverageRating(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.getAverageRating().compareTo(f2.getAverageRating()));
    }
    public void orderByHasMeal(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.hasMeal().compareTo(f2.hasMeal()));
    }
    public void orderByHasVegeterianMeal(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.hasVegeterianMeal().compareTo(f2.hasVegeterianMeal()));
    }
    public void orderByHasEntertainment(ArrayList<Flight> flightList) {
        Collections.sort(flightList, (Flight f1, Flight f2) -> f1.hasEntertainment().compareTo(f2.hasEntertainment()));
    }
    //--------------------------------------------------------------------------------
    //endregion


    //region Filter functions
    //--------------------------------------------------------------------------------

    public void filterReset() {
        flightFilter.clearAll();
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
