package jupiterpi.vocabulum.core;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.db.portions.Portion;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.sessions.Session;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException, Session.SessionLifecycleException, ReflectiveOperationException {
        System.out.println("----- Vocabulum Core -----");

        Database.get().connectAndLoad("mongodb://localhost");
        Database.get().prepareWordbase();

        Map<String, Portion> portions = Database.get().getPortions().getPortions();
        for (String key : portions.keySet()) {
            Portion portion = portions.get(key);
            /*System.out.println(portion);*/
        }

        /*Terminal terminal = new Terminal();
        terminal.run(Database.get().getI18ns().de());*/

        SampleSession sampleSession = new SampleSession();
        sampleSession.run(Database.get().getI18ns().de());
    }
}
