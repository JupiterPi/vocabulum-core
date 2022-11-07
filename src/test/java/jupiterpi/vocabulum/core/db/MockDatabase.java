package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.classes.ConjugationClasses;
import jupiterpi.vocabulum.core.db.classes.DeclensionClasses;
import jupiterpi.vocabulum.core.db.portions.Portions;
import jupiterpi.vocabulum.core.db.users.Users;
import jupiterpi.vocabulum.core.db.wordbase.Wordbase;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.i18n.I18ns;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.sessions.SessionConfiguration;
import jupiterpi.vocabulum.core.users.User;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.bson.Document;

import java.util.List;

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
        // do nothing
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

    @Override
    public Document getVerbsDocument() {
        return Document.parse("""
                {
                  "id": "verbs",
                  "noun_like_form_suffixes": {
                    "ppa": {
                      "nom_sg_sign": "ns",
                      "other_sign": "nt"
                    },
                    "pfa": {
                      "sign": "tur",
                      "alt_sign": "sur",
                      "alt_sign_ppp_root_ending": "s"
                    },
                    "gerundium": {
                      "sign": "nd"
                    },
                    "gerundivum": {
                      "sign": "nd"
                    }
                  }
                }
                """);
    }

    @Override
    public Document getTranslationsDocument() {
        return Document.parse("""
                {
                   "id": "translations",
                   "articles": [
                     "der", "die", "das",
                     "ein", "eine"
                   ],
                   "keywords": [
                     {
                       "primaryKeyword": "m.",
                       "secondaryKeywords": [
                         "mit"
                       ],
                       "optional": false
                     },
                     {
                       "primaryKeyword": "und",
                       "secondaryKeywords": [
                         "u.",
                         "&"
                       ],
                       "optional": false
                     },
                     {
                       "primaryKeyword": "jmdn.",
                       "secondaryKeywords": [
                         "jemanden",
                         "jemand"
                       ],
                       "optional": false
                     },
                     {
                       "primaryKeyword": "jmdm.",
                       "secondaryKeywords": [
                         "jemandem",
                         "jemand"
                       ],
                       "optional": false
                     },
                     {
                       "primaryKeyword": "Nom.",
                       "secondaryKeywords": [
                         "Nominativ"
                       ],
                       "optional": false
                     },
                     {
                       "primaryKeyword": "Gen.",
                       "secondaryKeywords": [
                         "Genitiv"
                       ],
                       "optional": false
                     },
                     {
                       "primaryKeyword": "Dat.",
                       "secondaryKeywords": [
                         "Dativ"
                       ],
                       "optional": false
                     },
                     {
                       "primaryKeyword": "Akk.",
                       "secondaryKeywords": [
                         "Akkusativ"
                       ],
                       "optional": false
                     },
                     {
                       "primaryKeyword": "Abl.",
                       "secondaryKeywords": [
                         "Ablativ"
                       ],
                       "optional": false
                     },
                     {
                       "primaryKeyword": "Sg.",
                       "secondaryKeywords": [
                         "Singular"
                       ],
                       "optional": false
                     },
                     {
                       "primaryKeyword": "Pl.",
                       "secondaryKeywords": [
                         "Plural"
                       ],
                       "optional": false
                     },
                     {
                       "primaryKeyword": "Subst",
                       "secondaryKeywords": [
                         "Substantiv"
                       ],
                       "optional": false
                     },
                     {
                       "primaryKeyword": "auch",
                       "secondaryKeywords": [],
                       "optional": true
                     },
                     {
                       "primaryKeyword": "doppeltem",
                       "secondaryKeywords": [],
                       "optional": false
                     }
                   ]
                 }
                """);
    }

    // load classes

    @Override
    protected void loadI18ns() {
        this.i18ns = new I18ns();
        this.i18ns.loadI18ns(List.of(
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
                            },
                                  "imperative": "Imp",
                            "infinitive": "Inf",
                            "infinitive_tense": {
                              "present": "Pres",
                              "perfect": "Perf",
                              "future": "Fut"
                            },
                            "noun_like_form": {
                              "ppp": "PPP",
                              "ppa": "PPA",
                                                 "pfa": "PFA",
                              "gerundium": "Gerund",
                              "gerundivum": "Gerundv"
                            }
                          }
                        }
                        """),
                Document.parse("""
                        {
                           "language": "de",
                           "texts": {
                             "terminal": {
                               "help-text": "Geben Sie \\"p\\" ein für Prompting, \\"t\\" für Translation Assistance. ",
                               "unknown-mode-err": "Unbekannter Modus: %s",
                               "prompt": {
                                 "title": "Prompting",
                                 "help-text": "Funktionsweise: Tippen Sie nach \\">\\" eine Vokabel (bspw. \\"amicus, amici m. - der Freund\\", \\"laetus, laeta, laetum - fröhlich\\" oder \\"felix, Gen. felicis - glücklich\\"). Tippen Sie anschließend nach dem eingerückten \\">\\" die Form, die Sie generieren wollen (bspw. \\"Nom. Sg.\\" oder \\"Gen. Pl. f.\\"). Um zu einem beliebigen Zeitpunkt zurückzugehen, drücken Sie Enter, ohne etwas einzugeben. "
                               },
                               "translation_assistance": {
                                 "title": "Translaton Assistance",
                                 "help-text": "Funktionsweise: Geben Sie nach \\">\\" einen Satz ein (bspw. \\"Asinus stat et exspectat.\\") und drücken Sie Enter. Die Translation Assistance wird Ihnen alle verfügbaren Informationen ausgeben, die zum Übersetzen notwendig sind. "
                               },
                               "noun": "Sustantiv",
                               "adjective": "Adjektiv",
                               "verb": "Verb",
                               "error": "FEHLER",
                               "done": "Fertig. "
                             }
                           },
                           "str-texts": {
                             "casus": {
                               "nom": "Nom",
                               "acc": "Akk",
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
                               "comparative": "Komp",
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
                               "conjunctive": "Konj"
                             },
                             "tense": {
                               "present": "Präs",
                               "imperfect": "Imperf",
                               "perfect": "Perf",
                               "pluperfect": "Plusq",
                               "future_i": "FutI",
                               "future_ii": "FutII"
                             },
                             "voice": {
                               "active": "Akt",
                               "passive": "Pass"
                             },
                                   "imperative": "Imp",
                             "infinitive": "Inf",
                             "infinitive_tense": {
                               "present": "Präs",
                               "perfect": "Perf",
                               "future": "Fut"
                             },
                             "noun_like_form": {
                               "ppp": "PPP",
                               "ppa": "PPA",
                                                  "pfa": "PFA",
                               "gerundium": "Gerund",
                               "gerundivum": "Gerundv"
                             }
                           }
                         }
                        """),
                Document.parse("""
                        {
                           "language": "en",
                           "texts": {
                             "terminal": {
                               "help-text": "Enter \\"p\\" for Prompting, \\"t\\" for Translation Assistance. ",
                               "unknown-mode-err": "Unknown mode: %s",
                               "prompt": {
                                 "title": "Prompting",
                                 "help-text": "Usage: Type a vocabulary after \\">\\" (e. g. \\"amicus, amici m. - friend\\", \\"laetus, laeta, laetum - happy\\" or \\"felix, Gen. felicis - lucky\\"). Then after the indented \\">\\", type the form you want to generate (e. g. \\"Nom. Sg.\\" or \\"Gen. Pl. Fem.\\"). To go back at any time, press Enter without typing something on a prompt. "
                               },
                               "translation_assistance": {
                                 "title": "Translaton Assistance",
                                 "help-text": "Usage: Type a sentence after \\">\\" (e. g. \\"Asinus stat es exspectat.\\") and press Enter. Translation Assistance will print you all available information required for translating it."
                               },
                               "noun": "Noun",
                               "adjective": "Adjective",
                               "verb": "Verb",
                               "error": "ERROR",
                               "done": "Done. "
                             }
                           },
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
                               "masc": "Masc",
                               "fem": "Fem",
                               "neut": "Neut"
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
                             },
                                   "imperative": "Imp",
                             "infinitive": "Inf",
                             "infinitive_tense": {
                               "present": "Pres",
                               "perfect": "Perf",
                               "future": "Fut"
                             },
                             "noun_like_form": {
                               "ppp": "PPP",
                               "ppa": "PPA",
                                                  "pfa": "PFA",
                               "gerundium": "Gerund",
                               "gerundivum": "Gerundv"
                             }
                           }
                         }
                        """)
        ));
    }
    public void reloadI18ns() {
        loadI18ns();
    }

    public void injectI18ns(I18ns i18ns) {
        this.i18ns = i18ns;
    }

    @Override
    protected void loadDeclensionClasses() throws LoadingDataException {
        this.declensionClasses = new DeclensionClasses();
        this.declensionClasses.loadDeclensionSchemas(List.of(
                Document.parse("""
                        {
                            "name": "a",
                            "schema": "general",
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
                            "schema": "general",
                            "sg": {
                                "nom": "",
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
                            "schema": "general",
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
                            "schema": "general",
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
    public void reloadDeclensionClasses() throws LoadingDataException, DeclinedFormDoesNotExistException {
        loadDeclensionClasses();
    }

    public void injectDeclensionClasses(DeclensionClasses declensionClasses) {
        this.declensionClasses = declensionClasses;
    }

    @Override
    protected void loadConjugationClasses() throws LoadingDataException {
        this.conjugationClasses = new ConjugationClasses();
        this.conjugationClasses.loadConjugationSchemas(List.of(
                Document.parse("""
                        {
                          "name": "a",
                          "imperative": {
                              "sg": "Pr+a",
                              "pl": "Pr+ate"
                            },
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
                        """)
        ));
    }
    public void reloadConjugationClasses() throws LoadingDataException {
        loadConjugationClasses();
    }

    public void injectConjugationClasses(ConjugationClasses conjugationClasses) {
        this.conjugationClasses = conjugationClasses;
    }

    @Override
    protected void loadPortions() throws ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException {
        this.portions = new Portions();
        this.portions.loadPortions(List.of(
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
    public void reloadPortions() throws ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException {
        loadPortions();
    }

    public void injectPortions(Portions portions) {
        this.portions = portions;
    }

    @Override
    protected void loadWordbase() {
        this.wordbase = new MockWordbase();
    }
    public void reloadWordbase() {
        loadWordbase();
    }

    public void injectWordbase(Wordbase wordbase) {
        this.wordbase = wordbase;
    }

    @Override
    protected void loadUsers(Class<? extends User> userClass, Class<? extends SessionConfiguration> sessionConfigurationClass) {
        users = new MockUsers();
    }

    public void reloadUsers() {
        loadUsers(null, null);
    }

    public void injectUsers(Users users) {
        this.users = users;
    }
}