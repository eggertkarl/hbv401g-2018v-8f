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
        String query = "SELECT * FROM FlightSeats WHERE Row < ? AND Column = ?;";
        ArrayList<Object> params = new ArrayList<>();
        params.add(4);
        params.add("A");


        DatabaseController db = new DatabaseController();
        List<Seat> x = db.executeQuery(query, params, SearchController.seatInitializer);
        for(int i = 0; i < x.size(); i++) {
            x.get(i).print();
        }
    }
}



//private Initializer<Seat> constructor = row ->
//    new Seat((int) row.get("Row"), (String) row.get("Column"), (boolean) row.get("isAvailable"), (boolean) row.get("isFirstClass"));
