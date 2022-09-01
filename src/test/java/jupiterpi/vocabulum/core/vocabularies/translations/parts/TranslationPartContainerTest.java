package jupiterpi.vocabulum.core.vocabularies.translations.parts;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.container.InputMatchedPart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.container.TranslationPartContainer;
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
        TranslationPartContainer part = TranslationPartContainer.fromString(false, "(hi) ... m. der Freund");
        TranslationPartContainer e = new TranslationPartContainer(
                new TranslationPartContainer(true, List.of(new PlainTextPart("hi"))),
                new DotsPart(),
                new AbbreviationPart("m", List.of("mit")),
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
            assertEquals(" *(\\.\\.\\.)? *\\QFreund\\E *", container.getRegex());
        }

        @Test
        void getNonNullRegex() {
            TranslationPartContainer container = new TranslationPartContainer(
                    new DotsPart(),
                    new PlainTextPart("Freund")
            );
            assertEquals("\\.\\.\\. *\\QFreund\\E", container.getNonNullRegex());
        }

    }

    @Nested
    @DisplayName("matchValidInput()")
    class MatchValidInput {

        @Test
        @DisplayName("simple")
        void simple() {
            TranslationPart article = new ArticlePart("der");
            TranslationPart noun = new PlainTextPart("Freund");
            TranslationPartContainer p = new TranslationPartContainer(
                    article, noun
            );
            List<InputMatchedPart> e = List.of(
                    new InputMatchedPart(article, false, ""),
                    new InputMatchedPart(" "),
                    new InputMatchedPart(noun, true, "Freund")
            );
            assertEquals(e, p.matchValidInput("Freund"));
        }

        @Test
        @DisplayName("deep")
        void deep() {
            TranslationPart pl1 = new PlainTextPart("von");
            TranslationPart dots = new DotsPart();
            TranslationPart pl2 = new PlainTextPart("her");
            TranslationPart container = new TranslationPartContainer(
                    dots, pl2
            );
            TranslationPartContainer p = new TranslationPartContainer(
                    pl1, container
            );
            List<InputMatchedPart> e = List.of(
                    new InputMatchedPart(pl1, true, "von"),
                    new InputMatchedPart(" "),
                    new InputMatchedPart("("),
                    new InputMatchedPart(dots, false, ""),
                    new InputMatchedPart(" "),
                    new InputMatchedPart(pl2, true, "her"),
                    new InputMatchedPart(")")
            );
            assertEquals(e, p.matchValidInput("von her"));
        }

    }
}