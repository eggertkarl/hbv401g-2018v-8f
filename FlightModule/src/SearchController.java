public class SearchController {
    // TODO: Implement

    public static final Initializer<Seat> seatInitializer = (map -> {
        int row = (int) map.get("Row");
        String column = (String) map.get("Column");
        boolean isAvailable = true;
        // TODO: Add isAvailable to query
        //boolean isAvailable = (boolean) map.get("isAvailable");
        boolean isFirstClass = (int) map.get("IsFirstClass") == 1;

        return new Seat(row, column, isAvailable, isFirstClass);
    });

    public static final Initializer<User> userInitializer = (map -> {
        String name = (String) map.get("Name");
        boolean isMinor = (int) map.get("IsMinor") == 1;
        String passportNumber = (String) map.get("PassportNumber");

        return new User(name, isMinor, passportNumber);
    });
}
