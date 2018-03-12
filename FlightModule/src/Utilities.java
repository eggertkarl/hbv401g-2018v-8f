import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Utilities {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static LocalDateTime convertStringToLocalDateTime(String dateText) {
        if(dateText == null) {
            return null;
        }
        if(dateText.trim().length() == 0) {
            return null;
        }
        return LocalDateTime.parse(dateText, dateFormatter);
    }

    static String convertLocalDateTimeToString(LocalDateTime date) {
        if(date == null) {
            return null;
        }
        return date.format(dateFormatter);
    }



    static class Tuple<L, U> {
        public L valueLower = null;
        public U valueUpper = null;
    }
}
