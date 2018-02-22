public class Seat {

    //region Attributes
    //--------------------------------------------------------------------------------
    private final int row;
    private final int column;
    private final boolean isAvailable;
    private final boolean isFirstClass;
    //--------------------------------------------------------------------------------
    //endregion


    public Seat(int row, int column, boolean isAvailable, boolean isFirstClass) {
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

    public int getColumn() {
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
