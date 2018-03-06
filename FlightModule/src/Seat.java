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

    public Seat(HashMap<String, Object> args) {
        this.row = (int) args.get("Row");
        this.column = (String) args.get("Column");
        this.isAvailable = true;
        // TODO: Add isAvailable to query
        //this.isAvailable = (boolean) args.get("isAvailable");
        this.isFirstClass = (int) args.get("IsFirstClass") == 1;
    }


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
