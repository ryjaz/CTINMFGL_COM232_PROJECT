
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ContinueController {

    @FXML
    private Button continuebutton;

    private static final Logger LOGGER = Logger.getLogger(ContinueController.class.getName());

    @FXML
    public void continueButtonHandler(ActionEvent event) {
        try {
            loadScreen(event, "/Home.fxml");  // Ensure this path is correct
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load", e);
        }
    }

    private void loadScreen(ActionEvent event, String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        if (event.getSource() instanceof Node node) {  // ✅ Fixed: Pattern matching
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
