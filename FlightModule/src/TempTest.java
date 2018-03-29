import java.util.ArrayList;

/**
 * This class should only be used to test before the interface has
 * been fully implemented.
 */
public class TempTest
{
    public static void main(String[] args)
    {
        SearchController sc = new SearchController();

        sc.filterSetAirlineEqualTo("Icelandair");
        sc.filterSetDepartureLocationEqualTo("Keflavík");
        sc.filterSetPriceCoachInterval(20000, 80000);

        ArrayList<Flight> flights = sc.searchForFlights();

        System .out.println("Flights found: ");
        for(int i = 0; i < flights.size(); i++) {
            System.out.println(flights.get(i).getFlightNumber()
                    + ",\t Reserved seats (first class): " + flights.get(i).getReservedSeatsFirstClass()
                    + "\tRating: " + flights.get(i).getAverageRating());
        }


        ArrayList<String> locations = sc.getListOfLocations();
        ArrayList<String> airplaneTypes = sc.getListOfAirplaneTypes();
        ArrayList<String> airlines = sc.getListOfAirlines();
        ArrayList<Integer> priceCoach = sc.getMinMaxPriceCoach();
        ArrayList<Integer> priceFirstClass = sc.getMinMaxPriceFirstClass();
        System.out.println("Done");
    }

}

