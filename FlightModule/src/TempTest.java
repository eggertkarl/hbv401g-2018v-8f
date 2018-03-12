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

        Filter.Flight filter = new Filter.Flight();
        filter.setAirlineEqual("Icelandair");
        filter.setDepartureLocationEqual("Keflavík");
        filter.setPriceCoachInterval(20000, 80000);

        ArrayList<Flight> flights = sc.searchForFlights(filter);

        System .out.println("Flights found: ");
        for(int i = 0; i < flights.size(); i++) {
            System.out.println(flights.get(i).getFlightNumber()
                    + ",\t Reserved seats (first class): " + flights.get(i).getReservedSeatsFirstClass()
                    + "\tRating: " + flights.get(i).getAverageRating());
        }

    }

}

