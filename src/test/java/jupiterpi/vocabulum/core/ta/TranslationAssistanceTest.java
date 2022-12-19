package jupiterpi.vocabulum.core.ta;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabase;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.db.MockWordbase;
import jupiterpi.vocabulum.core.db.wordbase.IdentificationResult;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.ta.result.TAResult;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.NounForm;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.PlainTextPart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.container.TranslationPartContainer;
import org.bson.Document;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class TranslationAssistanceTest {
    I18n i18n = Database.get().getI18ns().internal();

    @Nested
    @DisplayName("tokenize()")
    class Tokenize {

        @Test
        @DisplayName("normal")
        void normal() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Method tokenize = TranslationAssistance.class.getDeclaredMethod("tokenize", String.class);
            tokenize.setAccessible(true);
            List<TAToken> tokens = (List<TAToken>) tokenize.invoke(new TranslationAssistance(""), "word , word . word, word.");
            assertEquals(List.of(
                    new TAToken(TAToken.TAWordType.WORD, "word"),
                    new TAToken(TAToken.TAWordType.PUNCTUATION, ","),
                    new TAToken(TAToken.TAWordType.WORD, "word"),
                    new TAToken(TAToken.TAWordType.PUNCTUATION, "."),
                    new TAToken(TAToken.TAWordType.WORD, "word"),
                    new TAToken(TAToken.TAWordType.PUNCTUATION, ","),
                    new TAToken(TAToken.TAWordType.WORD, "word"),
                    new TAToken(TAToken.TAWordType.PUNCTUATION, ".")
            ), tokens);
        }

        @Test
        @DisplayName("other punctuation marks")
        void otherPunctuationMarks() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            Method tokenize = TranslationAssistance.class.getDeclaredMethod("tokenize", String.class);
            tokenize.setAccessible(true);
            List<TAToken> tokens = (List<TAToken>) tokenize.invoke(new TranslationAssistance(""), "word; word - word --word?! word... word\"");
            assertEquals(List.of(
                    new TAToken(TAToken.TAWordType.WORD, "word"),
                    new TAToken(TAToken.TAWordType.PUNCTUATION, ";"),
                    new TAToken(TAToken.TAWordType.WORD, "word"),
                    new TAToken(TAToken.TAWordType.PUNCTUATION, "-"),
                    new TAToken(TAToken.TAWordType.WORD, "word"),
                    new TAToken(TAToken.TAWordType.PUNCTUATION, "--"),
                    new TAToken(TAToken.TAWordType.WORD, "word"),
                    new TAToken(TAToken.TAWordType.PUNCTUATION, "?!"),
                    new TAToken(TAToken.TAWordType.WORD, "word"),
                    new TAToken(TAToken.TAWordType.PUNCTUATION, "..."),
                    new TAToken(TAToken.TAWordType.WORD, "word"),
                    new TAToken(TAToken.TAWordType.PUNCTUATION, "\"")
            ), tokens);
        }
    }

    @Nested
    @DisplayName("runTranslationAssistance()")
    class RunTranslationAssistance {

        @BeforeAll
        static void init() {
            TranslationSequence translations = new TranslationSequence(
                    new VocabularyTranslation(true, new TranslationPartContainer(new PlainTextPart("transl1"))),
                    new VocabularyTranslation(false, new TranslationPartContainer(new PlainTextPart("transl2")))
            );
            Vocabulary sampleVocabulary = new Vocabulary(translations, "test") {
                @Override
                public String getBaseForm() {
                    return "word";
                }

                @Override
                public String getDefinition(I18n i18n) {
                    return "word, wordis m.";
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

            ((MockDatabase) Database.get()).injectWordbase(new MockWordbase() {
                @Override
                public List<IdentificationResult> identifyWord(String word, boolean partialSearch) {
                    IdentificationResult sampleIdentificationResult = new IdentificationResult(sampleVocabulary, List.of(new NounForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC))));
                    return switch (word) {
                        case "1" -> List.of(sampleIdentificationResult);
                        case "2" -> List.of(sampleIdentificationResult, sampleIdentificationResult);
                        default -> new ArrayList<>();
                    };
                }
            });
        }

        @Test
        @DisplayName("normal")
        void normal() {
            TranslationAssistance ta = new TranslationAssistance("1, 1.");
            List<TAResult.TAResultItem> items = ta.getResult().getItems();
            assertAll(
                    () -> {
                        TAResult.TAResultItem item = items.get(0);
                        assertAll(
                                () -> assertEquals("1", item.getItem()),
                                () -> assertEquals(List.of(
                                        "Nom. Sg. m.",
                                        "*transl1*",
                                        "transl2"
                                ), item.getLines(i18n))
                        );
                    },
                    () -> {
                        TAResult.TAResultItem item = items.get(1);
                        assertAll(
                                () -> assertEquals(",", item.getItem()),
                                () -> assertEquals(List.of(), item.getLines(i18n))
                        );
                    },
                    () -> {
                        TAResult.TAResultItem item = items.get(2);
                        assertAll(
                                () -> assertEquals("1", item.getItem()),
                                () -> assertEquals(List.of(
                                        "Nom. Sg. m.",
                                        "*transl1*",
                                        "transl2"
                                ), item.getLines(i18n))
                        );
                    },
                    () -> {
                        TAResult.TAResultItem item = items.get(3);
                        assertAll(
                                () -> assertEquals(".", item.getItem()),
                                () -> assertEquals(List.of(), item.getLines(i18n))
                        );
                    }
            );
        }

        @Test
        @DisplayName("multiple results")
        void multipleResults() {
            TranslationAssistance ta = new TranslationAssistance("2.");
            List<TAResult.TAResultItem> items = ta.getResult().getItems();
            assertAll(
                () -> {
                    TAResult.TAResultItem item = items.get(0);
                    assertAll(
                        () -> assertEquals("2", item.getItem()),
                        () -> assertEquals(List.of(
                                "Nom. Sg. m.",
                                "*transl1*",
                                "transl2",
                                "",
                                "Nom. Sg. m.",
                                "*transl1*",
                                "transl2"
                        ), item.getLines(i18n))
                    );
                },
                () -> {
                    TAResult.TAResultItem item = items.get(1);
                    assertEquals(".", item.getItem());
                }
            );
        }

        @Test
        @DisplayName("not found")
        void notFound() {
            TranslationAssistance ta = new TranslationAssistance("0.");
            List<TAResult.TAResultItem> items = ta.getResult().getItems();
            assertAll(
                    () -> {
                        TAResult.TAResultItem item = items.get(0);
                        assertAll(
                                () -> assertEquals("0", item.getItem()),
                                () -> assertEquals(List.of(), item.getLines(i18n))
                        );
                    },
                    () -> {
                        TAResult.TAResultItem item = items.get(1);
                        assertEquals(".", item.getItem());
                    }
            );
        }

        @AfterAll
        static void cleanup() {
            ((MockDatabase) Database.get()).reloadWordbase();
        }

    }
}