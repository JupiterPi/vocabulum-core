package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.classes.DbConjugationClasses;
import jupiterpi.vocabulum.core.db.classes.DbDeclensionClasses;
import jupiterpi.vocabulum.core.db.portions.DbPortions;
import jupiterpi.vocabulum.core.i18n.DbI18ns;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.bson.Document;

import java.util.Arrays;

public class MockDatabase extends Database {
    public static void inject() {
        Database.inject(new MockDatabase());
    }

    /////


    @Override
    protected void connect(String mongoConnectUrl) {
        // do nothing
    }

    @Override
    public void prepareWordbase() {
        //TODO implement mock wordbase
    }

    @Override
    public Document getAdjectivesDocument() {
        return Document.parse("""
                {
                  "id": "adjectives",
                  "adverbs": {
                    "ao_suffix": {
                      "positive": "e",
                      "comparative": "ius",
                      "superlative": "issime"
                    },
                    "cons_suffix": {
                      "positive": "iter",
                      "comparative": "ius",
                      "superlative": "issime"
                    },
                    "cons_exceptions": [
                      {
                        "required_suffix": {
                          "form": "nom_sg_masc",
                          "suffix": "r"
                        },
                        "suffix": {
                          "positive": "iter",
                          "comparative": "ius",
                          "superlative": "rime",
                          "extra": "superlative_with_nom_sg_masc"
                        }
                      },
                      {
                        "required_suffix": {
                          "form": "root",
                          "suffix": "nt"
                        },
                        "suffix": {
                          "positive": "er",
                          "comparative": "ius",
                          "superlative": "errime"
                        }
                      }
                    ]
                  },
                  "comparative_forms": {
                    "comparative": {
                      "comparative_sign": "ior",
                      "comparative_sign_nom_sg_n": "ius"
                    },
                    "superlative": {
                      "superlative_sign": "issim",
                      "exception": {
                        "nom_sg_masc_suffix": "r",
                        "superlative_sign": "rim"
                      }
                    }
                  }
                }""");
    }

    // load classes


    @Override
    protected void loadI18ns() {
        this.i18ns = new DbI18ns();
        this.i18ns.loadI18ns(Arrays.asList(
                Document.parse("""
                        {
                          "language": "int",
                          "texts": null,
                          "str-texts": {
                            "casus": {
                              "nom": "Nom",
                              "acc": "Acc",
                              "gen": "Gen",
                              "dat": "Dat",
                              "abl": "Abl"
                            },
                            "number": {
                              "sg": "Sg",
                              "pl": "Pl"
                            },
                            "gender": {
                              "masc": "m",
                              "fem": "f",
                              "neut": "n"
                            },
                            "comparative_form": {
                              "positive": "Pos",
                              "comparative": "Comp",
                              "superlative": "Sup"
                            },
                            "adverb": "Adv",
                            "person": {
                              "first": "1",
                              "second": "2",
                              "third": "3"
                            },
                            "person_cosmetic": "Pers",
                            "mode": {
                              "indicative": "Ind",
                              "conjunctive": "Conj"
                            },
                            "tense": {
                              "present": "Pres",
                              "imperfect": "Imperf",
                              "perfect": "Perf",
                              "pluperfect": "Pluperf",
                              "future_i": "FutI",
                              "future_ii": "FutII"
                            },
                            "voice": {
                              "active": "Act",
                              "passive": "Pass"
                            }
                          }
                        }""")
        ));
    }

