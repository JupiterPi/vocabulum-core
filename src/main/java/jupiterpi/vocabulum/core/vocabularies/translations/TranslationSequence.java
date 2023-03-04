package jupiterpi.vocabulum.core.vocabularies.translations;

import java.util.ArrayList;
import java.util.List;

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
                part = part.trim();
                if (translation.isValid(part)) {
                    result.add(new ValidatedTranslation(true, part, translation));
                    found = true;
                    break;
                }
            }
            if (!found) result.add(new ValidatedTranslation(false, "", translation));
        }
        return result;
    }

    public record ValidatedTranslation(
            boolean valid,
            String input,
            VocabularyTranslation vocabularyTranslation
    ) {}
}
