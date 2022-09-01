package jupiterpi.vocabulum.core.vocabularies.translations.parts;

public abstract class TranslationPart {
    public abstract String getBasicString();

    public abstract String getRegex();

    public abstract String getNonNullRegex();

    @Override
    public String toString() {
        return getBasicString();
    }

    // custom equals()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslationPart that = (TranslationPart) o;
        return that.getBasicString().equals(getBasicString());
    }
}