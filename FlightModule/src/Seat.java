import java.util.HashMap;

public class Seat{

    //region Attributes
    //--------------------------------------------------------------------------------
    private final int row;
    private final String column;
    private final boolean isAvailable;
    private final boolean isFirstClass;
    //--------------------------------------------------------------------------------
    //endregion


    public Seat(int row, String column, boolean isAvailable, boolean isFirstClass) {
        this.row = row;
        this.column = column;
        this.isAvailable = isAvailable;
        this.isFirstClass = isFirstClass;
    }


    //region Getters
    //--------------------------------------------------------------------------------
    public int getRow() {
        return row;
    }

    public String getColumn() {
        return column;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isFirstClass() {
        return isFirstClass;
    }
    //--------------------------------------------------------------------------------
    //endregion

}
