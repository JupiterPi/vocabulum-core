package jupiterpi.vocabulum.core.vocabularies.translations;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class VocabularyTranslationTest {
    @Nested
    @DisplayName("fromString()")
    class FromString {

        @Test
        @DisplayName("important = true")
        void importantTrue() {
            VocabularyTranslation t = VocabularyTranslation.fromString("*der Freund*");
            assertAll(
                    () -> assertTrue(t.isImportant()),
                    () -> assertEquals("der Freund", t.getTranslationToString())
            );
        }

        @Test
        @DisplayName("important = false")
        void importantFalse() {
            VocabularyTranslation t = VocabularyTranslation.fromString("der Kamerad");
            assertAll(
                    () -> assertFalse(t.isImportant()),
                    () -> assertEquals("der Kamerad", t.getTranslationToString())
            );
        }

    }
}