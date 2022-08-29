package jupiterpi.vocabulum.core;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.db.portions.Portion;
import jupiterpi.vocabulum.core.db.wordbase.IdentificationResult;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException {
        System.out.println("----- Vocabulum Core -----");

        Database.get().connectAndLoad("mongodb://localhost");
        Database.get().prepareWordbase();

        Map<String, Portion> portions = Database.get().getPortions().getPortions();
        for (String key : portions.keySet()) {
            Portion portion = portions.get(key);
            /*System.out.println(portion);*/
        }

        List<IdentificationResult> identificationResults = Database.get().getWordbase().identifyWord("asin", true);
        for (IdentificationResult identificationResult : identificationResults) {
            System.out.println(identificationResult.toString());
        }

        /*Terminal terminal = new Terminal();
        terminal.run(Database.get().getI18ns().de());*/
    }
}
