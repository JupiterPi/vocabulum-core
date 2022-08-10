package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class AdjectiveTest {
    I18n i18n = Database.get().getI18ns().internal();

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
            assertEquals("acer, acris, acre", adjective.getDefinition(i18n));
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
            assertEquals("felix, Gen. felicis", adjective.getDefinition(i18n));
        }

    }

    @Test
    void generateWordbaseEntrySpecificPart() {
        TranslationSequence translations = new TranslationSequence(
                new VocabularyTranslation(true, "heftig"),
                new VocabularyTranslation(false, "hart"),
                new VocabularyTranslation(false, "scharf")
        );
        Adjective adjective = new Adjective(translations, "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS) {
            @Override
            public String makeForm(AdjectiveForm form) {
                return form.formToString(i18n);
            }

            @Override
            public String getBaseForm() {
                return "baseform";
            }
        };
        Document e = Document.parse("""
                {
                    "forms": {
                        "positive": {
                            "masc": {
                                "sg": {
                                    "nom": "Nom. Sg. m.",
                                    "gen": "Gen. Sg. m.",
                                    "dat": "Dat. Sg. m.",
                                    "acc": "Acc. Sg. m.",
                                    "abl": "Abl. Sg. m."
                                },
                                "pl": {
                                    "nom": "Nom. Pl. m.",
                                    "gen": "Gen. Pl. m.",
                                    "dat": "Dat. Pl. m.",
                                    "acc": "Acc. Pl. m.",
                                    "abl": "Abl. Pl. m."
                                }
                            },
                            "fem": {
                                "sg": {
                                    "nom": "Nom. Sg. f.",
                                    "gen": "Gen. Sg. f.",
                                    "dat": "Dat. Sg. f.",
                                    "acc": "Acc. Sg. f.",
                                    "abl": "Abl. Sg. f."
                                },
                                "pl": {
                                    "nom": "Nom. Pl. f.",
                                    "gen": "Gen. Pl. f.",
                                    "dat": "Dat. Pl. f.",
                                    "acc": "Acc. Pl. f.",
                                    "abl": "Abl. Pl. f."
                                }
                            },
                            "neut": {
                                "sg": {
                                    "nom": "Nom. Sg. n.",
                                    "gen": "Gen. Sg. n.",
                                    "dat": "Dat. Sg. n.",
                                    "acc": "Acc. Sg. n.",
                                    "abl": "Abl. Sg. n."
                                },
                                "pl": {
                                    "nom": "Nom. Pl. n.",
                                    "gen": "Gen. Pl. n.",
                                    "dat": "Dat. Pl. n.",
                                    "acc": "Acc. Pl. n.",
                                    "abl": "Abl. Pl. n."
                                }
                            }
                        },
                        "comparative": {
                            "masc": {
                                "sg": {
                                    "nom": "Nom. Sg. m. Comp.",
                                    "gen": "Gen. Sg. m. Comp.",
                                    "dat": "Dat. Sg. m. Comp.",
                                    "acc": "Acc. Sg. m. Comp.",
                                    "abl": "Abl. Sg. m. Comp."
                                },
                                "pl": {
                                    "nom": "Nom. Pl. m. Comp.",
                                    "gen": "Gen. Pl. m. Comp.",
                                    "dat": "Dat. Pl. m. Comp.",
                                    "acc": "Acc. Pl. m. Comp.",
                                    "abl": "Abl. Pl. m. Comp."
                                }
                            },
                            "fem": {
                                "sg": {
                                    "nom": "Nom. Sg. f. Comp.",
                                    "gen": "Gen. Sg. f. Comp.",
                                    "dat": "Dat. Sg. f. Comp.",
                                    "acc": "Acc. Sg. f. Comp.",
                                    "abl": "Abl. Sg. f. Comp."
                                },
                                "pl": {
                                    "nom": "Nom. Pl. f. Comp.",
                                    "gen": "Gen. Pl. f. Comp.",
                                    "dat": "Dat. Pl. f. Comp.",
                                    "acc": "Acc. Pl. f. Comp.",
                                    "abl": "Abl. Pl. f. Comp."
                                }
                            },
                            "neut": {
                                "sg": {
                                    "nom": "Nom. Sg. n. Comp.",
                                    "gen": "Gen. Sg. n. Comp.",
                                    "dat": "Dat. Sg. n. Comp.",
                                    "acc": "Acc. Sg. n. Comp.",
                                    "abl": "Abl. Sg. n. Comp."
                                },
                                "pl": {
                                    "nom": "Nom. Pl. n. Comp.",
                                    "gen": "Gen. Pl. n. Comp.",
                                    "dat": "Dat. Pl. n. Comp.",
                                    "acc": "Acc. Pl. n. Comp.",
                                    "abl": "Abl. Pl. n. Comp."
                                }
                            }
                        },
                        "superlative": {
                            "masc": {
                                "sg": {
                                    "nom": "Nom. Sg. m. Sup.",
                                    "gen": "Gen. Sg. m. Sup.",
                                    "dat": "Dat. Sg. m. Sup.",
                                    "acc": "Acc. Sg. m. Sup.",
                                    "abl": "Abl. Sg. m. Sup."
                                },
                                "pl": {
                                    "nom": "Nom. Pl. m. Sup.",
                                    "gen": "Gen. Pl. m. Sup.",
                                    "dat": "Dat. Pl. m. Sup.",
                                    "acc": "Acc. Pl. m. Sup.",
                                    "abl": "Abl. Pl. m. Sup."
                                }
                            },
                            "fem": {
                                "sg": {
                                    "nom": "Nom. Sg. f. Sup.",
                                    "gen": "Gen. Sg. f. Sup.",
                                    "dat": "Dat. Sg. f. Sup.",
                                    "acc": "Acc. Sg. f. Sup.",
                                    "abl": "Abl. Sg. f. Sup."
                                },
                                "pl": {
                                    "nom": "Nom. Pl. f. Sup.",
                                    "gen": "Gen. Pl. f. Sup.",
                                    "dat": "Dat. Pl. f. Sup.",
                                    "acc": "Acc. Pl. f. Sup.",
                                    "abl": "Abl. Pl. f. Sup."
                                }
                            },
                            "neut": {
                                "sg": {
                                    "nom": "Nom. Sg. n. Sup.",
                                    "gen": "Gen. Sg. n. Sup.",
                                    "dat": "Dat. Sg. n. Sup.",
                                    "acc": "Acc. Sg. n. Sup.",
                                    "abl": "Abl. Sg. n. Sup."
                                },
                                "pl": {
                                    "nom": "Nom. Pl. n. Sup.",
                                    "gen": "Gen. Pl. n. Sup.",
                                    "dat": "Dat. Pl. n. Sup.",
                                    "acc": "Acc. Pl. n. Sup.",
                                    "abl": "Abl. Pl. n. Sup."
                                }
                            }
                        }
                    },
                    "definition_type": "from_base_forms"
                }
                """);
        assertEquals(e, adjective.generateWordbaseEntrySpecificPart());
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
        assertEquals(Arrays.asList(nom_sg_m_pos, adv_comp), adjective.identifyForm("targetform"));
    }
}