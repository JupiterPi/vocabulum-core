package jupiterpi.vocabulum.core.db.lectures;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class LecturesTest {

    @Disabled("Ignored because Lectures#getExampleLines(Vocabulary) is deprecated")
    @Nested
    @DisplayName("getExampleLines()")
    class GetExampleLines {

        @Test
        @DisplayName("normal")
        void normal() {
            Vocabulary v = Database.get().getDictionary().getVocabulary("clamare");
            List<Lectures.ExampleLine> lines = Database.get().getLectures().getExampleLines(v);
            assertAll(
                    () -> assertTrue(lines.size() >= 2),
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
            Vocabulary v = Database.get().getDictionary().getVocabulary("et");
            List<Lectures.ExampleLine> lines = Database.get().getLectures().getExampleLines(v);
            assertEquals(17, lines.get(1).getStartIndex());
        }

    }

    @Test
    void getAllExampleLines() {
        Map<String, List<Lectures.ExampleLine>> allExampleLines = Database.get().getLectures().getAllExampleLines();
        List<Lectures.ExampleLine> lines = allExampleLines.get("clamare");
        assertAll(
                () -> assertTrue(lines.size() >= 2),
                () -> assertEquals("Etiam canis tacet, asinus non iam clamat.", lines.get(0).getLine()),
                () -> assertEquals(34, lines.get(0).getStartIndex()),
                () -> assertEquals(40, lines.get(0).getEndIndex()),
                () -> assertEquals(Database.get().getLectures().getLecture("L1"), lines.get(0).getLecture()),
                () -> assertEquals(1, lines.get(0).getLineIndex()),
                () -> assertTrue(allExampleLines.containsKey("silentium"))
        );
    }

}