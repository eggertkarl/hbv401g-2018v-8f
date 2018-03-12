import java.time.LocalDateTime;
import java.util.ArrayList;

public class Filter extends ColumnNames{

    public static class Flight {
        private String flightNumber = null;
        private String airline = null;
        private String airplaneType = null;
        private String departureLocation = null;
        private String arrivalLocation = null;

        private Boolean hasMeal = null;
        private Boolean hasVegeterianMeal = null;
        private Boolean hasEntertainment = null;

        private LocalDateTime departureTimeLower = null;
        private LocalDateTime departureTimeUpper = null;
        private LocalDateTime arrivalTimeLower = null;
        private LocalDateTime arrivalTimeUpper = null;
        private Integer priceCoachLower = null;
        private Integer priceCoachUpper = null;
        private Integer priceFirstClassLower = null;
        private Integer priceFirstClassUpper = null;

        private static String[] flightColumns = new String[]{
                FlightColumns.flightNumber + " = ",
                FlightColumns.airline + " = ",
                FlightColumns.airplaneType + " = ",
                FlightColumns.departureLocation + " = ",
                FlightColumns.arrivalLocation + " = ",
                FlightColumns.hasMeal + " = ",
                FlightColumns.hasVegeterianMeal + " = ",
                FlightColumns.hasEntertainment + " = ",
                FlightColumns.departureTime + " >= ", // Lower
                FlightColumns.departureTime + " <= ", // Upper
                FlightColumns.arrivalTime + " >= ", // Lower
                FlightColumns.arrivalTime + " <= ", // Upper
                FlightColumns.priceCoach + " >= ", // Lower
                FlightColumns.priceCoach + " <= ", // Upper
                FlightColumns.priceFirstClass + " >= ", // Lower
                FlightColumns.priceFirstClass + " <= " // Upper
        };

        private ArrayList<String> filters = null;
        private ArrayList<Object> parameters = null;


        private void updateFiltersAndParameters() {
            filters = new ArrayList<>();
            parameters = new ArrayList<>();

            Object[] tempParams = new Object[] {
                    this.flightNumber,
                    this.airline,
                    this.airplaneType,
                    this.departureLocation,
                    this.arrivalLocation,
                    this.hasMeal,
                    this.hasVegeterianMeal,
                    this.hasEntertainment,
                    this.departureTimeLower,
                    this.departureTimeUpper,
                    this.arrivalTimeLower,
                    this.arrivalTimeUpper,
                    this.priceCoachLower,
                    this.priceCoachUpper,
                    this.priceFirstClassLower,
                    this.priceFirstClassUpper
            };

            for(int i = 0; i < tempParams.length; i++) {
                if(tempParams[i] != null) {
                    filters.add(flightColumns[i] + "?");
                    parameters.add(tempParams[i]);
                }
            }
        }


        public ArrayList<String> getFilters() {
            updateFiltersAndParameters();
            return this.filters;
        }

        public ArrayList<Object> getParameters() {
            updateFiltersAndParameters();
            return this.parameters;
        }


        //region Set functions
        //--------------------------------------------------------------------------------
        public void setFlightNumberEqual(String flightNumber) {
            this.flightNumber = flightNumber;
        }

        public void setAirlineEqual(String airline) {
            this.airline = airline;
        }

        public void setAirplaneTypeEqual(String airplaneType) {
            this.airplaneType = airplaneType;
        }

        public void setDepartureLocationEqual(String departureLocation) {
            this.departureLocation = departureLocation;
        }

        public void setArrivalLocationEqual(String arrivalLocation) {
            this.arrivalLocation = arrivalLocation;
        }

        public void setHasMeal(Boolean hasMeal) {
            this.hasMeal = hasMeal;
        }

        public void setHasVegeterianMeal(Boolean hasVegeterianMeal) {
            this.hasVegeterianMeal = hasVegeterianMeal;
        }

        public void setHasEntertainment(Boolean hasEntertainment) {
            this.hasEntertainment = hasEntertainment;
        }


        // Departure time functions:
        public void setDepartureTimeEqual(LocalDateTime departureTime) {
            this.departureTimeLower = departureTime;
            this.departureTimeUpper = departureTime;
        }

        public void setDepartureTimeLowerThanOrEqual(LocalDateTime departureTime) {
            this.departureTimeUpper = departureTime;
            this.departureTimeLower = null;
        }

        public void setDepartureTimeGreaterThanOrEqual(LocalDateTime departureTime) {
            this.departureTimeLower = departureTime;
            this.departureTimeUpper = null;
        }

        public void setDepartureTimeInterval(LocalDateTime departureTimeLower, LocalDateTime departureTimeUpper) {
            if(departureTimeLower == null) {
                setArrivalTimeLowerThanOrEqual(departureTimeUpper);
            }
            else if(departureTimeUpper == null) {
                setArrivalTimeGreaterThanOrEqual(departureTimeLower);
            }
            else {
                if (departureTimeLower.isBefore(departureTimeUpper)) {
                    this.departureTimeLower = departureTimeLower;
                    this.departureTimeUpper = departureTimeUpper;
                } else {
                    this.departureTimeLower = departureTimeUpper;
                    this.departureTimeUpper = departureTimeLower;
                }
            }
        }


