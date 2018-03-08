import java.time.LocalDateTime;
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
        String airplaneType = (String) map.get("AirplaneType");
        int priceCoach = (int) map.get("PriceCoach");
        int priceFirstClass = (int) map.get("PriceFirstClass");

        // TODO: Add departure time and arrival timea
        int seatCountFirstClassAvailable = 10; //(int) map.get("FirstClassCount");
        int seatCountCouchAvailable = 6; //(int) map.get("FlightNumber");

        String departureLocation = (String) map.get("DepartureLocation");
        String arrivalLocation = (String) map.get("ArrivalLocation");

        LocalDateTime arrivalTime = (LocalDateTime) map.get("ArrivalTime");
        LocalDateTime departureTime = (LocalDateTime) map.get("DepartureTime");

        boolean hasMeal = (int) map.get("HasMeal") == 1;
        boolean hasVegeterianMeal = (int) map.get("HasVegeterianMeal") == 1;
        boolean hasEntertainment = (int) map.get("HasEntertainment") == 1;

        return new Flight(flightNumber, airline, airplaneType, priceCoach, priceFirstClass,
                seatCountFirstClassAvailable, seatCountCouchAvailable, departureLocation, arrivalLocation,
                departureTime, arrivalTime, hasMeal, hasVegeterianMeal, hasEntertainment);
    });


    public boolean fetchSeats(Flight flight) {
        String flightNumber = flight.getFlightNumber();
        LocalDateTime departureTime = flight.getDepartureTime();
        String airplaneType = flight.getAirplaneType();

        String query =
                "SELECT Row, Column, IsFirstClass, CASE WHEN PassportNumber IS NULL THEN 1 ELSE 0 END AS IsAvailable FROM \n" +
                "(\n" +
                "\tSELECT * FROM FlightSeats AS S\n" +
                "\tLEFT JOIN  Reservations AS R\n" +
                "\tON R.FlightNumber = ?\n" +
                "\tAND R.DepartureTime = ?\n" +
                "\tAND S.Row = R.SeatRow\n" +
                "\tAND S.Column = R.SeatColumn\n" +
                ")\n" +
                "WHERE AirplaneType = ?;";

        ArrayList<Object> params = new ArrayList<>();
        params.add(flightNumber);
        params.add(departureTime);
        params.add(airplaneType);

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
