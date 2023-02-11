package jupiterpi.vocabulum.core.ta.result;

import java.util.List;

/**
 * Result of a sentence analysis provided by <code>TranslationAssistance#getResult()</code>.
 * @see jupiterpi.vocabulum.core.ta.TranslationAssistance
 */
public class TAResult {
    private List<TAResultItem> items;

    public TAResult(List<TAResultItem> items) {
        this.items = items;
    }

    /**
     * @return the items that make up this result
     * @see TAResultItem
     */
    public List<TAResultItem> getItems() {
        return items;
    }

    // items

    /**
     * Interface for an item that makes up a <code>TAResult</code>.
     * @see TAResultPunctuation
     * @see TAResultWord
     */
    public interface TAResultItem {
        /**
         * @return the string that represents this item
         */
        String getItem();

        /**
         * It's better to use the item's built-in methods instead by typecasting it.
         * @return lines of text that describe this item
         */
        List<String> getLines();
    }
}