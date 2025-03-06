
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHandler {

    private static final String URL = "jdbc:mysql://localhost:3306/userdb";
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
    public static boolean validateLogin(String username, String password) {
        String query = "SELECT 1 FROM useracc WHERE username = ? AND password = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error validating login!", e);
        }
        return false;
    }

    // Validate admin login
    public static boolean validateAdminLogin(String adminUsername, String adminPassword) {
        String query = "SELECT 1 FROM adminuserdb WHERE adminuname = ? AND adminpword = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, adminUsername);
            stmt.setString(2, adminPassword);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // If a record exists, login is valid
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error validating admin login!", e);
        }
        return false;
    }

    // Get all users
    public static ResultSet getUsers() {
        String query = "SELECT * FROM useracc";
        Connection conn = getConnection();

        if (conn == null) {
            LOGGER.log(Level.SEVERE, "Database connection is null!");
            return null;
        }

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            return stmt.executeQuery();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving users!", e);
        }
        return null;
    }

    // Add a user (Now includes AccountStatus)
    public static boolean addUser(User user) {
        String query = "INSERT INTO useracc (username, password, AccountStatus) VALUES (?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, 1); // Default status (1 = Active)

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User added successfully: " + user.getUsername());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Delete a user
    public static boolean deleteUser(String username) {
        String query = "DELETE FROM useracc WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting user!", e);
        }
        return false;
    }

    // Update a user
    public static boolean updateUser(User user) {
        String query = "UPDATE useracc SET password = ? WHERE username = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getUsername());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating user!", e);
        }
        return false;
    }
}
