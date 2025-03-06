
import java.util.HashMap;
import java.util.Map;

public class GamePriceManager {

    private static final Map<String, Integer> gamePrices = new HashMap<>();

    public static int getPrice(String gameName) {
        return gamePrices.getOrDefault(gameName.toLowerCase(), 0);
    }

    public static void setPrice(String gameName, int price) {
        gamePrices.put(gameName.toLowerCase(), price);
    }

    // Individual getters for backward compatibility
    public static int getHelloKittyPrice() {
        return getPrice("hello kitty");
    }

    public static void setHelloKittyPrice(int price) {
        setPrice("hello kitty", price);
    }

    public static int getCrossfirePrice() {
        return getPrice("crossfire");
    }

    public static void setCrossfirePrice(int price) {
        setPrice("crossfire", price);
    }

    public static int getSimsPrice() {
        return getPrice("sims");
    }

    public static void setSimsPrice(int price) {
        setPrice("sims", price);
    }

    public static int getMinecraftPrice() {
        return getPrice("minecraft");
    }

    public static void setMinecraftPrice(int price) {
        setPrice("minecraft", price);
    }
}
