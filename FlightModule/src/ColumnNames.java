
public class ColumnNames {

    protected static class FlightColumns {
        static final String flightNumber = "FlightNumber";
        static final String airline = "Airline";
        static final String airplaneType = "AirplaneType";
        static final String departureLocation = "DepartureLocation";
        static final String arrivalLocation = "ArrivalLocation";
        static final String departureTime = "DepartureTime";
        static final String arrivalTime = "ArrivalTime";
        static final String priceCoach = "PriceCoach";
        static final String priceFirstClass = "PriceFirstClass";
        static final String hasMeal = "HasMeal";
        static final String hasVegeterianMeal = "HasVegeterianMeal";
        static final String hasEntertainment = "HasEntertainment";

        static final String totalSeatsFirstClass = "TotalSeatsFirstClass";
        static final String totalSeatsCoach = "TotalSeatsCoach";
        static final String reservedSeatsFirstClass = "ReservedSeatsFirstClass";
        static final String reservedSeatsCoach = "ReservedSeatsCoach";
        static final String averageRating = "AverageRating";
    }

    protected static class SeatColumns {
        static final String row = "Row";
        static final String column = "Column";
        static final String isFirstClass = "IsFirstClass";
        static final String isAvailable = "IsAvailable";
    }

    protected static class UserColumns {
        static final String name = "Name";
        static final String isMinor = "IsMinor";
        static final String passportNumber = "PassportNumber";
    }

    protected static class ReservationColumns {
        static final String flightNumber = "FlightNumber";
        static final String departureTime = "DepartureTime";
        static final String name = "Name";
        static final String passportNumber = "PassportNumber";
        static final String row = "SeatRow";
        static final String column = "SeatColumn";
        static final String bags = "Bags";
        static final String hasVegeterianMeal = "HasVegeterianMeal";
    }

    protected static class ReviewColumns {
        static final String flightNumber = "FlightNumber";
        static final String departureTime = "DepartureTime";
        static final String name = "Name";
        static final String passportNumber = "PassportNumber";
        static final String rating = "Rating";
        static final String comment = "Comment";
    }
}
