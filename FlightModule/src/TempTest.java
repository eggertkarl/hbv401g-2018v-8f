import java.io.File;
import java.util.List;

/**
 * This class should only be used to test before the interface has
 * been fully implemented.
 */
public class TempTest
{
    public static void main(String[] args)
    {
        DatabaseController db = new DatabaseController();
        List<Seat> x = db.executeQuery("SELECT * FROM FlightSeats;", Seat.class);
        for(int i = 0; i < x.size(); i++) {
            System.out.println(x.get(i).getRow());
        }
    }
}



//private Initializer<Seat> constructor = row ->
//    new Seat((int) row.get("Row"), (String) row.get("Column"), (boolean) row.get("isAvailable"), (boolean) row.get("isFirstClass"));
