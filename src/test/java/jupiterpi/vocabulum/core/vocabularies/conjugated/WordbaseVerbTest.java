package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class WordbaseVerbTest {
    Document sampleDocument = Document.parse("""
            {
              "_id": {
                "$oid": "630b606a20606068e7e8933a"
              },
              "kind": "verb",
              "base_form": "vocare",
              "portion": "1",
              "translations": [
                "*rufen*",
                "*nennen*"
              ],
              "forms": {
                "imperative": {
                  "sg": "voca",
                  "pl": "vocate"
                },
                "infinitive": {
                  "present": {
                    "active": "",
                    "passive": "vocari"
                  },
                  "perfect": {
                    "active": "vocavisse",
                    "passive": "vocatus esse"
                  },
                  "future": {
                    "active": "vocaturus esse",
                    "passive": "-"
                  }
                },
                "basic": {
                  "active": {
                    "present": {
                      "indicative": {
                        "sg": {
                          "first": "voco",
                          "second": "vocas",
                          "third": "vocat"
                        },
                        "pl": {
                          "first": "vocamus",
                          "second": "vocatis",
                          "third": "vocant"
                        }
                      },
                      "conjunctive": {
                        "sg": {
                          "first": "vocem",
                          "second": "voces",
                          "third": "vocet"
                        },
                        "pl": {
                          "first": "vocemus",
                          "second": "vocetis",
                          "third": "vocent"
                        }
                      }
                    },
                    "imperfect": {
                      "indicative": {
                        "sg": {
                          "first": "vocabam",
                          "second": "vocabas",
                          "third": "vocabat"
                        },
                        "pl": {
                          "first": "vocabamus",
                          "second": "vocabatis",
                          "third": "vocabant"
                        }
                      },
                      "conjunctive": {
                        "sg": {
                          "first": "vocarem",
                          "second": "vocares",
                          "third": "vocaret"
                        },
                        "pl": {
                          "first": "vocaremus",
                          "second": "vocaretis",
                          "third": "vocarent"
                        }
                      }
                    },
                    "perfect": {
                      "indicative": {
                        "sg": {
                          "first": "vocavi",
                          "second": "vocavisti",
                          "third": "vocavit"
                        },
                        "pl": {
                          "first": "vocavimus",
                          "second": "vocavistis",
                          "third": "vocaverunt"
                        }
                      },
                      "conjunctive": {
                        "sg": {
                          "first": "vocaverim",
                          "second": "vocaveris",
                          "third": "vocaverit"
                        },
                        "pl": {
                          "first": "vocaverimus",
                          "second": "vocaveritis",
                          "third": "vocaverint"
                        }
                      }
                    },
                    "pluperfect": {
                      "indicative": {
                        "sg": {
                          "first": "vocaveram",
                          "second": "vocaveras",
                          "third": "vocaverat"
                        },
                        "pl": {
                          "first": "vocaveramus",
                          "second": "vocaveratis",
                          "third": "vocaverant"
                        }
                      },
                      "conjunctive": {
                        "sg": {
                          "first": "vocavissem",
                          "second": "vocavisses",
                          "third": "vocavisset"
                        },
                        "pl": {
                          "first": "vocavissemus",
                          "second": "vocavissetis",
                          "third": "vocavissent"
                        }
                      }
                    },
                    "future_i": {
                      "indicative": {
                        "sg": {
                          "first": "vocabo",
                          "second": "vocabis",
                          "third": "vocabit"
                        },
                        "pl": {
                          "first": "vocabimus",
                          "second": "vocabitis",
                          "third": "vocabunt"
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
                          "first": "vocavero",
                          "second": "vocaveris",
                          "third": "vocaverit"
                        },
                        "pl": {
                          "first": "vocaverimus",
                          "second": "vocaveritis",
                          "third": "vocaverint"
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
                          "first": "vocor",
                          "second": "vocaris",
                          "third": "vocatur"
                        },
                        "pl": {
                          "first": "vocamur",
                          "second": "vocamini",
                          "third": "vocantur"
                        }
                      },
                      "conjunctive": {
                        "sg": {
                          "first": "vocer",
                          "second": "voceris",
                          "third": "vocetur"
                        },
                        "pl": {
                          "first": "vocemur",
                          "second": "vocemini",
                          "third": "vocentur"
                        }
                      }
                    },
                    "imperfect": {
                      "indicative": {
                        "sg": {
                          "first": "vocabar",
                          "second": "vocabaris",
                          "third": "vocabatur"
                        },
                        "pl": {
                          "first": "vocabamur",
                          "second": "vocabamini",
                          "third": "vocabantur"
                        }
                      },
                      "conjunctive": {
                        "sg": {
                          "first": "vocrer",
                          "second": "vocareris",
                          "third": "vocaretur"
                        },
                        "pl": {
                          "first": "vocaremur",
                          "second": "vocaremini",
                          "third": "vocarentur"
                        }
                      }
                    },
                    "perfect": {
                      "indicative": {
                        "sg": {
                          "first": "vocatus sum",
                          "second": "vocatus es",
                          "third": "vocatus est"
                        },
                        "pl": {
                          "first": "vocati sumus",
                          "second": "vocati estis",
                          "third": "vocati sunt"
                        }
                      },
                      "conjunctive": {
                        "sg": {
                          "first": "vocatus sim",
                          "second": "vocatus sis",
                          "third": "vocatus sit"
                        },
                        "pl": {
                          "first": "vocati simus",
                          "second": "vocati sitis",
                          "third": "vocati sunt"
                        }
                      }
                    },
                    "pluperfect": {
                      "indicative": {
                        "sg": {
                          "first": "vocatus eram",
                          "second": "vocatus eras",
                          "third": "vocatus erat"
                        },
                        "pl": {
                          "first": "vocati eramus",
                          "second": "vocati eratis",
                          "third": "vocati erant"
                        }
                      },
                      "conjunctive": {
                        "sg": {
                          "first": "vocatus essem",
                          "second": "vocatus esses",
                          "third": "vocatus esset"
                        },
                        "pl": {
                          "first": "vocati essemus",
                          "second": "vocati essetis",
                          "third": "vocati essent"
                        }
                      }
                    },
                    "future_i": {
                      "indicative": {
                        "sg": {
                          "first": "vocabor",
                          "second": "vocaberis",
                          "third": "vocabitur"
                        },
                        "pl": {
                          "first": "vocabimur",
                          "second": "vocabimini",
                          "third": "vocabuntur"
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
                          "first": "vocatus ero",
                          "second": "vocatus eris",
                          "third": "vocatus erit"
                        },
                        "pl": {
                          "first": "vocati erimus",
                          "second": "vocati eritis",
                          "third": "vocati erunt"
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
                  "ppp": {
                    "masc": {
                      "sg": {
                        "nom": "vocatus",
                        "gen": "vocati",
                        "dat": "vocato",
                        "acc": "vocatum",
                        "abl": "vocato"
                      },
                      "pl": {
                        "nom": "vocati",
                        "gen": "vocatorum",
                        "dat": "vocatis",
                        "acc": "vocatos",
                        "abl": "vocatis"
                      }
                    },
                    "fem": {
                      "sg": {
                        "nom": "vocata",
                        "gen": "vocatae",
                        "dat": "vocatae",
                        "acc": "vocatam",
                        "abl": "vocata"
                      },
                      "pl": {
                        "nom": "vocatae",
                        "gen": "vocatarum",
                        "dat": "vocatis",
                        "acc": "vocatas",
                        "abl": "vocatis"
                      }
                    },
                    "neut": {
                      "sg": {
                        "nom": "vocatum",
                        "gen": "vocati",
                        "dat": "vocato",
                        "acc": "vocatum",
                        "abl": "vocato"
                      },
                      "pl": {
                        "nom": "vocata",
                        "gen": "vocatorum",
                        "dat": "vocatis",
                        "acc": "vocata",
                        "abl": "vocatis"
                      }
                    }
                  },
                  "ppa": {
                    "masc": {
                      "sg": {
                        "nom": "vocans",
                        "gen": "vocantis",
                        "dat": "vocanti",
                        "acc": "vocantem",
                        "abl": "vocanti"
                      },
                      "pl": {
                        "nom": "vocantes",
                        "gen": "vocantium",
                        "dat": "vocantibus",
                        "acc": "vocantes",
                        "abl": "vocantibus"
                      }
                    },
                    "fem": {
                      "sg": {
                        "nom": "vocans",
                        "gen": "vocantis",
                        "dat": "vocanti",
                        "acc": "vocantem",
                        "abl": "vocanti"
                      },
                      "pl": {
                        "nom": "vocantes",
                        "gen": "vocantium",
                        "dat": "vocantibus",
                        "acc": "vocantes",
                        "abl": "vocantibus"
                      }
                    },
                    "neut": {
                      "sg": {
                        "nom": "vocans",
                        "gen": "vocantis",
                        "dat": "vocanti",
                        "acc": "vocantem",
                        "abl": "vocanti"
                      },
                      "pl": {
                        "nom": "vocantia",
                        "gen": "vocantium",
                        "dat": "vocantibus",
                        "acc": "vocantia",
                        "abl": "vocantibus"
                      }
                    }
                  },
                  "pfa": {
                    "masc": {
                      "sg": {
                        "nom": "vocaturus",
                        "gen": "vocaturi",
                        "dat": "vocaturo",
                        "acc": "vocaturum",
                        "abl": "vocaturo"
                      },
                      "pl": {
                        "nom": "vocaturi",
                        "gen": "vocaturorum",
                        "dat": "vocaturis",
                        "acc": "vocaturos",
                        "abl": "vocaturis"
                      }
                    },
                    "fem": {
                      "sg": {
                        "nom": "vocatura",
                        "gen": "vocaturae",
                        "dat": "vocaturae",
                        "acc": "vocaturam",
                        "abl": "vocatura"
                      },
                      "pl": {
                        "nom": "vocaturae",
                        "gen": "vocaturarum",
                        "dat": "vocaturis",
                        "acc": "vocaturas",
                        "abl": "vocaturis"
                      }
                    },
                    "neut": {
                      "sg": {
                        "nom": "vocaturum",
                        "gen": "vocaturi",
                        "dat": "vocaturo",
                        "acc": "vocaturum",
                        "abl": "vocaturo"
                      },
                      "pl": {
                        "nom": "vocatura",
                        "gen": "vocaturorum",
                        "dat": "vocaturis",
                        "acc": "vocatura",
                        "abl": "vocaturis"
                      }
                    }
                  },
                  "gerundium": {
                    "masc": {
                      "sg": {
                        "nom": "vocare",
                        "gen": "vocandi",
                        "dat": "-",
                        "acc": "vocandum",
                        "abl": "vocando"
                      },
                      "pl": {
                        "nom": "-",
                        "gen": "-",
                        "dat": "-",
                        "acc": "-",
                        "abl": "-"
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
                  "gerundivum": {
                    "masc": {
                      "sg": {
                        "nom": "vocandus",
                        "gen": "vocandi",
                        "dat": "vocando",
                        "acc": "vocandum",
                        "abl": "vocando"
                      },
                      "pl": {
                        "nom": "vocandi",
                        "gen": "vocandorum",
                        "dat": "vocandis",
                        "acc": "vocandos",
                        "abl": "vocandis"
                      }
                    },
                    "fem": {
                      "sg": {
                        "nom": "vocanda",
                        "gen": "vocandae",
                        "dat": "vocandae",
                        "acc": "vocandam",
                        "abl": "vocanda"
                      },
                      "pl": {
                        "nom": "vocandae",
                        "gen": "vocandarum",
                        "dat": "vocandis",
                        "acc": "vocandas",
                        "abl": "vocandis"
                      }
                    },
                    "neut": {
                      "sg": {
                        "nom": "vocandum",
                        "gen": "vocandi",
                        "dat": "vocando",
                        "acc": "vocandum",
                        "abl": "vocando"
                      },
                      "pl": {
                        "nom": "vocanda",
                        "gen": "vocandorum",
                        "dat": "vocandis",
                        "acc": "vocanda",
                        "abl": "vocandis"
                      }
                    }
                  }
                }
              }
            }
            """);

    @Test
    void readFromDocument() {
        WordbaseVerb verb = WordbaseVerb.readFromDocument(sampleDocument);
        assertEquals("vocare, voco, vocavi, vocatum", verb.getDefinition());
    }
}