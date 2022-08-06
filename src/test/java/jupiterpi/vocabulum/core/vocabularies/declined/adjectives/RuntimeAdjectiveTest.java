package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class RuntimeAdjectiveTest {
    @Nested
    @DisplayName("fromBaseForms()")
    class FromBaseForms {

        @Test
        @DisplayName("a/o")
        void ao() throws DeclinedFormDoesNotExistException {
            RuntimeAdjective a = RuntimeAdjective.fromBaseForms("pulcher", "pulchra", "pulchrum", new ArrayList<>(), "test");
            assertAll(
                    () -> assertEquals("pulcher", a.getBaseForm()),
                    () -> assertEquals("pulchra", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM), ComparativeForm.POSITIVE))),
                    () -> assertEquals("pulchrum", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT), ComparativeForm.POSITIVE))),
                    () -> assertEquals("pulchri", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE)))
            );
        }

        @Test
        @DisplayName("cons")
        void cons() throws DeclinedFormDoesNotExistException {
            RuntimeAdjective a = RuntimeAdjective.fromBaseForms("acer", "acris", "acre", new ArrayList<>(), "test");
            assertAll(
                    () -> assertEquals("acer", a.getBaseForm()),
                    () -> assertEquals("acris", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM), ComparativeForm.POSITIVE))),
                    () -> assertEquals("acre", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT), ComparativeForm.POSITIVE))),
                    () -> assertEquals("acres", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.ACC, NNumber.PL, Gender.MASC), ComparativeForm.POSITIVE)))
            );
        }

    }

    @Test
    void fromGenitive() throws ParserException, DeclinedFormDoesNotExistException {
        RuntimeAdjective a = RuntimeAdjective.fromGenitive("felix", "felicis", new ArrayList<>(), "test");
        assertAll(
                () -> assertEquals("felix", a.getBaseForm()),
                () -> assertEquals("felicis", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE))),
                () -> assertEquals("felices", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.ACC, NNumber.PL, Gender.MASC), ComparativeForm.POSITIVE)))
        );
    }

    @Nested
    @DisplayName("makeForm()")
    class MakeForm {

        RuntimeAdjective ao;
        RuntimeAdjective cons;

        @BeforeEach
        void init() {
            ao = new RuntimeAdjective(
                    "laetus", "laeta", "laetum", RuntimeAdjective.Kind.AO, "laet",
                    new ArrayList<>(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS);
            cons = new RuntimeAdjective(
                    "acer", "acris", "acre", RuntimeAdjective.Kind.CONS, "acr",
                    new ArrayList<>(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS);
        }

        @Nested
        @DisplayName("adverbs")
        class Adverbs {

            @Test
            @DisplayName("cons_exceptions")
            void consExceptions () throws DeclinedFormDoesNotExistException {
                assertEquals("acriter", cons.makeForm(new AdjectiveForm(true, ComparativeForm.POSITIVE)));
            }

            @Test
            @DisplayName("superlative")
            void superlative() throws DeclinedFormDoesNotExistException {
                assertEquals("acerrime", cons.makeForm(new AdjectiveForm(true, ComparativeForm.SUPERLATIVE)));
            }

            @Test
            @DisplayName("normal")
            void normal() throws DeclinedFormDoesNotExistException {
                assertEquals("laete", ao.makeForm(new AdjectiveForm(true, ComparativeForm.POSITIVE)));
            }

        }

        @Nested
        @DisplayName("adjectives")
        class Adjectives {

            @Nested
            @DisplayName("positive")
            class Positive {

                @Test
                @DisplayName("Nom. Sg.")
                void nom_sg() throws DeclinedFormDoesNotExistException {
                    assertEquals("acris", cons.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM), ComparativeForm.POSITIVE)));
                }

                @Test
                @DisplayName("a/o")
                void ao() throws DeclinedFormDoesNotExistException {
                    assertEquals("laeti", ao.makeForm(new AdjectiveForm(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE)));
                }

                @Test
                @DisplayName("cons")
                void cons() throws DeclinedFormDoesNotExistException {
                    assertEquals("acres", cons.makeForm(new AdjectiveForm(new DeclinedForm(Casus.ACC, NNumber.PL, Gender.MASC), ComparativeForm.POSITIVE)));
                }

            }

            @Nested
            @DisplayName("not positive")
            class NotPositive {

                @Nested
                @DisplayName("comparative")
                class Comparative {

                    @Test
                    @DisplayName("Nom. Sg. n.")
                    void nom_sg_n() throws DeclinedFormDoesNotExistException {
                        assertEquals("acrius", cons.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT), ComparativeForm.COMPARATIVE)));
                    }

                    @Test
                    @DisplayName("normal")
                    void normal() throws DeclinedFormDoesNotExistException {
                        assertEquals("acrior", cons.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.COMPARATIVE)));
                    }

                }

                @Nested
                @DisplayName("superlative")
                class Superlative {

                    @Test
                    @DisplayName("Nom. Sg. m.")
                    void nom_sg_m() throws DeclinedFormDoesNotExistException {
                        assertEquals("laetissimus", ao.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.SUPERLATIVE)));
                    }

                    @Test
                    @DisplayName("normal")
                    void normal() throws DeclinedFormDoesNotExistException {
                        assertEquals("laetissimos", ao.makeForm(new AdjectiveForm(new DeclinedForm(Casus.ACC, NNumber.PL, Gender.MASC), ComparativeForm.SUPERLATIVE)));
                    }

                }

            }

        }

    }
}