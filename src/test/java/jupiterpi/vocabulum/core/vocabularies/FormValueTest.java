package jupiterpi.vocabulum.core.vocabularies;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormValueTest {

    @Test
    void exists() {
        assertAll(
            () -> assertFalse(new FormValue().exists()),
            () -> assertTrue(new FormValue("pr").exists())
        );
    }

    @Test
    void hasSecondary() {
        assertAll(
                () -> assertFalse(new FormValue("pr").hasSecondary()),
                () -> assertTrue(new FormValue("pr", "sc").hasSecondary())
        );
    }

    @Nested
    @DisplayName("string representation")
    class StringRepresentation {

        @Test
        @DisplayName("toString()")
        void testToString() {
            assertAll(
                () -> assertEquals("-", new FormValue().toString()),
                () -> assertEquals("pr", new FormValue("pr").toString()),
                () -> assertEquals("pr/sc", new FormValue("pr", "sc").toString())
            );
        }

        @Nested
        @DisplayName("fromString()")
        class FromString {

            @Test
            @DisplayName("does not exist")
            void doesNotExist() {
                assertFalse(FormValue.fromString("-").exists());
            }

            @Test
            @DisplayName("has secondary")
            void hasSecondary() {
                assertTrue(FormValue.fromString("pr/sc").hasSecondary());
            }

            @Test
            @DisplayName("normal")
            void normal() {
                assertFalse(FormValue.fromString("pr").hasSecondary());
            }

        }

    }
}