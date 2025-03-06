
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

public class ActionController {

    private static final Logger LOGGER = Logger.getLogger(ActionController.class.getName());

    // Store Button
    @FXML
    private Button actionstorebtn;

    @FXML
    public void backtoActionStoreHandler(ActionEvent event) {
        loadFXML("Store.fxml", "Store", event);
    }

    // Library Button
    @FXML
    private Button actionlibrarybtn;

    @FXML
    public void backtoActionLibraryHandler(ActionEvent event) {
        loadFXML("Library.fxml", "Library", event);
    }

    // Community Button
    @FXML
    private Button actioncommbtn;

    @FXML
    public void backtoActionCommunityHandler(ActionEvent event) {
        loadFXML("Community.fxml", "Community", event);
    }

    // Action Category Button
    @FXML
    private Button actionbtn;

    @FXML
    public void actionbtnHandler(ActionEvent event) {
        LOGGER.info("Action button clicked.");
    }

    // Strategy Category Button
    @FXML
    private Button strategybtn;

    @FXML
    public void strategybtnHandler(ActionEvent event) {
        LOGGER.info("Strategy button clicked.");
    }

    // Adventure & Casual Category Button
    @FXML
    private Button adventurecasualbtn;

    @FXML
    public void adventurecasualbtnHandler(ActionEvent event) {
        LOGGER.info("Adventure & Casual button clicked.");
    }

    // Stimulation Category Button
    @FXML
    private Button stimulationbtn;

    @FXML
    public void stimulationbtnHandler(ActionEvent event) {
        LOGGER.info("Stimulation button clicked.");
    }

    /**
     * Generic method to load an FXML file and switch the scene.
     *
     * @param fxmlFileName Name of the FXML file
     * @param title Title of the new scene
     * @param event The action event triggering the scene change
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

    @FXML
    private Button actioncrossfirebtn;

    @FXML
    private void handleActionCrossfire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Crossfire.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Crossfire");
            stage.show();

            LOGGER.info("Successfully switched to Crossfire.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Crossfire.fxml", e);
            showAlert("Error", "Failed to load Crossfire: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private Button actionminecraftbtn;

    @FXML
    private void handleActionMinecraft(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Minecraft.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Minecraft");
            stage.show();

            LOGGER.info("Successfully switched to Minecraft.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Minecraft.fxml", e);
            showAlert("Error", "Failed to load Minecraft: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Helper method to display an alert box.
     *
     * @param title The title of the alert
     * @param message The message to display
     * @param alertType The type of alert (INFO, WARNING, ERROR)
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
