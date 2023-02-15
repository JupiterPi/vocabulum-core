package jupiterpi.vocabulum.core.db.lectures;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class LecturesTest {

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