
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class DatabaseController {

    //region Constants
    //--------------------------------------------------------------------------------
    private static final String DEFAULT_DATABASE_URL = "jdbc:sqlite:./flights.db";
    private static final int MAX_CONNECTION_ATTEMPTS = 3;
    private static final int DELAY_BETWEEN_ATTEMPTS = 100;
    //--------------------------------------------------------------------------------
    //endregion


    //region Attributes
    //--------------------------------------------------------------------------------
    private Connection connection;
    private String databaseUrl;
    private boolean connected;
    //--------------------------------------------------------------------------------
    //endregion


    public DatabaseController() {
        this(DEFAULT_DATABASE_URL);
    }


    public DatabaseController(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        this.connection = null;
        this.connected = false;
    }


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


    public <T> ArrayList<T> executeQuery(String query, ArrayList<Object> params, Initializer<T> initializer) {
        this.connect();
        query = query.trim();
        ArrayList<T> results = null;
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            for(int i = 0; i < params.size(); i++) {
                stmt.setObject(i+1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            if (query.substring(0, 6).toLowerCase().equals("select")) {
                results = this.convertResultSetToArray(rs, initializer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.close();
        return results;
    }


    private void connect() {
        if (!this.connected) {
            int attempt = MAX_CONNECTION_ATTEMPTS;
            // Attempts
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
}