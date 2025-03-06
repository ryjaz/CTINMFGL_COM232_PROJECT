
import java.io.Serializable;
import java.util.Objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    private final IntegerProperty gameID;
    private final StringProperty gameName;
    private final IntegerProperty originalPrice;
    private final IntegerProperty discountPercent;
    private final IntegerProperty finalAmount;
    private final StringProperty status;
    private final StringProperty timestamp;
    private final IntegerProperty purchaseAmount;
    private String purchase; // Used for LibraryController

    // ✅ Constructor for Store System (with price & discount)
    public Game(int gameID, String gameName, int originalPrice, int discountPercent, int finalAmount) {
        this.gameID = new SimpleIntegerProperty(gameID);
        this.gameName = new SimpleStringProperty(gameName.trim().toLowerCase());
        this.originalPrice = new SimpleIntegerProperty(originalPrice);
        this.discountPercent = new SimpleIntegerProperty(discountPercent);
        this.finalAmount = new SimpleIntegerProperty(finalAmount > 0 ? finalAmount : calculateFinalAmount(originalPrice, discountPercent));
        this.status = new SimpleStringProperty("Available");

        this.timestamp = new SimpleStringProperty("");
        this.purchaseAmount = new SimpleIntegerProperty(0);
    }

    // ✅ Constructor for LibraryController (Game Library System)
    public Game(String gameName, String purchase, String timeStamp) {
        this.gameID = new SimpleIntegerProperty(0); // Not used in Library
        this.gameName = new SimpleStringProperty(gameName);
        this.originalPrice = new SimpleIntegerProperty(0);
        this.discountPercent = new SimpleIntegerProperty(0);
        this.finalAmount = new SimpleIntegerProperty(0);
        this.status = new SimpleStringProperty("Owned");

        this.purchase = purchase;
        this.timestamp = new SimpleStringProperty(timeStamp);
        this.purchaseAmount = new SimpleIntegerProperty(0);
    }

    // ✅ New Constructor for Library System (Game with purchaseAmount)
    public Game(String gameName, String timestamp, int purchaseAmount) {
        this.gameID = new SimpleIntegerProperty(0);
        this.gameName = new SimpleStringProperty(gameName);
        this.originalPrice = new SimpleIntegerProperty(0);
        this.discountPercent = new SimpleIntegerProperty(0);
        this.finalAmount = new SimpleIntegerProperty(purchaseAmount);
        this.status = new SimpleStringProperty("Owned");

        this.timestamp = new SimpleStringProperty(timestamp);
        this.purchaseAmount = new SimpleIntegerProperty(purchaseAmount);
    }

    // Compute FinalAmount based on OriginalPrice and DiscountPercent
    private static int calculateFinalAmount(int originalPrice, int discountPercent) {
        return originalPrice - (originalPrice * discountPercent / 100);
    }

    // JavaFX property bindings
    public IntegerProperty gameIDProperty() {
        return gameID;
    }

    public StringProperty gameNameProperty() {
        return gameName;
    }

    public IntegerProperty originalPriceProperty() {
        return originalPrice;
    }

    public IntegerProperty discountPercentProperty() {
        return discountPercent;
    }

    public IntegerProperty finalAmountProperty() {
        return finalAmount;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty timestampProperty() {
        return timestamp;
    }

    public IntegerProperty purchaseAmountProperty() {
        return purchaseAmount;
    }

    // Standard getters
    public int getGameID() {
        return gameID.get();
    }

    public String getGameName() {
        return gameName.get();
    }

    public int getOriginalPrice() {
        return originalPrice.get();
    }

    public int getDiscountPercent() {
        return discountPercent.get();
    }

    public int getFinalAmount() {
        return finalAmount.get();
    }

    public String getStatus() {
        return status.get();
    }

    public String getTimestamp() {
        return timestamp.get();
    }

    public int getPurchaseAmount() {
        return purchaseAmount.get();
    }

    // Standard setters
    public void setGameID(int gameID) {
        this.gameID.set(gameID);
    }

    public void setGameName(String gameName) {
        this.gameName.set(gameName.trim().toLowerCase());
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice.set(originalPrice);
        updateFinalAmount();
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent.set(discountPercent);
        updateFinalAmount();
    }

    public void setFinalAmount(int finalAmount) {
        this.finalAmount.set(finalAmount);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setTimestamp(String timestamp) {
        this.timestamp.set(timestamp);
    }

    public void setPurchaseAmount(int purchaseAmount) {
        this.purchaseAmount.set(purchaseAmount);
    }

    // Automatically update finalAmount when originalPrice or discountPercent changes
    private void updateFinalAmount() {
        this.finalAmount.set(calculateFinalAmount(getOriginalPrice(), getDiscountPercent()));
    }

    @Override
    public String toString() {
        return String.format("Game[ID=%d, Name=%s, Price=%d, Discount=%d%%, Final=%d, Status=%s, PurchaseAmount=%d, Timestamp=%s]",
                getGameID(), getGameName(), getOriginalPrice(), getDiscountPercent(), getFinalAmount(), getStatus(), getPurchaseAmount(), getTimestamp());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Game game = (Game) obj;
        return getGameID() == game.getGameID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameID());
    }
}
