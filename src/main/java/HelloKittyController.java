
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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class HelloKittyController {

    private static final Logger LOGGER = Logger.getLogger(HelloKittyController.class.getName());

    // Database Credentials
    private static final String URL = "jdbc:mysql://localhost:3306/SteamDB";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    @FXML
    private Button hellokittyBuybtn, hellokittySavebtn, hellokittyExitBtn;

    @FXML
    private TextArea hellokittyPriceDisplay;

    // Initialize method to load price when scene opens
    @FXML
    public void initialize() {
        loadHelloKittyPrice();
    }

    private void loadHelloKittyPrice() {
        String query = "SELECT FinalAmount FROM Game WHERE GameID = 2";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                hellokittyPriceDisplay.setText("₱" + rs.getDouble("FinalAmount"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching price for Hello Kitty", e);
            showAlert("Error", "Failed to load Hello Kitty price.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleHelloKittyBuybtn(ActionEvent event) { // Fixed method name
        loadTransaction(event);
    }

    private void loadTransaction(ActionEvent event) {
        loadScene(event, "/Transaction.fxml", "Transaction");
    }

    @FXML
    private void handleHelloKittySaveLibraryBtn(ActionEvent event) {
        showAlert("Library", "Hello Kitty has been saved to your library!", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void handleHelloKittyExit(ActionEvent event) {
        loadScene(event, "/Store.fxml", "Store");
    }

    private void loadScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

            LOGGER.info("Successfully switched to " + title);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading " + title, e);
            showAlert("Error", "Failed to load " + title + ": " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
