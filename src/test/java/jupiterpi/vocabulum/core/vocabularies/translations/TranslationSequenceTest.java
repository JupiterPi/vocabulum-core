package jupiterpi.vocabulum.core.vocabularies.translations;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class TranslationSequenceTest {
    @Test
    @DisplayName("exchangeables")
    void exchangeables() {
        TranslationSequence s = TranslationSequence.fromString("*er/sie läuft*, wir laufen");
        TranslationSequence e = new TranslationSequence(
                VocabularyTranslation.fromString("*er läuft*").get(0),
                VocabularyTranslation.fromString("*sie läuft*").get(0),
                VocabularyTranslation.fromString("wir laufen").get(0)
        );
        assertEquals(e, s);
    }

    @Nested
    @DisplayName("validateInput()")
    class ValidateInput {

        @Test
        @DisplayName("normal")
        void normal() {
            VocabularyTranslation t1 = VocabularyTranslation.fromString("aus").get(0);
            VocabularyTranslation t2 = VocabularyTranslation.fromString("weg").get(0);
            VocabularyTranslation t3 = VocabularyTranslation.fromString("von (... her)").get(0);
            TranslationSequence s = new TranslationSequence(t1, t2, t3);
            List<TranslationSequence.ValidatedTranslation> e = List.of(
                    new TranslationSequence.ValidatedTranslation(false, "", t1),
                    new TranslationSequence.ValidatedTranslation(true, "weg", t2),
                    new TranslationSequence.ValidatedTranslation(true, "von her", t3)
            );
            assertEquals(e, s.validateInput("weg, au, von her"));
        }

        @Test
        @DisplayName("other separators")
        void otherSeparators() {
            VocabularyTranslation t1 = VocabularyTranslation.fromString("t1").get(0);
            VocabularyTranslation t2 = VocabularyTranslation.fromString("t2").get(0);
            VocabularyTranslation t3 = VocabularyTranslation.fromString("t3").get(0);
            VocabularyTranslation t4 = VocabularyTranslation.fromString("t4").get(0);
            TranslationSequence s = new TranslationSequence(t1, t2, t3, t4);
            List<TranslationSequence.ValidatedTranslation> e = List.of(
                    new TranslationSequence.ValidatedTranslation(true, "t1", t1),
                    new TranslationSequence.ValidatedTranslation(true, "t2", t2),
                    new TranslationSequence.ValidatedTranslation(true, "t3", t3),
                    new TranslationSequence.ValidatedTranslation(true, "t4", t4)
            );
            assertEquals(e, s.validateInput("t1,t2;t3/t4"));
        }

    }
}