package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.PlainTextPart;
import jupiterpi.vocabulum.core.vocabularies.translations.parts.container.TranslationPartContainer;
import org.bson.Document;
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
    void generateWordbaseEntrySpecificPart() {
        TranslationSequence translations = new TranslationSequence(
                new VocabularyTranslation(true, new TranslationPartContainer(new PlainTextPart("heftig"))),
                new VocabularyTranslation(false, new TranslationPartContainer(new PlainTextPart("hart"))),
                new VocabularyTranslation(false, new TranslationPartContainer(new PlainTextPart("scharf")))
        );
        Adjective adjective = new Adjective(translations, "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS) {
            @Override
            public String makeForm(AdjectiveForm form) {
                return form.formToString();
            }

            @Override
            public String getBaseForm() {
                return "baseform";
            }
        };
        Document e = Document.parse("""
                {
                    "forms": {
                        "adjectives": {
                            "positive": {
                                "masc": {
                                    "sg": {
                                        "nom": "Nom. Sg. m.",
                                        "gen": "Gen. Sg. m.",
                                        "dat": "Dat. Sg. m.",
                                        "acc": "Akk. Sg. m.",
                                        "abl": "Abl. Sg. m."
                                    },
                                    "pl": {
                                        "nom": "Nom. Pl. m.",
                                        "gen": "Gen. Pl. m.",
                                        "dat": "Dat. Pl. m.",
                                        "acc": "Akk. Pl. m.",
                                        "abl": "Abl. Pl. m."
                                    }
                                },
                                "fem": {
                                    "sg": {
                                        "nom": "Nom. Sg. f.",
                                        "gen": "Gen. Sg. f.",
                                        "dat": "Dat. Sg. f.",
                                        "acc": "Akk. Sg. f.",
                                        "abl": "Abl. Sg. f."
                                    },
                                    "pl": {
                                        "nom": "Nom. Pl. f.",
                                        "gen": "Gen. Pl. f.",
                                        "dat": "Dat. Pl. f.",
                                        "acc": "Akk. Pl. f.",
                                        "abl": "Abl. Pl. f."
                                    }
                                },
                                "neut": {
                                    "sg": {
                                        "nom": "Nom. Sg. n.",
                                        "gen": "Gen. Sg. n.",
                                        "dat": "Dat. Sg. n.",
                                        "acc": "Akk. Sg. n.",
                                        "abl": "Abl. Sg. n."
                                    },
                                    "pl": {
                                        "nom": "Nom. Pl. n.",
                                        "gen": "Gen. Pl. n.",
                                        "dat": "Dat. Pl. n.",
                                        "acc": "Akk. Pl. n.",
                                        "abl": "Abl. Pl. n."
                                    }
                                }
                            },
                            "comparative": {
                                "masc": {
                                    "sg": {
                                        "nom": "Nom. Sg. m. Komp.",
                                        "gen": "Gen. Sg. m. Komp.",
                                        "dat": "Dat. Sg. m. Komp.",
                                        "acc": "Akk. Sg. m. Komp.",
                                        "abl": "Abl. Sg. m. Komp."
                                    },
                                    "pl": {
                                        "nom": "Nom. Pl. m. Komp.",
                                        "gen": "Gen. Pl. m. Komp.",
                                        "dat": "Dat. Pl. m. Komp.",
                                        "acc": "Akk. Pl. m. Komp.",
                                        "abl": "Abl. Pl. m. Komp."
                                    }
                                },
                                "fem": {
                                    "sg": {
                                        "nom": "Nom. Sg. f. Komp.",
                                        "gen": "Gen. Sg. f. Komp.",
                                        "dat": "Dat. Sg. f. Komp.",
                                        "acc": "Akk. Sg. f. Komp.",
                                        "abl": "Abl. Sg. f. Komp."
                                    },
                                    "pl": {
                                        "nom": "Nom. Pl. f. Komp.",
                                        "gen": "Gen. Pl. f. Komp.",
                                        "dat": "Dat. Pl. f. Komp.",
                                        "acc": "Akk. Pl. f. Komp.",
                                        "abl": "Abl. Pl. f. Komp."
                                    }
                                },
                                "neut": {
                                    "sg": {
                                        "nom": "Nom. Sg. n. Komp.",
                                        "gen": "Gen. Sg. n. Komp.",
                                        "dat": "Dat. Sg. n. Komp.",
                                        "acc": "Akk. Sg. n. Komp.",
                                        "abl": "Abl. Sg. n. Komp."
                                    },
                                    "pl": {
                                        "nom": "Nom. Pl. n. Komp.",
                                        "gen": "Gen. Pl. n. Komp.",
                                        "dat": "Dat. Pl. n. Komp.",
                                        "acc": "Akk. Pl. n. Komp.",
                                        "abl": "Abl. Pl. n. Komp."
                                    }
                                }
                            },
                            "superlative": {
                                "masc": {
                                    "sg": {
                                        "nom": "Nom. Sg. m. Sup.",
                                        "gen": "Gen. Sg. m. Sup.",
                                        "dat": "Dat. Sg. m. Sup.",
                                        "acc": "Akk. Sg. m. Sup.",
                                        "abl": "Abl. Sg. m. Sup."
                                    },
                                    "pl": {
                                        "nom": "Nom. Pl. m. Sup.",
                                        "gen": "Gen. Pl. m. Sup.",
                                        "dat": "Dat. Pl. m. Sup.",
                                        "acc": "Akk. Pl. m. Sup.",
                                        "abl": "Abl. Pl. m. Sup."
                                    }
                                },
                                "fem": {
                                    "sg": {
                                        "nom": "Nom. Sg. f. Sup.",
                                        "gen": "Gen. Sg. f. Sup.",
                                        "dat": "Dat. Sg. f. Sup.",
                                        "acc": "Akk. Sg. f. Sup.",
                                        "abl": "Abl. Sg. f. Sup."
                                    },
                                    "pl": {
                                        "nom": "Nom. Pl. f. Sup.",
                                        "gen": "Gen. Pl. f. Sup.",
                                        "dat": "Dat. Pl. f. Sup.",
                                        "acc": "Akk. Pl. f. Sup.",
                                        "abl": "Abl. Pl. f. Sup."
                                    }
                                },
                                "neut": {
                                    "sg": {
                                        "nom": "Nom. Sg. n. Sup.",
                                        "gen": "Gen. Sg. n. Sup.",
                                        "dat": "Dat. Sg. n. Sup.",
                                        "acc": "Akk. Sg. n. Sup.",
                                        "abl": "Abl. Sg. n. Sup."
                                    },
                                    "pl": {
                                        "nom": "Nom. Pl. n. Sup.",
                                        "gen": "Gen. Pl. n. Sup.",
                                        "dat": "Dat. Pl. n. Sup.",
                                        "acc": "Akk. Pl. n. Sup.",
                                        "abl": "Abl. Pl. n. Sup."
                                    }
                                }
                            }
                        },
                        "adverbs": {
                            "positive": "Adv.",
                            "comparative": "Adv. Komp.",
                            "superlative": "Adv. Sup."
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
        assertEquals(List.of(nom_sg_m_pos, adv_comp), adjective.identifyForm("targetform", false));
    }
}