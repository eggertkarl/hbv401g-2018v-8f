import org.junit.*;

import java.util.ArrayList;

public class TestFilter {

    Filter filter = null;

    @Before
    public void setUp() {
        filter = new Filter();
    }

    @After
    public void tearDown() {
        filter = null;
    }

    // Næstu (nokkuð mörg) test erum við í raun að tjekka hvort
    // getFiltersAndParameters virki rétt, ekki setEqualTo o.s.f. því það eru bara
    // meira eða minna einfaldir setters (og því leyfum við þeim að vera með), en
    // við viljum athuga hvaða samblanda af key/value er bætt við filter objectinu
    // og lestur svo úr honum gegnum samspil getFiltersAndParameters og set-föllin úr klasanum Filter

    @Test
    public void testFilterEqualsToAddKey() {
        String keyName = "A";
        Object value = 345;
        filter.setEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;

        //System.out.println(keyName);
        //System.out.println(value);
        //System.out.println(filters);
        //System.out.println(params);
        assert(filters.size() == 1 & filters.contains(keyName + " = ?"));
    }

    @Test
    public void testFilterEqualsToAddValue() {
        String keyName = "A";
        Object value = 345;
        filter.setEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(params.size() == 1 & params.contains(value));
    }

    @Test
    public void testFilterEqualsToAddPair() {
        String keyName = "A";
        Object value = 345;
        filter.setEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(filters.size() == 1 & filters.contains(keyName + " = ?"));
        assert(params.size() == 1 & params.contains(value));
    }

    @Test
    public void testFilterLowerThanOrEqualToAddKey() {
        String keyName = "A";
        Object value = 345;
        filter.setLowerThanOrEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;

        assert(filters.size() == 1 & filters.contains(keyName + " <= ?"));
    }

    @Test
    public void testFilterLowerThanOrEqualToAddValue() {
        String keyName = "A";
        Object value = 345;
        filter.setLowerThanOrEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(params.size() == 1 & params.contains(value));
    }

    @Test
    public void testFilterLowerThanOrEqualToAddPair() {
        String keyName = "A";
        Object value = 345;
        filter.setLowerThanOrEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(filters.size() == 1 & filters.contains(keyName + " <= ?"));
        assert(params.size() == 1 & params.contains(value));
    }

    @Test
    public void testFilterGreaterThanOrEqualToAddKey() {
        String keyName = "A";
        Object value = 345;
        filter.setGreaterThanOrEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;

        assert(filters.size() == 1 & filters.contains(keyName + " >= ?"));
    }

    @Test
    public void testFilterGreaterThanOrEqualToAddValue() {
        String keyName = "A";
        Object value = 345;
        filter.setGreaterThanOrEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(params.size() == 1 & params.contains(value));
    }

    @Test
    public void testFilterGreaterThanOrEqualToAddPair() {
        String keyName = "A";
        Object value = 345;
        filter.setGreaterThanOrEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(filters.size() == 1 & filters.contains(keyName + " >= ?"));
        assert(params.size() == 1 & params.contains(value));
    }

    @Test
    public void testFilterIntervalAddKey() {
        String keyName = "A";
        Object valueLower = 345;
        Object valueUpper = 666;
        filter.setInterval(keyName, valueLower, valueUpper);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;

        assert(filters.size() == 2 & filters.contains(keyName + " <= ?") & filters.contains(keyName + " >= ?"));
    }

    @Test
    public void testFilterIntervalAddValueLower() {
        String keyName = "A";
        Object valueLower = 345;
        Object valueUpper = null;
        filter.setInterval(keyName, valueLower, valueUpper);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(params.size() == 1 & params.contains(valueLower) & !params.contains(valueUpper));
    }

    @Test
    public void testFilterIntervalAddValueUpper() {
        String keyName = "A";
        Object valueLower = null;
        Object valueUpper = 666;
        filter.setInterval(keyName, valueLower, valueUpper);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(params.size() == 1 & !params.contains(valueLower) & params.contains(valueUpper));
    }

    @Test
    public void testFilterIntervalAddTriple() {
        String keyName = "A";
        Object valueLower = 345;
        Object valueUpper = 666;
        filter.setInterval(keyName, valueLower, valueUpper);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(filters.size() == 2 & filters.contains(keyName + " <= ?") & filters.contains(keyName + " >= ?"));
        assert(params.size() == 2 & params.contains(valueLower) & params.contains(valueUpper));
    }

    // Testing adding multiple pairs to the hashmap.
    // Note that testing whether the pairs are still coupled correctly is indirectly done by removing
    // a single (key,value) pair, and checking the results, in a later test.
    @Test
    public void testFilterAddTwoPairs() {
        String keyName1 = "A";
        Object value1 = 345;
        filter.setEqualTo(keyName1, value1);
        String keyName2 = "B";
        Object value2 = 666;
        filter.setEqualTo(keyName2, value2);

        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert (filters.size() == 2 & filters.contains(keyName1 + " = ?") & filters.contains(keyName2 + " = ?"));
        assert (params.size() == 2 & params.contains(value1) & params.contains(value2));
    }
    
