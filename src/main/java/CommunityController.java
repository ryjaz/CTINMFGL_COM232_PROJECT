
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

public class CommunityController {

    private static final Logger LOGGER = Logger.getLogger(CommunityController.class.getName());

    // STORE BUTTON
    @FXML
    private Button commstorebtn;

    @FXML
    public void backtoCommStoreHandler(ActionEvent event) {
        loadScene(event, "/Store.fxml", "Store");
    }

    // LIBRARY BUTTON
    @FXML
    private Button commlibrarybtn;

    @FXML
    public void backtoCommLibraryHandler(ActionEvent event) {
        loadScene(event, "/Library.fxml", "Library");
    }

    // COMMON METHOD TO LOAD A SCENE
    private void loadScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

            LOGGER.info("Successfully switched to " + title);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading " + title + " scene.", e);
            showAlert("Error", "Failed to load " + title + ": " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // HELPER METHOD TO SHOW ALERTS
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
