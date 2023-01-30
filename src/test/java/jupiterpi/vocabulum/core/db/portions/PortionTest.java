package jupiterpi.vocabulum.core.db.portions;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class PortionTest {
    Document sampleDocument = Document.parse("""
            {
              "_id": {
                "$oid": "62dd57d5a4fb87ed55463483"
              },
              "name": "A",
              "i18n": "de",
              "vocabularies": [
                [
                  "asinus, asini m. -- *der Esel*",
                  "stare, sto, stavi, statum -- *dastehen*, aufrecht stehen",
                  "et -- *und*",
                  "exspectare, exspecto, exspectavi, exspectatum -- *erwarten*, warten auf"
                ]
              ]
            }
            """);

    @Test
    void readFromDocument() throws ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException {
        Portion portion = Portion.readFromDocument(sampleDocument);
        assertAll(
            () -> assertEquals("A", portion.getName()),
            () -> assertAll(
                () -> assertEquals("asinus", portion.getVocabularies().get(0).getBaseForm()),
                () -> assertEquals("stare", portion.getVocabularies().get(1).getBaseForm()),
                () -> assertEquals("et", portion.getVocabularies().get(2).getBaseForm()),
                () -> assertEquals("exspectare", portion.getVocabularies().get(3).getBaseForm())
            )
        );
    }

    @Test
    @DisplayName("toString()")
    void testToString() {
        Portion portion = new Portion("test", List.of(
                List.of(
                        getUtilVocabulary("voc1"),
                        getUtilVocabulary("voc2")
                ),
                List.of(
                        getUtilVocabulary("voc3"),
                        getUtilVocabulary("voc4"),
                        getUtilVocabulary("voc5")
                )
        ));
        assertEquals("Portion{name=test,vocabularies=[noun(\"voc1 - *voc1*\"),noun(\"voc2 - *voc2*\") // noun(\"voc3 - *voc3*\"),noun(\"voc4 - *voc4*\"),noun(\"voc5 - *voc5*\")]}", portion.toString());
    }

    Vocabulary getUtilVocabulary(String base_form) {
        return new Vocabulary(new TranslationSequence(VocabularyTranslation.fromString("*" + base_form + "*").get(0)), "test") {
            @Override
            public String getBaseForm() {
                return base_form;
            }

            @Override
            public String getDefinition(I18n i18n) {
                return base_form + ", " + base_form;
            }

            @Override
            public Kind getKind() {
                return Kind.NOUN;
            }

            @Override
            protected Document generateWordbaseEntrySpecificPart() {
                return new Document();
            }

            @Override
            protected List<String> getAllFormsToString() {
                return List.of();
            }
        };
    }
}