import java.sql.*;
import java.util.UUID;

public class UUIDCheck {

    public static void main(String[] args){

        //Creates random UUID and changes to string
        UUID randomUUID = UUID.randomUUID();

        String uuidString = randomUUID.toString();
    }

    public static boolean checkIfUUIDExists(String url, String uuidString, String table) {

        Connection connection = null;

        try {
            // Connect to the SQLite database
            connection = DriverManager.getConnection(url);

            // Counts the number of uuidString in the database
            String sql = "SELECT COUNT(*) FROM " + table + " WHERE eventuuid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, uuidString);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // If count of uuid is above 0, then uuid exists
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            } else {
                System.out.println("UUID Not Found in Database.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
