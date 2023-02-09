package jupiterpi.vocabulum.core.db.lectures;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabase;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.db.MockWordbase;
import jupiterpi.vocabulum.core.db.wordbase.IdentificationResult;
import jupiterpi.vocabulum.core.ta.result.TAResult;
import jupiterpi.vocabulum.core.ta.result.TAResultPunctuation;
import jupiterpi.vocabulum.core.ta.result.TAResultWord;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.NounForm;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class LectureTest {

    @Test
    @DisplayName("(processed) lines")
    void processedLines() {
        Vocabulary sampleVocabulary = new Vocabulary(new TranslationSequence(), "test") {
            @Override
            public String getBaseForm() {
                return "word";
            }

            @Override
            public String getDefinition() {
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
                if (List.of("asinus", "stat", "vocat").contains(word)) return List.of(sampleIdentificationResult);
                else return List.of();
            }
        });

        Lecture lecture = Lecture.fromTextString("name", "Asinus stat.\nQuintus vocat.");
        assertAll(
            () -> assertEquals(List.of("Asinus stat.", "Quintus vocat."), lecture.getLines()),
            () -> assertAll(
                () -> {
                    TAResult result = lecture.getProcessedLines().get(0);
                    assertAll(
                        () -> assertEquals(1, ((TAResultWord) result.getItems().get(0)).getPossibleWords().size()),
                        () -> assertEquals(1, ((TAResultWord) result.getItems().get(1)).getPossibleWords().size()),
                            () -> assertTrue(result.getItems().get(2) instanceof TAResultPunctuation)
                    );
                },
                () -> {
                    TAResult result = lecture.getProcessedLines().get(1);
                    assertAll(
                        () -> assertEquals(0, ((TAResultWord) result.getItems().get(0)).getPossibleWords().size()),
                        () -> assertEquals(1, ((TAResultWord) result.getItems().get(1)).getPossibleWords().size()),
                        () -> assertTrue(result.getItems().get(2) instanceof TAResultPunctuation)
                    );
                }
            )
        );
    }

    @Nested
    @DisplayName("toString()")
    class ToString {

        @Test
        @DisplayName("short")
        void shortText() {
            Lecture l = Lecture.fromTextString("Name", "This is short");
            assertEquals("Lecture{name=Name,text=\"This is short\"}", l.toString());
        }

        @Test
        @DisplayName("shortened")
        void shortened() {
            Lecture l = Lecture.fromTextString("Name", "This is some example text to check if this works");
            assertEquals("Lecture{name=Name,text=\"This is some example text to c...\"}", l.toString());
        }

        @Test
        @DisplayName("with line breaks")
        void withLineBreaks() {
            Lecture l = Lecture.fromTextString("Name", "This is some example text.\nIt will check if this works.");
            assertEquals("Lecture{name=Name,text=\"This is some example text. // It ...\"}", l.toString());
        }

    }

}