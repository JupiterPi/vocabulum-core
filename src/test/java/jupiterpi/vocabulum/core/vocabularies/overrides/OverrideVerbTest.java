package jupiterpi.vocabulum.core.vocabularies.overrides;

import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OverrideVerbTest {

    OverrideVerb v = new OverrideVerb(
            "test", "definition",
            List.of(
                    new OverrideVocabularies.TextTemplatePart("(pr)"),
                    new OverrideVocabularies.OverrideTemplatePart("esse"),
                    new OverrideVocabularies.TextTemplatePart("(sf)")
            ),
            new TranslationSequence(), Document.parse("""
                {
                  "name": "esse",
                  "kind": "verb",
                  "overrides": {
                    "imperative": {
                      "sg": "es",
                      "pl": "este"
                    },
                    "infinitive": {
                      "present": {
                        "active": "esse",
                        "passive": "-"
                      },
                      "perfect": {
                        "active": "fuisse",
                        "passive": "futum esse"
                      },
                      "future": {
                        "active": "futurum esse",
                        "passive": "futum iri"
                      }
                    },
                    "basic": {
                      "active": {
                        "present": {
                          "indicative": {
                            "sg": {
                              "first": "sum",
                              "second": "es",
                              "third": "est"
                            },
                            "pl": {
                              "first": "sumus",
                              "second": "estis",
                              "third": "sunt"
                            }
                          },
                          "conjunctive": {
                            "sg": {
                              "first": "sim",
                              "second": "sis",
                              "third": "sit"
                            },
                            "pl": {
                              "first": "simus",
                              "second": "sitis",
                              "third": "sint"
                            }
                          }
                        },
                        "imperfect": {
                          "indicative": {
                            "sg": {
                              "first": "eram",
                              "second": "eras",
                              "third": "erat"
                            },
                            "pl": {
                              "first": "eramus",
                              "second": "eratis",
                              "third": "erant"
                            }
                          },
                          "conjunctive": {
                            "sg": {
                              "first": "essem",
                              "second": "esses",
                              "third": "esset"
                            },
                            "pl": {
                              "first": "essemus",
                              "second": "essetis",
                              "third": "essent"
                            }
                          }
                        },
                        "perfect": {
                          "indicative": {
                            "sg": {
                              "first": "fui",
                              "second": "fuisti",
                              "third": "fuit"
                            },
                            "pl": {
                              "first": "fuimus",
                              "second": "fuistis",
                              "third": "fuerunt"
                            }
                          },
                          "conjunctive": {
                            "sg": {
                              "first": "fuerim",
                              "second": "fueris",
                              "third": "fuerit"
                            },
                            "pl": {
                              "first": "fuerimus",
                              "second": "fueritis",
                              "third": "fuerint"
                            }
                          }
                        },
                        "pluperfect": {
                          "indicative": {
                            "sg": {
                              "first": "fueram",
                              "second": "fueras",
                              "third": "fuerat"
                            },
                            "pl": {
                              "first": "fueramus",
                              "second": "fueratis",
                              "third": "fuerant"
                            }
                          },
                          "conjunctive": {
                            "sg": {
                              "first": "fuissem",
                              "second": "fuisses",
                              "third": "fuisset"
                            },
                            "pl": {
                              "first": "fuissemus",
                              "second": "fuissetis",
                              "third": "fuissent"
                            }
                          }
                        },
                        "future_i": {
                          "indicative": {
                            "sg": {
                              "first": "ero",
                              "second": "eris",
                              "third": "erit"
                            },
                            "pl": {
                              "first": "erimus",
                              "second": "eritis",
                              "third": "erunt"
                            }
                          },
                          "conjunctive": {
                            "sg": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            },
                            "pl": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            }
                          }
                        },
                        "future_ii": {
                          "indicative": {
                            "sg": {
                              "first": "fuero",
                              "second": "fueris",
                              "third": "fuerit"
                            },
                            "pl": {
                              "first": "fuerimus",
                              "second": "fueritis",
                              "third": "fuerint"
                            }
                          },
                          "conjunctive": {
                            "sg": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            },
                            "pl": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            }
                          }
                        }
                      },
                      "passive": {
                        "present": {
                          "indicative": {
                            "sg": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            },
                            "pl": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            }
                          },
                          "conjunctive": {
                            "sg": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            },
                            "pl": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            }
                          }
                        },
                        "imperfect": {
                          "indicative": {
                            "sg": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            },
                            "pl": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            }
                          },
                          "conjunctive": {
                            "sg": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            },
                            "pl": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            }
                          }
                        },
                        "perfect": {
                          "indicative": {
                            "sg": {
                              "first": "futus sum",
                              "second": "futus es",
                              "third": "futus est"
                            },
                            "pl": {
                              "first": "futi sumus",
                              "second": "futi estis",
                              "third": "futi sunt"
                            }
                          },
                          "conjunctive": {
                            "sg": {
                              "first": "futus sim",
                              "second": "futus sis",
                              "third": "futus sit"
                            },
                            "pl": {
                              "first": "futi simus",
                              "second": "futi sitis",
                              "third": "futi sint"
                            }
                          }
                        },
                        "pluperfect": {
                          "indicative": {
                            "sg": {
                              "first": "futus eram",
                              "second": "futus eras",
                              "third": "futus erat"
                            },
                            "pl": {
                              "first": "futi eramus",
                              "second": "futi eratis",
                              "third": "futi erant"
                            }
                          },
                          "conjunctive": {
                            "sg": {
                              "first": "futus essem",
                              "second": "futus esses",
                              "third": "futus esset"
                            },
                            "pl": {
                              "first": "futi essemus",
                              "second": "futi essetis",
                              "third": "futi essent"
                            }
                          }
                        },
                        "future_i": {
                          "indicative": {
                            "sg": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            },
                            "pl": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            }
                          },
                          "conjunctive": {
                            "sg": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            },
                            "pl": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            }
                          }
                        },
                        "future_ii": {
                          "indicative": {
                            "sg": {
                              "first": "futus ero",
                              "second": "futus eris",
                              "third": "futus erit"
                            },
                            "pl": {
                              "first": "futi erimus",
                              "second": "futi eritis",
                              "third": "futi erunt"
                            }
                          },
                          "conjunctive": {
                            "sg": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            },
                            "pl": {
                              "first": "-",
                              "second": "-",
                              "third": "-"
                            }
                          }
                        }
                      }
                    },
                    "noun_like": {
                      "ppp": "",
                      "gerundium": "-",
                      "gerundivum": "-",
                      "ppa": "",
                      "pfa": ""
                    }
                  }
                }
                """)
    );

    @Test
    @DisplayName("correct vocabulary")
    void normal() {
        assertAll(
            () -> assertEquals("(pr)esse(sf)", v.getBaseForm()),
            () -> assertEquals("definition", v.getDefinition()),
            () -> assertNull(v.getConjugationSchema())
        );
    }

    @Test
    @DisplayName("make forms")
    void makeForms() {
        assertAll(
            () -> assertEquals("(pr)es(sf)", v.makeForm(new VerbForm(CNumber.SG)).toString()),
            () -> assertEquals("(pr)esse(sf)", v.makeForm(new VerbForm(InfinitiveTense.PRESENT, Voice.ACTIVE)).toString()),
            () -> assertEquals("(pr)sum(sf)", v.makeForm(new VerbForm(new ConjugatedForm(Person.FIRST, CNumber.SG), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE)).toString())
        );
    }

}