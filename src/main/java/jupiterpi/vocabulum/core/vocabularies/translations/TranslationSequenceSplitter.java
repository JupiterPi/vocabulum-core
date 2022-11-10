package jupiterpi.vocabulum.core.vocabularies.translations;

import java.util.ArrayList;
import java.util.List;

public class TranslationSequenceSplitter {
    private List<String> result;

    public TranslationSequenceSplitter(String str) {
        result = split(str);
    }

    public List<String> getResult() {
        return result;
    }

    /* splitter */

    private List<String> split(String str) {
        List<String> result = new ArrayList<>();

        String buffer = "";
        boolean inParens = false;

        String[] characters = str.split("");
        for (int i = 0; i < characters.length; i++) {
            String c = characters[i];

            if (c.equals("(")) {
                inParens = true;
            }
            if (c.equals(")")) {
                inParens = false;
            }

            if (!inParens && c.equals(",")) {
                result.add(buffer);
                buffer = "";
                if (characters[i+1].equals(" ")) i++;
            } else {
                buffer += c;
            }
        }
        result.add(buffer);

        return result;
    }
}
