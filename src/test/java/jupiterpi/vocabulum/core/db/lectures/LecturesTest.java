package jupiterpi.vocabulum.core.db.lectures;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class LecturesTest {

    @Nested
    @DisplayName("getExampleLines()")
    class GetExampleLines {

        @Test
        @DisplayName("normal")
        void normal() {
            Vocabulary v = Database.get().getPortions().getVocabularyInPortion("clamare");
            List<Lectures.ExampleLine> lines = Database.get().getLectures().getExampleLines(v);
            assertAll(
                    () -> assertEquals(2, lines.size()),
                    () -> assertEquals("Etiam canis tacet, asinus non iam clamat.", lines.get(0).getLine()),
                    () -> assertEquals(34, lines.get(0).getStartIndex()),
                    () -> assertEquals(40, lines.get(0).getEndIndex()),
                    () -> assertEquals(Database.get().getLectures().getLecture("L1"), lines.get(0).getLecture()),
                    () -> assertEquals(1, lines.get(0).getLineIndex())
            );
        }

        @Test
        @DisplayName("multiple occurrences of item string in line")
        void multipleOccurrencesOfItemStringInLine() {
            Vocabulary v = Database.get().getPortions().getVocabularyInPortion("et");
            List<Lectures.ExampleLine> lines = Database.get().getLectures().getExampleLines(v);
            assertEquals(17, lines.get(1).getStartIndex());
        }

    }

}