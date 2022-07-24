package jupiterpi.vocabulum.core.vocabularies.translations;

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
    public String toString() {
        return getFormattedTranslation();
    }
}