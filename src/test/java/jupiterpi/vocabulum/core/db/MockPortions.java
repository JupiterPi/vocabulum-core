package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.portions.Portion;
import jupiterpi.vocabulum.core.db.portions.Portions;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.bson.Document;

import java.util.Map;

public class MockPortions implements Portions {
    //TODO implement

    @Override
    public void loadPortions(Iterable<Document> documents) throws ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException {

    }

    @Override
    public Portion getPortion(String name) {
        return null;
    }

    @Override
    public Map<String, Portion> getPortions() {
        return null;
    }
}
