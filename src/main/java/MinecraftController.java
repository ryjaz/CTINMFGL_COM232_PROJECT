
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MinecraftController {

    private static final Logger LOGGER = Logger.getLogger(MinecraftController.class.getName());
    private static final String DB_URL = "jdbc:mysql://localhost:3306/SteamDB?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    @FXML
    private Button minecraftBuybtn, minecraftSavebtn, minecraftExitBtn, librarystorebtn, librarycommbtn, librarydeletebtn;

    @FXML
    private TextArea minecraftPriceDisplay;

    @FXML
    private TextField gameName;

    @FXML
    private TableView<Game> libraryTable;

    @FXML
    private TableColumn<Game, String> gameNameColumn;

    @FXML
    private TableColumn<Game, String> timestampColumn;

    @FXML
    private TableColumn<Game, String> purchaseColumn;

    private ObservableList<Game> gameList = FXCollections.observableArrayList();

    private String selectedGame;

    public void setSelectedGame(String game) {
        this.selectedGame = game;
    }

    @FXML
    public void initialize() {
        if (libraryTable != null) {
            setupLibraryTable();
            clearFields();
            loadLibraryGames();
            if (selectedGame != null) {
                loadGamePrice(selectedGame);
            }
        } else {
            LOGGER.severe("Library Table is null in initialize method");
        }
    }

    private void setupLibraryTable() {
        if (gameNameColumn != null && timestampColumn != null && purchaseColumn != null) {
            gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));
            timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
            purchaseColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            libraryTable.setItems(gameList);
        } else {
            LOGGER.severe("One or more TableColumns are null in setupLibraryTable method");
        }
    }

    private void clearFields() {
        if (minecraftPriceDisplay != null) {
            minecraftPriceDisplay.clear();
        }
        if (gameName != null) {
            gameName.clear();
        }
    }

    @FXML
    private void handleMinecraftBuybtn(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Info", "Buy button clicked!");
    }

    @FXML
    private void handleMinecraftSaveLibrarybtn(ActionEvent event) {
        String gameNameValue = gameName.getText().trim();

        if (gameNameValue.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Game name cannot be empty.");
            return;
        }

        int gameID = getGameID(gameNameValue);
        if (gameID == -1) {
            showAlert(Alert.AlertType.ERROR, "Error", "Game not found in database.");
            return;
        }

        String query = "INSERT INTO Library (UserID, GameID, TimeStamp) VALUES (?, ?, NOW())";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, getCurrentUserID());
            pstmt.setInt(2, gameID);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Game successfully added to Library!");
                loadLibraryGames();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while saving game to Library", e);
            showAlert(Alert.AlertType.ERROR, "Error", "Database issue occurred.");
        }
    }

    private void loadLibraryGames() {
        gameList.clear();
        String query = "SELECT g.GameName, l.TimeStamp FROM Library l JOIN Game g ON l.GameID = g.GameID WHERE l.UserID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, getCurrentUserID());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String gameName = rs.getString("GameName");
                String timestamp = rs.getString("TimeStamp");
                gameList.add(new Game(gameName, "Purchased", timestamp));
            }
            libraryTable.refresh();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading library games", e);
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load library games.");
        }
    }

    private int getGameID(String gameName) {
        String query = "SELECT GameID FROM Game WHERE GameName = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, gameName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("GameID");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching GameID", e);
        }
        return -1;
    }

    private int getCurrentUserID() {
        return 1; // Placeholder for user authentication
    }

    private void loadGamePrice(String gameName) {
        String query = "SELECT FinalAmount FROM Game WHERE GameName = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, gameName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                minecraftPriceDisplay.setText("Rp. " + rs.getInt("FinalAmount"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error loading game price", e);
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load game price.");
        }
    }

    @FXML
    private void handleMinecraftExit(ActionEvent event) {
        navigateToScene(event, "/Store.fxml", "Store");
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
