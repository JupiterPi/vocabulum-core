package jupiterpi.vocabulum.core.vocabularies.translations;

import jupiterpi.vocabulum.core.vocabularies.translations.parts.TranslationPartContainer;

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
    public static VocabularyTranslation fromString(String str) {
        boolean important = str.startsWith("*");
        String translationStr = str;
        if (important) {
            translationStr = str.substring(1, str.length()-1);
        }
        TranslationPartContainer translation = TranslationPartContainer.fromString(false, translationStr);
        return new VocabularyTranslation(important, translation);
    }

    /* getters, operations */

    public boolean isImportant() {
        return important;
    }

    public String getTranslationToString() {
        return translation.getBasicString();
    }

    public boolean isValid(String input) {
        return input.matches(translation.getRegex());
    }

    /* equals */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VocabularyTranslation that = (VocabularyTranslation) o;
        return important == that.important && Objects.equals(translation, that.translation);
    }

    /* to string */

    public String getFormattedTranslation() {
        String translation = this.translation.getBasicString();
        if (isImportant()) {
            return "*" + translation + "*";
        } else {
            return translation;
        }
    }

    @Override
    public String toString() {
        return getFormattedTranslation();
    }
}