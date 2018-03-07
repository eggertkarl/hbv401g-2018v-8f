import java.util.ArrayList;

public class SearchController extends DatabaseController {
    // TODO: Implement

    // TODO: Set to private
    public static final Initializer<Seat> seatInitializer = (map -> {
        int row = (int) map.get("Row");
        String column = (String) map.get("Column");
        boolean isAvailable = true;
        // TODO: Add isAvailable to query
        //boolean isAvailable = (boolean) map.get("isAvailable");
        boolean isFirstClass = (int) map.get("IsFirstClass") == 1;

        return new Seat(row, column, isAvailable, isFirstClass);
    });

    // TODO: Set to private
    public static final Initializer<User> userInitializer = (map -> {
        String name = (String) map.get("Name");
        boolean isMinor = (int) map.get("IsMinor") == 1;
        String passportNumber = (String) map.get("PassportNumber");

        return new User(name, isMinor, passportNumber);
    });

    private static final Initializer<Flight> flightInitializer = (map -> {
        String flightNumber = (String) map.get("FlightNumber");
        String airline = (String) map.get("Airline");
        int priceCoach = (int) map.get("PriceCoach");
        int priceFirstClass = (int) map.get("PriceFirstClass");

        // TODO: Add departure time and arrival timea

        int seatCountFirstClassAvailable = 10; //(int) map.get("FirstClassCount");
        int seatCountCouchAvailable = 6; //(int) map.get("FlightNumber");

        String departureLocation = (String) map.get("DepartureLocation");
        String arrivalLocation = (String) map.get("ArrivalLocation");
        boolean hasMeal = (int) map.get("HasMeal") == 1;
        boolean hasVegeterianMeal = (int) map.get("HasVegeterianMeal") == 1;
        boolean hasEntertainment = (int) map.get("HasEntertainment") == 1;

        return new Flight(flightNumber, airline, priceCoach, priceFirstClass, seatCountFirstClassAvailable,
                seatCountCouchAvailable, departureLocation, arrivalLocation, hasMeal, hasVegeterianMeal,
                hasEntertainment);
    });




    public ArrayList<Flight> searchForAllFlights() {
        return executeQuery("SELECT * FROM Flights;", flightInitializer);
    }

    public ArrayList<Flight> searchForAllFlightsFilterByAirline(String airline) {
        if(airline == null) {
            return searchForAllFlights();
        }
        if(airline.length() == 0) {
            return searchForAllFlights();
        }
        ArrayList<Object> params = new ArrayList<>();
        params.add(airline);
        String query = "SELECT * FROM Flights WHERE Airline LIKE ?;";
        return executeQuery(query, params, flightInitializer);
    }
}
