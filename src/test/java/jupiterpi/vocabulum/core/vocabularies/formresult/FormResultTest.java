package jupiterpi.vocabulum.core.vocabularies.formresult;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormResultTest {

    @Nested
    @DisplayName("construct")
    class Construct {

        @Test
        @DisplayName("does not exist")
        void doesNotExist() {
            FormResult f = FormResult.doesNotExist();
            assertAll(
                    () -> assertFalse(f.exists()),
                    () -> assertEquals("-", f.getString())
            );
        }

        @Test
        @DisplayName("with primary form only")
        void withPrimaryFormOnly() {
            FormResult f = FormResult.withPrimaryForm("prim");
            assertAll(
                    () -> assertEquals("prim", f.getPrimaryForm()),
                    () -> assertEquals(List.of("prim"), f.getAllForms()),
                    () -> assertEquals("prim", f.getString())
            );
        }

        @Test
        @DisplayName("with secondary form")
        void withSecondaryForm() {
            FormResult f = FormResult.withSecondaryForm("prim", "second");
            assertAll(
                    () -> assertEquals("prim", f.getPrimaryForm()),
                    () -> assertEquals("second", f.getSecondaryForm()),
                    () -> assertTrue(f.hasSecondaryForm()),
                    () -> assertEquals(List.of("prim", "second"), f.getAllForms()),
                    () -> assertEquals("prim/second", f.getString())
            );
        }

    }

    @Nested
    @DisplayName("from string")
    class FromString {

        @Test
        @DisplayName("does not exist")
        void doesNotExist() {
            FormResult f = FormResult.fromString("-");
            assertFalse(f.exists());
        }

        @Test
        @DisplayName("with primary form only")
        void withPrimaryFormOnly() {
            FormResult f = FormResult.fromString("prim");
            assertAll(
                    () -> assertEquals("prim", f.getPrimaryForm()),
                    () -> assertFalse(f.hasSecondaryForm())
            );
        }

        @Test
        @DisplayName("with secondary form")
        void withSecondaryForm() {
            FormResult f = FormResult.fromString("prim/second");
            assertAll(
                    () -> assertEquals("prim", f.getPrimaryForm()),
                    () -> assertEquals("second", f.getSecondaryForm())
            );
        }

    }

}