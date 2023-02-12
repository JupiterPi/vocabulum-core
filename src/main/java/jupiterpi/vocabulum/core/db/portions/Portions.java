package jupiterpi.vocabulum.core.db.portions;

import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * Collection of all available <code>Portion</code>s.
 * @see Portion
 */
public class Portions {
    private Map<String, Portion> portions;

    public void loadPortions(Iterable<Document> documents) throws ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        portions = new HashMap<>();
        for (Document portionDocument : documents) {
            Portion portion = Portion.readFromDocument(portionDocument);
            portions.put(portion.getName(), portion);
        }
    }

    /**
     * @param name the name of the portion
     * @return the portion with the specified name
     */
    public Portion getPortion(String name) {
        return portions.get(name);
    }

    /**
     * @return all available portions
     */
    public Map<String, Portion> getPortions() {
        return portions;
    }
}