package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AbbreviationPart extends TranslationPart {
    private String abbreviation;
    private List<String> fullTexts;

    public AbbreviationPart(String abbreviation, List<String> fullTexts) {
        this.abbreviation = abbreviation;
        this.fullTexts = fullTexts;
    }

    @Override
    public String getBasicString() {
        return abbreviation + ".";
    }

    @Override
    public String getRegex() {
        List<String> strings = new ArrayList<>();
        strings.add(abbreviation + ".");
        strings.addAll(fullTexts);
        strings = strings.stream().map(Pattern::quote).toList();
        return "(" + String.join("|", strings) + ")";
    }
}