    // Testing overwriting the value if a duplicate key is added.
    // We already tested all of the set functionality above, so we only do this one case.
    @Test
    public void testFilterOverwriteValue() {
        String keyName = "A";
        Object value1 = 345;
        filter.setEqualTo(keyName, value1);
        Object value2 = 666;
        filter.setEqualTo(keyName, value2);

        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(filters.size() == 1 & filters.contains(keyName + " = ?"));
        assert(params.size() == 1 & params.contains(value2));
    }

    // Testing removing a (key, value)-tuple.
    // We already tested all of the set functionality above, so we only do this one case.
    @Test
    public void testFilterRemoveOneKeyValueTuple() {
        String keyName1 = "A";
        Object value1 = 345;
        filter.setEqualTo(keyName1, value1);
        String keyName2 = "B";
        Object value2 = 666;
        filter.setEqualTo(keyName2, value2);

        // Only (key2, value2)-tuple should remain
        filter.remove("A");

        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(filters.size() == 1 & filters.contains(keyName2 + " = ?"));
        assert(params.size() == 1 & params.contains(value2));
    }

    // Testing resetting the filter object. (Same effect as constructor, but without new memory)
    // We already tested all of the set functionality above, so we only do this one case.
    @Test
    public void testFilterClearAll() {
        String keyName1 = "A";
        Object value1 = 345;
        filter.setEqualTo(keyName1, value1);
        String keyName2 = "B";
        Object value2 = 666;
        filter.setEqualTo(keyName2, value2);

        // Clear the hashmap
        filter.clearAll();

        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(filters.isEmpty());
        assert(params.isEmpty());
    }

    // Tjekk fyrir ýmis null tillfelli út restin af skjalinu
    // Null gildis tjekk fyrir key, á að skila tóma lista
    @Test
    public void testFilterEqualsAddKeyNull() {
        String keyName = null;
        Object value = 345;
        filter.setEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;

        assert(filters.isEmpty());
    }

    // Null gildis tjekk fyrir value, á að skila tóma lista
    @Test
    public void testFilterEqualsAddValueNull() {
        String keyName = "A";
        Object value = null;
        filter.setEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(params.isEmpty());
    }

    // Null gildis tjekk fyrir bæði key og value, á að skila tóma lista
    @Test
    public void testFilterEqualsAddNullPair() {
        String keyName = null;
        Object value = null;
        filter.setEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(filters.isEmpty());
        assert(params.isEmpty());
    }

    // Null gildis tjekk fyrir key, á að skila tóma lista
    @Test
    public void testFilterLowerThanOrEqualToAddKeyNull() {
        String keyName = null;
        Object value = 345;
        filter.setLowerThanOrEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;

        assert(filters.isEmpty());
    }

    // Null gildis tjekk fyrir value, á að skila tóma lista
    @Test
    public void testFilterLowerThanOrEqualToAddValueNull() {
        String keyName = "A";
        Object value = null;
        filter.setLowerThanOrEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(params.isEmpty());
    }

    // Null gildis tjekk fyrir bæði key og value, á að skila tóma lista
    @Test
    public void testFilterLowerThanOrEqualToAddNullPair() {
        String keyName = null;
        Object value = null;
        filter.setLowerThanOrEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(filters.isEmpty());
        assert(params.isEmpty());
    }

    // Null gildis tjekk fyrir key, á að skila tóma lista
    @Test
    public void testFilterGreaterThanOrEqualToAddKeyNull() {
        String keyName = null;
        Object value = 345;
        filter.setGreaterThanOrEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;

        assert(filters.isEmpty());
    }

    // Null gildis tjekk fyrir value, á að skila tóma lista
    @Test
    public void testFilterGreaterThanOrEqualToAddValueNull() {
        String keyName = "A";
        Object value = null;
        filter.setGreaterThanOrEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(params.isEmpty());
    }

    // Null gildis tjekk fyrir bæði key og value, á að skila tóma lista
    @Test
    public void testFilterGreaterThanOrEqualToAddNullPair() {
        String keyName = null;
        Object value = null;
        filter.setGreaterThanOrEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(filters.isEmpty());
        assert(params.isEmpty());
    }

    // Null gildis tjekk fyrir key, á að skila tóma lista
    @Test
    public void testFilterIntervalAddKeyNull() {
        String keyName = null;
        Object valueLower = 345;
        Object valueUpper = 666;
        filter.setInterval(keyName, valueLower, valueUpper);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;

        assert(filters.isEmpty());
    }

    // Null gildis tjekk fyrir par af null
    @Test
    public void testFilterIntervalAddNullPair() {
        String keyName = "A";
        Object valueLower = null;
        Object valueUpper = null;
        filter.setInterval(keyName, valueLower, valueUpper);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(params.isEmpty());
    }

    // Null gildis tjekk fyrir par af null
    @Test
    public void testFilterIntervalAddNullTriple() {
        String keyName = null;
        Object valueLower = null;
        Object valueUpper = null;
        filter.setInterval(keyName, valueLower, valueUpper);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(filters.isEmpty());
        assert(params.isEmpty());
    }
}
