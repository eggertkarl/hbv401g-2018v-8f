public class User {

    //region Attributes
    //--------------------------------------------------------------------------------
    private String name;
    private Boolean isMinor;
    private String passportNumber;
    //--------------------------------------------------------------------------------
    //endregion


    public User(String name, String passportNumber, Boolean isMinor) {
        this.name = name;
        this.isMinor = isMinor;
        this.passportNumber = passportNumber;
    }


    //region Setters
    //--------------------------------------------------------------------------------
    public void setName(String name) {
        this.name = name;
    }

    public void setMinor(Boolean minor) {
        isMinor = minor;
    }

    public void setPassportNumber(String passportNumber) {
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
