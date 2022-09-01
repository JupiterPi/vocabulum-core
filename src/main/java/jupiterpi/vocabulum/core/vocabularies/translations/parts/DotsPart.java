package jupiterpi.vocabulum.core.vocabularies.translations.parts;

public class DotsPart extends TranslationPart {
    @Override
    public String getBasicString() {
        return "...";
    }

    @Override
    public String getRegex() {
        return "(\\.\\.\\.)?";
    }

    @Override
    public String getNonNullRegex() {
        return "\\.\\.\\.";
    }
}
