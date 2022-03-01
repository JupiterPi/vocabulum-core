package jupiterpi.vocabulum.core;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.portions.PortionManager;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.declinated.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declinated.adjectives.AdjectiveForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionClasses;

public class Main {
    public static I18n i18n = I18n.de;
    public static PortionManager portionManager;

    public static void main(String[] args) throws LoadingDataException {
        System.out.println("----- Vocabulum Core -----");

        DeclensionClasses.loadDeclensionSchemas();
        portionManager = new PortionManager();

        Terminal terminal = new Terminal();
        terminal.run();
    }

    private static void test5() throws ParserException, DeclinedFormDoesNotExistException, LexerException, I18nException {
        test5_print("acer, acris, acre");
        test5_print("brevis, brevis, breve");
        test5_print("felix, Gen. felicis");
        test5_print("clemens, Gen. clementis");
        test5_print("celer, celeris, celere");
        test5_print("pulcher, pulchra, pulchrum");
    }

    private static void test5_print(String str) throws ParserException, DeclinedFormDoesNotExistException, LexerException, I18nException {
        Adjective adjective = (Adjective) Vocabulary.fromString(str, Main.i18n);
        System.out.println("----- " + str + " -----");
        System.out.println("POSITIVE = " + adjective.makeForm(new AdjectiveForm(true, ComparativeForm.POSITIVE)));
        System.out.println("COMPARATIVE = " + adjective.makeForm(new AdjectiveForm(true, ComparativeForm.COMPARATIVE)));
        System.out.println("SUPERLATIVE = " + adjective.makeForm(new AdjectiveForm(true, ComparativeForm.SUPERLATIVE)));
        System.out.println();
    }
}
