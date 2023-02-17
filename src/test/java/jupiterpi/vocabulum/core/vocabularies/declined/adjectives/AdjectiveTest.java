package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.formresult.FormResult;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class AdjectiveTest {
    @Nested
    @DisplayName("getDefinition()")
    class GetDefinition {

        @Test
        @DisplayName("definitionType = from base forms")
        void fromBaseForms() {
            Adjective adjective = new Adjective(
                    "acer", "acris", "acre",
                    Adjective.Kind.CONS, "acr",
                    new TranslationSequence(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS
            );
            assertEquals("acer, acris, acre", adjective.getDefinition());
        }

        @Test
        @DisplayName("definitionType = from genitive")
        void fromGenitive() {
            Adjective adjective = new Adjective(
                    "felix", "felix", "felix",
                    Adjective.Kind.CONS, "felic",
                    new TranslationSequence(), "test", Adjective.AdjectiveDefinitionType.FROM_GENITIVE
            );
            assertEquals("felix, Gen. felicis", adjective.getDefinition());
        }

    }

    @Nested
    @DisplayName("fromBaseForms()")
    class FromBaseForms {

        @Test
        @DisplayName("a/o")
        void ao() throws DeclinedFormDoesNotExistException {
            Adjective a = Adjective.fromBaseForms("pulcher", "pulchra", "pulchrum", new TranslationSequence(), "test");
            assertAll(
                    () -> assertEquals("pulcher", a.getBaseForm()),
                    () -> assertEquals("pulchra", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM), ComparativeForm.POSITIVE)).toString()),
                    () -> assertEquals("pulchrum", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT), ComparativeForm.POSITIVE)).toString()),
                    () -> assertEquals("pulchri", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE)).toString())
            );
        }

        @Test
        @DisplayName("cons")
        void cons() throws DeclinedFormDoesNotExistException {
            Adjective a = Adjective.fromBaseForms("acer", "acris", "acre", new TranslationSequence(), "test");
            assertAll(
                    () -> assertEquals("acer", a.getBaseForm()),
                    () -> assertEquals("acris", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM), ComparativeForm.POSITIVE)).toString()),
                    () -> assertEquals("acre", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT), ComparativeForm.POSITIVE)).toString()),
                    () -> assertEquals("acres", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.ACC, NNumber.PL, Gender.MASC), ComparativeForm.POSITIVE)).toString())
            );
        }

    }

    @Test
    void fromGenitive() throws ParserException, DeclinedFormDoesNotExistException {
        Adjective a = Adjective.fromGenitive("felix", "felicis", new TranslationSequence(), "test");
        assertAll(
                () -> assertEquals("felix", a.getBaseForm()),
                () -> assertEquals("felicis", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE)).toString()),
                () -> assertEquals("felices", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.ACC, NNumber.PL, Gender.MASC), ComparativeForm.POSITIVE)).toString())
        );
    }

    @Nested
    @DisplayName("makeForm()")
    class MakeForm {

        Adjective ao;
        Adjective cons;

        @BeforeEach
        void init() {
            ao = new Adjective(
                    "laetus", "laeta", "laetum", Adjective.Kind.AO, "laet",
                    new TranslationSequence(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS);
            cons = new Adjective(
                    "acer", "acris", "acre", Adjective.Kind.CONS, "acr",
                    new TranslationSequence(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS);
        }

        @Nested
        @DisplayName("adverbs")
        class Adverbs {

            @Test
            @DisplayName("cons_exceptions")
            void consExceptions () {
                assertEquals("acriter", cons.makeForm(new AdjectiveForm(true, ComparativeForm.POSITIVE)).toString());
            }

            @Test
            @DisplayName("superlative")
            void superlative() {
                assertEquals("acerrime", cons.makeForm(new AdjectiveForm(true, ComparativeForm.SUPERLATIVE)).toString());
            }

            @Test
            @DisplayName("normal")
            void normal() {
                assertEquals("laete", ao.makeForm(new AdjectiveForm(true, ComparativeForm.POSITIVE)).toString());
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
                void nom_sg() {
                    assertEquals("acris", cons.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM), ComparativeForm.POSITIVE)).toString());
                }

                @Test
                @DisplayName("a/o")
                void ao() {
                    assertEquals("laeti", ao.makeForm(new AdjectiveForm(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE)).toString());
                }

                @Test
                @DisplayName("cons")
                void cons() {
                    assertEquals("acres", cons.makeForm(new AdjectiveForm(new DeclinedForm(Casus.ACC, NNumber.PL, Gender.MASC), ComparativeForm.POSITIVE)).toString());
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
                    void nom_sg_n() {
                        assertEquals("acrius", cons.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT), ComparativeForm.COMPARATIVE)).toString());
                    }

                    @Test
                    @DisplayName("normal")
                    void normal() {
                        assertEquals("acrior", cons.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.COMPARATIVE)).toString());
                    }

                }

                @Nested
                @DisplayName("superlative")
                class Superlative {

                    @Test
                    @DisplayName("Nom. Sg. m.")
                    void nom_sg_m() {
                        assertEquals("laetissimus", ao.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.SUPERLATIVE)).toString());
                    }

                    @Test
                    @DisplayName("normal")
                    void normal() {
                        assertEquals("laetissimos", ao.makeForm(new AdjectiveForm(new DeclinedForm(Casus.ACC, NNumber.PL, Gender.MASC), ComparativeForm.SUPERLATIVE)).toString());
                    }

                }

            }

        }

    }

    @Test
    void identifyForm() {
        AdjectiveForm nom_sg_m_pos = new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE);
        AdjectiveForm adv_comp = new AdjectiveForm(true, ComparativeForm.COMPARATIVE);
        Adjective adjective = new Adjective(
                "", "", "", Adjective.Kind.CONS, "",
                new TranslationSequence(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS
        ) {
            @Override
            public FormResult makeForm(AdjectiveForm form) {
                if (form.equals(nom_sg_m_pos) || form.equals(adv_comp)) return FormResult.withPrimaryForm("targetform");
                return FormResult.withPrimaryForm("");
            }

            @Override
            public String getBaseForm() { return null; }
        };
        assertEquals(List.of(nom_sg_m_pos, adv_comp), adjective.identifyForm("targetform", false));
    }
}