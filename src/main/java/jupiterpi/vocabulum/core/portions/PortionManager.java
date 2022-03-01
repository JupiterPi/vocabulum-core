package jupiterpi.vocabulum.core.portions;

import jupiterpi.vocabulum.core.Database;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class PortionManager {
    private Map<String, Portion> portions;

    public PortionManager() {
        this.portions = readPortions();
    }

    private Map<String, Portion> readPortions() {
        Map<String, Portion> portions = new HashMap<>();
        for (Document portionDocument : Database.portions.find()) {
            Portion portion = Portion.readFromDocument(portionDocument);
            portions.put(portion.getName(), portion);
        }
        return portions;
    }

    public Portion getPortion(String name) {
        return portions.get(name);
    }

    public Map<String, Portion> getPortions() {
        return portions;
    }
}