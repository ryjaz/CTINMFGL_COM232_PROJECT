
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AdminTransactionController {

    private static final Logger LOGGER = Logger.getLogger(AdminTransactionController.class.getName());

    private int loggedInUserID;

    @FXML
    private TableView<Transaction> TransactionTable;

    @FXML
    private TableColumn<Transaction, Integer> colTransactionID, colUserID, colGameID;

    @FXML
    private TableColumn<Transaction, Double> colFinalAmount;

    @FXML
    private TableColumn<Transaction, String> colPaymentMethod;

    @FXML
    private Button admintransactupdate, admintransactdelete, adminexitbtn;

    private final ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

    private static final String DB_URL = "jdbc:mysql://localhost:3306/SteamDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public void setLoggedInUserID(int userID) {
        this.loggedInUserID = userID;
        loadTransactionData();
    }

    @FXML
    private void initialize() {
        colTransactionID.setCellValueFactory(cellData -> cellData.getValue().transactionIDProperty().asObject());
        colUserID.setCellValueFactory(cellData -> cellData.getValue().userIDProperty().asObject());
        colGameID.setCellValueFactory(cellData -> cellData.getValue().gameIDProperty().asObject());
        colFinalAmount.setCellValueFactory(cellData -> cellData.getValue().finalAmountProperty().asObject());
        colPaymentMethod.setCellValueFactory(cellData -> cellData.getValue().paymentMethodProperty());

        // Load data initially if needed
        loadTransactionData();
    }

    private void loadTransactionData() {
        if (this.loggedInUserID == 0) {
            showAlert("Error", "Invalid User ID. Cannot load transaction data.", Alert.AlertType.ERROR);
            return;
        }

        transactionList.clear();
        String query = "SELECT * FROM TransactionTable WHERE UserID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, this.loggedInUserID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactionList.add(new Transaction(
                        rs.getInt("TransactionID"),
                        rs.getInt("UserID"),
                        rs.getInt("GameID"),
                        rs.getDouble("FinalAmount"),
                        rs.getString("PaymentMethod")
                ));
            }

            TransactionTable.setItems(transactionList);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception: " + e.getMessage(), e);
            showAlert("Error", "Failed to load transaction data.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleAdminTransactionUpdate(ActionEvent event) {
        showAlert("Update", "Transaction update functionality is not yet implemented.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void handleAdminTransactionDelete(ActionEvent event) {
        showAlert("Delete", "Transaction delete functionality is not yet implemented.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void handleAdminExit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Main Menu");
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading MainMenu.fxml", e);
            showAlert("Error", "Failed to return to main menu.", Alert.AlertType.ERROR);
        }
    }

    public void setTransactionData(int userID, String gameName, double gamePrice, String paymentMethod) {
        int gameID = getGameID(gameName);
        if (gameID == -1) {
            return;
        }

        transactionList.add(new Transaction(
                transactionList.size() + 1,
                userID,
                gameID,
                gamePrice,
                paymentMethod
        ));

        TransactionTable.setItems(transactionList);
    }

    private int getGameID(String gameName) {
        String query = "SELECT GameID FROM Game WHERE GameName = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, gameName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("GameID");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching GameID for: " + gameName, e);
        }
        return -1;
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
