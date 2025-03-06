
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

public class StoreController {

    private static final Logger LOGGER = Logger.getLogger(StoreController.class.getName());

    //FOR LIBRARY
    @FXML
    private Button librarybtn;

    @FXML
    private void librarybtnHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Library.fxml"));
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

    //FOR COMMUNITY
    @FXML
    private Button commbtn;

    @FXML
    public void commbtnHandler(ActionEvent event) {
        loadCommunity(event);
    }

    private void loadCommunity(ActionEvent event) {
        try {
            // ✅ Corrected FXML Path (Relative to resources folder)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Community.fxml"));

            // ✅` Load FXML and set the scene
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

    //FOR ACTION BUTTON 
    @FXML
    private Button actionbtn;

    @FXML
    public void actionbtnHandler(ActionEvent event) {
        loadAction(event);
    }

    private void loadAction(ActionEvent event) {
        try {
            // ✅ Corrected FXML Path (Relative to resources folder)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Action.fxml"));

            // ✅ Load FXML and set the scene
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Action");
            stage.show();

            LOGGER.info("Successfully switched to Action.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Action.fxml", e);
            showAlert("Error", "Failed to load Action: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    //FOR STRATEGY
    @FXML
    private Button strategybtn;

    @FXML
    public void strategybtnHandler(ActionEvent event) {
        loadStrategy(event);
    }

    private void loadStrategy(ActionEvent event) {
        try {
            // ✅ Corrected FXML Path (Relative to resources folder)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Strategy.fxml"));

            // ✅` Load FXML and set the scene
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Strategy");
            stage.show();

            LOGGER.info("Successfully switched to Strategy.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Strategy.fxml", e);
            showAlert("Error", "Failed to load Strategy: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    //FOR ADVENTURE CASUAL
    @FXML
    private Button adventurecasualbtn;

    @FXML
    public void adventurecasualbtnHandler(ActionEvent event) {
        loadAdventureCasual(event);
    }

    private void loadAdventureCasual(ActionEvent event) {
        try {
            // ✅ Corrected FXML Path (Relative to resources folder)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdventureCasual.fxml"));

            // ✅ Load FXML and set the scene
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("AdventureCasual");
            stage.show();

            LOGGER.info("Successfully switched to AdventureCasual.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading AdventureCasual.fxml", e);
            showAlert("Error", "Failed to load AdventureCasual: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    //FOR STIMULATION
    @FXML
    private Button stimulationbtn;

    @FXML
    public void stimulationbtnHandler(ActionEvent event) {
        loadStoreStimulation(event);
    }

    private void loadStoreStimulation(ActionEvent event) {
        try {
            // ✅ Corrected FXML Path (Relative to resources folder)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Stimulation.fxml"));

            // ✅ Load FXML and set the scene
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Stimulation");
            stage.show();

            LOGGER.info("Successfully switched to Stimulation.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Stimulation.fxml", e);
            showAlert("Error", "Failed to load Stimulation: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    //SIMS
    @FXML
    private Button simsbtn;

    @FXML
    private void handleSimsbtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Sims.fxml"));
            Parent root = loader.load();

            // ✅ Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sims");
            stage.show();

            LOGGER.info("Successfully switched to Sims.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Sims.fxml", e);
        }

    }

    //HELLO KITTY 
    @FXML
    private Button hellokittybtn;

    @FXML
    private void handleHelloKittybtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HelloKitty.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hello Kitty");
            stage.show();

            LOGGER.info("Successfully switched to HelloKitty.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading HelloKitty.fxml", e);
            showAlert("Error", "Failed to load HelloKitty.fxml: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();  // Add this to print error details in console
        }
    }

    //CROSSFIRE
    @FXML
    private Button crossfirebtn;

    @FXML
    private void handleCrossfirebtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Crossfire.fxml"));
            Parent root = loader.load();

            // ✅ Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Crossfire");
            stage.show();

            LOGGER.info("Successfully switched to Crossfire.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Crossfire.fxml", e);
        }
    }

    //MINECRAFT
    @FXML
    private Button minecraftbtn;

    @FXML
    private void handleMinecraftbtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Minecraft.fxml"));
            Parent root = loader.load();

            // ✅ Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Minecraft");
            stage.show();

            LOGGER.info("Successfully switched to Minecraft.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Minecraft.fxml", e);
        }
    }

    //BACK OR EXIT BUTTON
    @FXML
    private Button storebackbtn;

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            // ✅ Ensure correct path to AdminStore.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            // ✅ Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

            LOGGER.info("Successfully switched to Login.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading Login.fxml", e);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);

        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
