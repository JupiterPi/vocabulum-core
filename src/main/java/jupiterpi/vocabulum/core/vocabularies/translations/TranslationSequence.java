package jupiterpi.vocabulum.core.vocabularies.translations;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TranslationSequence extends ArrayList<VocabularyTranslation> {
    public TranslationSequence(VocabularyTranslation... translations) {
        super(List.of(translations));
    }

    public static TranslationSequence fromString(String str) {
        TranslationSequence translationSequence = new TranslationSequence();
        for (String translationStr : new TranslationSequenceSplitter(str).getResult()) {
            translationSequence.addAll(VocabularyTranslation.fromString(translationStr));
        }
        return translationSequence;
    }
    public static TranslationSequence readFromDocument(Document document) {
        List<String> translationsStr = document.getList("translations", String.class);
        TranslationSequence translations = new TranslationSequence();
        for (String translationStr : translationsStr) {
            translations.addAll(VocabularyTranslation.fromString(translationStr));
        }
        return translations;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "TranslationSequence{" + String.join(", ", this.stream().map(VocabularyTranslation::getFormattedTranslation).toList()) + "}";
    }

    /* validation */

    public List<ValidatedTranslation> validateInput(String input) {
        String[] parts = input.split("(,|;|/)");
        List<ValidatedTranslation> result = new ArrayList<>();
        for (VocabularyTranslation translation : this) {
            boolean found = false;
            for (String part : parts) {
                if (translation.isValid(part)) {
                    while (part.startsWith(" ")) {
                        part = part.substring(1);
                    }
                    while (part.endsWith(" ")) {
                        part = part.substring(0, part.length() - 1);
                    }
                    result.add(new ValidatedTranslation(true, part, translation));
                    found = true;
                    break;
                }
            }
            if (!found) result.add(new ValidatedTranslation(false, "", translation));
        }
        return result;
    }

    public static class ValidatedTranslation {
        private boolean valid;
        private String input;
        private VocabularyTranslation vocabularyTranslation;

        public ValidatedTranslation(boolean valid, String input, VocabularyTranslation vocabularyTranslation) {
            this.valid = valid;
            this.input = input;
            this.vocabularyTranslation = vocabularyTranslation;
        }

        public boolean isValid() {
            return valid;
        }

        public String getInput() {
            return input;
        }

        public VocabularyTranslation getVocabularyTranslation() {
            return vocabularyTranslation;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ValidatedTranslation that = (ValidatedTranslation) o;
            return valid == that.valid && Objects.equals(input, that.input) && Objects.equals(vocabularyTranslation, that.vocabularyTranslation);
        }

        @Override
        public String toString() {
            return "ValidatedTranslation{" +
                    "valid=" + valid +
                    ", input='" + input + '\'' +
                    ", vocabularyTranslation=" + vocabularyTranslation +
                    '}';
        }
    }
}
