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
        DatabaseController db = new DatabaseController();

        selectAllSeats(db);
        createTwoUsers(db);
        selectAllUsers(db);
        deleteAllUsers(db);


        // SearchController sort of started.
        SearchController sc = new SearchController();

        ArrayList<Flight> flights = sc.searchForAllFlightsFilterByAirline("Icelandair");

        System .out.println("Flights found: ");
        for(int i = 0; i < flights.size(); i++) {
            System.out.println(flights.get(i).getFlightNumber());
        }
    }

    public static void selectAllSeats(DatabaseController db) {
        String query = "SELECT * FROM FlightSeats WHERE Row < ? AND Column = ?;";
        ArrayList<Object> params = new ArrayList<>();
        params.add(4);
        params.add("A");

        // Note: The SearchController.seatInitializer will be private at a later point and will only be used in
        // the SearchController. It has been made public to provide a simpler example of how to use the
        // DatabaseController.

        List<Seat> x = db.executeQuery(query, params, SearchController.seatInitializer);
        for(int i = 0; i < x.size(); i++) {
            x.get(i).print();
        }
    }

    public static void createTwoUsers(DatabaseController db) {
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
    }

    public static void selectAllUsers(DatabaseController db) {
        ArrayList<User> users = db.executeQuery("SELECT * FROM Users", SearchController.userInitializer);
        for(int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).getName());
        }
    }

    public static void deleteAllUsers(DatabaseController db) {
        db.execute("DELETE FROM Users;");
    }
}

