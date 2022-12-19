package jupiterpi.vocabulum.core.ta.result;

import jupiterpi.vocabulum.core.i18n.I18n;

import java.util.ArrayList;
import java.util.List;

public class TAResultPunctuation implements TAResult.TAResultItem {
    private String punctuation;

    public TAResultPunctuation(String punctuation) {
        this.punctuation = punctuation;
    }

    public String getPunctuation() {
        return punctuation;
    }

    // ---

    @Override
    public String getItem() {
        return getPunctuation();
    }

    @Override
    public List<String> getLines(I18n i18n) {
        return new ArrayList<>();
    }
}
