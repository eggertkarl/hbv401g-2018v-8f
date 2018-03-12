import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Filter extends ColumnNames{

    private HashMap<String, Utilities.Tuple> map = null;

    private Filter() {
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

    protected void setEqualTo(String key, Object value) {
        set(key, value, value);
    }

    protected void setLowerThanOrEqualTo(String key, Object value) {
        set(key, null, value);
    }

    protected void setGreaterThanOrEqualTo(String key, Object value) {
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


    public static class Flight extends Filter{

        //region Set functions
        //--------------------------------------------------------------------------------
        public void setFlightNumberEqualTo(String flightNumber) {
            setEqualTo(FlightColumns.flightNumber, flightNumber);
        }

        public void setAirlineEqualTo(String airline) {
            setEqualTo(FlightColumns.airline, airline);
        }

        public void setAirplaneTypeEqualTo(String airplaneType) {
            setEqualTo(FlightColumns.airplaneType, airplaneType);
        }

        public void setDepartureLocationEqualTo(String departureLocation) {
            setEqualTo(FlightColumns.departureLocation, departureLocation);
        }

        public void setArrivalLocationEqualTo(String arrivalLocation) {
            setEqualTo(FlightColumns.arrivalLocation, arrivalLocation);
        }

        public void setHasMealEqualTo(Boolean hasMeal) {
            setEqualTo(FlightColumns.hasMeal, hasMeal);
        }

        public void setHasVegeterianMealEqualTo(Boolean hasVegeterianMeal) {
            setEqualTo(FlightColumns.hasVegeterianMeal, hasVegeterianMeal);
        }

        public void setHasEntertainmentEqualTo(Boolean hasEntertainment) {
            setEqualTo(FlightColumns.hasEntertainment, hasEntertainment);
        }


        // Departure time functions:
        public void setDepartureTimeEqualTo(LocalDateTime departureTime) {
            setEqualTo(FlightColumns.departureTime, Utilities.convertLocalDateTimeToString(departureTime));
        }

        public void setDepartureTimeLowerThanOrEqualTo(LocalDateTime departureTime) {
            setLowerThanOrEqualTo(FlightColumns.departureTime, Utilities.convertLocalDateTimeToString(departureTime));
        }

        public void setDepartureTimeGreaterThanOrEqualTo(LocalDateTime departureTime) {
            setGreaterThanOrEqualTo(FlightColumns.departureTime, Utilities.convertLocalDateTimeToString(departureTime));
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
        public void setArrivalTimeEqualTo(LocalDateTime arrivalTime) {
            setEqualTo(FlightColumns.arrivalTime, Utilities.convertLocalDateTimeToString(arrivalTime));
        }

        public void setArrivalTimeLowerThanOrEqualTo(LocalDateTime arrivalTime) {
            setLowerThanOrEqualTo(FlightColumns.arrivalTime, Utilities.convertLocalDateTimeToString(arrivalTime));
        }

        public void setArrivalTimeGreaterThanOrEqualTo(LocalDateTime arrivalTime) {
            setGreaterThanOrEqualTo(FlightColumns.arrivalTime, Utilities.convertLocalDateTimeToString(arrivalTime));
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
        public void setPriceCoachEqualTo(Integer priceCoach) {
            setEqualTo(FlightColumns.priceCoach, priceCoach);
        }

        public void setPriceCoachLowerThanOrEqualTo(Integer priceCoach) {
            setLowerThanOrEqualTo(FlightColumns.priceCoach, priceCoach);
        }

        public void setPriceCoachGreaterThanOrEqualTo(Integer priceCoach) {
            setGreaterThanOrEqualTo(FlightColumns.priceCoach, priceCoach);
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
        public void setPriceFirstClassEqualTo(Integer priceFirstClass) {
            setEqualTo(FlightColumns.priceFirstClass, priceFirstClass);
        }

        public void setPriceFirstClassLowerThanOrEqualTo(Integer priceFirstClass) {
            setLowerThanOrEqualTo(FlightColumns.priceFirstClass, priceFirstClass);
        }

        public void setPriceFirstClassGreaterThanOrEqualTo(Integer priceFirstClass) {
            setGreaterThanOrEqualTo(FlightColumns.priceFirstClass, priceFirstClass);
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

    public static class Reservation extends Filter {

        //region Set functions
        //--------------------------------------------------------------------------------
        public void setFlightNumberEqualTo(String flightNumber) {
            setEqualTo(ReservationColumns.flightNumber, flightNumber);
        }

        // Departure time functions:
        public void setDepartureTimeEqualTo(LocalDateTime departureTime) {
            setEqualTo(ReservationColumns.departureTime, Utilities.convertLocalDateTimeToString(departureTime));
        }

        public void setDepartureTimeLowerThanOrEqualTo(LocalDateTime departureTime) {
            setLowerThanOrEqualTo(ReservationColumns.departureTime, Utilities.convertLocalDateTimeToString(departureTime));
        }

        public void setDepartureTimeGreaterThanOrEqualTo(LocalDateTime departureTime) {
            setGreaterThanOrEqualTo(ReservationColumns.departureTime, Utilities.convertLocalDateTimeToString(departureTime));
        }

        public void setDepartureTimeInterval(LocalDateTime departureTimeLower, LocalDateTime departureTimeUpper) {
            if(departureTimeLower != null && departureTimeUpper != null) {
                // If both have set times, we want to check if the user swapped the upper/lower bounds.
                if(departureTimeUpper.isBefore(departureTimeLower)) {
                    setInterval(ReservationColumns.departureTime,
                            Utilities.convertLocalDateTimeToString(departureTimeUpper),
                            Utilities.convertLocalDateTimeToString(departureTimeLower));
                    return;
                }
            }
            setInterval(FlightColumns.departureTime,
                    Utilities.convertLocalDateTimeToString(departureTimeLower),
                    Utilities.convertLocalDateTimeToString(departureTimeUpper));
        }


        public void setNameEqualTo(String name) {
            setEqualTo(ReservationColumns.name, name);
        }

        public void setPassportNumberEqualTo(String passportNumber) {
            setEqualTo(ReservationColumns.passportNumber, passportNumber);
        }

        public void setSeatRowEqualTo(Integer row) {
            setEqualTo(ReservationColumns.row, row);
        }

        public void setSeatColumnEqualTo(String column) {
            setEqualTo(ReservationColumns.column, column);
        }

        public void setHasVegeterianMealEqualTo(Boolean hasVegeterianMeal) {
            setEqualTo(ReservationColumns.hasVegeterianMeal, hasVegeterianMeal);
        }
        //--------------------------------------------------------------------------------
        //endregion


        //region Remove functions
        //--------------------------------------------------------------------------------
        public void removeDepartureTime() {
            remove(ReservationColumns.departureTime);
        }

        public void removeName() {
            remove(ReservationColumns.name);
        }

        public void removePassportNumber() {
            remove(ReservationColumns.passportNumber);
        }

        public void removeSeatRow() {
            remove(ReservationColumns.row);
        }

        public void removeSeatColumn() {
            remove(ReservationColumns.column);
        }

        public void removeHasVegeterianMeal() {
            remove(ReservationColumns.hasVegeterianMeal);
        }
        //--------------------------------------------------------------------------------
        //endregion
    }
}
