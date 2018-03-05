import java.sql.*;
import java.util.List;

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


    private void executeQuery(String query, boolean close) {
        if(!this.connected) {
            this.connect();
        }
        Statement stmt = null;
        try {
            stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                System.out.println(rs.getString("AirplaneType"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(close) {
            this.close();
        }
    }


    public void executeQuery(String query) {
        this.executeQuery(query, true);
    }


    public void executeQueries(List<String> queries) {
        queries.forEach(query -> this.executeQuery(query, false));
        this.close();
    }


    private void connect() {
        int attempt = MAX_CONNECTION_ATTEMPTS;
        // Attempts
        while(attempt > 0) {
            try {
                connection = DriverManager.getConnection(this.databaseUrl);
                this.connected = true;
                return;
            }
            catch(java.sql.SQLException e) {
                attempt--;
                if (attempt < 1) {
                    connection = null;
                    this.connected = false;
                    e.printStackTrace();
                }
                else {
                    try {
                        Thread.sleep(DELAY_BETWEEN_ATTEMPTS);
                    }
                    catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    private void close() {
        if(this.connection != null) {
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
