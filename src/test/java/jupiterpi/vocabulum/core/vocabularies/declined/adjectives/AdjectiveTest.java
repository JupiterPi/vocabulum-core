package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

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
class AdjectiveTest {
    @Nested
    @DisplayName("getDefinition()")
    class GetDefinition {

        @Test
        @DisplayName("definitionType = from base forms")
        void fromBaseForms() {
            Adjective adjective = new Adjective(new TranslationSequence(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS) {
                @Override
                public String makeForm(AdjectiveForm form) {
                    if (form.getComparativeForm() != ComparativeForm.POSITIVE) return null;
                    DeclinedForm declinedForm = form.getDeclinedForm();
                    if (declinedForm.getCasus() != Casus.NOM) return null;
                    if (declinedForm.getNumber() != NNumber.SG) return null;
                    if (!declinedForm.hasGender()) return null;
                    return switch (declinedForm.getGender()) {
                        case MASC -> "acer";
                        case FEM -> "acris";
                        case NEUT -> "acre";
                    };
                }

                @Override
                public String getBaseForm() {
                    return "acer";
                }
            };
            assertEquals("acer, acris, acre", adjective.getDefinition());
        }

        @Test
        @DisplayName("definitionType = from genitive")
        void fromGenitive() {
            Adjective adjective = new Adjective(new TranslationSequence(), "test", Adjective.AdjectiveDefinitionType.FROM_GENITIVE) {
                @Override
                public String makeForm(AdjectiveForm form) {
                    if (form.equals(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE))) {
                        return "felix";
                    }
                    if (form.equals(new AdjectiveForm(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE))) {
                        return "felicis";
                    }
                    return null;
                }

                @Override
                public String getBaseForm() {
                    return "felix";
                }
            };
            assertEquals("felix, Gen. felicis", adjective.getDefinition());
        }

    }

    @Test
    void identifyForm() {
        AdjectiveForm nom_sg_m_pos = new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE);
        AdjectiveForm adv_comp = new AdjectiveForm(true, ComparativeForm.COMPARATIVE);
        Adjective adjective = new Adjective(new TranslationSequence(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS) {
            @Override
            public String makeForm(AdjectiveForm form) {
                if (form.equals(nom_sg_m_pos) || form.equals(adv_comp)) return "targetform";
                return "";
            }

            @Override
            public String getBaseForm() { return null; }
        };
        assertEquals(List.of(nom_sg_m_pos, adv_comp), adjective.identifyForm("targetform", false));
    }
}