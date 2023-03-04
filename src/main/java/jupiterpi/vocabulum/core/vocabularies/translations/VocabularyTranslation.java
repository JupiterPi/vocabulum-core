package jupiterpi.vocabulum.core.vocabularies.translations;

import jupiterpi.vocabulum.core.vocabularies.translations.exchangeables.ExchangeablesPreprocessor;
import jupiterpi.vocabulum.core.vocabularies.translations.exchangeables.StringWithImportance;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.container.InputMatchedPart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.container.TranslationPartContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VocabularyTranslation {
    private boolean important;
    private TranslationPartContainer translation;

    /* constructors */

    public VocabularyTranslation(boolean important, TranslationPartContainer translation) {
        this.important = important;
        this.translation = translation;
    }

    private VocabularyTranslation() {}
    public static List<VocabularyTranslation> fromString(String str) {
        StringWithImportance string = StringWithImportance.fromString(str);
        List<VocabularyTranslation> translations = new ArrayList<>();
        for (String translationStr : new ExchangeablesPreprocessor(string.string()).getResult()) {
            TranslationPartContainer translation = TranslationPartContainer.fromString(false, translationStr);
            translations.add(new VocabularyTranslation(string.important(), translation));
        }
        return translations;
    }

    /* getters, operations */

    public boolean isImportant() {
        return important;
    }

    public String getTranslation() {
        return translation.getBasicString();
    }

    public String getFormattedTranslation() {
        String translation = this.translation.getBasicString();
        if (isImportant()) {
            return "*" + translation + "*";
        } else {
            return translation;
        }
    }

    public String getRegex() {
        return "(?i)" + " *" + translation.getRegex() + " *";
    }

    public boolean isValid(String input) {
        String regex = getRegex();
        return input.matches(regex);
    }

    public List<InputMatchedPart> matchValidInput(String input) {
        return translation.matchValidInput(input);
    }

    /* equals, toString */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VocabularyTranslation that = (VocabularyTranslation) o;
        return important == that.important && Objects.equals(translation, that.translation);
    }

    @Override
    public String toString() {
        return getFormattedTranslation();
    }
}