
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class HomeController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(HomeController.class.getName());

    private final ObservableList<User> userlist = FXCollections.observableArrayList();

    @FXML
    private TableView<User> mytable;

    @FXML
    private TableColumn<User, String> usernamecol;

    @FXML
    private TableColumn<User, String> passwordcol;

    @FXML
    private Button btncreate;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnupdate;

    @FXML
    private TextField usernametextfield;

    @FXML
    private TextField passwordtextfield;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeCol();
        displayUsers();
    }

    private void initializeCol() {
        usernamecol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordcol.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    private void displayUsers() {
        userlist.clear();

        ResultSet result = DatabaseHandler.getUsers();
        if (result == null) {
            showAlert(Alert.AlertType.ERROR, "Error retrieving users.");
            return;
        }

        try {
            while (result.next()) {
                String username = result.getString("username");
                String password = result.getString("password");

                userlist.add(new User(username, password));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving users", e);
        } finally {
            try {
                result.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing ResultSet", e);
            }
        }

        mytable.setItems(userlist);
    }

    @FXML
    private void createUser(ActionEvent event) {
        String uname = usernametextfield.getText().trim();
        String pword = passwordtextfield.getText().trim();

        if (uname.isEmpty() || pword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Username or password cannot be empty.");
            return;
        }

        // Check if username already exists in the table
        if (doesUserExist(uname)) {
            showAlert(Alert.AlertType.ERROR, "User already exists! Please use a different username.");
            return;
        }

        User user = new User(uname, pword);

        if (DatabaseHandler.addUser(user)) {
            showAlert(Alert.AlertType.INFORMATION, "User Created Successfully.");
            displayUsers();
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to create user.");
        }
    }

    @FXML
    private void deleteUser(ActionEvent event) {
        User user = mytable.getSelectionModel().getSelectedItem();

        if (user == null) {
            showAlert(Alert.AlertType.ERROR, "No user selected.");
            return;
        }

        if (DatabaseHandler.deleteUser(user.getUsername())) {
            showAlert(Alert.AlertType.INFORMATION, "User deleted successfully.");
            displayUsers();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to delete user.");
        }
    }

    @FXML
    private void updateUser(ActionEvent event) {
        String uname = usernametextfield.getText().trim();
        String pword = passwordtextfield.getText().trim();

        if (uname.isEmpty() || pword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Username or password cannot be empty.");
            return;
        }

        User user = new User(uname, pword);

        if (DatabaseHandler.updateUser(user)) {
            showAlert(Alert.AlertType.INFORMATION, "User updated successfully.");
            displayUsers();
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failed to update user.");
        }
    }

    private boolean doesUserExist(String username) {
        for (User user : userlist) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true; // User already exists
            }
        }
        return false;
    }

    private void clearFields() {
        usernametextfield.clear();
        passwordtextfield.clear();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private Button adminexitbtn;

    @FXML
    private void handleExitButton(ActionEvent event) {
        try {
            // ✅ Ensure correct path to AdminStore.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminStore.fxml"));
            Parent root = loader.load();

            // ✅ Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Store");
            stage.show();

            LOGGER.info("Successfully switched to AdminStore.fxml.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading AdminStore.fxml", e);
        }
    }
}
