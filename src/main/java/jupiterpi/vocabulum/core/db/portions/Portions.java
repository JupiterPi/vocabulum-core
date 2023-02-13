package jupiterpi.vocabulum.core.db.portions;

import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Collection of all available <code>Portion</code>s.
 * @see Portion
 */
public class Portions {
    private Map<String, Portion> portions;

    public void loadPortions(Map<String, List<List<String>>> portions) throws ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        this.portions = new HashMap<>();
        for (Map.Entry<String, List<List<String>>> entry : portions.entrySet()) {
            String portionName = entry.getKey();
            List<List<String>> vocabulariesStr = entry.getValue();
            Portion portion = Portion.readFromVocabularyStrBlocks(portionName, vocabulariesStr);
            this.portions.put(portion.getName(), portion);
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