    @Override
    protected void loadDeclensionClasses() throws LoadingDataException {
        this.declensionClasses = new DbDeclensionClasses();
        this.declensionClasses.loadDeclensionSchemas(Arrays.asList(
                Document.parse("""
                        {
                            "name": "a",
                            "schema": "simple",
                            "sg": {
                                "nom": "a",\s
                                "gen": "ae",\s
                                "dat": "ae",\s
                                "acc": "am",\s
                                "abl": "a"
                            },\s
                            "pl": {
                                "nom": "ae",\s
                                "gen": "arum",\s
                                "dat": "is",\s
                                "acc": "as",\s
                                "abl": "is"
                            }
                        }"""),
                Document.parse("""
                        {
                            "name": "o",
                            "schema": "gender_dependant",
                            "masc": {
                                "sg": {
                                    "nom": "us",\s
                                    "gen": "i",\s
                                    "dat": "o",\s
                                    "acc": "um",\s
                                    "abl": "o"
                                },\s
                                "pl": {
                                    "nom": "i",\s
                                    "gen": "orum",\s
                                    "dat": "is",\s
                                    "acc": "os",\s
                                    "abl": "is"
                                }
                            },\s
                            "fem": {
                                "sg": {
                                    "nom": "-",\s
                                    "gen": "-",\s
                                    "dat": "-",\s
                                    "acc": "-",\s
                                    "abl": "-"
                                },\s
                                "pl": {
                                    "nom": "-",\s
                                    "gen": "-",\s
                                    "dat": "-",\s
                                    "acc": "-",\s
                                    "abl": "-"
                                }
                            },\s
                            "neut": {
                                "sg": {
                                    "nom": "um",\s
                                    "gen": "i",\s
                                    "dat": "o",\s
                                    "acc": "um",\s
                                    "abl": "o"
                                },\s
                                "pl": {
                                    "nom": "a",\s
                                    "gen": "orum",\s
                                    "dat": "is",\s
                                    "acc": "a",\s
                                    "abl": "is"
                                }
                            }
                        }"""),
                Document.parse("""
                        {
                            "name": "cons",
                            "schema": "simple",
                            "sg": {
                                "nom": "-",
                                "gen": "is",
                                "dat": "i",
                                "acc": "em",
                                "abl": "e"
                            },\s
                            "pl": {
                                "nom": "es",
                                "gen": "um",
                                "dat": "ibus",
                                "acc": "es",
                                "abl": "ibus"
                            }
                        }"""),
                Document.parse("""
                        {
                            "name": "e",
                            "schema": "simple",
                            "sg": {
                                "nom": "es",
                                "gen": "ei",
                                "dat": "ei",
                                "acc": "em",
                                "abl": "e"
                            },\s
                            "pl": {
                                "nom": "es",
                                "gen": "erum",
                                "dat": "ebus",
                                "acc": "es",
                                "abl": "ebus"
                            }
                        }"""),
                Document.parse("""
                        {
                            "name": "u",
                            "schema": "simple",
                            "sg": {
                                "nom": "us",
                                "gen": "us",
                                "dat": "ui",
                                "acc": "um",
                                "abl": "u"
                            },\s
                            "pl": {
                                "nom": "us",
                                "gen": "uum",
                                "dat": "ibus",
                                "acc": "us",
                                "abl": "ibus"
                            }
                        }"""),
                Document.parse("""
                        {
                          "name": "cons_adjectives",
                          "parent": "cons",
                          "schema": "gender_dependant",
                          "masc": {
                            "sg": {
                              "nom": ".",
                              "gen": ".",
                              "dat": ".",
                              "acc": ".",
                              "abl": "i"
                            },
                            "pl": {
                              "nom": ".",
                              "gen": "ium",
                              "dat": ".",
                              "acc": ".",
                              "abl": "."
                            }
                          },
                          "fem": {
                            "sg": {
                              "nom": ".",
                              "gen": ".",
                              "dat": ".",
                              "acc": ".",
                              "abl": "i"
                            },
                            "pl": {
                              "nom": ".",
                              "gen": "ium",
                              "dat": ".",
                              "acc": ".",
                              "abl": "."
                            }
                          },
                          "neut": {
                            "sg": {
                              "nom": ".",
                              "gen": ".",
                              "dat": ".",
                              "acc": ".",
                              "abl": "i"
                            },
                            "pl": {
                              "nom": "ia",
                              "gen": "ium",
                              "dat": ".",
                              "acc": "ia",
                              "abl": "."
                            }
                          }
                        }""")
        ));
    }

