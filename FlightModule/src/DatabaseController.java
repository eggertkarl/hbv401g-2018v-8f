
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class DatabaseController {

    //region Constants
    //--------------------------------------------------------------------------------
    private static final String DEFAULT_DATABASE_URL = "jdbc:sqlite:./flights.db";
    private static final int MAX_CONNECTION_ATTEMPTS = 3;
    private static final int DELAY_BETWEEN_ATTEMPTS = 100;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //--------------------------------------------------------------------------------
    //endregion


    //region Attributes
    //--------------------------------------------------------------------------------
    private Connection connection;
    private String databaseUrl;
    private boolean connected;
    //--------------------------------------------------------------------------------
    //endregion


    DatabaseController() {
        this(DEFAULT_DATABASE_URL);
    }

    DatabaseController(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        this.connection = null;
        this.connected = false;
    }


    /**
     * Converts an instance of ResultSet to an ArrayList<T>.
     *
     * @param rs Results from an SQL query.
     * @param initializer Function that passes values to the constructor of T.
     * @return A list of types T containing the data from the result set.
     */
    private <T> ArrayList<T> convertResultSetToArray(ResultSet rs, Initializer<T> initializer) {
        ArrayList<T> results = new ArrayList<>();
        try {
            ResultSetMetaData metadata = rs.getMetaData();
            int numberOfColumns = metadata.getColumnCount();

            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>(numberOfColumns);
                for (int i = 0; i < numberOfColumns; i++) {
                    row.put(metadata.getColumnName(i+1), rs.getObject(i+1));
                }
                results.add(initializer.create(row));
            }
            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Executes a SQL query with the supplied parameters.
     * Returns an ArrayList containing objects of the type that the initializer returns.
     *
     * @param query A valid SQL query.
     * @param params List of arguments to include in the SQL statement.
     * @param initializer Function that passes values to the constructor of T.
     * @return Results from the executed query. Returns null if the execution was unsuccessful.
     */
    protected <T> ArrayList<T> executeQuery(String query, ArrayList<Object> params, Initializer<T> initializer) {
        ArrayList<T> results = null;
        PreparedStatement stmt = this.prepareStatement(query, params);
        if(stmt != null) {
            try {
                ResultSet rs = stmt.executeQuery();
                results = this.convertResultSetToArray(rs, initializer);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        this.close();
        return results;
    }


    /**
     * Executes a SQL query and returns an ArrayList containing objects of the type that the initializer returns.
     *
     * @param query A valid SQL query.
     * @param initializer Function that passes values to the constructor of T.
     * @return Results from the executed query. Returns null if the execution was unsuccessful.
     */
    protected <T> ArrayList<T> executeQuery(String query, Initializer<T> initializer) {
        return this.executeQuery(query, null, initializer);
    }


    /**
     * Executes a SQL statement and returns a boolean stating whether the execution was successful.
     *
     * @param query A valid SQL statement.
     * @param params List of arguments to include in the SQL statement.
     * @return True if the SQL statement is successfully executed.
     */
    protected boolean execute(String query, ArrayList<Object> params) {
        boolean success = true;
        PreparedStatement stmt = this.prepareStatement(query, params);
        if(stmt != null) {
            try {
                stmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                success = false;
            }
        }
        else{
            success = false;
        }
        this.close();
        return success;
    }


    /**
     * Executes a SQL statement and returns a boolean stating whether the execution was successful.
     *
     * @param query A valid SQL statement.
     * @return True if the SQL statement is successfully executed.
     */
    protected boolean execute(String query) {
        return this.execute(query, null);
    }


    /**
     * Creates an instance of PreparedStatement containing the query with the parameters.
     *
     * @param query A valid SQL statement.
     * @param params List of parameters to be passed to the query.
     * @return An instance of PreparedStatement containing the query with the parameters.
     */
    private PreparedStatement prepareStatement(String query, ArrayList<Object> params) {
        this.connect();
        if(this.connected) {
            query = query.trim();
            try {
                PreparedStatement stmt = this.connection.prepareStatement(query);
                if (params != null) {
                    for (int i = 0; i < params.size(); i++) {
                        stmt.setObject(i + 1, params.get(i));
                    }
                }
                return stmt;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * Connects to the database.
     */
    private void connect() {
        if (!this.connected) {
            int attempt = MAX_CONNECTION_ATTEMPTS;
            while (attempt > 0) {
                try {
                    connection = DriverManager.getConnection(this.databaseUrl);
                    this.connected = true;
                    return;
                } catch (java.sql.SQLException e) {
                    attempt--;
                    if (attempt < 1) {
                        this.connection = null;
                        this.connected = false;
                        e.printStackTrace();
                    } else {
                        try {
                            Thread.sleep(DELAY_BETWEEN_ATTEMPTS);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * Closes the connection to the database.
     */
    private void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                this.connection = null;
            }
        }
        this.connected = false;
    }

    protected static LocalDateTime getDateTime(String dateText) {
        if(dateText == null) {
            return null;
        }
        return LocalDateTime.parse(dateText, dateFormatter);
    }
}