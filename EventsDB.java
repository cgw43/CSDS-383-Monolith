import java.sql.*;


public class EventsDB {
    
    private Connection connection;
    private boolean hasData = false;

    public void getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:EventsDB");
        initialize();
    }

    public void getEvents() throws SQLException {
        if (connection == null) {
            getConnection();
        }

        Statement st = connection.createStatement();
        ResultSet res = st.executeQuery("SELECT * FROM events");
        printReslultSet(res);
    }

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
       // Sample data
       // st1.execute("INSERT INTO events values('uuid', '12-12-2023', '12:00 AM', 'My first event', 'description of my first event', 'cgw43@case.edu');");
       // st1.execute("INSERT INTO participants values('personid', 'uuid', 'callie wells', 'callie@email.com')");
    }

    /*
     * Prints input result set in a readable way (hopefully)
     */
    public void printReslultSet(ResultSet res) {
        
        try {
            ResultSetMetaData meta = res.getMetaData();
            int cols = meta.getColumnCount();
            
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
        
    }


    public static void main(String[] args) {
        EventsDB test = new EventsDB();
        try {
            test.getEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
