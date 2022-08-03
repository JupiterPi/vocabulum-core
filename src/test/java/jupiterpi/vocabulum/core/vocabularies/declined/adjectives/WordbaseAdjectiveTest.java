package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class WordbaseAdjectiveTest {
    Document sampleDocument = Document.parse("""
            {
              "kind": "adjective",
              "base_form": "pulcher",
              "portion": "1",
              "forms": {
                "positive": {
                  "masc": {
                    "sg": {
                      "nom": "pulcher",
                      "acc": "pulchrum",
                      "gen": "pulchri",
                      "dat": "pulchro",
                      "abl": "pulchro"
                    },
                    "pl": {
                      "nom": "pulchri",
                      "acc": "pulchros",
                      "gen": "pulchrorum",
                      "dat": "pulchris",
                      "abl": "pulchris"
                    }
                  },
                  "fem": {
                    "sg": {
                      "nom": "pulchra",
                      "acc": "pulchram",
                      "gen": "pulchrae",
                      "dat": "pulchrae",
                      "abl": "pulchra"
                    },
                    "pl": {
                      "nom": "pulchrae",
                      "acc": "pulchras",
                      "gen": "pulchrarum",
                      "dat": "pulchris",
                      "abl": "pulchris"
                    }
                  },
                  "neut": {
                    "sg": {
                      "nom": "pulchrum",
                      "acc": "pulchrum",
                      "gen": "pulchri",
                      "dat": "pulchro",
                      "abl": "pulchro"
                    },
                    "pl": {
                      "nom": "pulchra",
                      "acc": "pulchra",
                      "gen": "pulchrorum",
                      "dat": "pulchris",
                      "abl": "pulchris"
                    }
                  }
                },
                "comparative": {
                  "masc": {
                    "sg": {
                      "nom": "pulcher",
                      "acc": "pulchrum",
                      "gen": "pulchri",
                      "dat": "pulchro",
                      "abl": "pulchro"
                    },
                    "pl": {
                      "nom": "pulchri",
                      "acc": "pulchros",
                      "gen": "pulchrorum",
                      "dat": "pulchris",
                      "abl": "pulchris"
                    }
                  },
                  "fem": {
                    "sg": {
                      "nom": "pulchra",
                      "acc": "pulchram",
                      "gen": "pulchrae",
                      "dat": "pulchrae",
                      "abl": "pulchra"
                    },
                    "pl": {
                      "nom": "pulchrae",
                      "acc": "pulchras",
                      "gen": "pulchrarum",
                      "dat": "pulchris",
                      "abl": "pulchris"
                    }
                  },
                  "neut": {
                    "sg": {
                      "nom": "pulchrum",
                      "acc": "pulchrum",
                      "gen": "pulchri",
                      "dat": "pulchro",
                      "abl": "pulchro"
                    },
                    "pl": {
                      "nom": "pulchra",
                      "acc": "pulchra",
                      "gen": "pulchrorum",
                      "dat": "pulchris",
                      "abl": "pulchris"
                    }
                  }
                },
                "superlative": {
                  "masc": {
                    "sg": {
                      "nom": "pulcher",
                      "acc": "pulchrum",
                      "gen": "pulchri",
                      "dat": "pulchro",
                      "abl": "pulchro"
                    },
                    "pl": {
                      "nom": "pulchri",
                      "acc": "pulchros",
                      "gen": "pulchrorum",
                      "dat": "pulchris",
                      "abl": "pulchris"
                    }
                  },
                  "fem": {
                    "sg": {
                      "nom": "pulchra",
                      "acc": "pulchram",
                      "gen": "pulchrae",
                      "dat": "pulchrae",
                      "abl": "pulchra"
                    },
                    "pl": {
                      "nom": "pulchrae",
                      "acc": "pulchras",
                      "gen": "pulchrarum",
                      "dat": "pulchris",
                      "abl": "pulchris"
                    }
                  },
                  "neut": {
                    "sg": {
                      "nom": "pulchrum",
                      "acc": "pulchrum",
                      "gen": "pulchri",
                      "dat": "pulchro",
                      "abl": "pulchro"
                    },
                    "pl": {
                      "nom": "pulchra",
                      "acc": "pulchra",
                      "gen": "pulchrorum",
                      "dat": "pulchris",
                      "abl": "pulchris"
                    }
                  }
                }
              },
              "translations": [
                "*hübsch*",
                "schön"
              ],
              "definition_type": "from_base_forms"
            }
            """);

    @Test
    void readFromDocument() {
        WordbaseAdjective a = WordbaseAdjective.readFromDocument(sampleDocument);
        assertAll(
                () -> assertEquals("pulcher", a.getBaseForm()),
                () -> assertEquals("pulcher", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE))),
                () -> assertEquals("pulchra", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM), ComparativeForm.POSITIVE)))
        );
    }

    @Nested
    @DisplayName("makeForm()")
    class MakeForm {

        WordbaseAdjective a;

        @BeforeEach
        void init() {
            a = new WordbaseAdjective("pulcher", (Document) sampleDocument.get("forms"), new ArrayList<>(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS);
        }

        @Test
        @DisplayName("normal")
        void normal() throws DeclinedFormDoesNotExistException {
            assertEquals("pulchrorum", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.GEN, NNumber.PL, Gender.NEUT), ComparativeForm.SUPERLATIVE)));
        }

        @Test
        @DisplayName("does not exist")
        void doesNotExist() {
            Document formsDocument = Document.parse("""
                    {
                      "positive": {
                        "masc": {
                          "sg": {
                            "nom": "pulcher",
                            "acc": "pulchrum",
                            "gen": "pulchri",
                            "dat": "pulchro",
                            "abl": "pulchro"
                          },
                          "pl": {
                            "nom": "pulchri",
                            "acc": "pulchros",
                            "gen": "pulchrorum",
                            "dat": "pulchris",
                            "abl": "pulchris"
                          }
                        },
                        "fem": {
                          "sg": {
                            "nom": "-",
                            "acc": "-",
                            "gen": "-",
                            "dat": "-",
                            "abl": "-"
                          },
                          "pl": {
                            "nom": "-",
                            "acc": "-",
                            "gen": "-",
                            "dat": "-",
                            "abl": "-"
                          }
                        },
                        "neut": {
                          "sg": {
                            "nom": "pulchrum",
                            "acc": "pulchrum",
                            "gen": "pulchri",
                            "dat": "pulchro",
                            "abl": "pulchro"
                          },
                          "pl": {
                            "nom": "pulchra",
                            "acc": "pulchra",
                            "gen": "pulchrorum",
                            "dat": "pulchris",
                            "abl": "pulchris"
                          }
                        }
                      },
                      "comparative": {
                        "masc": {
                          "sg": {
                            "nom": "pulcher",
                            "acc": "pulchrum",
                            "gen": "pulchri",
                            "dat": "pulchro",
                            "abl": "pulchro"
                          },
                          "pl": {
                            "nom": "pulchri",
                            "acc": "pulchros",
                            "gen": "pulchrorum",
                            "dat": "pulchris",
                            "abl": "pulchris"
                          }
                        },
                        "fem": {
                          "sg": {
                            "nom": "pulchra",
                            "acc": "pulchram",
                            "gen": "pulchrae",
                            "dat": "pulchrae",
                            "abl": "pulchra"
                          },
                          "pl": {
                            "nom": "pulchrae",
                            "acc": "pulchras",
                            "gen": "pulchrarum",
                            "dat": "pulchris",
                            "abl": "pulchris"
                          }
                        },
                        "neut": {
                          "sg": {
                            "nom": "pulchrum",
                            "acc": "pulchrum",
                            "gen": "pulchri",
                            "dat": "pulchro",
                            "abl": "pulchro"
                          },
                          "pl": {
                            "nom": "pulchra",
                            "acc": "pulchra",
                            "gen": "pulchrorum",
                            "dat": "pulchris",
                            "abl": "pulchris"
                          }
                        }
                      },
                      "superlative": {
                        "masc": {
                          "sg": {
                            "nom": "pulcher",
                            "acc": "pulchrum",
                            "gen": "pulchri",
                            "dat": "pulchro",
                            "abl": "pulchro"
                          },
                          "pl": {
                            "nom": "pulchri",
                            "acc": "pulchros",
                            "gen": "pulchrorum",
                            "dat": "pulchris",
                            "abl": "pulchris"
                          }
                        },
                        "fem": {
                          "sg": {
                            "nom": "pulchra",
                            "acc": "pulchram",
                            "gen": "pulchrae",
                            "dat": "pulchrae",
                            "abl": "pulchra"
                          },
                          "pl": {
                            "nom": "pulchrae",
                            "acc": "pulchras",
                            "gen": "pulchrarum",
                            "dat": "pulchris",
                            "abl": "pulchris"
                          }
                        },
                        "neut": {
                          "sg": {
                            "nom": "pulchrum",
                            "acc": "pulchrum",
                            "gen": "pulchri",
                            "dat": "pulchro",
                            "abl": "pulchro"
                          },
                          "pl": {
                            "nom": "pulchra",
                            "acc": "pulchra",
                            "gen": "pulchrorum",
                            "dat": "pulchris",
                            "abl": "pulchris"
                          }
                        }
                      }
                    }
                    """);
            a = new WordbaseAdjective("pulcher", formsDocument, new ArrayList<>(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS);
            assertThrows(DeclinedFormDoesNotExistException.class, () -> {
                a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM), ComparativeForm.POSITIVE));
            });
        }

    }
}