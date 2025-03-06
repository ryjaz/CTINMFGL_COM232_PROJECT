
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

public class AdventureCasualController {

    private static final Logger LOGGER = Logger.getLogger(AdventureCasualController.class.getName());

    // STORE 
    @FXML
    private Button adventurecasualstorebtn;

    @FXML
    public void backtoAdventureCasualStoreHandler(ActionEvent event) {
        loadStore(event);
    }

    private void loadStore(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Store.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Store");
            stage.show();
            LOGGER.info("Successfully switched to Store.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Store.fxml", e);
            showAlert("Error", "Failed to load Store: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // LIBRARY
    @FXML
    private Button adventurecasuallibrarybtn;

    @FXML
    public void backtoAdventureCasualLibraryHandler(ActionEvent event) {
        loadLibrary(event);
    }

    private void loadLibrary(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Library.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Library");
            stage.show();
            LOGGER.info("Successfully switched to Library.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Library.fxml", e);
            showAlert("Error", "Failed to load Library: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // COMMUNITY
    @FXML
    private Button adventurecasualcommbtn;

    @FXML
    public void backtoAdventureCasualCommunityHandler(ActionEvent event) {
        loadCommunity(event);
    }

    private void loadCommunity(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Community.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Community");
            stage.show();
            LOGGER.info("Successfully switched to Community.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Community.fxml", e);
            showAlert("Error", "Failed to load Community: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Alert Method
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
