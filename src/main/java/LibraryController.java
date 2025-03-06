
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class LibraryController {

    private static final Logger LOGGER = Logger.getLogger(LibraryController.class.getName());
    private static final String DB_URL = "jdbc:mysql://localhost:3306/SteamDB?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    @FXML
    private TableView<Game> libraryTable;

    @FXML
    private TableColumn<Game, String> gameColumn;

    @FXML
    private TableColumn<Game, String> purchaseColumn;

    @FXML
    private TableColumn<Game, String> timestampColumn;

    @FXML
    private Button librarystorebtn, librarydeletebtn, librarycommbtn;

    private ObservableList<Game> gameList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTableColumns();
        libraryTable.setItems(gameList);
        loadLibraryData();
    }

    private void setupTableColumns() {
        gameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        purchaseColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseAmount"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
    }

    private void loadLibraryData() {
        gameList.clear();
        String query = "SELECT GameName, Purchase, TimeStamp FROM Library WHERE UserID = ? ORDER BY TimeStamp DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, getCurrentUserID());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String gameName = rs.getString("GameName");
                String purchase = rs.getString("Purchase");
                String timestamp = rs.getTimestamp("TimeStamp").toString();

                gameList.add(new Game(gameName, purchase, timestamp));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to load library data", e);
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load library data.");
        }
    }

    public void addGameToTable(String gameName) {
        Game newGame = new Game(gameName, "Saved", LocalDateTime.now().toString());
        gameList.add(newGame);
        libraryTable.refresh();
    }

    private int getCurrentUserID() {
        return 1; // Placeholder for user authentication
    }

    @FXML
    private void handleLibraryDelete(ActionEvent event) {
        Game selectedGame = libraryTable.getSelectionModel().getSelectedItem();
        if (selectedGame == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No game selected.");
            return;
        }

        String query = "DELETE FROM Library WHERE GameName = ? AND UserID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, selectedGame.getGameName());
            pstmt.setInt(2, getCurrentUserID());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Game successfully deleted from Library!");
                gameList.remove(selectedGame);
                libraryTable.refresh();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while deleting game from Library", e);
            showAlert(Alert.AlertType.ERROR, "Error", "Database issue occurred.");
        }
    }

    @FXML
    private void backtoLibraryStoreHandler(ActionEvent event) {
        navigateToScene(event, "/Store.fxml", "Store");
    }

    @FXML
    private void backtoLibraryCommunityHandler(ActionEvent event) {
        navigateToScene(event, "/Community.fxml", "Community");
    }

    private void navigateToScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
            LOGGER.info("Successfully switched to " + title + ".");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading " + title + ".fxml", e);
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load " + title + ".");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
