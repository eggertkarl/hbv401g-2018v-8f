import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Filter extends ColumnNames{

    private HashMap<String, Utilities.Tuple> map = null;



    protected Filter() {
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

    protected void setEqual(String key, Object value) {
        set(key, value, value);
    }

    protected void setLowerThanOrEqual(String key, Object value) {
        set(key, null, value);
    }

    protected void setGreaterThanOrEqual(String key, Object value) {
        set(key, value, null);
    }

    protected void setInterval(String key, Object valueLower, Object valueUpper) {
        set(key, valueLower, valueUpper);
    }

    protected void remove(String key) {
        map.remove(key);
    }


    public Utilities.Tuple<ArrayList<String>, ArrayList<Object>> getFiltersAndParameters() {
        ArrayList<String> filters = new ArrayList<>();
        ArrayList<Object> parameters = new ArrayList<>();;

        ArrayList<String> keys = new ArrayList<>(this.map.keySet());

        for(int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Utilities.Tuple tmp = this.map.get(key);
            if(tmp != null) {
                Object lower = tmp.valueLower;
                Object upper = tmp.valueUpper;
                // We know at least one of them is not null.
                if(lower == null) {
                    filters.add(key + " <= ?");
                    parameters.add(upper);
                }
                else if(upper == null) {
                    filters.add(key + " >= ?");
                    parameters.add(lower);
                }
                else if(lower == upper){
                    filters.add(key + " = ?");
                    parameters.add(lower);
                }
                else {
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


    public static class Flight extends Filter{

        //region Set functions
        //--------------------------------------------------------------------------------
        public void setFlightNumberEqual(String flightNumber) {
            setEqual(FlightColumns.flightNumber, flightNumber);
        }

        public void setAirlineEqual(String airline) {
            setEqual(FlightColumns.airline, airline);
        }

        public void setAirplaneTypeEqual(String airplaneType) {
            setEqual(FlightColumns.airplaneType, airplaneType);
        }

        public void setDepartureLocationEqual(String departureLocation) {
            setEqual(FlightColumns.departureLocation, departureLocation);
        }

        public void setArrivalLocationEqual(String arrivalLocation) {
            setEqual(FlightColumns.arrivalLocation, arrivalLocation);
        }

        public void setHasMeal(Boolean hasMeal) {
            setEqual(FlightColumns.hasMeal, hasMeal);
        }

        public void setHasVegeterianMeal(Boolean hasVegeterianMeal) {
            setEqual(FlightColumns.hasVegeterianMeal, hasVegeterianMeal);
        }

        public void setHasEntertainment(Boolean hasEntertainment) {
            setEqual(FlightColumns.hasEntertainment, hasEntertainment);
        }


        // Departure time functions:
        public void setDepartureTimeEqual(LocalDateTime departureTime) {
            setEqual(FlightColumns.departureTime, Utilities.convertLocalDateTimeToString(departureTime));
        }

        public void setDepartureTimeLowerThanOrEqual(LocalDateTime departureTime) {
            setLowerThanOrEqual(FlightColumns.departureTime, Utilities.convertLocalDateTimeToString(departureTime));
        }

        public void setDepartureTimeGreaterThanOrEqual(LocalDateTime departureTime) {
            setGreaterThanOrEqual(FlightColumns.departureTime, Utilities.convertLocalDateTimeToString(departureTime));
        }

        public void setDepartureTimeInterval(LocalDateTime departureTimeLower, LocalDateTime departureTimeUpper) {
            if(departureTimeLower != null && departureTimeUpper != null) {
                // If both have set times, we want to check if the user swapped the upper/lower bounds.
                if(departureTimeUpper.isBefore(departureTimeLower)) {
                    setInterval(FlightColumns.departureTime,
                            Utilities.convertLocalDateTimeToString(departureTimeUpper),
                            Utilities.convertLocalDateTimeToString(departureTimeLower));
                    return;
                }
            }
            setInterval(FlightColumns.departureTime,
                    Utilities.convertLocalDateTimeToString(departureTimeLower),
                    Utilities.convertLocalDateTimeToString(departureTimeUpper));
        }


        // Arrival time functions:
        public void setArrivalTimeEqual(LocalDateTime arrivalTime) {
            setEqual(FlightColumns.arrivalTime, Utilities.convertLocalDateTimeToString(arrivalTime));
        }

        public void setArrivalTimeLowerThanOrEqual(LocalDateTime arrivalTime) {
            setLowerThanOrEqual(FlightColumns.arrivalTime, Utilities.convertLocalDateTimeToString(arrivalTime));
        }

        public void setArrivalTimeGreaterThanOrEqual(LocalDateTime arrivalTime) {
            setGreaterThanOrEqual(FlightColumns.arrivalTime, Utilities.convertLocalDateTimeToString(arrivalTime));
        }

        public void setArrivalTimeInterval(LocalDateTime arrivalTimeLower, LocalDateTime arrivalTimeUpper) {
            if(arrivalTimeLower != null && arrivalTimeUpper != null) {
                // If both have set times, we want to check if the user swapped the upper/lower bounds.
                if(arrivalTimeUpper.isBefore(arrivalTimeLower)) {
                    setInterval(FlightColumns.arrivalTime,
                            Utilities.convertLocalDateTimeToString(arrivalTimeUpper),
                            Utilities.convertLocalDateTimeToString(arrivalTimeLower));
                    return;
                }
            }
            setInterval(FlightColumns.arrivalTime,
                    Utilities.convertLocalDateTimeToString(arrivalTimeLower),
                    Utilities.convertLocalDateTimeToString(arrivalTimeUpper));
        }


        // Price coach functions:
        public void setPriceCoachEqual(Integer priceCoach) {
            setEqual(FlightColumns.priceCoach, priceCoach);
        }

        public void setPriceCoachLowerThanOrEqual(Integer priceCoach) {
            setLowerThanOrEqual(FlightColumns.priceCoach, priceCoach);
        }

        public void setPriceCoachGreaterThanOrEqual(Integer priceCoach) {
            setGreaterThanOrEqual(FlightColumns.priceCoach, priceCoach);
        }

        public void setPriceCoachInterval(Integer priceCoachLower, Integer priceCoachUpper) {
            if(priceCoachLower != null && priceCoachUpper != null) {
                // If both have set times, we want to check if the user swapped the upper/lower bounds.
                if(priceCoachLower > priceCoachUpper) {
                    setInterval(FlightColumns.priceCoach, priceCoachUpper, priceCoachLower);
                    return;
                }
            }
            setInterval(FlightColumns.priceCoach, priceCoachLower, priceCoachUpper);
        }


        // Price first class functions:
        public void setPriceFirstClassEqual(Integer priceFirstClass) {
            setEqual(FlightColumns.priceFirstClass, priceFirstClass);
        }

        public void setPriceFirstClassLowerThanOrEqual(Integer priceFirstClass) {
            setLowerThanOrEqual(FlightColumns.priceFirstClass, priceFirstClass);
        }

        public void setPriceFirstClassGreaterThanOrEqual(Integer priceFirstClass) {
            setGreaterThanOrEqual(FlightColumns.priceFirstClass, priceFirstClass);
        }

        public void setPriceFirstClassInterval(Integer priceFirstClassLower, Integer priceFirstClassUpper) {
            if(priceFirstClassLower != null && priceFirstClassUpper != null) {
                // If both have set times, we want to check if the user swapped the upper/lower bounds.
                if(priceFirstClassLower > priceFirstClassUpper) {
                    setInterval(FlightColumns.priceFirstClass, priceFirstClassUpper, priceFirstClassLower);
                    return;
                }
            }
            setInterval(FlightColumns.priceFirstClass, priceFirstClassLower, priceFirstClassUpper);
        }
        //--------------------------------------------------------------------------------
        //endregion


        //region Remove functions
        //--------------------------------------------------------------------------------
        public void removeFlightNumber() {
            remove(FlightColumns.flightNumber);
        }

        public void removeAirline() {
            remove(FlightColumns.airline);
        }

        public void removeAirplaneType() {
            remove(FlightColumns.airplaneType);
        }

        public void removeDepartureLocation() {
            remove(FlightColumns.departureLocation);
        }

        public void removeArrivalLocation() {
            remove(FlightColumns.arrivalLocation);
        }

        public void removeHasMeal() {
            remove(FlightColumns.hasMeal);
        }

        public void removeHasVegeterianMeal() {
            remove(FlightColumns.hasVegeterianMeal);
        }

        public void removeHasEntertainment() {
            remove(FlightColumns.hasEntertainment);
        }

        public void removeDepartureTime() {
            remove(FlightColumns.departureTime);
        }

        public void removeArrivalTime() {
            remove(FlightColumns.arrivalTime);
        }

        public void removePriceCoach() {
            remove(FlightColumns.priceCoach);
        }

        public void removePriceFirstClass() {
            remove(FlightColumns.priceFirstClass);
        }
        //--------------------------------------------------------------------------------
        //endregion
    }
}
