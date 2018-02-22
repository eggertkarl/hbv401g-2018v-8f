public class User {

    //region Attributes
    //--------------------------------------------------------------------------------
    private String name;
    private boolean isMinor;
    private String passportNumber;
    //--------------------------------------------------------------------------------
    //endregion


    public User(String name, boolean isMinor, String passportNumber) {
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

    public void setMinor(boolean minor) {
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

    public boolean isMinor() {
        return isMinor;
    }

    public String getPassportNumber() {
        return passportNumber;
    }
    //--------------------------------------------------------------------------------
    //endregion
}
