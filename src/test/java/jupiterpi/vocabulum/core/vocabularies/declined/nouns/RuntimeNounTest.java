package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class RuntimeNounTest {
    @Test
    void fromGenitive() throws ParserException, DeclinedFormDoesNotExistException {
        RuntimeNoun n = RuntimeNoun.fromGenitive("amicus", "amici", Gender.MASC, new ArrayList<>(), "test");
        assertAll(
                () -> assertEquals("amicus", n.getBaseForm()),
                () -> assertEquals("amico", n.makeForm(new NounForm(new DeclinedForm(Casus.ABL, NNumber.SG)))),
                () -> assertEquals(Gender.MASC, n.getGender())
        );
    }

    @Nested
    @DisplayName("makeForm()")
    class MakeForm {

        RuntimeNoun n;

        @BeforeEach
        void init() {
            n = new RuntimeNoun(
                    Database.get().getDeclensionClasses().o_Declension(),
                    "amicus", "amic", Gender.MASC,
                    new ArrayList<>(), "test"
            );
        }

        @Test
        @DisplayName("wrong gender")
        void wrongGender() {
            NounForm form = new NounForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM));
            assertThrows(DeclinedFormDoesNotExistException.class, () -> n.makeForm(form));
        }

        @Test
        @DisplayName("unset gender")
        void unsetGender() throws DeclinedFormDoesNotExistException {
            NounForm form = new NounForm(new DeclinedForm(Casus.GEN, NNumber.PL));
            assertEquals("amicorum", n.makeForm(form));
        }

        @Test
        @DisplayName("Nom. Sg.")
        void nomSg() throws DeclinedFormDoesNotExistException {
            NounForm form = new NounForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC));
            assertEquals("amicus", n.makeForm(form));
        }

        @Test
        @DisplayName("other form")
        void otherForm() throws DeclinedFormDoesNotExistException {
            NounForm form = new NounForm(new DeclinedForm(Casus.GEN, NNumber.PL, Gender.MASC));
            assertEquals("amicorum", n.makeForm(form));
        }

    }
}