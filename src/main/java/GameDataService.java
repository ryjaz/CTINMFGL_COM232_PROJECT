
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameDataService {

    private static final Logger LOGGER = Logger.getLogger(GameDataService.class.getName());
    private static final String DB_URL = "jdbc:mysql://localhost:3306/SteamDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    // Thread-safe cache for game prices
    private static final ConcurrentHashMap<String, Integer> gamePrices = new ConcurrentHashMap<>();

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Preload all game prices into memory (useful for applications that need
     * frequent price access).
     */
    public static void loadGamePrices() {
        String query = "SELECT GameName, FinalAmount FROM Game";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            gamePrices.clear();
            while (rs.next()) {
                String gameName = rs.getString("GameName").trim().toLowerCase();
                int price = rs.getInt("FinalAmount");
                gamePrices.put(gameName, price);
            }
            LOGGER.info("Game prices loaded successfully.");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading game prices from database", e);
        }
    }

    /**
     * Get the price of a game. Checks the cache first before querying the
     * database.
     */
    public static int getGamePrice(String gameName) {
        if (gameName == null || gameName.trim().isEmpty()) {
            LOGGER.warning("Invalid game name provided.");
            return 0;
        }
        String normalizedGameName = gameName.trim().toLowerCase();

        // Check cache first
        if (gamePrices.containsKey(normalizedGameName)) {
            return gamePrices.get(normalizedGameName);
        }

        // If not in cache, fetch from database
        String query = "SELECT FinalAmount FROM Game WHERE GameName = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, gameName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int price = rs.getInt("FinalAmount");
                    gamePrices.put(normalizedGameName, price); // Cache the result
                    return price;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching price for: " + gameName, e);
        }

        return 0; // Default to 0 if not found
    }

    /**
     * Update the price of a game in the database and refresh cache.
     */
    public static void savePriceToDatabase(String gameName, int price) {
        if (gameName == null || gameName.trim().isEmpty() || price < 0) {
            LOGGER.warning("Invalid game name or price provided.");
            return;
        }

        String normalizedGameName = gameName.trim().toLowerCase();

        String query = "UPDATE Game SET FinalAmount = ? WHERE GameName = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, price);
            stmt.setString(2, gameName);

            int updatedRows = stmt.executeUpdate();
            if (updatedRows > 0) {
                LOGGER.info("Price updated for: " + gameName);
                gamePrices.put(normalizedGameName, price); // Update cache
            } else {
                LOGGER.warning("No game found with name: " + gameName);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating price in database", e);
        }
    }

    public static int getGameID(String gameName) {
        if (gameName == null || gameName.trim().isEmpty()) {
            LOGGER.warning("Invalid game name provided.");
            return -1;
        }
        String query = "SELECT GameID FROM Game WHERE GameName = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, gameName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("GameID");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching GameID for: " + gameName, e);
        }
        return -1;
    }
}
