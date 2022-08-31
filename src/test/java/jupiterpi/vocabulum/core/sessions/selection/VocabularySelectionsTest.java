package jupiterpi.vocabulum.core.sessions.selection;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VocabularySelectionsTest {
    @Test
    void union() {
        Vocabulary a1 = makeDummyVocabulary("a1");
        Vocabulary a2 = makeDummyVocabulary("a2");
        Vocabulary b1 = makeDummyVocabulary("b1");
        Vocabulary b2 = makeDummyVocabulary("b2");
        VocabularySelection a = new BasicVocabularySelection(a1, a2);
        VocabularySelection b = new BasicVocabularySelection(b1, b2);
        VocabularySelection e = new BasicVocabularySelection(a1, a2, b1, b2);
        assertEquals(e, VocabularySelections.union(a, b));
    }

    private Vocabulary makeDummyVocabulary(String name) {
        return new Vocabulary(TranslationSequence.fromString("*" + name + "*"), name) {
            @Override
            public String getBaseForm() {
                return name;
            }

            @Override
            public String getDefinition(I18n i18n) {
                return name;
            }

            @Override
            public Kind getKind() {
                return Kind.NOUN;
            }

            @Override
            protected Document generateWordbaseEntrySpecificPart() {
                return null;
            }

            @Override
            protected List<String> getAllFormsToString() {
                return null;
            }
        };
    }
}