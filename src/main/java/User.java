
import javafx.beans.property.SimpleStringProperty;

public class User {

    private final SimpleStringProperty username;
    private final SimpleStringProperty password;

    public User(String username, String password) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    } // Required for JavaFX bindings

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    } // Required for JavaFX bindings
}
