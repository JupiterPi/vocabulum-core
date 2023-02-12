package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
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
class VerbTest {
    @Test
    void makeFormOrDash() {
        Verb verb = new Verb(new TranslationSequence(), "test") {
            @Override
            public String makeForm(VerbForm form) throws VerbFormDoesNotExistException, DeclinedFormDoesNotExistException {
                throw new VerbFormDoesNotExistException(form);
            }

            @Override
            public String getBaseForm() {
                return null;
            }

            @Override
            public String getConjugationSchema() {
                return "test";
            }
        };
        assertEquals("-", verb.makeFormOrDash(new VerbForm(InfinitiveTense.PRESENT, Voice.ACTIVE)));
    }

    @Test
    void getDefinition() {
        Verb verb = new Verb(new TranslationSequence(), "test") {
            @Override
            public String makeForm(VerbForm form) throws VerbFormDoesNotExistException {
                if (form.equals(new VerbForm(InfinitiveTense.PRESENT, Voice.ACTIVE))) {
                    return "vocare";
                } else if (form.equals(new VerbForm(new ConjugatedForm(Person.FIRST, CNumber.SG), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE))) {
                    return "voco";
                } else if (form.equals(new VerbForm(new ConjugatedForm(Person.FIRST, CNumber.SG), Mode.INDICATIVE, Tense.PERFECT, Voice.ACTIVE))) {
                    return "vocavi";
                } else if (form.equals(new VerbForm(NounLikeForm.PPP, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.NEUT)))) {
                    return "vocatum";
                }
                throw new VerbFormDoesNotExistException(form);
            }

            @Override
            public String getBaseForm() {
                return "vocare";
            }

            @Override
            public String getConjugationSchema() {
                return "test";
            }
        };
        assertEquals("vocare, voco, vocavi, vocatum", verb.getDefinition());
    }

    @Nested
    @DisplayName("identifyForm()")
    class IdentifyForm {

        @Test
        @DisplayName("one possibility")
        void onePossibility() {
            final VerbForm verbForm = new VerbForm(InfinitiveTense.PRESENT, Voice.ACTIVE);
            Verb verb = new Verb(new TranslationSequence(), "test") {
                @Override
                public String makeForm(VerbForm form) {
                    if (form.equals(verbForm)) return "theform";
                    else return "nottheform";
                }

                @Override
                public String getBaseForm() {
                    return "baseform";
                }

                @Override
                public String getConjugationSchema() {
                    return "test";
                }
            };
            assertEquals(List.of(verbForm), verb.identifyForm("theform", false));
        }

        @Test
        @DisplayName("multiple possibilities")
        void multiplePossibilities() {
            final VerbForm verbForm1 = new VerbForm(InfinitiveTense.PRESENT, Voice.ACTIVE);
            final VerbForm verbForm2 = new VerbForm(InfinitiveTense.PERFECT, Voice.ACTIVE);
            Verb verb = new Verb(new TranslationSequence(), "test") {
                @Override
                public String makeForm(VerbForm form) {
                    if (form.equals(verbForm1)) return "theform";
                    if (form.equals(verbForm2)) return "theform";
                    else return "nottheform";
                }

                @Override
                public String getBaseForm() {
                    return "baseform";
                }

                @Override
                public String getConjugationSchema() {
                    return "test";
                }
            };
            assertEquals(List.of(verbForm1, verbForm2), verb.identifyForm("theform", false));
        }

    }
}