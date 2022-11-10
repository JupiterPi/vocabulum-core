package jupiterpi.vocabulum.core.vocabularies.translations;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
public class VocabularyTranslationMatchingTest {

    @Nested
    @DisplayName("articles")
    class Articles {

        @Test
        @DisplayName("der/die/das")
        void derDieDas() {
            VocabularyTranslation translation = VocabularyTranslation.fromString("der Freund").get(0);
            assertAll(
                () -> assertTrue(translation.isValid("der Freund")),
                () -> assertTrue(translation.isValid("Freund"))
            );
        }

        @Test
        @DisplayName("einer/eine/eines")
        void einerEineEines() {
            VocabularyTranslation translation = VocabularyTranslation.fromString("ein Freund").get(0);
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
            VocabularyTranslation translation = VocabularyTranslation.fromString("noch (immer)").get(0);
            assertAll(
                    () -> assertTrue(translation.isValid("noch (immer)")),
                    () -> assertTrue(translation.isValid("noch immer")),
                    () -> assertTrue(translation.isValid("noch"))
            );
        }

        @Test
        @DisplayName("word part in parens")
        void wordPartInParens() {
            VocabularyTranslation translation = VocabularyTranslation.fromString("(weg)bringen").get(0);
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
        VocabularyTranslation translation = VocabularyTranslation.fromString("von ... weg").get(0);
        assertAll(
                () -> assertTrue(translation.isValid("von ... weg")),
                () -> assertTrue(translation.isValid("von weg"))
        );
    }

    @Test
    @DisplayName("parens and dots")
    void parensAndDots() {
        VocabularyTranslation translation = VocabularyTranslation.fromString("von (... her)").get(0);
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
        VocabularyTranslation translation = VocabularyTranslation.fromString("(m. Akk.) helfen").get(0);
        assertAll(
                () -> assertTrue(translation.isValid("(m. Akk.) helfen")),
                () -> assertTrue(translation.isValid("m. Akk. helfen")),
                () -> assertTrue(translation.isValid("(mit Akkusativ) helfen")),
                () -> assertTrue(translation.isValid("mit Akkusativ helfen")),
                () -> assertTrue(translation.isValid("helfen"))
        );
    }

    @Test
    @DisplayName("other keywords")
    void otherKeywords() {
        VocabularyTranslation translation = VocabularyTranslation.fromString("Pl. auch viele").get(0);
        assertAll(
            () -> assertTrue(translation.isValid("Pl. auch viele")),
            () -> assertTrue(translation.isValid("Plural auch viele")),
            () -> assertTrue(translation.isValid("Pl. viele")),
            () -> assertFalse(translation.isValid("viele"))
        );
    }

    @Nested
    @DisplayName("whitespaces")
    class Whitespaces {

        @Test
        @DisplayName("valid left out whitespaces")
        void validLeftOutWhitespaces() {
            VocabularyTranslation translation = VocabularyTranslation.fromString("von (... her)").get(0);
            assertTrue(translation.isValid("von (...her)"));
        }

        @Test
        @DisplayName("additional whitespaces")
        void additionalWhitespaces() {
            VocabularyTranslation translation = VocabularyTranslation.fromString("der Freund").get(0);
            assertTrue(translation.isValid("der  Freund"));
        }

    }

    @Test
    @DisplayName("capitalization")
    void capitalization() {
        VocabularyTranslation translation = VocabularyTranslation.fromString("der Freund").get(0);
        assertTrue(translation.isValid("der freund"));
    }

    @Test
    @DisplayName("empty input")
    void emptyInput() {
        VocabularyTranslation translation = VocabularyTranslation.fromString("der Freund").get(0);
        assertFalse(translation.isValid(""));
    }

}