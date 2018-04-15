package FlightModule.src;

public class User {

    //region Attributes
    //--------------------------------------------------------------------------------
    private String name;
    private Boolean isMinor;
    private String passportNumber;
    //--------------------------------------------------------------------------------
    //endregion

    // TODO: Implement generic constructor
    /* public User(HashMap<String, Object> args) {
        this.name = (String) args.get("???");
        ...
    } */

    public User(String name, String passportNumber, Boolean isMinor) {
        this.name = name;
        this.isMinor = isMinor;
        this.passportNumber = passportNumber;
    }


    //region Setters
    //--------------------------------------------------------------------------------
    public void setName(String name) {
        // TODO: Add checks if necessary
        this.name = name;
    }

    public void setMinor(Boolean minor) {
        // TODO: Add checks if necessary
        isMinor = minor;
    }

    public void setPassportNumber(String passportNumber) {
        // TODO: Add checks if necessary
        this.passportNumber = passportNumber;
    }
    //--------------------------------------------------------------------------------
    //endregion


    //region Getters
    //--------------------------------------------------------------------------------
    public String getName() {
        return name;
    }

    public Boolean isMinor() {
        return isMinor;
    }

    public String getPassportNumber() {
        return passportNumber;
    }
    //--------------------------------------------------------------------------------
    //endregion
}
