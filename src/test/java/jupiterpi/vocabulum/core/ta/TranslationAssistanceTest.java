package jupiterpi.vocabulum.core.ta;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabase;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.db.MockWordbase;
import jupiterpi.vocabulum.core.db.wordbase.IdentificationResult;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.NounForm;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class TranslationAssistanceTest {
    I18n i18n = Database.get().getI18ns().internal();

    @Test
    void tokenize() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, TAException {
        Method tokenize = TranslationAssistance.class.getDeclaredMethod("tokenize", String.class);
        tokenize.setAccessible(true);
        List<TranslationAssistance.TAToken> tokens = (List<TranslationAssistance.TAToken>) tokenize.invoke(new TranslationAssistance(""), "word , word . word, word.");
        assertEquals(Arrays.asList(
                new TranslationAssistance.TAToken(TranslationAssistance.TAToken.TAWordType.WORD, "word"),
                new TranslationAssistance.TAToken(TranslationAssistance.TAToken.TAWordType.PUNCTUATION, ","),
                new TranslationAssistance.TAToken(TranslationAssistance.TAToken.TAWordType.WORD, "word"),
                new TranslationAssistance.TAToken(TranslationAssistance.TAToken.TAWordType.PUNCTUATION, "."),
                new TranslationAssistance.TAToken(TranslationAssistance.TAToken.TAWordType.WORD, "word"),
                new TranslationAssistance.TAToken(TranslationAssistance.TAToken.TAWordType.PUNCTUATION, ","),
                new TranslationAssistance.TAToken(TranslationAssistance.TAToken.TAWordType.WORD, "word"),
                new TranslationAssistance.TAToken(TranslationAssistance.TAToken.TAWordType.PUNCTUATION, ".")
        ), tokens);
    }

    @Nested
    @DisplayName("runTranslationAssistance()")
    class RunTranslationAssistance {

        @BeforeAll
        static void init() {
            TranslationSequence translations = new TranslationSequence(
                    new VocabularyTranslation(true, "transl1"),
                    new VocabularyTranslation(false, "transl2")
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
            };

            ((MockDatabase) Database.get()).injectWordbase(new MockWordbase() {
                @Override
                public List<IdentificationResult> identifyWord(String word) {
                    IdentificationResult sampleIdentificationResult = new IdentificationResult(sampleVocabulary, Arrays.asList(new NounForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC))));
                    return switch (word) {
                        case "1" -> Arrays.asList(sampleIdentificationResult);
                        case "2" -> Arrays.asList(sampleIdentificationResult, sampleIdentificationResult);
                        default -> new ArrayList<>();
                    };
                }
            });
        }

        @Test
        @DisplayName("cannot identify")
        void cannotIdentify() {
            assertThrows(TAException.class, () -> {
                TranslationAssistance ta = new TranslationAssistance("0");
            });
        }

        @Test
        @DisplayName("multiple results")
        void multipleResults() {
            assertThrows(TAException.class, () -> {
                TranslationAssistance ta = new TranslationAssistance("2");
            });
        }

        @Test
        @DisplayName("normal")
        void normal() throws TAException {
            TranslationAssistance ta = new TranslationAssistance("1, 1.");
            List<TAResult.TAResultItem> items = ta.getResult().getItems();
            assertAll(
                    () -> {
                        TAResult.TAResultItem item = items.get(0);
                        assertAll(
                                () -> assertEquals("1", item.getItem()),
                                () -> assertEquals(Arrays.asList(
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
                                () -> assertEquals(Arrays.asList(
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

        @AfterAll
        static void cleanup() {
            ((MockDatabase) Database.get()).reloadWordbase();
        }

    }
}