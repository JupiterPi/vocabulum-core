package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import java.util.regex.Pattern;

public class PlainTextPart extends TranslationPart {
    private String text;

    public PlainTextPart(String text) {
        this.text = text;
    }

    @Override
    public String getBasicString() {
        return text;
    }

    @Override
    public String getRegex() {
        return Pattern.quote(text);
    }

    @Override
    public String getNonNullRegex() {
        return getRegex();
    }
}
