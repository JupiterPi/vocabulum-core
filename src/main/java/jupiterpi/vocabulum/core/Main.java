package jupiterpi.vocabulum.core;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.sessions.Session;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;

public class Main {
    public static void main(String[] args) throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException, Session.SessionLifecycleException {
        System.out.println("----- Vocabulum Core -----");

        Database.get().load();

        /*SampleSession sampleSession = new SampleSession();
        sampleSession.run();*/
    }
}
