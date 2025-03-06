
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminLoginController {

    private static final Logger LOGGER = Logger.getLogger(AdminLoginController.class.getName());

    @FXML
    private Label passwordlabel;

    @FXML
    private Label usernamelabel;

    @FXML
    private TextField adminusernametextfield;

    @FXML
    private PasswordField adminpasswordtextfield;

    @FXML
    private Button adminloginbutton;

    @FXML
    public void AdminLoginButtonHandler(ActionEvent event) {
        if (adminusernametextfield == null || adminpasswordtextfield == null || adminloginbutton == null) {
            LOGGER.severe("UI components not properly initialized!");
            showAlert("Error", "UI components not loaded properly.", Alert.AlertType.ERROR);
            return;
        }

        // Get input values
        String adminuname = adminusernametextfield.getText().trim();
        String adminpword = adminpasswordtextfield.getText();

        if (adminuname.isEmpty() || adminpword.isEmpty()) {
            showAlert("Admin Login Failed", "Username and password cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        // Validate admin login
        boolean isValid = DatabaseHandler.validateAdminLogin(adminuname, adminpword);

        if (isValid) {
            LOGGER.log(Level.INFO, "Login successful for admin: {0}", adminuname);
            showAlert("Login Successful", "Welcome, " + adminuname + "!", Alert.AlertType.INFORMATION);
            loadAdminStore(event);
        } else {
            LOGGER.log(Level.WARNING, "Admin login failed for user: {0}", adminuname);
            showAlert("Login Failed", "Invalid username or password.", Alert.AlertType.ERROR);
        }
    }

    private void loadAdminStore(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminStore.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Store");
            stage.show();
            LOGGER.info("Successfully switched to AdminStore.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading AdminStore.fxml", e);
            showAlert("Error", "Failed to load Admin Store: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initialize() {
        LOGGER.info("AdminLoginController initialized.");
    }
}
