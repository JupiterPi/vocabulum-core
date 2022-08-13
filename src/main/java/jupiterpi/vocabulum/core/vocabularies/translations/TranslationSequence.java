package jupiterpi.vocabulum.core.vocabularies.translations;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class TranslationSequence extends ArrayList<VocabularyTranslation> {
    public TranslationSequence(VocabularyTranslation... translations) {
        super(List.of(translations));
    }

    public static TranslationSequence fromString(String str) {
        TranslationSequence translationSequence = new TranslationSequence();
        for (String translationStr : str.split(", ")) {
            VocabularyTranslation translation = VocabularyTranslation.fromString(translationStr);
            translationSequence.add(translation);
        }
        return translationSequence;
    }
    public static TranslationSequence readFromDocument(Document document) {
        List<String> translationsStr = document.getList("translations", String.class);
        TranslationSequence translations = new TranslationSequence();
        for (String translationStr : translationsStr) {
            translations.add(VocabularyTranslation.fromString(translationStr));
        }
        return translations;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}