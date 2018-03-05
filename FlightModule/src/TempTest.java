import java.io.File;

/**
 * This class should only be used to test before the interface has
 * been fully implemented.
 */
public class TempTest
{
    public static void main(String[] args)
    {
        DatabaseController db = new DatabaseController();
        db.executeQuery("SELECT * FROM AirplaneTypes;");
    }
}
