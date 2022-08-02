package jupiterpi.vocabulum.core.db.portions;

import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class DbPortions implements Portions {
    private Map<String, Portion> portions;

    @Override
    public void loadPortions(Iterable<Document> documents) throws ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException, VerbFormDoesNotExistException {
        portions = new HashMap<>();
        for (Document portionDocument : documents) {
            Portion portion = Portion.readFromDocument(portionDocument);
            portions.put(portion.getName(), portion);
        }
    }

    @Override
    public Portion getPortion(String name) {
        return portions.get(name);
    }

    @Override
    public Map<String, Portion> getPortions() {
        return portions;
    }
}