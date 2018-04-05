import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;
/*
 * Þessi klasi profar orderBy föllin
 * i SearchController klasanum
 */
public class TestOrder {
    //controller
    private SearchController sc = null;
    private ArrayList<Flight> flights = null;

    // Við gerum mock object sem er ArrayList<Flight> og gerum það með þvi að smiða
    // lista með alls konar breytum fyrir öll svið Flight-hlutsins og i hverju profi
    // fyrir sig latum við handahofskennt langan lista fluga taka handahofskenndar
    // breytur fyrir hvert svið

    // region random options
    //--------------------------------------------------------------------------------
    // possible dates
    // index for arrays
    private int[] indices = new int[14];

    private int fjoldiFluga = 0;

    private String[] flightNumbers = {"wow100", "wow200", "fi101", "fi102", "ezy1000", "ezy2018"};
    private String[] airlines = {"wow", "fi", "ezy"};
    private String[] airplanes = {"b777", "b767", "b757", "a320", "a340"};
    private Integer[] prices = {10000, 15000, 18000, 35000, 50000};
    private Integer[] totalSeats = {250, 200, 150};
    private double[] reservedSeats = {0, 0.2, 0.6, 0.9, 1}; // hlutfall upptekinna sæta - þessu er breytt i int
                                                            // við smiðun hlutsins
    private String[] departureLocations = {"kef", "rkv"};
    private String[] arrivalLocations = {"london", "copenhagen", "paris", "stockholm", "oslo"};
    // setjum timanna svona upp til að stytta linunar
        private LocalDateTime time1 = Utilities.convertStringToLocalDateTime("2018-04-04 10:00:00");
        private LocalDateTime time2 = Utilities.convertStringToLocalDateTime("2018-05-04 11:30:00");
        private LocalDateTime time3 = Utilities.convertStringToLocalDateTime("2018-05-06 16:30:00");
        private LocalDateTime time4 = Utilities.convertStringToLocalDateTime("2018-05-06 16:40:00");
        private LocalDateTime time5 = Utilities.convertStringToLocalDateTime("2018-06-07 12:30:00");
    private LocalDateTime[] departureDates = {time1, time2, time3, time4, time5};
    private LocalDateTime[] arrivalDates = {time1, time2, time3, time4, time5};
    private Double[] ratings = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
    private Boolean[] perks = {true, false}; // þetta heldur utan um mat, grænmetisfæði og skemmtanir
    //--------------------------------------------------------------------------------
    //endregion

    @Before
    public void setUp() {
        sc = new SearchController();
        fjoldiFluga = new Random().nextInt(20) +10; // lengd listans okkar er handahofskennd

        flights = new ArrayList<Flight>();

        for(int i=0; i<fjoldiFluga; i++){
            // setjum oll flugin handahofskennt upp
            // ut fra mogulegu breytum fylkjanna að ofan
            indices[0] = new Random().nextInt(flightNumbers.length);
            indices[1] = new Random().nextInt(airlines.length);
            indices[2] = new Random().nextInt(airplanes.length);
            indices[3] = new Random().nextInt(prices.length);
            indices[4] = new Random().nextInt(totalSeats.length);
            indices[5] = new Random().nextInt(reservedSeats.length);
            indices[6] = new Random().nextInt(departureLocations.length);
            indices[7] = new Random().nextInt(arrivalLocations.length);
            indices[8] = new Random().nextInt(departureDates.length);
            indices[9] = new Random().nextInt(arrivalDates.length);
            indices[10] = new Random().nextInt(ratings.length);
            indices[11] = new Random().nextInt(perks.length);
            indices[12] = new Random().nextInt(perks.length);
            indices[13] = new Random().nextInt(perks.length);
            // smiðurinn breytir einhverjum stökum fylkjanna til að smiða hlutinn rett
            Flight flight = new Flight(flightNumbers[indices[0]], airlines[indices[1]], airplanes[indices[2]],
                    prices[indices[3]], 2*prices[indices[3]], (int) Math.floor(0.2*totalSeats[indices[4]]),
                    (int) Math.floor(totalSeats[indices[4]]), (int) Math.floor(0.2*reservedSeats[indices[5]]*totalSeats[indices[4]]),
                    (int) Math.floor(reservedSeats[indices[5]]*totalSeats[indices[4]]), departureLocations[indices[6]],
                    arrivalLocations[indices[7]], departureDates[indices[8]], arrivalDates[indices[9]],
                    ratings[indices[10]], perks[indices[11]], perks[indices[12]], perks[indices[13]]);

            flights.add(flight);
        }
    }

    //nullstillum hlutina i lokin
    @After
    public void tearDown() {
        sc = null;
        fjoldiFluga = 0;
        flights.clear();
    }

