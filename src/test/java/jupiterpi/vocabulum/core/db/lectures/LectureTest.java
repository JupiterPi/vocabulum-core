package jupiterpi.vocabulum.core.db.lectures;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LectureTest {

    @Nested
    @DisplayName("toString()")
    class ToString {

        @Test
        @DisplayName("short")
        void shortText() {
            Lecture l = new Lecture("Name", "This is short");
            assertEquals("Lecture{name=Name,text=\"This is short\"}", l.toString());
        }

        @Test
        @DisplayName("shortened")
        void shortened() {
            Lecture l = new Lecture("Name", "This is some example text to check if this works");
            assertEquals("Lecture{name=Name,text=\"This is some example text to c...\"}", l.toString());
        }

        @Test
        @DisplayName("with line breaks")
        void withLineBreaks() {
            Lecture l = new Lecture("Name", "This is some example text.\nIt will check if this works.");
            assertEquals("Lecture{name=Name,text=\"This is some example text. // It ...\"}", l.toString());
        }

    }

}