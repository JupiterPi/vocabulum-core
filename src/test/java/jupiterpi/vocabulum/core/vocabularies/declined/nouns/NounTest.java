package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;
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
    @DisplayName("generateWordbaseEntrySpecificPart()")
    class GenerateWordbaseEntrySpecificPart {

        @Test
        @DisplayName("all forms exist")
        void allFormsExist() {
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
                    return form.formToString();
                }

                @Override
                public String getBaseForm() {
                    return "amicus";
                }
            };
            Document e = Document.parse("""
                    {
                        "forms": {
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
                        "gender": "masc",
                        "declension_schema": "o"
                    }
                    """);
            assertEquals(e, noun.generateWordbaseEntrySpecificPart());
        }

        @Test
        @DisplayName("some forms don't exist")
        void someFormsDontExist() {
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
                    if (form.getDeclinedForm().getGender() != Gender.MASC) return "-";
                    return form.formToString();
                }

                @Override
                public String getBaseForm() {
                    return "amicus";
                }
            };
            Document e = Document.parse("""
                    {
                        "forms": {
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
                                    "nom": "-",
                                    "gen": "-",
                                    "dat": "-",
                                    "acc": "-",
                                    "abl": "-"
                                },
                                "pl": {
                                    "nom": "-",
                                    "gen": "-",
                                    "dat": "-",
                                    "acc": "-",
                                    "abl": "-"
                                }
                            },
                            "neut": {
                                "sg": {
                                    "nom": "-",
                                    "gen": "-",
                                    "dat": "-",
                                    "acc": "-",
                                    "abl": "-"
                                },
                                "pl": {
                                    "nom": "-",
                                    "gen": "-",
                                    "dat": "-",
                                    "acc": "-",
                                    "abl": "-"
                                }
                            }
                        },
                        "gender": "masc",
                        "declension_schema": "o"
                    }
                    """);
            assertEquals(e, noun.generateWordbaseEntrySpecificPart());
        }

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