package jupiterpi.vocabulum.core.vocabularies.translations;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockDatabaseSetup.class)
public class VocabularyTranslationMatchingTest {

    @Nested
    @DisplayName("articles")
    class Articles {

        @Test
        @DisplayName("der/die/das")
        void derDieDas() {
            VocabularyTranslation translation = VocabularyTranslation.fromString("der Freund");
            assertAll(
                () -> assertTrue(translation.isValid("der Freund")),
                () -> assertTrue(translation.isValid("Freund"))
            );
        }

        @Test
        @DisplayName("einer/eine/eines")
        void einerEineEines() {
            VocabularyTranslation translation = VocabularyTranslation.fromString("ein Freund");
            assertAll(
                    () -> assertTrue(translation.isValid("ein Freund")),
                    () -> assertTrue(translation.isValid("Freund"))
            );
        }

    }
    
    @Nested
    @DisplayName("parens")
    class Parens {

        @Test
        @DisplayName("words separated")
        void wordsSeparated() {
            VocabularyTranslation translation = VocabularyTranslation.fromString("noch (immer)");
            assertAll(
                    () -> assertTrue(translation.isValid("noch (immer)")),
                    () -> assertTrue(translation.isValid("noch immer")),
                    () -> assertTrue(translation.isValid("noch"))
            );
        }

        @Test
        @DisplayName("word part in parens")
        void wordPartInParens() {
            VocabularyTranslation translation = VocabularyTranslation.fromString("(weg)bringen");
            assertAll(
                    () -> assertTrue(translation.isValid("(weg)bringen")),
                    () -> assertTrue(translation.isValid("wegbringen")),
                    () -> assertTrue(translation.isValid("bringen"))
            );
        }

    }

    @Test
    @DisplayName("dots")
    void dots() {
        VocabularyTranslation translation = VocabularyTranslation.fromString("von ... weg");
        assertAll(
                () -> assertTrue(translation.isValid("von ... weg")),
                () -> assertTrue(translation.isValid("von weg"))
        );
    }

    @Test
    @DisplayName("parens and dots")
    void parensAndDots() {
        VocabularyTranslation translation = VocabularyTranslation.fromString("von (... her)");
        assertAll(
                () -> assertTrue(translation.isValid("von (... her)")),
                () -> assertTrue(translation.isValid("von (her)")),
                () -> assertTrue(translation.isValid("von ... her")),
                () -> assertTrue(translation.isValid("von her"))
        );
    }

    @Test
    @DisplayName("abbreviations")
    void abbreviations() {
        VocabularyTranslation translation = VocabularyTranslation.fromString("(m. Akk.) helfen");
        assertAll(
                () -> assertTrue(translation.isValid("(m. Akk.) helfen")),
                () -> assertTrue(translation.isValid("m. Akk. helfen")),
                () -> assertTrue(translation.isValid("(mit Akkusativ) helfen")),
                () -> assertTrue(translation.isValid("mit Akkusativ helfen")),
                () -> assertTrue(translation.isValid("helfen"))
        );
    }
    
    @Nested
    @DisplayName("whitespaces")
    class Whitespaces {
        
        @Test
        void t1() {
            VocabularyTranslation translation = VocabularyTranslation.fromString("von (... her)");
            assertTrue(translation.isValid("von (...her)"));
        }
        
    }

}