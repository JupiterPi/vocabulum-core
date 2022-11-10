package jupiterpi.vocabulum.core.vocabularies.translations.exchangeables;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringWithImportanceTest {

    @Test
    void fromString() {
        StringWithImportance s = StringWithImportance.fromString("*der Freund*");
        assertAll(
            () -> assertEquals("der Freund", s.getString()),
            () -> assertTrue(s.isImportant())
        );
    }

    @Test
    void testToString() {
        StringWithImportance s = new StringWithImportance("der Freund", true);
        assertEquals("*der Freund*", s.toString());
    }
}