
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction {

    private final IntegerProperty transactionID;
    private final IntegerProperty userID;
    private final IntegerProperty gameID;
    private final DoubleProperty finalAmount;
    private final StringProperty paymentMethod;

    public Transaction(int transactionID, int userID, int gameID, double finalAmount, String paymentMethod) {
        this.transactionID = new SimpleIntegerProperty(transactionID);
        this.userID = new SimpleIntegerProperty(userID);
        this.gameID = new SimpleIntegerProperty(gameID);
        this.finalAmount = new SimpleDoubleProperty(finalAmount);
        this.paymentMethod = new SimpleStringProperty(paymentMethod);
    }

    public int getTransactionID() {
        return transactionID.get();
    }

    public IntegerProperty transactionIDProperty() {
        return transactionID;
    }

    public int getUserID() {
        return userID.get();
    }

    public IntegerProperty userIDProperty() {
        return userID;
    }

    public int getGameID() {
        return gameID.get();
    }

    public IntegerProperty gameIDProperty() {
        return gameID;
    }

    public double getFinalAmount() {
        return finalAmount.get();
    }

    public DoubleProperty finalAmountProperty() {
        return finalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod.get();
    }

    public StringProperty paymentMethodProperty() {
        return paymentMethod;
    }

    @Override
    public String toString() {
        return "Transaction{"
                + "TransactionID=" + getTransactionID()
                + ", UserID=" + getUserID()
                + ", GameID=" + getGameID()
                + ", FinalAmount=" + getFinalAmount()
                + ", PaymentMethod='" + getPaymentMethod() + '\''
                + '}';
    }
}
