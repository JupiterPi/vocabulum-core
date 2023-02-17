package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class NounTest {
    @Test
    void getDefinition() {
        Noun noun = new Noun(
                Database.get().getDeclensionClasses().o_Declension(),
                "amicus", "amic", Gender.MASC,
                new TranslationSequence(), "test"
        );
        assertEquals("amicus, amici m.", noun.getDefinition());
    }

    @Nested
    @DisplayName("fromGenitive()")
    class FromGenitive {

        @Test
        @DisplayName("simple")
        void simple() throws ParserException, DeclinedFormDoesNotExistException {
            Noun n = Noun.fromGenitive("amicus", "amici", Gender.MASC, new TranslationSequence(), "test");
            assertAll(
                    () -> assertEquals("amicus", n.getBaseForm()),
                    () -> assertEquals("amico", n.makeForm(new NounForm(new DeclinedForm(Casus.ABL, NNumber.SG))).toString()),
                    () -> assertEquals(Gender.MASC, n.getGender()),
                    () -> assertEquals("o", n.getDeclensionSchema().getName())
            );
        }

        @Test
        @DisplayName("not mixed up with cons_adjectives")
        void notMixedUpWithConsAdjectives() throws ParserException, DeclinedFormDoesNotExistException {
            Noun n = Noun.fromGenitive("sol", "solis", Gender.MASC, new TranslationSequence(), "test");
            assertAll(
                    () -> assertEquals("sol", n.getBaseForm()),
                    () -> assertEquals(Gender.MASC, n.getGender()),
                    () -> assertEquals("cons", n.getDeclensionSchema().getName())
            );
        }

    }

    @Nested
    @DisplayName("makeForm()")
    class MakeForm {

        Noun n;

        @BeforeEach
        void init() {
            n = new Noun(
                    Database.get().getDeclensionClasses().o_Declension(),
                    "amicus", "amic", Gender.MASC,
                    new TranslationSequence(), "test"
            );
        }

        @Test
        @DisplayName("wrong gender")
        void wrongGender() {
            NounForm form = new NounForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM));
            assertFalse(n.makeForm(form).exists());
        }

        @Test
        @DisplayName("unset gender")
        void unsetGender() {
            NounForm form = new NounForm(new DeclinedForm(Casus.GEN, NNumber.PL));
            assertEquals("amicorum", n.makeForm(form).toString());
        }

        @Test
        @DisplayName("Nom. Sg.")
        void nomSg() {
            NounForm form = new NounForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC));
            assertEquals("amicus", n.makeForm(form).toString());
        }

        @Test
        @DisplayName("other form")
        void otherForm() {
            NounForm form = new NounForm(new DeclinedForm(Casus.GEN, NNumber.PL, Gender.MASC));
            assertEquals("amicorum", n.makeForm(form).toString());
        }

    }

    @Nested
    @DisplayName("identifyForm()")
    class IdentifyForm {

        @Test
        @DisplayName("one possibility")
        void onePossibility() {
            Noun noun = new Noun(
                    Database.get().getDeclensionClasses().o_Declension(),
                    "amicus", "amic", Gender.MASC,
                    new TranslationSequence(), "test"
            );
            NounForm gen_pl = new NounForm(new DeclinedForm(Casus.GEN, NNumber.PL, Gender.MASC));
            assertEquals(List.of(gen_pl), noun.identifyForm("amicorum", false));
        }

        @Test
        @DisplayName("multiple possibilities")
        void multiplePossibilities() {
            Noun noun = new Noun(
                    Database.get().getDeclensionClasses().o_Declension(),
                    "amicus", "amic", Gender.MASC,
                    new TranslationSequence(), "test"
            );
            NounForm gen_sg = new NounForm(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC));
            NounForm nom_pl = new NounForm(new DeclinedForm(Casus.NOM, NNumber.PL, Gender.MASC));
            NounForm voc_pl = new NounForm(new DeclinedForm(Casus.VOC, NNumber.PL, Gender.MASC));
            assertEquals(List.of(nom_pl, gen_sg, voc_pl), noun.identifyForm("amici", false));
        }

    }
}