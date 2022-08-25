package jupiterpi.vocabulum.core.vocabularies.translations;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertAll;

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
                () -> translation.isValid("der Freund"),
                () -> translation.isValid("Freund")
            );
        }

        @Test
        @DisplayName("einer/eine/eines")
        void einerEineEines() {
            VocabularyTranslation translation = VocabularyTranslation.fromString("ein Freund");
            assertAll(
                    () -> translation.isValid("ein Freund"),
                    () -> translation.isValid("Freund")
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
                    () -> translation.isValid("noch (immer)"),
                    () -> translation.isValid("noch immer"),
                    () -> translation.isValid("noch")
            );
        }

        @Test
        @DisplayName("word part in parens")
        void wordPartInParens() {
            VocabularyTranslation translation = VocabularyTranslation.fromString("(weg)bringen");
            assertAll(
                    () -> translation.isValid("(weg)bringen"),
                    () -> translation.isValid("wegbringen"),
                    () -> translation.isValid("bringen")
            );
        }

    }

    @Test
    @DisplayName("dots")
    void dots() {
        VocabularyTranslation translation = VocabularyTranslation.fromString("von ... weg");
        assertAll(
                () -> translation.isValid("von ... weg"),
                () -> translation.isValid("von weg")
        );
    }

    @Test
    @DisplayName("parens and dots")
    void parensAndDots() {
        VocabularyTranslation translation = VocabularyTranslation.fromString("von (... her)");
        assertAll(
                () -> translation.isValid("von (... her)"),
                () -> translation.isValid("von (her)"),
                () -> translation.isValid("von ... her"),
                () -> translation.isValid("von her")
        );
    }

    @Test
    @DisplayName("abbreviations")
    void abbreviations() {
        VocabularyTranslation translation = VocabularyTranslation.fromString("(m. Akk.) helfen");
        assertAll(
                () -> translation.isValid("(m. Akk.) helfen"),
                () -> translation.isValid("m. Akk. helfen"),
                () -> translation.isValid("(mit Akkusativ) helfen"),
                () -> translation.isValid("mit Akkusativ helfen"),
                () -> translation.isValid("helfen")
        );
    }

}