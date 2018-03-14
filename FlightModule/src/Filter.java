import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

class Filter extends ColumnNames{

    private HashMap<String, Utilities.Tuple> map = null;

    Filter() {
        map = new HashMap<>();
    }

    private void set(String key, Object valueLower, Object valueUpper) {
        Utilities.Tuple tmp = map.get(key);
        if(tmp == null) {
            tmp = new Utilities.Tuple();
        }
        tmp.valueLower = valueLower;
        tmp.valueUpper = valueUpper;
        map.put(key, tmp);

        if(tmp.valueLower == null && tmp.valueUpper == null) {
            map.remove(key);
        }
    }

    void setEqualTo(String key, Object value) {
        set(key, value, value);
    }

    void setLowerThanOrEqualTo(String key, Object value) {
        set(key, null, value);
    }

    void setGreaterThanOrEqualTo(String key, Object value) {
        set(key, value, null);
    }

    void setInterval(String key, Object valueLower, Object valueUpper) {
        set(key, valueLower, valueUpper);
    }

    void remove(String key) {
        map.remove(key);
    }


    public Utilities.Tuple<ArrayList<String>, ArrayList<Object>> getFiltersAndParameters() {
        ArrayList<String> filters = new ArrayList<>();
        ArrayList<Object> parameters = new ArrayList<>();;

        ArrayList<String> keys = new ArrayList<>(this.map.keySet());

        for (String key : keys) {
            Utilities.Tuple tmp = this.map.get(key);
            if (tmp != null) {
                Object lower = tmp.valueLower;
                Object upper = tmp.valueUpper;
                // We know at least one of them is not null.
                if (lower == null) {
                    filters.add(key + " <= ?");
                    parameters.add(upper);
                } else if (upper == null) {
                    filters.add(key + " >= ?");
                    parameters.add(lower);
                } else if (lower == upper) {
                    filters.add(key + " = ?");
                    parameters.add(lower);
                } else {
                    // Interval case
                    filters.add(key + " <= ?");
                    parameters.add(upper);

                    filters.add(key + " >= ?");
                    parameters.add(lower);
                }
            }
        }

        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> obj = new Utilities.Tuple<>();
        obj.valueLower = filters;
        obj.valueUpper = parameters;
        return obj;
    }
}
