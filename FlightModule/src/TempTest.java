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

        String insertQuery = "INSERT INTO Users (Name, IsMinor, PassportNumber) VALUES (?, ?, ?)";
        ArrayList<Object> insertParams = new ArrayList<>();
        insertParams.add("Ned Stark");
        insertParams.add(false);
        insertParams.add("12345");

        db.execute(insertQuery, insertParams);

        insertParams = new ArrayList<>();
        insertParams.add("Arya Stark");
        insertParams.add(true);
        insertParams.add("54321");

        db.execute(insertQuery, insertParams);

        List<User> users = db.executeQuery("SELECT * FROM Users", SearchController.userInitializer);
        for(int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).getName());
        }
        db.execute("DELETE FROM Users;");
    }
}

