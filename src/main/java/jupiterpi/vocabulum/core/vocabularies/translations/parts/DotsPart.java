package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import java.util.regex.Pattern;

public class DotsPart extends TranslationPart {
    @Override
    public String getBasicString() {
        return "...";
    }

    @Override
    public Pattern getRegex() {
        return Pattern.compile("(\\.\\.\\.)?");
    }
}
