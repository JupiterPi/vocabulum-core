package jupiterpi.vocabulum.core.vocabularies.translations.parts.container;

import jupiterpi.vocabulum.core.vocabularies.translations.parts.TranslationPart;

import java.util.Objects;

public class InputMatchedPart {
    private TranslationPart translationPart;
    private String decorativeString;
    private boolean matched;
    private String input;

    public InputMatchedPart(TranslationPart translationPart, boolean matched, String input) {
        this.translationPart = translationPart;
        this.matched = matched;
        this.input = input;
    }

    public InputMatchedPart(String decorativeString) {
        this.decorativeString = decorativeString;
    }

    public boolean isDecorative() {
        return translationPart == null;
    }

    public TranslationPart getTranslationPart() {
        return translationPart;
    }

    public String getDecorativeString() {
        return decorativeString;
    }

    public boolean isMatched() {
        return matched;
    }

    public String getInput() {
        return input;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputMatchedPart that = (InputMatchedPart) o;
        return matched == that.matched && Objects.equals(translationPart, that.translationPart) && Objects.equals(input, that.input);
    }

    @Override
    public String toString() {
        return "InputMatchedPart{" +
                "translationPart=" + translationPart +
                ", matched=" + matched +
                ", input='" + input + '\'' +
                '}';
    }
}
