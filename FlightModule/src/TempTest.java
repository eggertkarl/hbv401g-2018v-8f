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
        sc.fetchSeats(flights.get(1));
        //System .out.println("Flights found: ");
        //for(int i = 0; i < flights.size(); i++) {
        //    System.out.println(flights.get(i).gEetFlightNumber()
        //            + ",\t Reserved seats (first class): " + flights.get(i).getReservedSeatsFirstClass()
        //            + "\tRating: " + flights.get(i).getAverageRating());
        //}

        BookingController bc = new BookingController();

        bc.selectFlight(flights.get(1));
        Seat seat1 = flights.get(1).getSeats().get(40);
        Seat seat2 = flights.get(1).getSeats().get(41);
        bc.addSeat("Helgi", "12345", seat1, 1);
        bc.addSeat("Katrin", "23456", seat2, 2);
        bc.confirmReservation();

        ArrayList<Reservation> siggaFlug = bc.searchForReservations("Helgi", "12345");
        System.out.println(siggaFlug.get(0).getSeat().isFirstClass());


        ArrayList<String> locations = sc.getListOfLocations();
        ArrayList<String> airplaneTypes = sc.getListOfAirplaneTypes();
        ArrayList<String> airlines = sc.getListOfAirlines();
        ArrayList<Integer> priceCoach = sc.getMinMaxPriceCoach();
        ArrayList<Integer> priceFirstClass = sc.getMinMaxPriceFirstClass();
        System.out.println("Done");
    }

}

