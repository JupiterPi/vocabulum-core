package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
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
class VerbTest {
    @Test
    void getDefinition() {
        Verb verb = new Verb(
                Database.get().getConjugationClasses().a_Conjugation(),
                "vocare", "voc", "vocav", "vocat",
                null, new TranslationSequence(), "test"
        );
        assertEquals("vocare, voco, vocavi, vocatum", verb.getDefinition());
    }

    @Test
    void fromBaseForms() throws ParserException, DeclinedFormDoesNotExistException, VerbFormDoesNotExistException {
        Verb verb = Verb.fromBaseForms("vocare", "voco", "vocavi", "vocatum", null, new TranslationSequence(), "test");
        assertAll(
                () -> assertEquals("vocare", verb.getBaseForm()),
                () -> assertEquals("voco", verb.makeForm(new VerbForm(new ConjugatedForm(Person.FIRST, CNumber.SG), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE)).toString())
        );
    }

    @Nested
    @DisplayName("makeForm()")
    class MakeForm {

        Verb verb;

        @BeforeEach
        void init() {
            verb = new Verb(Database.get().getConjugationClasses().a_Conjugation(), "vocare", "voc", "vocav", "vocat", null, new TranslationSequence(), "test");
        }

        @Test
        @DisplayName("imperative")
        void imperative() {
            assertEquals("vocate", verb.makeForm(new VerbForm(CNumber.PL)).toString());
        }

        @Test
        @DisplayName("base infinitive")
        void baseInfinitive() {
            assertEquals("vocare", verb.makeForm(new VerbForm(InfinitiveTense.PRESENT, Voice.ACTIVE)).toString());
        }

        @Test
        @DisplayName("infinitive")
        void infinitive() {
            assertEquals("vocavisse", verb.makeForm(new VerbForm(InfinitiveTense.PERFECT, Voice.ACTIVE)).toString());
        }

        @Test
        @DisplayName("basic")
        void basic() {
            assertEquals("vocati sunt", verb.makeForm(new VerbForm(new ConjugatedForm(Person.THIRD, CNumber.PL), Mode.INDICATIVE, Tense.PERFECT, Voice.PASSIVE)).toString());
        }

        @Nested
        @DisplayName("noun-like")
        class NounLike {

            @Nested
            @DisplayName("PPP")
            class PPP {

                @Test
                @DisplayName("Nom. Sg.")
                void nom_sg() {
                    assertEquals("vocatus", verb.makeForm(new VerbForm(NounLikeForm.PPP, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC))).toString());
                }

                @Test
                @DisplayName("other")
                void other() {
                    assertEquals("vocatis", verb.makeForm(new VerbForm(NounLikeForm.PPP, new DeclinedForm(Casus.DAT, NNumber.PL, Gender.MASC))).toString());
                }

            }

            @Nested
            @DisplayName("PPA")
            class PPA {

                @Test
                @DisplayName("Nom. Sg.")
                void nom_sg() {
                    assertEquals("vocans", verb.makeForm(new VerbForm(NounLikeForm.PPA, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC))).toString());
                }

                @Test
                @DisplayName("other")
                void other() {
                    assertEquals("vocantes", verb.makeForm(new VerbForm(NounLikeForm.PPA, new DeclinedForm(Casus.ACC, NNumber.PL, Gender.MASC))).toString());
                }

            }

            @Nested
            @DisplayName("PFA")
            class PFA {

                @Test
                @DisplayName("with -sur-")
                void withSur() {
                    verb = new Verb(Database.get().getConjugationClasses().a_Conjugation(), "vocare", "voc", "vocav", "vocas", null, new TranslationSequence(), "test");
                    assertEquals("vocasurus", verb.makeForm(new VerbForm(NounLikeForm.PFA, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC))).toString());
                }

                @Test
                @DisplayName("normal (-tur-)")
                void normalTur() {
                    assertEquals("vocaturi", verb.makeForm(new VerbForm(NounLikeForm.PFA, new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC))).toString());
                }

            }

            @Nested
            @DisplayName("Gerundium")
            class Gerundium {

                @Test
                @DisplayName("infinitive")
                void infinitive() {
                    assertEquals("vocare", verb.makeForm(new VerbForm(NounLikeForm.GERUNDIUM, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC))).toString());
                }

                @Test
                @DisplayName("other")
                void other() {
                    assertEquals("vocandi", verb.makeForm(new VerbForm(NounLikeForm.GERUNDIUM, new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC))).toString());
                }

                @Test
                @DisplayName("Dat. Pl.")
                void dat_pl() {
                    assertFalse(verb.makeForm(new VerbForm(NounLikeForm.GERUNDIUM, new DeclinedForm(Casus.DAT, NNumber.SG, Gender.MASC))).exists());
                }

            }

            @Test
            @DisplayName("Gerundivum")
            void gerundivum() {
                assertEquals("vocanda", verb.makeForm(new VerbForm(NounLikeForm.GERUNDIVUM, new DeclinedForm(Casus.NOM, NNumber.PL, Gender.NEUT))).toString());
            }

        }

    }

    @Nested
    @DisplayName("identifyForm()")
    class IdentifyForm {

        @Test
        @DisplayName("one possibility")
        void onePossibility() {
            VerbForm first_sg = new VerbForm(new ConjugatedForm(Person.FIRST, CNumber.SG), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE);
            Verb verb = new Verb(
                    Database.get().getConjugationClasses().a_Conjugation(),
                    "vocare", "voc", "vocav", "vocat",
                    null, new TranslationSequence(), "test"
            );
            assertEquals(List.of(first_sg), verb.identifyForm("voco", false));
        }

        @Test
        @DisplayName("multiple possibilities")
        void multiplePossibilities() {
            VerbForm inf_pres = new VerbForm(InfinitiveTense.PRESENT, Voice.ACTIVE);
            VerbForm gerund_nom_sg = new VerbForm(NounLikeForm.GERUNDIUM, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC));
            Verb verb = new Verb(
                    Database.get().getConjugationClasses().a_Conjugation(),
                    "vocare", "voc", "vocav", "vocat",
                    null, new TranslationSequence(), "test"
            );
            assertEquals(List.of(inf_pres, gerund_nom_sg), verb.identifyForm("vocare", false));
        }

    }
}