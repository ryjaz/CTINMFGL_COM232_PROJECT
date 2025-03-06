
public class LoggedInUser {

    private static String username;
    private static int userID; // Store UserID

    public static void setUsername(String user) {
        username = user;
    }

    public static String getUsername() {
        return username;
    }

    // Set UserID along with username
    public static void setUserID(int id) {
        userID = id;
    }

    // Get the logged-in UserID
    public static int getUserID() {
        return userID;
    }
}
