package jupiterpi.vocabulum.core.db.portions;

import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
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
    private Map<String, Vocabulary> vocabulariesInPortions;

    public void loadPortions(Iterable<Document> documents) throws ParserException, DeclinedFormDoesNotExistException, LexerException, VerbFormDoesNotExistException {
        portions = new HashMap<>();
        vocabulariesInPortions = new HashMap<>();
        for (Document portionDocument : documents) {
            Portion portion = Portion.readFromDocument(portionDocument);
            portions.put(portion.getName(), portion);
            for (Vocabulary vocabulary : portion.getVocabularies()) {
                vocabulariesInPortions.put(vocabulary.getBaseForm(), vocabulary);
            }
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

    /**
     * Finds the instance of a vocabulary in a portion.
     * @param base_form the base form of the looked for vocabulary
     * @return the vocabulary instance
     */
    public Vocabulary getVocabularyInPortion(String base_form) {
        return vocabulariesInPortions.get(base_form);
    }
}