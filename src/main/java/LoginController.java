
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @FXML
    private TextField usernametextfield;

    @FXML
    private PasswordField passwordtextfield;

    @FXML
    private Button loginbutton;

    @FXML
    private Button adminButton;

    @FXML
    public void loginbuttonHandler(ActionEvent event) {
        if (usernametextfield == null || passwordtextfield == null || loginbutton == null) {
            LOGGER.severe("UI components not properly initialized!");
            showAlert("Error", "UI components not loaded properly.", Alert.AlertType.ERROR);
            return;
        }

        String uname = usernametextfield.getText().trim();
        String pword = passwordtextfield.getText();

        if (uname.isEmpty() || pword.isEmpty()) {
            showAlert("Login Failed", "Username and password cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        boolean isValid = DatabaseHandler.validateLogin(uname, pword);

        if (isValid) {
            // ✅ Store logged-in username
            LoggedInUser.setUsername(uname);

            LOGGER.log(Level.INFO, "Login successful for user: {0}", uname);
            showAlert("Login Successful", "Welcome, " + uname + "!", Alert.AlertType.INFORMATION);
            switchScene(event, "/Store.fxml");
        } else {
            LOGGER.log(Level.WARNING, "Login failed for user: {0}", uname);
            showAlert("Login Failed", "Invalid username or password.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAdminButton(ActionEvent event) {
        LOGGER.info("Admin button clicked. Launching Admin.fxml...");
        switchScene(event, "/Admin.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading " + fxmlFile, e);
            showAlert("Error", "Failed to open panel: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        LOGGER.info("LoginController initialized.");
        if (adminButton == null) {
            LOGGER.severe("adminButton is NULL! Check FXML fx:id.");
        } else {
            LOGGER.info("adminButton is loaded correctly.");
            adminButton.setOnAction(this::handleAdminButton);
        }
    }
}
