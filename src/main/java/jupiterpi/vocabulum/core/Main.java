package jupiterpi.vocabulum.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.Noun;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionClasses;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Number;

public class Main {
    public static MongoClient mongoClient;

    public static void main(String[] args) throws LexerException, DeclinedFormDoesNotExistException, ParserException {
        System.out.println("----- Vocabulum Core -----");

        mongoClient = MongoClients.create("mongodb://localhost");

        DeclensionClasses.loadDeclensionSchemas(mongoClient);

        //test1();
        test2();
    }

    private static void test2() throws LexerException, DeclinedFormDoesNotExistException, ParserException {
        Lexer lexer = new Lexer("amicus, amici m.");
        for (Token token : lexer.getTokens()) {
            System.out.println(token);
        }

        Vocabulary vocabulary = Vocabulary.fromString("exercitus, exercitus m.");
        System.out.println(vocabulary.getBaseForm());
        System.out.println(((Noun) vocabulary).getForm(new DeclinedForm(Casus.NOM, Number.SG, Gender.MASC)));
        System.out.println(((Noun) vocabulary).getForm(new DeclinedForm(Casus.GEN, Number.PL, Gender.MASC)));
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
