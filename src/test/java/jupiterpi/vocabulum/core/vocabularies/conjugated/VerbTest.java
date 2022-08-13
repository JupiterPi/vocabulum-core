package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.InfinitiveTense;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.Voice;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class VerbTest {
    I18n i18n = Database.get().getI18ns().internal();

    @Test
    void getDefinition() {
        //TODO write test when to-do is done
    }

    @Nested
    @DisplayName("generateWordbaseEntrySpecificPart()")
    class GenerateWordbaseEntrySpecificPart {

        @Test
        @DisplayName("all forms exist")
        void allFormsExist() {
            Verb verb = new Verb(new TranslationSequence(), "test") {
                @Override
                public String makeForm(VerbForm form) {
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
                        "imperative": {
                           "sg": "Imp. Sg.",
                           "pl": "Imp. Pl.",
                        },
                        "infinitive": {
                                "present": {
                                    "active": "Inf. Pres. Act.",
                                    "passive": "Inf. Pres. Pass."
                                },
                                "perfect": {
                                    "active": "Inf. Perf. Act.",
                                    "passive": "Inf. Perf. Pass."
                                },
                                "future": {
                                    "active": "Inf. Fut. Act.",
                                    "passive": "Inf. Fut. Pass."
                                }
                            },
                        "basic": {
                          "active": {
                            "present": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg.",
                                  "second": "2. Pers. Sg.",
                                  "third": "3. Pers. Sg."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl.",
                                  "second": "2. Pers. Pl.",
                                  "third": "3. Pers. Pl."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj.",
                                  "second": "2. Pers. Sg. Conj.",
                                  "third": "3. Pers. Sg. Conj."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj.",
                                  "second": "2. Pers. Pl. Conj.",
                                  "third": "3. Pers. Pl. Conj."
                                }
                              }
                            },
                            "imperfect": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Imperf.",
                                  "second": "2. Pers. Sg. Imperf.",
                                  "third": "3. Pers. Sg. Imperf."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Imperf.",
                                  "second": "2. Pers. Pl. Imperf.",
                                  "third": "3. Pers. Pl. Imperf."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Imperf.",
                                  "second": "2. Pers. Sg. Conj. Imperf.",
                                  "third": "3. Pers. Sg. Conj. Imperf."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Imperf.",
                                  "second": "2. Pers. Pl. Conj. Imperf.",
                                  "third": "3. Pers. Pl. Conj. Imperf."
                                }
                              }
                            },
                            "perfect": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Perf.",
                                  "second": "2. Pers. Sg. Perf.",
                                  "third": "3. Pers. Sg. Perf."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Perf.",
                                  "second": "2. Pers. Pl. Perf.",
                                  "third": "3. Pers. Pl. Perf."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Perf.",
                                  "second": "2. Pers. Sg. Conj. Perf.",
                                  "third": "3. Pers. Sg. Conj. Perf."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Perf.",
                                  "second": "2. Pers. Pl. Conj. Perf.",
                                  "third": "3. Pers. Pl. Conj. Perf."
                                }
                              }
                            },
                            "pluperfect": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Pluperf.",
                                  "second": "2. Pers. Sg. Pluperf.",
                                  "third": "3. Pers. Sg. Pluperf."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Pluperf.",
                                  "second": "2. Pers. Pl. Pluperf.",
                                  "third": "3. Pers. Pl. Pluperf."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Pluperf.",
                                  "second": "2. Pers. Sg. Conj. Pluperf.",
                                  "third": "3. Pers. Sg. Conj. Pluperf."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Pluperf.",
                                  "second": "2. Pers. Pl. Conj. Pluperf.",
                                  "third": "3. Pers. Pl. Conj. Pluperf."
                                }
                              }
                            },
                            "future_i": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. FutI.",
                                  "second": "2. Pers. Sg. FutI.",
                                  "third": "3. Pers. Sg. FutI."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. FutI.",
                                  "second": "2. Pers. Pl. FutI.",
                                  "third": "3. Pers. Pl. FutI."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. FutI.",
                                  "second": "2. Pers. Sg. Conj. FutI.",
                                  "third": "3. Pers. Sg. Conj. FutI."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. FutI.",
                                  "second": "2. Pers. Pl. Conj. FutI.",
                                  "third": "3. Pers. Pl. Conj. FutI."
                                }
                              }
                            },
                            "future_ii": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. FutII.",
                                  "second": "2. Pers. Sg. FutII.",
                                  "third": "3. Pers. Sg. FutII."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. FutII.",
                                  "second": "2. Pers. Pl. FutII.",
                                  "third": "3. Pers. Pl. FutII."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. FutII.",
                                  "second": "2. Pers. Sg. Conj. FutII.",
                                  "third": "3. Pers. Sg. Conj. FutII."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. FutII.",
                                  "second": "2. Pers. Pl. Conj. FutII.",
                                  "third": "3. Pers. Pl. Conj. FutII."
                                }
                              }
                            }
                          },
                          "passive": {
                            "present": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Pass.",
                                  "second": "2. Pers. Sg. Pass.",
                                  "third": "3. Pers. Sg. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Pass.",
                                  "second": "2. Pers. Pl. Pass.",
                                  "third": "3. Pers. Pl. Pass."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Pass.",
                                  "second": "2. Pers. Sg. Conj. Pass.",
                                  "third": "3. Pers. Sg. Conj. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Pass.",
                                  "second": "2. Pers. Pl. Conj. Pass.",
                                  "third": "3. Pers. Pl. Conj. Pass."
                                }
                              }
                            },
                            "imperfect": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Imperf. Pass.",
                                  "second": "2. Pers. Sg. Imperf. Pass.",
                                  "third": "3. Pers. Sg. Imperf. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Imperf. Pass.",
                                  "second": "2. Pers. Pl. Imperf. Pass.",
                                  "third": "3. Pers. Pl. Imperf. Pass."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Imperf. Pass.",
                                  "second": "2. Pers. Sg. Conj. Imperf. Pass.",
                                  "third": "3. Pers. Sg. Conj. Imperf. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Imperf. Pass.",
                                  "second": "2. Pers. Pl. Conj. Imperf. Pass.",
                                  "third": "3. Pers. Pl. Conj. Imperf. Pass."
                                }
                              }
                            },
                            "perfect": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Perf. Pass.",
                                  "second": "2. Pers. Sg. Perf. Pass.",
                                  "third": "3. Pers. Sg. Perf. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Perf. Pass.",
                                  "second": "2. Pers. Pl. Perf. Pass.",
                                  "third": "3. Pers. Pl. Perf. Pass."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Perf. Pass.",
                                  "second": "2. Pers. Sg. Conj. Perf. Pass.",
                                  "third": "3. Pers. Sg. Conj. Perf. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Perf. Pass.",
                                  "second": "2. Pers. Pl. Conj. Perf. Pass.",
                                  "third": "3. Pers. Pl. Conj. Perf. Pass."
                                }
                              }
                            },
                            "pluperfect": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Pluperf. Pass.",
                                  "second": "2. Pers. Sg. Pluperf. Pass.",
                                  "third": "3. Pers. Sg. Pluperf. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Pluperf. Pass.",
                                  "second": "2. Pers. Pl. Pluperf. Pass.",
                                  "third": "3. Pers. Pl. Pluperf. Pass."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Pluperf. Pass.",
                                  "second": "2. Pers. Sg. Conj. Pluperf. Pass.",
                                  "third": "3. Pers. Sg. Conj. Pluperf. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Pluperf. Pass.",
                                  "second": "2. Pers. Pl. Conj. Pluperf. Pass.",
                                  "third": "3. Pers. Pl. Conj. Pluperf. Pass."
                                }
                              }
                            },
                            "future_i": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. FutI. Pass.",
                                  "second": "2. Pers. Sg. FutI. Pass.",
                                  "third": "3. Pers. Sg. FutI. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. FutI. Pass.",
                                  "second": "2. Pers. Pl. FutI. Pass.",
                                  "third": "3. Pers. Pl. FutI. Pass."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. FutI. Pass.",
                                  "second": "2. Pers. Sg. Conj. FutI. Pass.",
                                  "third": "3. Pers. Sg. Conj. FutI. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. FutI. Pass.",
                                  "second": "2. Pers. Pl. Conj. FutI. Pass.",
                                  "third": "3. Pers. Pl. Conj. FutI. Pass."
                                }
                              }
                            },
                            "future_ii": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. FutII. Pass.",
                                  "second": "2. Pers. Sg. FutII. Pass.",
                                  "third": "3. Pers. Sg. FutII. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. FutII. Pass.",
                                  "second": "2. Pers. Pl. FutII. Pass.",
                                  "third": "3. Pers. Pl. FutII. Pass."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. FutII. Pass.",
                                  "second": "2. Pers. Sg. Conj. FutII. Pass.",
                                  "third": "3. Pers. Sg. Conj. FutII. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. FutII. Pass.",
                                  "second": "2. Pers. Pl. Conj. FutII. Pass.",
                                  "third": "3. Pers. Pl. Conj. FutII. Pass."
                                }
                              }
                            }
                          }
                        },
                        "noun_like": {
                          "ppp": {
                            "masc": {
                              "sg": {
                                "nom": "PPP. Nom. Sg. m.",
                                "gen": "PPP. Gen. Sg. m.",
                                "dat": "PPP. Dat. Sg. m.",
                                "acc": "PPP. Acc. Sg. m.",
                                "abl": "PPP. Abl. Sg. m."
                              },
                              "pl": {
                                "nom": "PPP. Nom. Pl. m.",
                                "gen": "PPP. Gen. Pl. m.",
                                "dat": "PPP. Dat. Pl. m.",
                                "acc": "PPP. Acc. Pl. m.",
                                "abl": "PPP. Abl. Pl. m."
                              }
                            },
                            "fem": {
                              "sg": {
                                "nom": "PPP. Nom. Sg. f.",
                                "gen": "PPP. Gen. Sg. f.",
                                "dat": "PPP. Dat. Sg. f.",
                                "acc": "PPP. Acc. Sg. f.",
                                "abl": "PPP. Abl. Sg. f."
                              },
                              "pl": {
                                "nom": "PPP. Nom. Pl. f.",
                                "gen": "PPP. Gen. Pl. f.",
                                "dat": "PPP. Dat. Pl. f.",
                                "acc": "PPP. Acc. Pl. f.",
                                "abl": "PPP. Abl. Pl. f."
                              }
                            },
                            "neut": {
                              "sg": {
                                "nom": "PPP. Nom. Sg. n.",
                                "gen": "PPP. Gen. Sg. n.",
                                "dat": "PPP. Dat. Sg. n.",
                                "acc": "PPP. Acc. Sg. n.",
                                "abl": "PPP. Abl. Sg. n."
                              },
                              "pl": {
                                "nom": "PPP. Nom. Pl. n.",
                                "gen": "PPP. Gen. Pl. n.",
                                "dat": "PPP. Dat. Pl. n.",
                                "acc": "PPP. Acc. Pl. n.",
                                "abl": "PPP. Abl. Pl. n."
                              }
                            }
                          },
                          "ppa": {
                            "masc": {
                              "sg": {
                                "nom": "PPA. Nom. Sg. m.",
                                "gen": "PPA. Gen. Sg. m.",
                                "dat": "PPA. Dat. Sg. m.",
                                "acc": "PPA. Acc. Sg. m.",
                                "abl": "PPA. Abl. Sg. m."
                              },
                              "pl": {
                                "nom": "PPA. Nom. Pl. m.",
                                "gen": "PPA. Gen. Pl. m.",
                                "dat": "PPA. Dat. Pl. m.",
                                "acc": "PPA. Acc. Pl. m.",
                                "abl": "PPA. Abl. Pl. m."
                              }
                            },
                            "fem": {
                              "sg": {
                                "nom": "PPA. Nom. Sg. f.",
                                "gen": "PPA. Gen. Sg. f.",
                                "dat": "PPA. Dat. Sg. f.",
                                "acc": "PPA. Acc. Sg. f.",
                                "abl": "PPA. Abl. Sg. f."
                              },
                              "pl": {
                                "nom": "PPA. Nom. Pl. f.",
                                "gen": "PPA. Gen. Pl. f.",
                                "dat": "PPA. Dat. Pl. f.",
                                "acc": "PPA. Acc. Pl. f.",
                                "abl": "PPA. Abl. Pl. f."
                              }
                            },
                            "neut": {
                              "sg": {
                                "nom": "PPA. Nom. Sg. n.",
                                "gen": "PPA. Gen. Sg. n.",
                                "dat": "PPA. Dat. Sg. n.",
                                "acc": "PPA. Acc. Sg. n.",
                                "abl": "PPA. Abl. Sg. n."
                              },
                              "pl": {
                                "nom": "PPA. Nom. Pl. n.",
                                "gen": "PPA. Gen. Pl. n.",
                                "dat": "PPA. Dat. Pl. n.",
                                "acc": "PPA. Acc. Pl. n.",
                                "abl": "PPA. Abl. Pl. n."
                              }
                            }
                          },
                          "pfa": {
                            "masc": {
                              "sg": {
                                "nom": "PFA. Nom. Sg. m.",
                                "gen": "PFA. Gen. Sg. m.",
                                "dat": "PFA. Dat. Sg. m.",
                                "acc": "PFA. Acc. Sg. m.",
                                "abl": "PFA. Abl. Sg. m."
                              },
                              "pl": {
                                "nom": "PFA. Nom. Pl. m.",
                                "gen": "PFA. Gen. Pl. m.",
                                "dat": "PFA. Dat. Pl. m.",
                                "acc": "PFA. Acc. Pl. m.",
                                "abl": "PFA. Abl. Pl. m."
                              }
                            },
                            "fem": {
                              "sg": {
                                "nom": "PFA. Nom. Sg. f.",
                                "gen": "PFA. Gen. Sg. f.",
                                "dat": "PFA. Dat. Sg. f.",
                                "acc": "PFA. Acc. Sg. f.",
                                "abl": "PFA. Abl. Sg. f."
                              },
                              "pl": {
                                "nom": "PFA. Nom. Pl. f.",
                                "gen": "PFA. Gen. Pl. f.",
                                "dat": "PFA. Dat. Pl. f.",
                                "acc": "PFA. Acc. Pl. f.",
                                "abl": "PFA. Abl. Pl. f."
                              }
                            },
                            "neut": {
                              "sg": {
                                "nom": "PFA. Nom. Sg. n.",
                                "gen": "PFA. Gen. Sg. n.",
                                "dat": "PFA. Dat. Sg. n.",
                                "acc": "PFA. Acc. Sg. n.",
                                "abl": "PFA. Abl. Sg. n."
                              },
                              "pl": {
                                "nom": "PFA. Nom. Pl. n.",
                                "gen": "PFA. Gen. Pl. n.",
                                "dat": "PFA. Dat. Pl. n.",
                                "acc": "PFA. Acc. Pl. n.",
                                "abl": "PFA. Abl. Pl. n."
                              }
                            }
                          },
                          "gerundium": {
                            "masc": {
                              "sg": {
                                "nom": "Gerund. Nom. Sg. m.",
                                "gen": "Gerund. Gen. Sg. m.",
                                "dat": "Gerund. Dat. Sg. m.",
                                "acc": "Gerund. Acc. Sg. m.",
                                "abl": "Gerund. Abl. Sg. m."
                              },
                              "pl": {
                                "nom": "Gerund. Nom. Pl. m.",
                                "gen": "Gerund. Gen. Pl. m.",
                                "dat": "Gerund. Dat. Pl. m.",
                                "acc": "Gerund. Acc. Pl. m.",
                                "abl": "Gerund. Abl. Pl. m."
                              }
                            },
                            "fem": {
                              "sg": {
                                "nom": "Gerund. Nom. Sg. f.",
                                "gen": "Gerund. Gen. Sg. f.",
                                "dat": "Gerund. Dat. Sg. f.",
                                "acc": "Gerund. Acc. Sg. f.",
                                "abl": "Gerund. Abl. Sg. f."
                              },
                              "pl": {
                                "nom": "Gerund. Nom. Pl. f.",
                                "gen": "Gerund. Gen. Pl. f.",
                                "dat": "Gerund. Dat. Pl. f.",
                                "acc": "Gerund. Acc. Pl. f.",
                                "abl": "Gerund. Abl. Pl. f."
                              }
                            },
                            "neut": {
                              "sg": {
                                "nom": "Gerund. Nom. Sg. n.",
                                "gen": "Gerund. Gen. Sg. n.",
                                "dat": "Gerund. Dat. Sg. n.",
                                "acc": "Gerund. Acc. Sg. n.",
                                "abl": "Gerund. Abl. Sg. n."
                              },
                              "pl": {
                                "nom": "Gerund. Nom. Pl. n.",
                                "gen": "Gerund. Gen. Pl. n.",
                                "dat": "Gerund. Dat. Pl. n.",
                                "acc": "Gerund. Acc. Pl. n.",
                                "abl": "Gerund. Abl. Pl. n."
                              }
                            }
                          },
                          "gerundivum": {
                            "masc": {
                              "sg": {
                                "nom": "Gerundv. Nom. Sg. m.",
                                "gen": "Gerundv. Gen. Sg. m.",
                                "dat": "Gerundv. Dat. Sg. m.",
                                "acc": "Gerundv. Acc. Sg. m.",
                                "abl": "Gerundv. Abl. Sg. m."
                              },
                              "pl": {
                                "nom": "Gerundv. Nom. Pl. m.",
                                "gen": "Gerundv. Gen. Pl. m.",
                                "dat": "Gerundv. Dat. Pl. m.",
                                "acc": "Gerundv. Acc. Pl. m.",
                                "abl": "Gerundv. Abl. Pl. m."
                              }
                            },
                            "fem": {
                              "sg": {
                                "nom": "Gerundv. Nom. Sg. f.",
                                "gen": "Gerundv. Gen. Sg. f.",
                                "dat": "Gerundv. Dat. Sg. f.",
                                "acc": "Gerundv. Acc. Sg. f.",
                                "abl": "Gerundv. Abl. Sg. f."
                              },
                              "pl": {
                                "nom": "Gerundv. Nom. Pl. f.",
                                "gen": "Gerundv. Gen. Pl. f.",
                                "dat": "Gerundv. Dat. Pl. f.",
                                "acc": "Gerundv. Acc. Pl. f.",
                                "abl": "Gerundv. Abl. Pl. f."
                              }
                            },
                            "neut": {
                              "sg": {
                                "nom": "Gerundv. Nom. Sg. n.",
                                "gen": "Gerundv. Gen. Sg. n.",
                                "dat": "Gerundv. Dat. Sg. n.",
                                "acc": "Gerundv. Acc. Sg. n.",
                                "abl": "Gerundv. Abl. Sg. n."
                              },
                              "pl": {
                                "nom": "Gerundv. Nom. Pl. n.",
                                "gen": "Gerundv. Gen. Pl. n.",
                                "dat": "Gerundv. Dat. Pl. n.",
                                "acc": "Gerundv. Acc. Pl. n.",
                                "abl": "Gerundv. Abl. Pl. n."
                              }
                            }
                          }
                        }
                      }
                    }
                    """);
            assertEquals(e, verb.generateWordbaseEntrySpecificPart());
        }

        @Test
        @DisplayName("some forms don't exist")
        void someFormsDontExist() {
            Verb verb = new Verb(new TranslationSequence(), "test") {
                @Override
                public String makeForm(VerbForm form) {
                    if (form.isInfinitive()) return "-";
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
                      "imperative": {
                           "sg": "Imp. Sg.",
                           "pl": "Imp. Pl.",
                        },
                        "infinitive": {
                                "present": {
                                    "active": "-",
                                    "passive": "-"
                                },
                                "perfect": {
                                    "active": "-",
                                    "passive": "-"
                                },
                                "future": {
                                    "active": "-",
                                    "passive": "-"
                                }
                            },
                        "basic": {
                          "active": {
                            "present": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg.",
                                  "second": "2. Pers. Sg.",
                                  "third": "3. Pers. Sg."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl.",
                                  "second": "2. Pers. Pl.",
                                  "third": "3. Pers. Pl."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj.",
                                  "second": "2. Pers. Sg. Conj.",
                                  "third": "3. Pers. Sg. Conj."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj.",
                                  "second": "2. Pers. Pl. Conj.",
                                  "third": "3. Pers. Pl. Conj."
                                }
                              }
                            },
                            "imperfect": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Imperf.",
                                  "second": "2. Pers. Sg. Imperf.",
                                  "third": "3. Pers. Sg. Imperf."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Imperf.",
                                  "second": "2. Pers. Pl. Imperf.",
                                  "third": "3. Pers. Pl. Imperf."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Imperf.",
                                  "second": "2. Pers. Sg. Conj. Imperf.",
                                  "third": "3. Pers. Sg. Conj. Imperf."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Imperf.",
                                  "second": "2. Pers. Pl. Conj. Imperf.",
                                  "third": "3. Pers. Pl. Conj. Imperf."
                                }
                              }
                            },
                            "perfect": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Perf.",
                                  "second": "2. Pers. Sg. Perf.",
                                  "third": "3. Pers. Sg. Perf."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Perf.",
                                  "second": "2. Pers. Pl. Perf.",
                                  "third": "3. Pers. Pl. Perf."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Perf.",
                                  "second": "2. Pers. Sg. Conj. Perf.",
                                  "third": "3. Pers. Sg. Conj. Perf."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Perf.",
                                  "second": "2. Pers. Pl. Conj. Perf.",
                                  "third": "3. Pers. Pl. Conj. Perf."
                                }
                              }
                            },
                            "pluperfect": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Pluperf.",
                                  "second": "2. Pers. Sg. Pluperf.",
                                  "third": "3. Pers. Sg. Pluperf."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Pluperf.",
                                  "second": "2. Pers. Pl. Pluperf.",
                                  "third": "3. Pers. Pl. Pluperf."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Pluperf.",
                                  "second": "2. Pers. Sg. Conj. Pluperf.",
                                  "third": "3. Pers. Sg. Conj. Pluperf."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Pluperf.",
                                  "second": "2. Pers. Pl. Conj. Pluperf.",
                                  "third": "3. Pers. Pl. Conj. Pluperf."
                                }
                              }
                            },
                            "future_i": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. FutI.",
                                  "second": "2. Pers. Sg. FutI.",
                                  "third": "3. Pers. Sg. FutI."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. FutI.",
                                  "second": "2. Pers. Pl. FutI.",
                                  "third": "3. Pers. Pl. FutI."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. FutI.",
                                  "second": "2. Pers. Sg. Conj. FutI.",
                                  "third": "3. Pers. Sg. Conj. FutI."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. FutI.",
                                  "second": "2. Pers. Pl. Conj. FutI.",
                                  "third": "3. Pers. Pl. Conj. FutI."
                                }
                              }
                            },
                            "future_ii": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. FutII.",
                                  "second": "2. Pers. Sg. FutII.",
                                  "third": "3. Pers. Sg. FutII."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. FutII.",
                                  "second": "2. Pers. Pl. FutII.",
                                  "third": "3. Pers. Pl. FutII."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. FutII.",
                                  "second": "2. Pers. Sg. Conj. FutII.",
                                  "third": "3. Pers. Sg. Conj. FutII."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. FutII.",
                                  "second": "2. Pers. Pl. Conj. FutII.",
                                  "third": "3. Pers. Pl. Conj. FutII."
                                }
                              }
                            }
                          },
                          "passive": {
                            "present": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Pass.",
                                  "second": "2. Pers. Sg. Pass.",
                                  "third": "3. Pers. Sg. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Pass.",
                                  "second": "2. Pers. Pl. Pass.",
                                  "third": "3. Pers. Pl. Pass."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Pass.",
                                  "second": "2. Pers. Sg. Conj. Pass.",
                                  "third": "3. Pers. Sg. Conj. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Pass.",
                                  "second": "2. Pers. Pl. Conj. Pass.",
                                  "third": "3. Pers. Pl. Conj. Pass."
                                }
                              }
                            },
                            "imperfect": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Imperf. Pass.",
                                  "second": "2. Pers. Sg. Imperf. Pass.",
                                  "third": "3. Pers. Sg. Imperf. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Imperf. Pass.",
                                  "second": "2. Pers. Pl. Imperf. Pass.",
                                  "third": "3. Pers. Pl. Imperf. Pass."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Imperf. Pass.",
                                  "second": "2. Pers. Sg. Conj. Imperf. Pass.",
                                  "third": "3. Pers. Sg. Conj. Imperf. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Imperf. Pass.",
                                  "second": "2. Pers. Pl. Conj. Imperf. Pass.",
                                  "third": "3. Pers. Pl. Conj. Imperf. Pass."
                                }
                              }
                            },
                            "perfect": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Perf. Pass.",
                                  "second": "2. Pers. Sg. Perf. Pass.",
                                  "third": "3. Pers. Sg. Perf. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Perf. Pass.",
                                  "second": "2. Pers. Pl. Perf. Pass.",
                                  "third": "3. Pers. Pl. Perf. Pass."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Perf. Pass.",
                                  "second": "2. Pers. Sg. Conj. Perf. Pass.",
                                  "third": "3. Pers. Sg. Conj. Perf. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Perf. Pass.",
                                  "second": "2. Pers. Pl. Conj. Perf. Pass.",
                                  "third": "3. Pers. Pl. Conj. Perf. Pass."
                                }
                              }
                            },
                            "pluperfect": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. Pluperf. Pass.",
                                  "second": "2. Pers. Sg. Pluperf. Pass.",
                                  "third": "3. Pers. Sg. Pluperf. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Pluperf. Pass.",
                                  "second": "2. Pers. Pl. Pluperf. Pass.",
                                  "third": "3. Pers. Pl. Pluperf. Pass."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. Pluperf. Pass.",
                                  "second": "2. Pers. Sg. Conj. Pluperf. Pass.",
                                  "third": "3. Pers. Sg. Conj. Pluperf. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. Pluperf. Pass.",
                                  "second": "2. Pers. Pl. Conj. Pluperf. Pass.",
                                  "third": "3. Pers. Pl. Conj. Pluperf. Pass."
                                }
                              }
                            },
                            "future_i": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. FutI. Pass.",
                                  "second": "2. Pers. Sg. FutI. Pass.",
                                  "third": "3. Pers. Sg. FutI. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. FutI. Pass.",
                                  "second": "2. Pers. Pl. FutI. Pass.",
                                  "third": "3. Pers. Pl. FutI. Pass."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. FutI. Pass.",
                                  "second": "2. Pers. Sg. Conj. FutI. Pass.",
                                  "third": "3. Pers. Sg. Conj. FutI. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. FutI. Pass.",
                                  "second": "2. Pers. Pl. Conj. FutI. Pass.",
                                  "third": "3. Pers. Pl. Conj. FutI. Pass."
                                }
                              }
                            },
                            "future_ii": {
                              "indicative": {
                                "sg": {
                                  "first": "1. Pers. Sg. FutII. Pass.",
                                  "second": "2. Pers. Sg. FutII. Pass.",
                                  "third": "3. Pers. Sg. FutII. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. FutII. Pass.",
                                  "second": "2. Pers. Pl. FutII. Pass.",
                                  "third": "3. Pers. Pl. FutII. Pass."
                                }
                              },
                              "conjunctive": {
                                "sg": {
                                  "first": "1. Pers. Sg. Conj. FutII. Pass.",
                                  "second": "2. Pers. Sg. Conj. FutII. Pass.",
                                  "third": "3. Pers. Sg. Conj. FutII. Pass."
                                },
                                "pl": {
                                  "first": "1. Pers. Pl. Conj. FutII. Pass.",
                                  "second": "2. Pers. Pl. Conj. FutII. Pass.",
                                  "third": "3. Pers. Pl. Conj. FutII. Pass."
                                }
                              }
                            }
                          }
                        },
                        "noun_like": {
                          "ppp": {
                            "masc": {
                              "sg": {
                                "nom": "PPP. Nom. Sg. m.",
                                "gen": "PPP. Gen. Sg. m.",
                                "dat": "PPP. Dat. Sg. m.",
                                "acc": "PPP. Acc. Sg. m.",
                                "abl": "PPP. Abl. Sg. m."
                              },
                              "pl": {
                                "nom": "PPP. Nom. Pl. m.",
                                "gen": "PPP. Gen. Pl. m.",
                                "dat": "PPP. Dat. Pl. m.",
                                "acc": "PPP. Acc. Pl. m.",
                                "abl": "PPP. Abl. Pl. m."
                              }
                            },
                            "fem": {
                              "sg": {
                                "nom": "PPP. Nom. Sg. f.",
                                "gen": "PPP. Gen. Sg. f.",
                                "dat": "PPP. Dat. Sg. f.",
                                "acc": "PPP. Acc. Sg. f.",
                                "abl": "PPP. Abl. Sg. f."
                              },
                              "pl": {
                                "nom": "PPP. Nom. Pl. f.",
                                "gen": "PPP. Gen. Pl. f.",
                                "dat": "PPP. Dat. Pl. f.",
                                "acc": "PPP. Acc. Pl. f.",
                                "abl": "PPP. Abl. Pl. f."
                              }
                            },
                            "neut": {
                              "sg": {
                                "nom": "PPP. Nom. Sg. n.",
                                "gen": "PPP. Gen. Sg. n.",
                                "dat": "PPP. Dat. Sg. n.",
                                "acc": "PPP. Acc. Sg. n.",
                                "abl": "PPP. Abl. Sg. n."
                              },
                              "pl": {
                                "nom": "PPP. Nom. Pl. n.",
                                "gen": "PPP. Gen. Pl. n.",
                                "dat": "PPP. Dat. Pl. n.",
                                "acc": "PPP. Acc. Pl. n.",
                                "abl": "PPP. Abl. Pl. n."
                              }
                            }
                          },
                          "ppa": {
                            "masc": {
                              "sg": {
                                "nom": "PPA. Nom. Sg. m.",
                                "gen": "PPA. Gen. Sg. m.",
                                "dat": "PPA. Dat. Sg. m.",
                                "acc": "PPA. Acc. Sg. m.",
                                "abl": "PPA. Abl. Sg. m."
                              },
                              "pl": {
                                "nom": "PPA. Nom. Pl. m.",
                                "gen": "PPA. Gen. Pl. m.",
                                "dat": "PPA. Dat. Pl. m.",
                                "acc": "PPA. Acc. Pl. m.",
                                "abl": "PPA. Abl. Pl. m."
                              }
                            },
                            "fem": {
                              "sg": {
                                "nom": "PPA. Nom. Sg. f.",
                                "gen": "PPA. Gen. Sg. f.",
                                "dat": "PPA. Dat. Sg. f.",
                                "acc": "PPA. Acc. Sg. f.",
                                "abl": "PPA. Abl. Sg. f."
                              },
                              "pl": {
                                "nom": "PPA. Nom. Pl. f.",
                                "gen": "PPA. Gen. Pl. f.",
                                "dat": "PPA. Dat. Pl. f.",
                                "acc": "PPA. Acc. Pl. f.",
                                "abl": "PPA. Abl. Pl. f."
                              }
                            },
                            "neut": {
                              "sg": {
                                "nom": "PPA. Nom. Sg. n.",
                                "gen": "PPA. Gen. Sg. n.",
                                "dat": "PPA. Dat. Sg. n.",
                                "acc": "PPA. Acc. Sg. n.",
                                "abl": "PPA. Abl. Sg. n."
                              },
                              "pl": {
                                "nom": "PPA. Nom. Pl. n.",
                                "gen": "PPA. Gen. Pl. n.",
                                "dat": "PPA. Dat. Pl. n.",
                                "acc": "PPA. Acc. Pl. n.",
                                "abl": "PPA. Abl. Pl. n."
                              }
                            }
                          },
                          "pfa": {
                            "masc": {
                              "sg": {
                                "nom": "PFA. Nom. Sg. m.",
                                "gen": "PFA. Gen. Sg. m.",
                                "dat": "PFA. Dat. Sg. m.",
                                "acc": "PFA. Acc. Sg. m.",
                                "abl": "PFA. Abl. Sg. m."
                              },
                              "pl": {
                                "nom": "PFA. Nom. Pl. m.",
                                "gen": "PFA. Gen. Pl. m.",
                                "dat": "PFA. Dat. Pl. m.",
                                "acc": "PFA. Acc. Pl. m.",
                                "abl": "PFA. Abl. Pl. m."
                              }
                            },
                            "fem": {
                              "sg": {
                                "nom": "PFA. Nom. Sg. f.",
                                "gen": "PFA. Gen. Sg. f.",
                                "dat": "PFA. Dat. Sg. f.",
                                "acc": "PFA. Acc. Sg. f.",
                                "abl": "PFA. Abl. Sg. f."
                              },
                              "pl": {
                                "nom": "PFA. Nom. Pl. f.",
                                "gen": "PFA. Gen. Pl. f.",
                                "dat": "PFA. Dat. Pl. f.",
                                "acc": "PFA. Acc. Pl. f.",
                                "abl": "PFA. Abl. Pl. f."
                              }
                            },
                            "neut": {
                              "sg": {
                                "nom": "PFA. Nom. Sg. n.",
                                "gen": "PFA. Gen. Sg. n.",
                                "dat": "PFA. Dat. Sg. n.",
                                "acc": "PFA. Acc. Sg. n.",
                                "abl": "PFA. Abl. Sg. n."
                              },
                              "pl": {
                                "nom": "PFA. Nom. Pl. n.",
                                "gen": "PFA. Gen. Pl. n.",
                                "dat": "PFA. Dat. Pl. n.",
                                "acc": "PFA. Acc. Pl. n.",
                                "abl": "PFA. Abl. Pl. n."
                              }
                            }
                          },
                          "gerundium": {
                            "masc": {
                              "sg": {
                                "nom": "Gerund. Nom. Sg. m.",
                                "gen": "Gerund. Gen. Sg. m.",
                                "dat": "Gerund. Dat. Sg. m.",
                                "acc": "Gerund. Acc. Sg. m.",
                                "abl": "Gerund. Abl. Sg. m."
                              },
                              "pl": {
                                "nom": "Gerund. Nom. Pl. m.",
                                "gen": "Gerund. Gen. Pl. m.",
                                "dat": "Gerund. Dat. Pl. m.",
                                "acc": "Gerund. Acc. Pl. m.",
                                "abl": "Gerund. Abl. Pl. m."
                              }
                            },
                            "fem": {
                              "sg": {
                                "nom": "Gerund. Nom. Sg. f.",
                                "gen": "Gerund. Gen. Sg. f.",
                                "dat": "Gerund. Dat. Sg. f.",
                                "acc": "Gerund. Acc. Sg. f.",
                                "abl": "Gerund. Abl. Sg. f."
                              },
                              "pl": {
                                "nom": "Gerund. Nom. Pl. f.",
                                "gen": "Gerund. Gen. Pl. f.",
                                "dat": "Gerund. Dat. Pl. f.",
                                "acc": "Gerund. Acc. Pl. f.",
                                "abl": "Gerund. Abl. Pl. f."
                              }
                            },
                            "neut": {
                              "sg": {
                                "nom": "Gerund. Nom. Sg. n.",
                                "gen": "Gerund. Gen. Sg. n.",
                                "dat": "Gerund. Dat. Sg. n.",
                                "acc": "Gerund. Acc. Sg. n.",
                                "abl": "Gerund. Abl. Sg. n."
                              },
                              "pl": {
                                "nom": "Gerund. Nom. Pl. n.",
                                "gen": "Gerund. Gen. Pl. n.",
                                "dat": "Gerund. Dat. Pl. n.",
                                "acc": "Gerund. Acc. Pl. n.",
                                "abl": "Gerund. Abl. Pl. n."
                              }
                            }
                          },
                          "gerundivum": {
                            "masc": {
                              "sg": {
                                "nom": "Gerundv. Nom. Sg. m.",
                                "gen": "Gerundv. Gen. Sg. m.",
                                "dat": "Gerundv. Dat. Sg. m.",
                                "acc": "Gerundv. Acc. Sg. m.",
                                "abl": "Gerundv. Abl. Sg. m."
                              },
                              "pl": {
                                "nom": "Gerundv. Nom. Pl. m.",
                                "gen": "Gerundv. Gen. Pl. m.",
                                "dat": "Gerundv. Dat. Pl. m.",
                                "acc": "Gerundv. Acc. Pl. m.",
                                "abl": "Gerundv. Abl. Pl. m."
                              }
                            },
                            "fem": {
                              "sg": {
                                "nom": "Gerundv. Nom. Sg. f.",
                                "gen": "Gerundv. Gen. Sg. f.",
                                "dat": "Gerundv. Dat. Sg. f.",
                                "acc": "Gerundv. Acc. Sg. f.",
                                "abl": "Gerundv. Abl. Sg. f."
                              },
                              "pl": {
                                "nom": "Gerundv. Nom. Pl. f.",
                                "gen": "Gerundv. Gen. Pl. f.",
                                "dat": "Gerundv. Dat. Pl. f.",
                                "acc": "Gerundv. Acc. Pl. f.",
                                "abl": "Gerundv. Abl. Pl. f."
                              }
                            },
                            "neut": {
                              "sg": {
                                "nom": "Gerundv. Nom. Sg. n.",
                                "gen": "Gerundv. Gen. Sg. n.",
                                "dat": "Gerundv. Dat. Sg. n.",
                                "acc": "Gerundv. Acc. Sg. n.",
                                "abl": "Gerundv. Abl. Sg. n."
                              },
                              "pl": {
                                "nom": "Gerundv. Nom. Pl. n.",
                                "gen": "Gerundv. Gen. Pl. n.",
                                "dat": "Gerundv. Dat. Pl. n.",
                                "acc": "Gerundv. Acc. Pl. n.",
                                "abl": "Gerundv. Abl. Pl. n."
                              }
                            }
                          }
                        }
                      }
                    }
                    """);
            assertEquals(e, verb.generateWordbaseEntrySpecificPart());
        }

    }

    @Nested
    @DisplayName("identifyForm()")
    class IdentifyForm {

        @Test
        @DisplayName("one possibility")
        void onePossibility() {
            final VerbForm verbForm = new VerbForm(InfinitiveTense.PRESENT, Voice.ACTIVE);
            Verb verb = new Verb(new TranslationSequence(), "test") {
                @Override
                public String makeForm(VerbForm form) {
                    if (form.equals(verbForm)) return "theform";
                    else return "nottheform";
                }

                @Override
                public String getBaseForm() {
                    return "baseform";
                }
            };
            assertEquals(List.of(verbForm), verb.identifyForm("theform"));
        }

        @Test
        @DisplayName("multiple possibilities")
        void multiplePossibilities() {
            final VerbForm verbForm1 = new VerbForm(InfinitiveTense.PRESENT, Voice.ACTIVE);
            final VerbForm verbForm2 = new VerbForm(InfinitiveTense.PERFECT, Voice.ACTIVE);
            Verb verb = new Verb(new TranslationSequence(), "test") {
                @Override
                public String makeForm(VerbForm form) {
                    if (form.equals(verbForm1)) return "theform";
                    if (form.equals(verbForm2)) return "theform";
                    else return "nottheform";
                }

                @Override
                public String getBaseForm() {
                    return "baseform";
                }
            };
            assertEquals(List.of(verbForm1, verbForm2), verb.identifyForm("theform"));
        }

    }
}