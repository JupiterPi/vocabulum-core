package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.sessions.SessionConfiguration;
import jupiterpi.vocabulum.core.users.User;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class MockDatabaseSetup implements BeforeAllCallback {
    private boolean started = false;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        // We need to use a unique key here, across all usages of this particular extension.
        String uniqueKey = this.getClass().getName();
        Object value = context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).get(uniqueKey);
        if (value == null) {
            // First test container invocation.
            context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).put(uniqueKey, this);
            setup();
        }
    }

    private final boolean mockDatabase = true;
    private void setup() throws LoadingDataException, ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException, ReflectiveOperationException {
        if (mockDatabase) MockDatabase.inject();
        Database.get().connectAndLoad("mongodb://localhost", User.class, SessionConfiguration.class);
        Database.get().prepareWordbase();
        System.out.println("Connected and loaded " + (mockDatabase ? "mocked " : "") + "database");
    }

    // https://stackoverflow.com/a/64070019/13164753
}