package jupiterpi.vocabulum.core.db.lectures;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class LecturesTest {

    @Test
    void getExampleLines() {
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

}