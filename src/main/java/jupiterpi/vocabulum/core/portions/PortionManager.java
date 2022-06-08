package jupiterpi.vocabulum.core.portions;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class PortionManager {
    private Map<String, Portion> portions;

    public PortionManager() throws ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException {
        this.portions = readPortions();
    }

    private Map<String, Portion> readPortions() throws ParserException, DeclinedFormDoesNotExistException, I18nException, LexerException {
        Map<String, Portion> portions = new HashMap<>();
        for (Document portionDocument : Database.portions.find()) {
            Portion portion = Portion.readFromDocument(portionDocument);
            portions.put(portion.getName(), portion);
        }
        return portions;
    }

    public Portion getPortion(String name) {
        return portions.get(name);
    }

    public Map<String, Portion> getPortions() {
        return portions;
    }
}