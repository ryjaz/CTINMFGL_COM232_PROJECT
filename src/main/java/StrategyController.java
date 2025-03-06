
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

public class StrategyController {

    private static final Logger LOGGER = Logger.getLogger(StrategyController.class.getName());

    @FXML
    private Button strategystorebtn;
    @FXML
    private Button strategylibrarybtn;
    @FXML
    private Button strategycommbtn;
    @FXML
    private Button actionbtn;
    @FXML
    private Button strategybtn;
    @FXML
    private Button adventurecasualbtn;
    @FXML
    private Button stimulationbtn;
    @FXML
    private Button crossfirebtn;

    @FXML
    public void backtoStrategyStoreHandler(ActionEvent event) {
        loadFXML("Store.fxml", "Store", event);
    }

    @FXML
    public void backtoStrategyLibraryHandler(ActionEvent event) {
        loadFXML("Library.fxml", "Library", event);
    }

    @FXML
    public void backtoStrategyCommunityHandler(ActionEvent event) {
        loadFXML("Community.fxml", "Community", event);
    }

    @FXML
    public void actionbtnHandler(ActionEvent event) {
        LOGGER.info("Action button clicked.");
    }

    @FXML
    public void strategybtnHandler(ActionEvent event) {
        LOGGER.info("Strategy button clicked.");
    }

    @FXML
    public void adventurecasualbtnHandler(ActionEvent event) {
        LOGGER.info("Adventure & Casual button clicked.");
    }

    @FXML
    public void stimulationbtnHandler(ActionEvent event) {
        LOGGER.info("Stimulation button clicked.");
    }

    @FXML
    public void crossfirebtnHandler(ActionEvent event) {
        LOGGER.info("Crossfire button clicked.");
    }

    /**
     * Generic method to load an FXML file and switch scenes.
     *
     * @param fxmlFileName Name of the FXML file
     * @param title Title of the new scene
     * @param event Action event triggering the scene change
     */
    private void loadFXML(String fxmlFileName, String title, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

            LOGGER.info("Successfully switched to " + fxmlFileName);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading " + fxmlFileName, e);
            showAlert("Error", "Failed to load " + title + ": " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Helper method to display an alert box.
     *
     * @param title Title of the alert
     * @param message Message to display
     * @param alertType Type of alert (INFO, WARNING, ERROR)
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
