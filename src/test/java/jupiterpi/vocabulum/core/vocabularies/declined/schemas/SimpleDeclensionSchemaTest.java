package jupiterpi.vocabulum.core.vocabularies.declined.schemas;

import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class SimpleDeclensionSchemaTest {
    @Nested
    @DisplayName("readFromDocument() and getSuffixRaw()")
    class ReadFromDocument {

        Document generalWithNoParentDocument = Document.parse("""
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
                }
                """);
        Document genderDependantWithNoParentDocument = Document.parse("""
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
                    }
                    """);
        Document genderDependantWithParentDocument = Document.parse("""
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
                }
                """);

        @Test
        @DisplayName("general with no parent")
        void generalWithNoParent() throws LoadingDataException, DeclinedFormDoesNotExistException {
            SimpleDeclensionSchema s = SimpleDeclensionSchema.readFromDocument(generalWithNoParentDocument);
            assertAll(
                    () -> assertEquals("a", s.getName()),
                    () -> assertEquals("am", s.getSuffixRaw(new DeclinedForm(Casus.ACC, NNumber.SG))),
                    () -> assertEquals("am", s.getSuffixRaw(new DeclinedForm(Casus.ACC, NNumber.SG, Gender.FEM))),
                    () -> assertEquals("am", s.getSuffixRaw(new DeclinedForm(Casus.ACC, NNumber.SG, Gender.MASC)))
            );
        }

        @Test
        @DisplayName("gender dependant with no parent")
        void genderDependantWithNoParent() throws LoadingDataException, DeclinedFormDoesNotExistException {
            SimpleDeclensionSchema s = SimpleDeclensionSchema.readFromDocument(genderDependantWithNoParentDocument);
            assertAll(
                    () -> assertEquals("o", s.getName()),
                    () -> assertEquals("i", s.getSuffixRaw(new DeclinedForm(Casus.NOM, NNumber.PL, Gender.MASC))),
                    () -> assertEquals("a", s.getSuffixRaw(new DeclinedForm(Casus.NOM, NNumber.PL, Gender.NEUT))),
                    () -> assertThrows(DeclinedFormDoesNotExistException.class, () -> s.getSuffixRaw(new DeclinedForm(Casus.NOM, NNumber.SG)))
            );
        }

        @Test
        @DisplayName("gender dependant with parent")
        void genderDependantWithParent() throws LoadingDataException, DeclinedFormDoesNotExistException {
            SimpleDeclensionSchema s = SimpleDeclensionSchema.readFromDocument(genderDependantWithParentDocument);
            assertAll(
                    () -> assertEquals("cons_adjectives", s.getName()),
                    () -> assertEquals("ium", s.getSuffixRaw(new DeclinedForm(Casus.GEN, NNumber.PL, Gender.MASC))),
                    () -> assertEquals("is", s.getSuffixRaw(new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC)))
            );
        }

    }
}