
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CrossfireController {

    private static final Logger LOGGER = Logger.getLogger(CrossfireController.class.getName());

    @FXML
    private Button crossfireBuybtn, crossfireSavebtn, crossfireExitBtn;

    @FXML
    private TextArea crossfirePriceDisplay;

    @FXML
    private TextField gameName;

    @FXML
    public void initialize() {
        loadCrossfirePrice();
    }

    private void loadCrossfirePrice() {
        int price = GameDataService.getGamePrice("Crossfire"); // Fetch updated price
        if (price > 0) {
            crossfirePriceDisplay.setText("₱" + price);
            gameName.setText("Crossfire");
        } else {
            crossfirePriceDisplay.setText("₱0");
            LOGGER.warning("Crossfire price not found in the database.");
        }
    }

    @FXML
    public void handlecrossfireBuybtn(ActionEvent event) {
        int userID = getCurrentUserID();
        int gameID = getGameID("Crossfire");

        if (gameID == -1) {
            showAlert("Error", "Game not found.", Alert.AlertType.ERROR);
            return;
        }

        if (processPurchase(userID, gameID)) {
            showAlert("Success", "Purchase successful!", Alert.AlertType.INFORMATION);
            LOGGER.info("Purchase successful, transitioning to Transaction.fxml...");

            // ✅ Load Transaction Scene
            loadTransaction(event);
        } else {
            showAlert("Error", "Purchase failed!", Alert.AlertType.ERROR);
        }
    }

    private boolean processPurchase(int userID, int gameID) {
        double finalAmount = GameDataService.getGamePrice("Crossfire");

        if (finalAmount <= 0) {
            showAlert("Error", "Invalid game price.", Alert.AlertType.ERROR);
            return false;
        }

        String insertSQL = "INSERT INTO transactiontable (UserID, GameID, FinalAmount, PaymentMethod) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SteamDB", "root", "password"); PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {

            insertStmt.setInt(1, userID);
            insertStmt.setInt(2, gameID);
            insertStmt.setDouble(3, finalAmount);
            insertStmt.setString(4, "Gcash"); // ✅ FIX: Adding a default payment method

            int rowsInserted = insertStmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while purchasing", e);
            showAlert("Error", "Database issue: " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }

    @FXML
    private void handlecrossfireSaveLibrarybtn(ActionEvent event) {
        int userID = getCurrentUserID(); // Retrieve the logged-in user ID
        int gameID = getGameID("Crossfire");

        if (gameID == -1) {
            showAlert("Error", "Game not found.", Alert.AlertType.ERROR);
            return;
        }

        String insertQuery = "INSERT INTO library (UserID, GameID, GameName, Purchase, TimeStamp, SourceScreen) VALUES (?, ?, ?, ?, NOW(), ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SteamDB", "root", "password"); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setInt(1, userID);
            pstmt.setInt(2, gameID);
            pstmt.setString(3, "Crossfire");
            pstmt.setString(4, "Saved to Library");
            pstmt.setString(5, "Crossfire Screen"); // Track source of addition

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Game successfully added to Library!", Alert.AlertType.INFORMATION);
                loadLibrary(event);
            } else {
                showAlert("Error", "Failed to add game to Library.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while saving to Library", e);
            showAlert("Error", "An unexpected database issue occurred.", Alert.AlertType.ERROR);
        }
    }

    private void loadTransaction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Transaction.fxml"));
            Parent root = loader.load();

            // ✅ Get controller instance to pass game details
            TransactionController transactionController = loader.getController();
            if (transactionController != null) {
                transactionController.setTransactionDetails(
                        gameName.getText(), // Pass game name
                        crossfirePriceDisplay.getText() // Pass price
                );
            } else {
                LOGGER.warning("TransactionController is null. Data not passed.");
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Transaction");
            stage.show();

            LOGGER.info("Successfully loaded Transaction.fxml");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Transaction.fxml", e);
            showAlert("Error", "Failed to load Transaction.", Alert.AlertType.ERROR);
        }
    }

    private void loadLibrary(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Library.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Library");
            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Library.fxml", e);
            showAlert("Error", "Failed to load Library.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handlecrossfireExit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Store.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Store");
            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Store.fxml", e);
            showAlert("Error", "Failed to load Store.", Alert.AlertType.ERROR);
        }
    }

    private int getCurrentUserID() {
        return 1; // TODO: Replace with actual logged-in user ID retrieval
    }

    private int getGameID(String gameName) {
        String query = "SELECT GameID FROM Game WHERE GameName = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SteamDB", "root", "password"); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, gameName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("GameID");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching GameID for: " + gameName, e);
        }
        return -1; // Return -1 if not found
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