    // öll profin eru i aðalatriðum eins.
    // Fyrst segjum við SearchController klasanum að raða eftir einhverju sviði
    // svo athugum við hvort rett hafi verið raðað með einfaldri for-lykkju
    // og assert-um svo að svo hafi verið.
    // Auk þess prentum við röðunina i lokin ef við skyldum ekki treysta for lykkjunni.
    @Test
    public void testOrderByPriceCoach() {

        sc.orderByPriceCoach(flights);
        boolean correctSort =true;
        System.out.println("Price Coach");
        System.out.println(flights.get(0).getPriceCoach());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getPriceCoach());
            if(flights.get(i).getPriceCoach()>flights.get(i+1).getPriceCoach()){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByPriceFirstClass() {

        sc.orderByPriceFirstClass(flights);
        boolean correctSort =true;
        System.out.println("Price first class");
        System.out.println(flights.get(0).getPriceFirstClass());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getPriceFirstClass());
            if(flights.get(i).getPriceFirstClass() > flights.get(i+1).getPriceFirstClass()){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByFlightNumber() {

        sc.orderByFlightNumber(flights);
        boolean correctSort =true;
        System.out.println("Flight number");
        System.out.println(flights.get(0).getFlightNumber());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getFlightNumber());
            if(flights.get(i).getFlightNumber().compareTo(flights.get(i+1).getFlightNumber())>0){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByAirline() {

        sc.orderByAirline(flights);
        boolean correctSort =true;
        System.out.println("Airline");
        System.out.println(flights.get(0).getAirline());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getAirline());
            if(flights.get(i).getAirline().compareTo(flights.get(i+1).getAirline())>0){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByAirplaneType() {

        sc.orderByAirplaneType(flights);
        boolean correctSort =true;
        System.out.println("Airplane");
        System.out.println(flights.get(0).getAirplaneType());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getAirplaneType());
            if(flights.get(i).getAirplaneType().compareTo(flights.get(i+1).getAirplaneType())>0){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByTotalSeatsFirstClass() {

        sc.orderByTotalSeatsFirstClass(flights);
        boolean correctSort =true;
        System.out.println("Total seats first class");
        System.out.println(flights.get(0).getTotalSeatsFirstClass());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getTotalSeatsFirstClass());
            if(flights.get(i).getTotalSeatsFirstClass() > flights.get(i+1).getTotalSeatsFirstClass() ){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByTotalSeatsCouch() {
        //System.out.println(flights.get(0).getTotalSeatsCoach());
        sc.orderByTotalSeatsCoach(flights);
        boolean correctSort =true;
        System.out.println("Total seats coach");
        System.out.println(flights.get(0).getTotalSeatsCoach());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getTotalSeatsCoach());
            if(flights.get(i).getTotalSeatsCoach() > flights.get(i+1).getTotalSeatsCoach() ){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByReservedSeatsCouch() {

        sc.orderByReservedSeatsCoach(flights);
        boolean correctSort =true;
        System.out.println("Reserved seats coach");
        System.out.println(flights.get(0).getReservedSeatsCoach());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getReservedSeatsCoach());
            if(flights.get(i).getReservedSeatsCoach() > flights.get(i+1).getReservedSeatsCoach() ){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByReservedSeatsFirstClass() {

        sc.orderByReservedSeatsFirstClass(flights);
        boolean correctSort =true;
        System.out.println("Reserved seats first class");
        System.out.println(flights.get(0).getReservedSeatsFirstClass());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getReservedSeatsFirstClass());
            if(flights.get(i).getReservedSeatsFirstClass() > flights.get(i+1).getReservedSeatsFirstClass() ){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByDepartureLocation() {

        sc.orderByDepartureLocation(flights);
        boolean correctSort =true;
        System.out.println("Departure location");
        System.out.println(flights.get(0).getDepartureLocation());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getDepartureLocation());
            if(flights.get(i).getDepartureLocation().compareTo(flights.get(i+1).getDepartureLocation())>0){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByArrivalLocation() {

        sc.orderByArrivalLocation(flights);
        boolean correctSort =true;
        System.out.println("Arrival location");
        System.out.println(flights.get(0).getArrivalLocation());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getArrivalLocation());
            if(flights.get(i).getArrivalLocation().compareTo(flights.get(i+1).getArrivalLocation())>0){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByDepartureTime() {

        sc.orderByDepartureTime(flights);
        boolean correctSort =true;
        System.out.println("Departure time");
        System.out.println(flights.get(0).getDepartureTime());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getDepartureTime());
            if(flights.get(i).getDepartureTime().compareTo(flights.get(i+1).getDepartureTime())>0){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByArrivalTime() {

        sc.orderByArrivalTime(flights);
        boolean correctSort =true;
        System.out.println("Arrival time");
        System.out.println(flights.get(0).getArrivalTime());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getArrivalTime());
            if(flights.get(i).getArrivalTime().compareTo(flights.get(i+1).getArrivalTime())>0){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByAverageRating() {

        sc.orderByAverageRating(flights);
        boolean correctSort =true;
        System.out.println("Average rating");
        System.out.println(flights.get(0).getAverageRating());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).getAverageRating());
            if(flights.get(i).getAverageRating()>flights.get(i+1).getAverageRating()){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByHasMeal() {

        sc.orderByHasMeal(flights);
        boolean correctSort =true;
        System.out.println("Has meal");
        System.out.println(flights.get(0).hasMeal());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).hasMeal());
            if(flights.get(i).hasMeal().compareTo(flights.get(i+1).hasMeal())>0){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByHasVegetarianMeal() {

        sc.orderByHasVegeterianMeal(flights);
        boolean correctSort =true;
        System.out.println("Has veggie meal");
        System.out.println(flights.get(0).hasVegeterianMeal());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).hasVegeterianMeal());
            if(flights.get(i).hasVegeterianMeal().compareTo(flights.get(i+1).hasVegeterianMeal())>0){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }

    @Test
    public void testOrderByHasEntertainment() {

        sc.orderByHasEntertainment(flights);
        boolean correctSort =true;
        System.out.println("Has entertainment");
        System.out.println(flights.get(0).hasEntertainment());
        for(int i=0; i<fjoldiFluga-1; i++){
            System.out.println(flights.get(i+1).hasEntertainment());
            if(flights.get(i).hasEntertainment().compareTo(flights.get(i+1).hasEntertainment())>0){
                correctSort=false;
            }
        }
        System.out.println();
        assert(correctSort);
    }
}