    @Override
    protected void loadConjugationClasses() throws LoadingDataException {
        this.conjugationClasses = new DbConjugationClasses();
        this.conjugationClasses.loadConjugationSchemas(Arrays.asList(
                Document.parse("""
                        {
                          "name": "a",
                          "active": {
                            "present": {
                              "root": "present",\s
                              "indicative": {
                                "sg": {
                                  "first": "o",\s
                                  "second": "as",
                                  "third": "at"
                                },\s
                                "pl": {
                                  "first": "amus",\s
                                  "second": "atis",\s
                                  "third": "ant"
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "em",
                                  "second": "es",
                                  "third": "et"
                                },
                                "pl": {
                                  "first": "emus",
                                  "second": "etis",
                                  "third": "ent"
                                }
                              }
                            },
                            "imperfect": {
                              "root": "present",
                              "indicative": {
                                "sg": {
                                  "first": "bam",
                                  "second": "bas",
                                  "third": "bat"
                                },
                                "pl": {
                                  "first": "bamus",
                                  "second": "batis",
                                  "third": "bant"
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "rem",
                                  "second": "res",
                                  "third": "ret"
                                },
                                "pl": {
                                  "first": "remus",
                                  "second": "retis",
                                  "third": "rent"
                                }
                              }
                            },\s
                            "perfect": {
                              "root": "perfect",
                              "indicative": {
                                "sg": {
                                  "first": "i",
                                  "second": "isti",
                                  "third": "it"
                                },
                                "pl": {
                                  "first": "imus",
                                  "second": "istis",
                                  "third": "erunt"
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "erim",
                                  "second": "eris",
                                  "third": "erit"
                                },
                                "pl": {
                                  "first": "erimus",
                                  "second": "eritis",
                                  "third": "erint"
                                }
                              }
                            },\s
                            "pluperfect": {
                              "root": "perfect",
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
                                  "first": "issem",
                                  "second": "isses",
                                  "third": "isset"
                                },
                                "pl": {
                                  "first": "issemus",
                                  "second": "issetis",
                                  "third": "issent"
                                }
                              }
                            },\s
                            "future_i": {
                              "root": "present",
                              "indicative": {
                                "sg": {
                                  "first": "bo",
                                  "second": "bis",
                                  "third": "bit"
                                },
                                "pl": {
                                  "first": "bimus",
                                  "second": "bitis",
                                  "third": "bunt"
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
                            },\s
                            "future_ii": {
                              "root": "perfect",
                              "indicative": {
                                "sg": {
                                  "first": "ero",
                                  "second": "eris",
                                  "third": "erit"
                                },
                                "pl": {
                                  "first": "erimus",
                                  "second": "eritis",
                                  "third": "erint"
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
                          },\s
                          "passive": {
                            "present": {
                              "root": "present",
                              "indicative": {
                                "sg": {
                                  "first": "or",
                                  "second": "ris",
                                  "third": "tur"
                                },
                                "pl": {
                                  "first": "mur",
                                  "second": "mini",
                                  "third": "ntur"
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "er",
                                  "second": "eris",
                                  "third": "etur"
                                },
                                "pl": {
                                  "first": "emur",
                                  "second": "emini",
                                  "third": "entur"
                                }
                              }
                            },
                            "imperfect": {
                              "root": "present",
                              "indicative": {
                                "sg": {
                                  "first": "bar",
                                  "second": "baris",
                                  "third": "batur"
                                },
                                "pl": {
                                  "first": "bamur",
                                  "second": "bamini",
                                  "third": "bantur"
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "rer",
                                  "second": "reris",
                                  "third": "retur"
                                },
                                "pl": {
                                  "first": "remur",
                                  "second": "remini",
                                  "third": "rentur"
                                }
                              }
                            },
                            "perfect": {
                              "root": "perfect",
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
                            "pluperfect": {
                              "root": "perfect",
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
                            "future_i": {
                              "root": "present",
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
                              "root": "perfect",
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
                            }
                          }
                        }""")
        ));
    }

    @Override
    protected void loadPortions() throws ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException {
        this.portions = new DbPortions();
        this.portions.loadPortions(Arrays.asList(
                Document.parse("""
                        {
                          "name": "1",
                          "i18n": "de",
                          "vocabularies": [
                            [
                              "sol, solis m. - *die Sonne*",
                              "silentium, silentii n. - *die Ruhe*, *die Stille*, das Schweigen",
                              "villa, villae f. - *das (Land)Haus*, die Villa",
                              "canis, canis n. - *der Hund*",
                              "acer, acris, acre - *heftig*, hart, scharf"
                            ],
                            [
                              "brevis, brevis, breve - *kurz*",
                              "felix, Gen. felicis - *glücklich*",
                              "clemens, Gen. clementis - *sanft*, *zart*",
                              "celer, celeris, celere - *schnell*",
                              "pulcher, pulchra, pulchrum - *hübsch*, schön",
                              "vocare, voco, vocavi, vocatum - *rufen*, *nennen*"
                            ]
                          ]
                        }""")
        ));
    }

    @Override
    protected void loadWordbase() {
        super.loadWordbase();
        //TODO implement mock wordbase
    }
}