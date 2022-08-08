package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas;

import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo.Pattern;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleConjugationSchemaTest {
    @Test
    void readFromDocument() throws LoadingDataException {
        Document document = Document.parse("""
                {
                  "name": "a",
                  "infinitive": {
                    "present": {
                      "active": "",
                      "passive": "Pr+ari"
                    },
                    "perfect": {
                      "active": "Pf+isse",
                      "passive": "PPP+ esse"
                    },
                    "future": {
                      "active": "PFA+ esse",
                      "passive": "-"
                    }
                  },
                  "basic": {
                    "active": {
                      "present": {
                        "indicative": {
                          "sg": {
                            "first": "Pr+o",
                            "second": "Pr+as",
                            "third": "Pr+at"
                          },
                          "pl": {
                            "first": "Pr+amus",
                            "second": "Pr+atis",
                            "third": "Pr+ant"
                          }
                        },
                        "conjunctive": {
                          "sg": {
                            "first": "Pr+em",
                            "second": "Pr+es",
                            "third": "Pr+et"
                          },
                          "pl": {
                            "first": "Pr+emus",
                            "second": "Pr+etis",
                            "third": "Pr+ent"
                          }
                        }
                      },
                      "imperfect": {
                        "indicative": {
                          "sg": {
                            "first": "Pr+abam",
                            "second": "Pr+abas",
                            "third": "Pr+abat"
                          },
                          "pl": {
                            "first": "Pr+abamus",
                            "second": "Pr+abatis",
                            "third": "Pr+abant"
                          }
                        },
                        "conjunctive": {
                          "sg": {
                            "first": "Pr+arem",
                            "second": "Pr+ares",
                            "third": "Pr+aret"
                          },
                          "pl": {
                            "first": "Pr+aremus",
                            "second": "Pr+aretis",
                            "third": "Pr+arent"
                          }
                        }
                      },
                      "perfect": {
                        "indicative": {
                          "sg": {
                            "first": "Pf+i",
                            "second": "Pf+isti",
                            "third": "Pf+it"
                          },
                          "pl": {
                            "first": "Pf+imus",
                            "second": "Pf+istis",
                            "third": "Pf+erunt"
                          }
                        },
                        "conjunctive": {
                          "sg": {
                            "first": "Pf+erim",
                            "second": "Pf+eris",
                            "third": "Pf+erit"
                          },
                          "pl": {
                            "first": "Pf+erimus",
                            "second": "Pf+eritis",
                            "third": "Pf+erint"
                          }
                        }
                      },
                      "pluperfect": {
                        "indicative": {
                          "sg": {
                            "first": "Pf+eram",
                            "second": "Pf+eras",
                            "third": "Pf+erat"
                          },
                          "pl": {
                            "first": "Pf+eramus",
                            "second": "Pf+eratis",
                            "third": "Pf+erant"
                          }
                        },
                        "conjunctive": {
                          "sg": {
                            "first": "Pf+issem",
                            "second": "Pf+isses",
                            "third": "Pf+isset"
                          },
                          "pl": {
                            "first": "Pf+issemus",
                            "second": "Pf+issetis",
                            "third": "Pf+issent"
                          }
                        }
                      },
                      "future_i": {
                        "indicative": {
                          "sg": {
                            "first": "Pr+abo",
                            "second": "Pr+abis",
                            "third": "Pr+abit"
                          },
                          "pl": {
                            "first": "Pr+abimus",
                            "second": "Pr+abitis",
                            "third": "Pr+abunt"
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
                            "first": "Pf+ero",
                            "second": "Pf+eris",
                            "third": "Pf+erit"
                          },
                          "pl": {
                            "first": "Pf+erimus",
                            "second": "Pf+eritis",
                            "third": "Pf+erint"
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
                            "first": "Pr+or",
                            "second": "Pr+aris",
                            "third": "Pr+atur"
                          },
                          "pl": {
                            "first": "Pr+amur",
                            "second": "Pr+amini",
                            "third": "Pr+antur"
                          }
                        },
                        "conjunctive": {
                          "sg": {
                            "first": "Pr+er",
                            "second": "Pr+eris",
                            "third": "Pr+etur"
                          },
                          "pl": {
                            "first": "Pr+emur",
                            "second": "Pr+emini",
                            "third": "Pr+entur"
                          }
                        }
                      },
                      "imperfect": {
                        "indicative": {
                          "sg": {
                            "first": "Pr+abar",
                            "second": "Pr+abaris",
                            "third": "Pr+abatur"
                          },
                          "pl": {
                            "first": "Pr+abamur",
                            "second": "Pr+abamini",
                            "third": "Pr+abantur"
                          }
                        },
                        "conjunctive": {
                          "sg": {
                            "first": "Pr+rer",
                            "second": "Pr+areris",
                            "third": "Pr+aretur"
                          },
                          "pl": {
                            "first": "Pr+aremur",
                            "second": "Pr+aremini",
                            "third": "Pr+arentur"
                          }
                        }
                      },
                      "perfect": {
                        "indicative": {
                          "sg": {
                            "first": "PPP+ sum",
                            "second": "PPP+ es",
                            "third": "PPP+ est"
                          },
                          "pl": {
                            "first": "PPPs+ sumus",
                            "second": "PPPs+ estis",
                            "third": "PPPs+ sunt"
                          }
                        },
                        "conjunctive": {
                          "sg": {
                            "first": "PPP+ sim",
                            "second": "PPP+ sis",
                            "third": "PPP+ sit"
                          },
                          "pl": {
                            "first": "PPPs+ simus",
                            "second": "PPPs+ sitis",
                            "third": "PPPs+ sunt"
                          }
                        }
                      },
                      "pluperfect": {
                        "indicative": {
                          "sg": {
                            "first": "PPP+ eram",
                            "second": "PPP+ eras",
                            "third": "PPP+ erat"
                          },
                          "pl": {
                            "first": "PPPs+ eramus",
                            "second": "PPPs+ eratis",
                            "third": "PPPs+ erant"
                          }
                        },
                        "conjunctive": {
                          "sg": {
                            "first": "PPP+ essem",
                            "second": "PPP+ esses",
                            "third": "PPP+ esset"
                          },
                          "pl": {
                            "first": "PPPs+ essemus",
                            "second": "PPPs+ essetis",
                            "third": "PPPs+ essent"
                          }
                        }
                      },
                      "future_i": {
                        "indicative": {
                          "sg": {
                            "first": "Pr+abor",
                            "second": "Pr+aberis",
                            "third": "Pr+abitur"
                          },
                          "pl": {
                            "first": "Pr+abimur",
                            "second": "Pr+abimini",
                            "third": "Pr+abuntur"
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
                            "first": "PPP+ ero",
                            "second": "PPP+ eris",
                            "third": "PPP+ erit"
                          },
                          "pl": {
                            "first": "PPPs+ erimus",
                            "second": "PPPs+ eritis",
                            "third": "PPPs+ erunt"
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
                      "ppa": "Pr+a",
                      "pfa": "Pr+a",
                      "gerundium": "Pr+a",
                      "gerundivum": "Pr+a"
                    }
                }
                """);
        SimpleConjugationSchema s = SimpleConjugationSchema.readFromDocument(document);
        assertAll(
                () -> assertEquals(Pattern.fromString("Pr+ari"), s.getPattern(new VerbForm(InfinitiveTense.PRESENT, Voice.PASSIVE))),
                () -> assertEquals(Pattern.fromString("Pr+aremini"), s.getPattern(new VerbForm(new ConjugatedForm(Person.SECOND, CNumber.PL), Mode.CONJUNCTIVE, Tense.IMPERFECT, Voice.PASSIVE))),
                () -> assertEquals(Pattern.fromString("Pr+a"), s.getNounLikeFormRootPattern(NounLikeForm.PPA))
        );
    }
}