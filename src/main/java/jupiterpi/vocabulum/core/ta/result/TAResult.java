package jupiterpi.vocabulum.core.ta.result;

import java.util.List;

public class TAResult {
    private List<TAResultItem> items;

    public TAResult(List<TAResultItem> items) {
        this.items = items;
    }

    public List<TAResultItem> getItems() {
        return items;
    }

    // items

    public interface TAResultItem {
        String getItem();
        List<String> getLines();
    }
}