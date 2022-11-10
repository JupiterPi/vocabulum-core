package jupiterpi.vocabulum.core.vocabularies.translations;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class VocabularyTranslationTest {
    @Nested
    @DisplayName("fromString()")
    class FromString {

        @Test
        @DisplayName("important = true")
        void importantTrue() {
            List<VocabularyTranslation> ts = VocabularyTranslation.fromString("*der Freund*");
            VocabularyTranslation t = ts.get(0);
            assertAll(
                    () -> assertEquals(1, ts.size()),
                    () -> assertTrue(t.isImportant()),
                    () -> assertEquals("der Freund", t.getTranslation())
            );
        }

        @Test
        @DisplayName("important = false")
        void importantFalse() {
            List<VocabularyTranslation> ts = VocabularyTranslation.fromString("der Kamerad");
            VocabularyTranslation t = ts.get(0);
            assertAll(
                    () -> assertEquals(1, ts.size()),
                    () -> assertFalse(t.isImportant()),
                    () -> assertEquals("der Kamerad", t.getTranslation())
            );
        }

    }
}