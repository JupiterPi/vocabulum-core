package jupiterpi.vocabulum.core.vocabularies;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.ArticlePart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.PlainTextPart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.container.TranslationPartContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class VocabularyTest {
    @Test
    void fromString() throws ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        Vocabulary vocabulary = Vocabulary.fromString("amicus, amici m. -- *der Freund*, der Kamerad", "test");
        TranslationSequence translations = new TranslationSequence(
                new VocabularyTranslation(true, new TranslationPartContainer(new ArticlePart("der"), new PlainTextPart("Freund"))),
                new VocabularyTranslation(false, new TranslationPartContainer(new ArticlePart("der"), new PlainTextPart("Kamerad")))
        );
        assertAll(
                () -> assertEquals(translations, vocabulary.getTranslations()),
                () -> assertEquals("amicus, amici m.", vocabulary.getDefinition())
        );
    }

    @Test
    void vocabularyToString() throws ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        Vocabulary vocabulary = Vocabulary.fromString("amicus, amici m. -- *der Freund*, der Kamerad", "test");
        assertEquals("amicus, amici m. - *der Freund*, der Kamerad", vocabulary.vocabularyToString());
    }
}