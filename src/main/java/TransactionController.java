
import java.io.IOException;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class TransactionController {

    private static final Logger LOGGER = Logger.getLogger(TransactionController.class.getName());

    @FXML
    private Button completeTransactbtn;
    @FXML
    private RadioButton mayaPayment, gcashPayment, visaPayment;
    @FXML
    private TextField selectedGame, selectedPrice;

    private String gameName;
    private double gamePrice;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/SteamDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    private ToggleGroup paymentToggleGroup;

    @FXML
    public void initialize() {
        paymentToggleGroup = new ToggleGroup();
        mayaPayment.setToggleGroup(paymentToggleGroup);
        gcashPayment.setToggleGroup(paymentToggleGroup);
        visaPayment.setToggleGroup(paymentToggleGroup);
    }

    public void setGameDetails(String game, double price) {
        this.gameName = game;
        this.gamePrice = price;
        selectedGame.setText(game);
        selectedPrice.setText("₱" + price);
    }

    public void setTransactionDetails(String game, String price) {
        this.gameName = game;
        this.gamePrice = Double.parseDouble(price);
        selectedGame.setText(game);
        selectedPrice.setText(price);
    }

    @FXML
    public void handleCompleteTransaction(ActionEvent event) {
        if (paymentToggleGroup.getSelectedToggle() == null) {
            showAlert("Payment Selection", "Please select a payment method.", Alert.AlertType.WARNING);
            return;
        }

        int gameID = getGameID(gameName);
        if (gameID == -1) {
            showAlert("Error", "Game not found in the database.", Alert.AlertType.ERROR);
            return;
        }

        int userID = getUserIDFromUsername(LoggedInUser.getUsername());
        if (userID == -1) {
            showAlert("Error", "User not found in the database.", Alert.AlertType.ERROR);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            boolean success = saveTransactionToDatabase(userID, gameID, gamePrice, getSelectedPaymentMethod(), conn);
            if (success) {
                showAlert("Transaction Successful", "Game: " + gameName, Alert.AlertType.INFORMATION);
                loadAdminTransactionScene(event, userID, gameName, gamePrice, getSelectedPaymentMethod());
            } else {
                showAlert("Transaction Failed", "An error occurred.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection failed.", e);
        }
    }

    private void loadAdminTransactionScene(ActionEvent event, int userID, String gameName, double gamePrice, String paymentMethod) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminTransaction.fxml"));
            Parent root = loader.load();
            AdminTransactionController adminController = loader.getController();
            adminController.setTransactionData(userID, gameName, gamePrice, paymentMethod);
            adminController.setLoggedInUserID(userID);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Transactions");
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load Admin Transaction Page", Alert.AlertType.ERROR);
        }
    }

    private int getGameID(String gameName) {
        String query = "SELECT id FROM games WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, gameName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching game ID", e);
        }
        return -1;
    }

    private int getUserIDFromUsername(String username) {
        String query = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching user ID", e);
        }
        return -1;
    }

    private boolean saveTransactionToDatabase(int userID, int gameID, double price, String paymentMethod, Connection conn) {
        String sql = "INSERT INTO transactions (user_id, game_id, price, payment_method) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, gameID);
            stmt.setDouble(3, price);
            stmt.setString(4, paymentMethod);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Transaction saving failed", e);
            return false;
        }
    }

    private String getSelectedPaymentMethod() {
        if (mayaPayment.isSelected()) {
            return "Maya";
        }
        if (gcashPayment.isSelected()) {
            return "Gcash";
        }
        if (visaPayment.isSelected()) {
            return "Visa";
        }
        return "";
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
