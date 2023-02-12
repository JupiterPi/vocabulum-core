package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class NounTest {
    @Test
    void getDefinition() {
        Noun noun = new Noun(new TranslationSequence(), "test") {
            @Override
            protected Gender getGender() {
                return Gender.MASC;
            }

            @Override
            public String getDeclensionSchema() {
                return "o";
            }

            @Override
            public String makeForm(NounForm form) {
                if (form.equals(new NounForm(new DeclinedForm(Casus.GEN, NNumber.SG)))) return "amici";
                return null;
            }

            @Override
            public String getBaseForm() {
                return "amicus";
            }
        };
        assertEquals("amicus, amici m.", noun.getDefinition());
    }

    @Nested
    @DisplayName("identifyForm()")
    class IdentifyForm {

        @Test
        @DisplayName("one possibility")
        void onePossibility() {
            final NounForm abl_pl = new NounForm(new DeclinedForm(Casus.ABL, NNumber.PL, Gender.MASC));
            Noun noun = new Noun(new TranslationSequence(), "test") {
                @Override
                protected Gender getGender() {
                    return Gender.MASC;
                }

                @Override
                public String getDeclensionSchema() {
                    return "o";
                }

                @Override
                public String makeForm(NounForm form) {
                    if (form.equals(abl_pl)) return "amicis";
                    else return "";
                }

                @Override
                public String getBaseForm() {
                    return "amicus";
                }
            };
            assertEquals(List.of(abl_pl), noun.identifyForm("amicis", false));
        }

        @Test
        @DisplayName("multiple possibilities")
        void multiplePossibilities() {
            final NounForm gen_sg = new NounForm(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC));
            final NounForm nom_pl = new NounForm(new DeclinedForm(Casus.NOM, NNumber.PL, Gender.MASC));
            Noun noun = new Noun(new TranslationSequence(), "test") {
                @Override
                protected Gender getGender() {
                    return Gender.MASC;
                }

                @Override
                public String getDeclensionSchema() {
                    return "o";
                }

                @Override
                public String makeForm(NounForm form) {
                    if (form.equals(gen_sg)) return "amici";
                    if (form.equals(nom_pl)) return "amici";
                    else return "";
                }

                @Override
                public String getBaseForm() {
                    return "amicus";
                }
            };
            assertEquals(List.of(nom_pl, gen_sg), noun.identifyForm("amici", false));
        }

    }
}