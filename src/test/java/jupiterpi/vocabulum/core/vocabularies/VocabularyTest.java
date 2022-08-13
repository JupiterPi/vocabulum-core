package jupiterpi.vocabulum.core.vocabularies;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.ArticlePart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.PlainTextPart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.TranslationPartContainer;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class VocabularyTest {
    @Test
    void fromString() throws ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException {
        Vocabulary vocabulary = Vocabulary.fromString("amicus, amici m. - *der Freund*, der Kamerad", Database.get().getI18ns().internal(), "test");
        TranslationSequence translations = new TranslationSequence(
                new VocabularyTranslation(true, new TranslationPartContainer(new ArticlePart("der"), new PlainTextPart("Freund"))),
                new VocabularyTranslation(false, new TranslationPartContainer(new ArticlePart("der"), new PlainTextPart("Kamerad")))
        );
        assertAll(
                () -> assertEquals(translations, vocabulary.getTranslations()),
                () -> assertEquals("amicus, amici m.", vocabulary.getDefinition(Database.get().getI18ns().internal()))
        );
    }

    @Test
    void generateWordbaseEntry() {
        TranslationSequence translations = new TranslationSequence(
                new VocabularyTranslation(true, new TranslationPartContainer(new PlainTextPart("der Freund"))),
                new VocabularyTranslation(false, new TranslationPartContainer(new PlainTextPart("der Kamerad")))
        );
        Vocabulary vocabulary = new Vocabulary(translations, "test") {
            @Override
            public String getBaseForm() {
                return "amicus";
            }

            @Override
            public String getDefinition(I18n i18n) { return null; }

            @Override
            public Kind getKind() {
                return Kind.NOUN;
            }

            @Override
            protected Document generateWordbaseEntrySpecificPart() {
                return new Document();
            }
        };
        Document e = new Document();
        e.put("kind", "noun");
        e.put("base_form", "amicus");
        e.put("portion", "test");
        e.put("translations", List.of("*der Freund*", "der Kamerad"));
        assertEquals(e, vocabulary.generateWordbaseEntry());
    }
}