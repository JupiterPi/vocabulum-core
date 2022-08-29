package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class WordbaseAdjectiveTest {
    Document sampleDocument = Document.parse("""
            {
              "_id": {
                "$oid": "630cad4529682e1debfc87bb"
              },
              "kind": "adjective",
              "base_form": "pulcher",
              "portion": "1",
              "translations": [
                "*hübsch*",
                "schön"
              ],
              "forms": {
                "adjectives": {
                  "positive": {
                    "masc": {
                      "sg": {
                        "nom": "pulcher",
                        "gen": "pulchri",
                        "dat": "pulchro",
                        "acc": "pulchrum",
                        "abl": "pulchro"
                      },
                      "pl": {
                        "nom": "pulchri",
                        "gen": "pulchrorum",
                        "dat": "pulchris",
                        "acc": "pulchros",
                        "abl": "pulchris"
                      }
                    },
                    "fem": {
                      "sg": {
                        "nom": "pulchra",
                        "gen": "pulchrae",
                        "dat": "pulchrae",
                        "acc": "pulchram",
                        "abl": "pulchra"
                      },
                      "pl": {
                        "nom": "pulchrae",
                        "gen": "pulchrarum",
                        "dat": "pulchris",
                        "acc": "pulchras",
                        "abl": "pulchris"
                      }
                    },
                    "neut": {
                      "sg": {
                        "nom": "pulchrum",
                        "gen": "pulchri",
                        "dat": "pulchro",
                        "acc": "pulchrum",
                        "abl": "pulchro"
                      },
                      "pl": {
                        "nom": "pulchra",
                        "gen": "pulchrorum",
                        "dat": "pulchris",
                        "acc": "pulchra",
                        "abl": "pulchris"
                      }
                    }
                  },
                  "comparative": {
                    "masc": {
                      "sg": {
                        "nom": "pulchrior",
                        "gen": "pulchrioris",
                        "dat": "pulchriori",
                        "acc": "pulchriorem",
                        "abl": "pulchriori"
                      },
                      "pl": {
                        "nom": "pulchriores",
                        "gen": "pulchriorium",
                        "dat": "pulchrioribus",
                        "acc": "pulchriores",
                        "abl": "pulchrioribus"
                      }
                    },
                    "fem": {
                      "sg": {
                        "nom": "pulchrior",
                        "gen": "pulchrioris",
                        "dat": "pulchriori",
                        "acc": "pulchriorem",
                        "abl": "pulchriori"
                      },
                      "pl": {
                        "nom": "pulchriores",
                        "gen": "pulchriorium",
                        "dat": "pulchrioribus",
                        "acc": "pulchriores",
                        "abl": "pulchrioribus"
                      }
                    },
                    "neut": {
                      "sg": {
                        "nom": "pulchrius",
                        "gen": "pulchrioris",
                        "dat": "pulchriori",
                        "acc": "pulchriorem",
                        "abl": "pulchriori"
                      },
                      "pl": {
                        "nom": "pulchrioria",
                        "gen": "pulchriorium",
                        "dat": "pulchrioribus",
                        "acc": "pulchrioria",
                        "abl": "pulchrioribus"
                      }
                    }
                  },
                  "superlative": {
                    "masc": {
                      "sg": {
                        "nom": "pulchrrimus",
                        "gen": "pulchrrimi",
                        "dat": "pulchrrimo",
                        "acc": "pulchrrimum",
                        "abl": "pulchrrimo"
                      },
                      "pl": {
                        "nom": "pulchrrimi",
                        "gen": "pulchrrimorum",
                        "dat": "pulchrrimis",
                        "acc": "pulchrrimos",
                        "abl": "pulchrrimis"
                      }
                    },
                    "fem": {
                      "sg": {
                        "nom": "pulchrrima",
                        "gen": "pulchrrimae",
                        "dat": "pulchrrimae",
                        "acc": "pulchrrimam",
                        "abl": "pulchrrima"
                      },
                      "pl": {
                        "nom": "pulchrrimae",
                        "gen": "pulchrrimarum",
                        "dat": "pulchrrimis",
                        "acc": "pulchrrimas",
                        "abl": "pulchrrimis"
                      }
                    },
                    "neut": {
                      "sg": {
                        "nom": "pulchrrimum",
                        "gen": "pulchrrimi",
                        "dat": "pulchrrimo",
                        "acc": "pulchrrimum",
                        "abl": "pulchrrimo"
                      },
                      "pl": {
                        "nom": "pulchrrima",
                        "gen": "pulchrrimorum",
                        "dat": "pulchrrimis",
                        "acc": "pulchrrima",
                        "abl": "pulchrrimis"
                      }
                    }
                  }
                },
                "adverbs": {
                  "positive": "pulchre",
                  "comparative": "pulchrius",
                  "superlative": "pulchrissime"
                }
              },
              "definition_type": "from_base_forms",
              "allFormsIndex": "pulcher pulchrior pulchrrimus pulchra pulchrior pulchrrima pulchrum pulchrius pulchrrimum pulchri pulchriores pulchrrimi pulchrae pulchriores pulchrrimae pulchra pulchrioria pulchrrima pulchri pulchrioris pulchrrimi pulchrae pulchrioris pulchrrimae pulchri pulchrioris pulchrrimi pulchrorum pulchriorium pulchrrimorum pulchrarum pulchriorium pulchrrimarum pulchrorum pulchriorium pulchrrimorum pulchro pulchriori pulchrrimo pulchrae pulchriori pulchrrimae pulchro pulchriori pulchrrimo pulchris pulchrioribus pulchrrimis pulchris pulchrioribus pulchrrimis pulchris pulchrioribus pulchrrimis pulchrum pulchriorem pulchrrimum pulchram pulchriorem pulchrrimam pulchrum pulchriorem pulchrrimum pulchros pulchriores pulchrrimos pulchras pulchriores pulchrrimas pulchra pulchrioria pulchrrima pulchro pulchriori pulchrrimo pulchra pulchriori pulchrrima pulchro pulchriori pulchrrimo pulchris pulchrioribus pulchrrimis pulchris pulchrioribus pulchrrimis pulchris pulchrioribus pulchrrimis pulchre pulchrius pulchrissime"
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
            a = new WordbaseAdjective("pulcher",
                    (Document) ((Document) sampleDocument.get("forms")).get("adjectives"), (Document) ((Document) sampleDocument.get("forms")).get("adverbs"),
                    new TranslationSequence(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS);
        }

        @Test
        @DisplayName("normal")
        void normal() throws DeclinedFormDoesNotExistException {
            assertEquals("pulchrrimorum", a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.GEN, NNumber.PL, Gender.NEUT), ComparativeForm.SUPERLATIVE)));
        }

        @Test
        @DisplayName("does not exist")
        void doesNotExist() {
            Document adjectiveFormsDocument = Document.parse("""
                    {
                                "positive": {
                                    "masc": {
                                        "sg": {
                                            "nom": "pulcher",
                                            "gen": "pulchri",
                                            "dat": "pulchro",
                                            "acc": "pulchrum",
                                            "abl": "pulchro"
                                        },
                                        "pl": {
                                            "nom": "pulchri",
                                            "gen": "pulchrorum",
                                            "dat": "pulchris",
                                            "acc": "pulchros",
                                            "abl": "pulchris"
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
                                            "nom": "pulchrum",
                                            "gen": "pulchri",
                                            "dat": "pulchro",
                                            "acc": "pulchrum",
                                            "abl": "pulchro"
                                        },
                                        "pl": {
                                            "nom": "pulchra",
                                            "gen": "pulchrorum",
                                            "dat": "pulchris",
                                            "acc": "pulchra",
                                            "abl": "pulchris"
                                        }
                                    }
                                },
                                "comparative": {
                                    "masc": {
                                        "sg": {
                                            "nom": "pulchrior",
                                            "gen": "pulchrioris",
                                            "dat": "pulchriori",
                                            "acc": "pulchriorem",
                                            "abl": "pulchriori"
                                        },
                                        "pl": {
                                            "nom": "pulchriores",
                                            "gen": "pulchriorium",
                                            "dat": "pulchrioribus",
                                            "acc": "pulchriores",
                                            "abl": "pulchrioribus"
                                        }
                                    },
                                    "fem": {
                                        "sg": {
                                            "nom": "pulchrior",
                                            "gen": "pulchrioris",
                                            "dat": "pulchriori",
                                            "acc": "pulchriorem",
                                            "abl": "pulchriori"
                                        },
                                        "pl": {
                                            "nom": "pulchriores",
                                            "gen": "pulchriorium",
                                            "dat": "pulchrioribus",
                                            "acc": "pulchriores",
                                            "abl": "pulchrioribus"
                                        }
                                    },
                                    "neut": {
                                        "sg": {
                                            "nom": "pulchrius",
                                            "gen": "pulchrioris",
                                            "dat": "pulchriori",
                                            "acc": "pulchriorem",
                                            "abl": "pulchriori"
                                        },
                                        "pl": {
                                            "nom": "pulchrioria",
                                            "gen": "pulchriorium",
                                            "dat": "pulchrioribus",
                                            "acc": "pulchrioria",
                                            "abl": "pulchrioribus"
                                        }
                                    }
                                },
                                "superlative": {
                                    "masc": {
                                        "sg": {
                                            "nom": "pulchrrimus",
                                            "gen": "pulchrrimi",
                                            "dat": "pulchrrimo",
                                            "acc": "pulchrrimum",
                                            "abl": "pulchrrimo"
                                        },
                                        "pl": {
                                            "nom": "pulchrrimi",
                                            "gen": "pulchrrimorum",
                                            "dat": "pulchrrimis",
                                            "acc": "pulchrrimos",
                                            "abl": "pulchrrimis"
                                        }
                                    },
                                    "fem": {
                                        "sg": {
                                            "nom": "pulchrrima",
                                            "gen": "pulchrrimae",
                                            "dat": "pulchrrimae",
                                            "acc": "pulchrrimam",
                                            "abl": "pulchrrima"
                                        },
                                        "pl": {
                                            "nom": "pulchrrimae",
                                            "gen": "pulchrrimarum",
                                            "dat": "pulchrrimis",
                                            "acc": "pulchrrimas",
                                            "abl": "pulchrrimis"
                                        }
                                    },
                                    "neut": {
                                        "sg": {
                                            "nom": "pulchrrimum",
                                            "gen": "pulchrrimi",
                                            "dat": "pulchrrimo",
                                            "acc": "pulchrrimum",
                                            "abl": "pulchrrimo"
                                        },
                                        "pl": {
                                            "nom": "pulchrrima",
                                            "gen": "pulchrrimorum",
                                            "dat": "pulchrrimis",
                                            "acc": "pulchrrima",
                                            "abl": "pulchrrimis"
                                        }
                                    }
                                }
                            }
                    """);
            Document adverbFormsDocument = Document.parse("""
                    {
                                "positive": "pulchre",
                                "comparative": "pulchrius",
                                "superlative": "pulchrissime"
                            }
                    """);
            a = new WordbaseAdjective("pulcher", adjectiveFormsDocument, adverbFormsDocument, new TranslationSequence(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS);
            assertThrows(DeclinedFormDoesNotExistException.class, () -> {
                a.makeForm(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.FEM), ComparativeForm.POSITIVE));
            });
        }

    }

    @Test
    @DisplayName("adverbs")
    void adverbs() throws DeclinedFormDoesNotExistException {
        Document adverbFormsDocument = Document.parse("""
                    {
                                "positive": "pulchre",
                                "comparative": "pulchrius",
                                "superlative": "pulchrissime"
                            }
                    """);
        WordbaseAdjective a = new WordbaseAdjective("pulcher", new Document(), adverbFormsDocument, new TranslationSequence(), "test", Adjective.AdjectiveDefinitionType.FROM_BASE_FORMS);
        assertEquals("pulchrius", a.makeForm(new AdjectiveForm(true, ComparativeForm.COMPARATIVE)));
    }
}
