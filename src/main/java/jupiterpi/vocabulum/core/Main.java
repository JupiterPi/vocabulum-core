package jupiterpi.vocabulum.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.Number;
import jupiterpi.vocabulum.core.vocabularies.declinated.nouns.Noun;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionClasses;

public class Main {
    public static MongoClient mongoClient;

    public static void main(String[] args) throws LexerException, DeclinedFormDoesNotExistException, ParserException {
        System.out.println("----- Vocabulum Core -----");

        mongoClient = MongoClients.create("mongodb://localhost");

        DeclensionClasses.loadDeclensionSchemas(mongoClient);

        //test1();
        //test2();

        //Terminal terminal = new Terminal();
        //terminal.run();

        test3();
    }

    private static void test3() throws ParserException, LexerException, DeclinedFormDoesNotExistException {
        test3_full("acer", "acris", "acre");
        System.out.println();
        test3_full("brevis", "brevis", "breve");
        System.out.println();
        test3_print(Adjective.fromBaseForm("felix", "felicis"));
        System.out.println();
    }
    private static void test3_full(String nom_sg_masc, String nom_sg_fem, String nom_sg_neut) throws DeclinedFormDoesNotExistException, LexerException, ParserException {
        Adjective adjective = Adjective.fromBaseForms(nom_sg_masc, nom_sg_fem, nom_sg_neut);
        test3_print(adjective);
    }
    private static void test3_print(Adjective adjective) throws ParserException, LexerException, DeclinedFormDoesNotExistException {
        System.out.println(adjective.getBaseForm());
        System.out.println(adjective.getForm(DeclinedForm.fromString("Nom. Sg. f.")));
        System.out.println(adjective.getForm(DeclinedForm.fromString("Gen. Pl. m.")));
        System.out.println(adjective.getForm(DeclinedForm.fromString("Acc. Sg. n.")));
    }

    private static void test2() throws LexerException, DeclinedFormDoesNotExistException, ParserException {
        Vocabulary vocabulary = Vocabulary.fromString("consilium, consilii n.");
        System.out.println(((Noun) vocabulary).getForm(DeclinedForm.fromString("Nom. Sg.")));
        System.out.println(((Noun) vocabulary).getForm(DeclinedForm.fromString("Gen. Pl.")));
    }

    private static void test1() {
        try {
            printForms(new Noun(DeclensionClasses.a_Declension, "amica", "amic", Gender.FEM),
                    new DeclinedForm(Casus.NOM, Number.SG, Gender.FEM),
                    new DeclinedForm(Casus.GEN, Number.PL, Gender.FEM));
            printForms(new Noun(DeclensionClasses.o_Declension, "amicus", "amic", Gender.MASC),
                    new DeclinedForm(Casus.NOM, Number.SG, Gender.MASC),
                    new DeclinedForm(Casus.ABL, Number.PL, Gender.MASC));
        } catch (DeclinedFormDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void printForms(Noun noun, DeclinedForm... forms) throws DeclinedFormDoesNotExistException {
        System.out.println("--- " + noun.getBaseForm() + " ---");
        for (DeclinedForm form : forms) {
            System.out.println(form.formToString() + " = " + noun.getForm(form));
        }
    }
}
