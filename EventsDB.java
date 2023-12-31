import java.sql.*;

/*
 * Class interacts directly with the SQLite DB,
 * holds functions that will access, read, or write to/from the DB
 */
public class EventsDB {
    
    private Connection connection;
    private boolean hasData = false;
    public void getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:EventsDB");
        initialize();
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /*
     * Gets all events from events table and prints
     */
    public void getEvents() throws SQLException {
        if (connection == null) {
            getConnection();
        }
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery("SELECT * FROM events");
        printReslultSet(res);
    }

    /*
     * Gets all registered participants from participants table and prints
     */
    public void getParticipants() throws SQLException {
        if (connection == null) {
            getConnection();
        }
        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery("SELECT participants.*, title as event_title FROM participants, events WHERE events.uuid = participants.eventID");
        printReslultSet(res);
    }

    /*
     * Inserts new event
     */
    public void createEvent(String uuid, String date, String time, String title, String description, String email) throws SQLException {
        if (connection == null) {
            getConnection();
        }
        PreparedStatement prep = connection.prepareStatement("INSERT INTO events values (?,?,?,?,?,?)");
        prep.setString(1, uuid);
        prep.setString(2, date);
        prep.setString(3, time);
        prep.setString(4, title);
        prep.setString(5, description);
        prep.setString(6, email);
        prep.execute();
        System.out.println("Event successfully created\n");
    }

    public void registerParticipant(String uuid, String eventID, String name, String email) throws SQLException {
        if (connection == null) {
            getConnection();
        }
        PreparedStatement prep = connection.prepareStatement("INSERT INTO participants values (?,?,?,?)");
        prep.setString(1, uuid);
        prep.setString(2, eventID);
        prep.setString(3, name);
        prep.setString(4, email);
        prep.execute();
        System.out.println("Participant successfully registered\n");
    }

    /*
     * Checks if the database & tables exists and creates them if needed.
     */
    private void initialize() throws SQLException {
        if (!hasData) {
            hasData = true;
        }
        Statement st1 = connection.createStatement();
        ResultSet res1 = st1.executeQuery("SELECT name from sqlite_master WHERE type='table' AND name='events'");
        Statement st2 = connection.createStatement();
        ResultSet res2 = st2.executeQuery("SELECT name from sqlite_master WHERE type='table' AND name='participants'");
        if (!res1.next()) {
            System.out.println("Creating events table");
            Statement st3 = connection.createStatement();
            st3.execute("CREATE TABLE events(uuid VARCHAR(36) PRIMARY KEY, date DATE, time TIME, title VARCHAR(255), description VARCHAR(600), email VARCHAR(300));");
        }

        if (!res2.next()) {
            System.out.println("Creating participants table");
            Statement st4 = connection.createStatement();
            st4.execute("CREATE TABLE participants(uuid VARCHAR(36) PRIMARY KEY, eventID VARCHAR(36), name CHAR(600), email VARCHAR(300), FOREIGN KEY (eventID) REFERENCES events(uuid));");
        }
    }

    /*
     * Prints input result set in a readable way (hopefully)
     */
    public void printReslultSet(ResultSet res) {

        try {
            ResultSetMetaData meta = res.getMetaData();
            int cols = meta.getColumnCount();
            System.out.println();
            // Build a string for each row and print until none left
            while (res.next()) {
                StringBuilder builder = new StringBuilder();
                for (int i = 1; i<=cols;i++) {
                    builder.append(meta.getColumnName(i));
                    builder.append(": ");
                    builder.append(res.getString(i));
                    builder.append("   ");
                }

                System.out.println(builder.toString());
            }
        } catch (Exception e) {
            System.out.println("Error printing results");
        }
        System.out.println();
    }

    public boolean checkIfEventUUIDExists(String uuid) {
        try {
            if (connection == null) {
                getConnection();
            }

            PreparedStatement prep = connection.prepareStatement("SELECT uuid FROM events WHERE events.uuid = ?");
            prep.setString(1, uuid);
            ResultSet res = prep.executeQuery();
           
            // if there is a next, uuid exists in db
            if (res.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: Could not validate Event UUID");
            return false;
        }
    }

    public boolean checkIfUUIDExists(String uuidString){

        // Connect to the SQLite database
        if (connection == null) {
            try {
                getConnection();
            } catch (SQLException e) {
                System.out.println("Could not connect to DB- Could not validate Participant UUID. Please try again.");
                return false;
            }
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid FROM participants WHERE uuid = ?");
            
            preparedStatement.setString(1, uuidString);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            // if there is a next, uuid exists in db
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        }
        catch (SQLException e){
            System.out.println("Could not execute search - could not validate participant UUID. Please try again");
            return false;
        }
    }
}
