package jupiterpi.vocabulum.core;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.i18n.I18nManager;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.portions.Portion;
import jupiterpi.vocabulum.core.portions.PortionManager;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionClasses;

import java.util.Map;

public class Main {
    public static I18nManager i18nManager = new I18nManager();
    public static I18n i18n = i18nManager.de;
    public static PortionManager portionManager;

    public static void main(String[] args) throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException {
        System.out.println("----- Vocabulum Core -----");

        DeclensionClasses.loadDeclensionSchemas();
        portionManager = new PortionManager();
        Map<String, Portion> portions = portionManager.getPortions();
        for (String key : portions.keySet()) {
            Portion portion = portions.get(key);
            System.out.println(portion);
        }

        for (Vocabulary vocabulary : portions.get("01-1").getVocabularies()) {
            System.out.println(vocabulary);
        }

        //Terminal terminal = new Terminal();
        //terminal.run();
    }
}