        // Arrival time functions:
        public void setArrivalTimeEqual(LocalDateTime arrivalTime) {
            this.arrivalTimeLower = arrivalTime;
            this.arrivalTimeUpper = arrivalTime;
        }

        public void setArrivalTimeLowerThanOrEqual(LocalDateTime arrivalTime) {
            this.arrivalTimeUpper = arrivalTime;
            this.arrivalTimeLower = null;
        }

        public void setArrivalTimeGreaterThanOrEqual(LocalDateTime arrivalTime) {
            this.arrivalTimeLower = arrivalTime;
            this.arrivalTimeUpper = null;
        }

        public void setArrivalTimeInterval(LocalDateTime arrivalTimeLower, LocalDateTime arrivalTimeUpper) {
            if(arrivalTimeLower == null) {
                setArrivalTimeLowerThanOrEqual(arrivalTimeUpper);
            }
            else if(arrivalTimeUpper == null) {
                setArrivalTimeGreaterThanOrEqual(arrivalTimeLower);
            }
            else {
                if (arrivalTimeLower.isBefore(arrivalTimeUpper)) {
                    this.arrivalTimeLower = arrivalTimeLower;
                    this.arrivalTimeUpper = arrivalTimeUpper;
                } else {
                    this.arrivalTimeLower = arrivalTimeUpper;
                    this.arrivalTimeUpper = arrivalTimeLower;
                }
            }
        }


        // Price coach functions:
        public void setPriceCoachEqual(Integer priceCoach) {
            this.priceCoachLower = priceCoach;
            this.priceCoachUpper = priceCoach;
        }

        public void setPriceCoachLessThanOrEqual(Integer priceCoach) {
            this.priceCoachUpper = priceCoach;
            this.priceCoachLower = null;
        }

        public void setPriceCoachGreaterThanOrEqual(Integer priceCoach) {
            this.priceCoachLower = priceCoach;
            this.arrivalTimeUpper = null;
        }

        public void setPriceCoachInterval(Integer priceCoachLower, Integer priceCoachUpper) {
            if(priceCoachLower == null) {
                setPriceCoachLessThanOrEqual(priceCoachUpper);
            }
            else if(priceCoachUpper == null) {
                setPriceCoachGreaterThanOrEqual(priceCoachLower);
            }
            else {
                if (priceCoachLower < priceCoachUpper) {
                    this.priceCoachLower = priceCoachLower;
                    this.priceCoachUpper = priceCoachUpper;
                } else {
                    this.priceCoachLower = priceCoachUpper;
                    this.priceCoachUpper = priceCoachLower;
                }
            }
        }


        // Price first class functions:
        public void setPriceFirstClassEqual(Integer priceFirstClass) {
            this.priceFirstClassLower = priceFirstClass;
            this.priceFirstClassUpper = priceFirstClass;
        }

        public void setPriceFirstClassLowerThanOrEqual(Integer priceFirstClass) {
            this.priceFirstClassUpper = priceFirstClass;
            this.priceFirstClassLower = null;
        }

        public void setPriceFirstClassGreaterThanOrEqual(Integer priceFirstClass) {
            this.priceFirstClassLower = priceFirstClass;
            this.arrivalTimeUpper = null;
        }

        public void setPriceFirstClassInterval(Integer priceFirstClassLower, Integer priceFirstClassUpper) {
            if(priceFirstClassLower == null) {
                setPriceFirstClassLowerThanOrEqual(priceFirstClassUpper);
            }
            else if(priceFirstClassUpper == null) {
                setPriceFirstClassGreaterThanOrEqual(priceFirstClassLower);
            }
            else {
                if (priceFirstClassLower < priceFirstClassUpper) {
                    this.priceFirstClassLower = priceFirstClassLower;
                    this.priceFirstClassUpper = priceFirstClassUpper;
                } else {
                    this.priceFirstClassLower = priceFirstClassUpper;
                    this.priceFirstClassUpper = priceFirstClassLower;
                }
            }
        }
        //--------------------------------------------------------------------------------
        //endregion


        //region Remove functions
        //--------------------------------------------------------------------------------
        public void removeFlightNumber() {
            this.flightNumber = null;
        }

        public void removeAirline() {
            this.airline = null;
        }

        public void removeAirplaneType() {
            this.airplaneType = null;
        }

        public void removeDepartureLocation() {
            this.departureLocation = null;
        }

        public void removeArrivalLocation() {
            this.arrivalLocation = null;
        }

        public void removeHasMeal() {
            this.hasMeal = null;
        }

        public void removeHasVegeterianMeal() {
            this.hasVegeterianMeal = null;
        }

        public void removeHasEntertainment() {
            this.hasEntertainment = null;
        }

        public void removeDepartureTime() {
            this.departureTimeLower = null;
            this.departureTimeUpper = null;
        }

        public void removeArrivalTime() {
            this.arrivalTimeLower = null;
            this.arrivalTimeUpper = null;
        }

        public void removePriceCoach() {
            this.priceCoachLower = null;
            this.priceCoachUpper = null;
        }

        public void removePriceFirstClass() {
            this.priceFirstClassLower = null;
            this.priceFirstClassUpper = null;
        }
        //--------------------------------------------------------------------------------
        //endregion
    }
}
