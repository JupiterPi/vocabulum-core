package jupiterpi.vocabulum.core.vocabularies.translations;

import java.util.Objects;

public class VocabularyTranslation {
    private boolean important;
    private String translation;

    public VocabularyTranslation(boolean important, String translation) {
        this.important = important;
        this.translation = translation;
    }

    public VocabularyTranslation(String translation) {
        this.important = false;
        this.translation = translation;
    }

    private VocabularyTranslation() {}
    public static VocabularyTranslation fromString(String str) {
        VocabularyTranslation translationObj = new VocabularyTranslation();
        if (str.startsWith("*")) {
            translationObj.important = true;
            translationObj.translation = str.substring(1, str.length()-1);
        } else {
            translationObj.important = false;
            translationObj.translation = str;
        }
        return translationObj;
    }

    public boolean isImportant() {
        return important;
    }

    public String getTranslation() {
        return translation;
    }

    public String getFormattedTranslation() {
        if (isImportant()) {
            return "*" + translation + "*";
        } else {
            return translation;
        }
    }

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