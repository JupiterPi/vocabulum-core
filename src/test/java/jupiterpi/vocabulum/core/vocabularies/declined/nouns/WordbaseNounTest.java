package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class WordbaseNounTest {
    Document sampleDocument = Document.parse("""
                {
                    "kind": "noun",
                    "base_form": "asinus",
                    "portion": "test",
                    "forms": {
                      "masc": {
                        "sg": {
                          "nom": "asinus",
                          "acc": "asinum",
                          "gen": "asini",
                          "dat": "asino",
                          "abl": "asino"
                        },
                        "pl": {
                          "nom": "asini",
                          "acc": "asinos",
                          "gen": "asinorum",
                          "dat": "asinis",
                          "abl": "asinis"
                        }
                      },
                      "fem": {
                        "sg": {
                          "nom": "-",
                          "acc": "-",
                          "gen": "-",
                          "dat": "-",
                          "abl": "-"
                        },
                        "pl": {
                          "nom": "-",
                          "acc": "-",
                          "gen": "-",
                          "dat": "-",
                          "abl": "-"
                        }
                      },
                      "neut": {
                        "sg": {
                          "nom": "-",
                          "acc": "-",
                          "gen": "-",
                          "dat": "-",
                          "abl": "-"
                        },
                        "pl": {
                          "nom": "-",
                          "acc": "-",
                          "gen": "-",
                          "dat": "-",
                          "abl": "-"
                        }
                      }
                    },
                    "translations": [
                      "*der Esel*"
                    ],
                    "gender": "masc"
                  }
                """);

    @Test
    void readFromDocument() {
        WordbaseNoun n = WordbaseNoun.readFromDocument(sampleDocument);
        assertAll(
                () -> assertEquals("asinus", n.getBaseForm()),
                () -> assertEquals("asino", n.makeForm(new NounForm(new DeclinedForm(Casus.ABL, NNumber.SG)))),
                () -> assertThrows(DeclinedFormDoesNotExistException.class, () -> n.makeForm(new NounForm(new DeclinedForm(Casus.ABL, NNumber.SG, Gender.FEM)))),
                () -> assertEquals(Gender.MASC, n.getGender()),
                () -> assertEquals(new TranslationSequence(new VocabularyTranslation(true, "der Esel")), n.getTranslations())
        );
    }

    @Nested
    @DisplayName("makeForm()")
    class MakeForm {

        WordbaseNoun n;

        @BeforeEach
        void init() {
            TranslationSequence translations = new TranslationSequence(
                    new VocabularyTranslation(true, "der Esel")
            );
            n = new WordbaseNoun(
                    "asinus", Gender.MASC, (Document) sampleDocument.get("forms"), translations, "test"
            );
        }

        @Test
        @DisplayName("wrong gender")
        void wrongGender() {
            NounForm form = new NounForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM));
            assertThrows(DeclinedFormDoesNotExistException.class, () -> n.makeForm(form));
        }

        @Test
        @DisplayName("unset gender")
        void unsetGender() throws DeclinedFormDoesNotExistException {
            NounForm form = new NounForm(new DeclinedForm(Casus.GEN, NNumber.PL));
            assertEquals("asinorum", n.makeForm(form));
        }

        @Test
        @DisplayName("Nom. Sg.")
        void nomSg() throws DeclinedFormDoesNotExistException {
            NounForm form = new NounForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC));
            assertEquals("asinus", n.makeForm(form));
        }

        @Test
        @DisplayName("other form")
        void otherForm() throws DeclinedFormDoesNotExistException {
            NounForm form = new NounForm(new DeclinedForm(Casus.GEN, NNumber.PL, Gender.MASC));
            assertEquals("asinorum", n.makeForm(form));
        }

    }
}