package jupiterpi.vocabulum.core.ta.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a string of punctuation, like periods or commas, that's part of a translation assistance result.
 * @see TAResult
 */
public class TAResultPunctuation implements TAResult.TAResultItem {
    private String punctuation;

    public TAResultPunctuation(String punctuation) {
        this.punctuation = punctuation;
    }

    /**
     * Equivalent to <code>getItem()</code>.
     * @return the string of punctuation
     */
    public String getPunctuation() {
        return punctuation;
    }

    // ---

    @Override
    public String getItem() {
        return getPunctuation();
    }

    @Override
    public List<String> getLines() {
        return new ArrayList<>();
    }
}
