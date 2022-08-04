package jupiterpi.vocabulum.core.vocabularies.declined.schemas;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class DeclensionSchemaTest {
    @Nested
    @DisplayName("getSuffix()")
    class GetSuffix {

        @Test
        @DisplayName("on gender dependant with form with unset gender")
        void genderDependantUnsetGender() {
            DeclensionSchema s = new DeclensionSchema("test") {
                @Override
                protected String getSuffixRaw(DeclinedForm form) { return null; }

            };
            assertThrows(DeclinedFormDoesNotExistException.class, () -> s.getSuffix(new DeclinedForm(Casus.ABL, NNumber.PL)));
        }

        @Test
        @DisplayName("throws DeclinedFormDoesNotExistException on '-' raw suffix")
        void throwsDoesNotExistException() {
            DeclensionSchema s = new DeclensionSchema("test") {
                @Override
                protected String getSuffixRaw(DeclinedForm form) {
                    return "-";
                }

            };
            assertThrows(DeclinedFormDoesNotExistException.class, () -> s.getSuffix(new DeclinedForm(Casus.ABL, NNumber.PL)));
        }

        @Test
        @DisplayName("basic")
        void basic() throws DeclinedFormDoesNotExistException {
            DeclensionSchema s = new DeclensionSchema("test") {
                @Override
                protected String getSuffixRaw(DeclinedForm form) {
                    return "is";
                }

            };
            assertEquals("is", s.getSuffix(new DeclinedForm(Casus.ABL, NNumber.PL)));
        }

    }
}