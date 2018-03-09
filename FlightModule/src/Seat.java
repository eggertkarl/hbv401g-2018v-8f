import java.util.HashMap;

public class Seat{

    //region Attributes
    //--------------------------------------------------------------------------------
    private final Integer row;
    private final String column;
    private final Boolean isAvailable;
    private final Boolean isFirstClass;
    //--------------------------------------------------------------------------------
    //endregion


    public Seat(Integer row, String column, Boolean isAvailable, Boolean isFirstClass) {
        this.row = row;
        this.column = column;
        this.isAvailable = isAvailable;
        this.isFirstClass = isFirstClass;
    }


    //region Getters
    //--------------------------------------------------------------------------------
    public Integer getRow() {
        return row;
    }

    public String getColumn() {
        return column;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public Boolean isFirstClass() {
        return isFirstClass;
    }
    //--------------------------------------------------------------------------------
    //endregion

    // TODO: Remove
    public void print() {
        System.out.println("Row: " + row + "\tColumn: " + column);
    }

}
