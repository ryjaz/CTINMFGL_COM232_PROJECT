
import java.io.IOException;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminGamesController {

    private static final Logger LOGGER = Logger.getLogger(AdminGamesController.class.getName());
    private static final String DB_URL = "jdbc:mysql://localhost:3306/SteamDB?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    @FXML
    private Button adminexitbtn, admingameupdate, admingamedelete, saveGameButton;

    @FXML
    private TableView<Game> admingametable;

    @FXML
    private TableColumn<Game, Integer> gameIDColumn, originalPriceColumn, discountPercentColumn, finalAmountColumn;

    @FXML
    private TableColumn<Game, String> gameNameColumn;

    @FXML
    private TextField adminminecraftdisplay, adminsimsdisplay, adminhellokittydisplay, admincrossfiredisplay;

    private ObservableList<Game> gameList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupTable();
        admingametable.setItems(gameList);
        loadGameData();
        clearFields();
        admingametable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Listen for row selection and load data into text fields
        admingametable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadSelectedGameData(newSelection);
            }
        });
    }

    private void setupTable() {
        gameIDColumn.setCellValueFactory(new PropertyValueFactory<>("gameID"));
        gameNameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        originalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("originalPrice"));
        discountPercentColumn.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));
        finalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("finalAmount"));
    }

    private void loadGameData() {
        gameList.setAll(fetchGameData());
    }

    private void clearFields() {
        adminminecraftdisplay.clear();
        adminsimsdisplay.clear();
        adminhellokittydisplay.clear();
        admincrossfiredisplay.clear();
    }

    private void loadSelectedGameData(Game selectedGame) {
        clearFields();

        // Normalize game name by trimming spaces and converting to lowercase
        String gameName = selectedGame.getGameName().trim().toLowerCase();

        switch (gameName) {
            case "minecraft":
                adminminecraftdisplay.setText(String.valueOf(selectedGame.getFinalAmount()));
                break;
            case "sims":
                adminsimsdisplay.setText(String.valueOf(selectedGame.getFinalAmount()));
                break;
            case "hello kitty":
                adminhellokittydisplay.setText(String.valueOf(selectedGame.getFinalAmount()));  // FIXED
                break;
            case "crossfire":
                admincrossfiredisplay.setText(String.valueOf(selectedGame.getFinalAmount()));
                break;
            default:
                showAlert(Alert.AlertType.WARNING, "Unknown Game", "Game name does not match any text field.");
                break;
        }
    }

    @FXML
    private void handleSaveGame(ActionEvent event) {
        Game selectedGame = admingametable.getSelectionModel().getSelectedItem();
        if (selectedGame == null) {
            showAlert(Alert.AlertType.ERROR, "No Game Selected", "Please select a game to save.");
            return;
        }

        String updatedPriceText = "";
        switch (selectedGame.getGameName().trim().toLowerCase()) {
            case "minecraft":
                updatedPriceText = adminminecraftdisplay.getText().trim();
                break;
            case "sims":
                updatedPriceText = adminsimsdisplay.getText().trim();
                break;
            case "hello kitty":
                updatedPriceText = adminhellokittydisplay.getText().trim();
                break;
            case "crossfire":
                updatedPriceText = admincrossfiredisplay.getText().trim();
                break;
        }

        if (updatedPriceText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty Field", "Please enter a valid price.");
            return;
        }

        try {
            int updatedPrice = Integer.parseInt(updatedPriceText);
            GameDataService.savePriceToDatabase(selectedGame.getGameName(), updatedPrice);

            // Refresh Table and Display Fields
            loadGameData();
            showAlert(Alert.AlertType.INFORMATION, "Game Saved", "Changes have been saved successfully.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid number.");
        }
    }

    private void reselectGame(int gameID) {
        for (Game game : admingametable.getItems()) {
            if (game.getGameID() == gameID) {
                admingametable.getSelectionModel().select(game);
                loadSelectedGameData(game);
                break;
            }
        }
    }

    private void updateFinalAmount(int gameID, int newPrice) {
        String query = "UPDATE Game SET FinalAmount = ? WHERE GameID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newPrice);
            stmt.setInt(2, gameID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating game price", e);
        }
    }

    @FXML
    private void handleDeleteGame(ActionEvent event) {
        Game selectedGame = admingametable.getSelectionModel().getSelectedItem();

        if (selectedGame == null) {
            showAlert(Alert.AlertType.ERROR, "No Game Selected", "Please select a game to delete.");
            return;
        }

        // Confirm deletion
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Game");
        confirmationAlert.setHeaderText("Are you sure you want to delete " + selectedGame.getGameName() + "?");
        confirmationAlert.setContentText("This action cannot be undone.");

        if (confirmationAlert.showAndWait().get() == ButtonType.OK) {
            deleteGameFromDatabase(selectedGame.getGameID());

            // Refresh the table view
            loadGameData();
            showAlert(Alert.AlertType.INFORMATION, "Game Deleted", selectedGame.getGameName() + " has been deleted successfully.");
        }
    }

    private void deleteGameFromDatabase(int gameID) {
        String query = "DELETE FROM Game WHERE GameID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, gameID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting game", e);
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete the selected game.");
        }
    }

    private ObservableList<Game> fetchGameData() {
        ObservableList<Game> games = FXCollections.observableArrayList();
        String query = "SELECT GameID, GameName, OriginalPrice, DiscountPercent, FinalAmount FROM Game";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                games.add(new Game(
                        rs.getInt("GameID"),
                        rs.getString("GameName"),
                        rs.getInt("OriginalPrice"),
                        rs.getInt("DiscountPercent"),
                        rs.getInt("FinalAmount")
                ));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching game data", e);
        }
        return games;
    }

    @FXML
    private void handleExitButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminStore.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("AdminStore");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load AdminStore.fxml");
            LOGGER.log(Level.SEVERE, "Error loading AdminStore.fxml", e);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
