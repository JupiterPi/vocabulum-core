package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class TranslationPartContainerTest {
    @Test
    void fromString() {
        TranslationPartContainer part = TranslationPartContainer.fromString(false, "(hi) ... der Freund");
        TranslationPartContainer e = new TranslationPartContainer(
                new TranslationPartContainer(true, List.of(new PlainTextPart("hi"))),
                new DotsPart(),
                new ArticlePart("der"),
                new PlainTextPart("Freund")
        );
        assertEquals(e, part);
    }

    @Nested
    @DisplayName("operations")
    class Operations {

        TranslationPartContainer container;

        @BeforeEach
        void init() {
            container = new TranslationPartContainer(
                    new TranslationPartContainer(true, List.of(new PlainTextPart("hi"))),
                    new DotsPart(),
                    new ArticlePart("der"),
                    new PlainTextPart("Freund")
            );
        }

        @Test
        void getBasicString() {
            TranslationPartContainer container = new TranslationPartContainer(
                    new TranslationPartContainer(true, List.of(new PlainTextPart("hi"))),
                    new DotsPart(),
                    new ArticlePart("der"),
                    new PlainTextPart("Freund")
            );
            assertEquals("(hi) ... der Freund", container.getBasicString());
        }

        @Test
        void getRegex() {
            TranslationPartContainer container = new TranslationPartContainer(
                    new DotsPart(),
                    new PlainTextPart("Freund")
            );
            assertEquals("(\\.\\.\\.)? \\QFreund\\E", container.getRegex());
        }

    }
}