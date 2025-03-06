
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminDatabaseHandler {

    private static final String URL = "jdbc:mysql://localhost:3306/adminuserdb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    private static final Logger LOGGER = Logger.getLogger(DatabaseHandler.class.getName());

    // Establish Database Connection
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC Driver not found!", e);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection failed!", e);
        }
        return null;
    }

    // Validate login credentials
    public static boolean validateLogin(String adminusername, String adminpassword) {
        String query = "SELECT 1 FROM adminuseracc WHERE adminusername = ? AND adminpassword = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, adminusername);
            stmt.setString(2, adminpassword);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error validating login!", e);
        }
        return false;
    }

}
