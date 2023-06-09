package jupiterpi.vocabulum.core.testutil;

import jupiterpi.vocabulum.core.db.portions.Portion;
import jupiterpi.vocabulum.core.sessions.selection.VocabularySelection;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;

import java.util.List;

public class TestUtil {
    public static Vocabulary makeVocabulary(String base_form) {
        return new Vocabulary(null, new TranslationSequence(VocabularyTranslation.fromString("*translation*").get(0)), "test") {
            @Override
            public String getBaseForm() {
                return base_form;
            }

            @Override
            public String getDefinition() {
                return base_form;
            }

            @Override
            public Kind getKind() {
                return Kind.NOUN;
            }

            @Override
            public List<String> getAllFormsToString() {
                return List.of(base_form);
            }
        };
    }

    public static Portion makePortion(Vocabulary... vocabularies) {
        return new Portion("test", List.of(List.of(vocabularies)));
    }

    @SafeVarargs // (?)
    public static Portion makePortionWithBlocks(List<Vocabulary>... vocabularyBlocks) {
        return new Portion("test", List.of(vocabularyBlocks));
    }

    public static VocabularySelection makeVocabularySelection(Vocabulary... vocabularies) {
        return () -> List.of(vocabularies);
    }
}