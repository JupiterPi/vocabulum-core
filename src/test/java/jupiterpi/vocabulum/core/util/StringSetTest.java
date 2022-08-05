package jupiterpi.vocabulum.core.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringSetTest {
    @Test
    void getCharacters() {
        StringSet s = StringSet.getCharacters("abc ,");
        StringSet e = new StringSet("a", "b", "c", " ", ",");
        assertEquals(e, s);
    }

    @Test
    void contains() {
        StringSet s = new StringSet("a", "b", "c", " ", ",");
        assertAll(
                () -> assertTrue(s.contains("b")),
                () -> assertFalse(s.contains("d"))
        );
    }

    @Nested
    @DisplayName("indexOf()")
    class IndexOf {

        StringSet s = new StringSet("a", "b", "c", " ", ",");

        @Test
        @DisplayName("valid")
        void valid() {
            assertEquals(1, s.indexOf("b"));
        }

        @Test
        @DisplayName("invalid")
        void invalid() {
            assertEquals(-1, s.indexOf("d"));
        }

    }
}