import com.sun.corba.se.impl.orb.DataCollectorBase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class should only be used to test before the interface has
 * been fully implemented.
 */
public class TempTest
{
    public static void main(String[] args)
    {
        SearchController sc = new SearchController();

        ArrayList<Flight> flights = sc.searchForAllFlightsFilterByAirline("Icelandair");

        System .out.println("Flights found: ");
        for(int i = 0; i < flights.size(); i++) {
            System.out.println(flights.get(i).getFlightNumber()
                    + ",\t Reserved seats (first class): " + flights.get(i).getReservedSeatsFirstClass());
        }

        Flight flight = flights.get(0);
        sc.fetchSeats(flight);
        ArrayList<Seat> seats = flight.getSeats();
        for(int i = 0; i < seats.size(); i++) {
            System.out.println(seats.get(i).isAvailable());
        }
    }

}

