
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
import javafx.stage.Stage;

public class AdminStoreController {

    private static final Logger LOGGER = Logger.getLogger(AdminStoreController.class.getName());

    // FOR USERS BUTTON 
    @FXML
    private Button adminmanageusers;

    @FXML
    private void adminmanageusersButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home");
            stage.show();
            LOGGER.info("Successfully switched to Home.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Home.fxml", e);
            showAlert("Error", "Failed to load Home: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // FOR TRANSACTION BUTTON
    @FXML
    private Button admintransactionbtn;

    @FXML
    private void adminTransactionButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminTransaction.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Transaction");
            stage.show();
            LOGGER.info("Successfully switched to AdminTransaction.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading AdminTransaction.fxml", e);
            showAlert("Error", "Failed to load AdminTransaction: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // FOR GAME BUTTON
    @FXML
    private Button adminGameButton;

    @FXML
    private void adminGameButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminGames.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Games");
            stage.show();
            LOGGER.info("Successfully switched to AdminGames.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading AdminGames.fxml", e);
            showAlert("Error", "Failed to load AdminGames: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // BACK TO LOGIN
    @FXML
    private Button backtologinbtn;

    @FXML
    private void backtologinHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();
            LOGGER.info("Successfully switched to Login.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Login.fxml", e);
            showAlert("Error", "Failed to load Login: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // ALERT FUNCTION
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
