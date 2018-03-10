import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

abstract class Initializer<T>{
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private HashMap<String, Object> map = null;

    // The create function passes values from a HashMap to the constructor for class T.
    abstract T create(HashMap<String, Object> map);

    protected void set(HashMap<String, Object> map) {
        this.map = map;
    }

    protected String getString(String key) {
        Object value = this.map.get(key);
        if(value == null) {
            return null;
        }
        return (String) value;
    }

    protected Integer getInt(String key) {
        Object value = this.map.get(key);
        if(value == null) {
            return null;
        }
        return (int) value;
    }

    protected Boolean getBoolean(String key) {
        Object value = this.map.get(key);
        if(value == null) {
            return null;
        }
        return (int) value == 1;
    }

    protected Double getDouble(String key) {
        Object value = this.map.get(key);
        if(value == null) {
            return null;
        }
        return (double) value;
    }

    protected LocalDateTime getDateTime(String key) {
        Object value = this.map.get(key);
        if(value == null) {
            return null;
        }
        String text = (String) value;
        return LocalDateTime.parse(text, dateFormatter);
    }
}

