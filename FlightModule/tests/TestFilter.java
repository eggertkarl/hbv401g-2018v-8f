import org.junit.*;

import java.util.ArrayList;

public class TestFilter {
    // TODO: ?

    Filter filter = null;

    @Before
    public void setUp() {
        filter = new Filter();
    }

    @After
    public void tearDown() {
        filter = null;
    }

    @Test
    public void testFilterEqualsAddsKey() {
        String keyName = "A";
        Object value = 345;
        filter.setEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<String> filters = filtersAndParams.valueLower;

        assert(filters.size() == 1 & filters.contains(keyName));
    }

    @Test
    public void testFilterEqualsAddsValue() {
        String keyName = "A";
        Object value = 345;
        filter.setEqualTo(keyName, value);
        Utilities.Tuple<ArrayList<String>, ArrayList<Object>> filtersAndParams = filter.getFiltersAndParameters();
        ArrayList<Object> params = filtersAndParams.valueUpper;

        assert(params.size() == 1 & params.contains(value));
    }


    // Remove virkar?
    // Reset virkar?
    // Null gildi ekki bætt við

}
