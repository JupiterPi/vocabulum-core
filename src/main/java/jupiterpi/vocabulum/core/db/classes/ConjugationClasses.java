package jupiterpi.vocabulum.core.db.classes;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationSchema;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.SimpleConjugationSchema;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An ordered collection of all available <code>ConjugationSchema</code>s.
 * @see Database#getConjugationClasses()
 * @see ConjugationSchema
 */
public class ConjugationClasses {
    private Map<String, ConjugationSchema> conjugationSchemas;

    public void loadConjugationSchemas(Iterable<Document> documents) throws LoadingDataException {
        conjugationSchemas = new HashMap<>();
        for (Document document : documents) {
            String name = document.getString("name");
            conjugationSchemas.put(name, makeSchema(document));
        }
    }

    public ConjugationSchema makeSchema(Document document) throws LoadingDataException {
        return SimpleConjugationSchema.readFromDocument(document);
    }

    /**
     * @param name the name of the conjugation schema
     * @return the conjugation schema with the specified name
     */
    public ConjugationSchema getSchema(String name) {
        return conjugationSchemas.get(name);
    }

    /**
     * @return all available conjugation schemas
     */
    public List<ConjugationSchema> getAll() {
        List<ConjugationSchema> schemas = new ArrayList<>();
        for (String key : conjugationSchemas.keySet()) {
            schemas.add(conjugationSchemas.get(key));
        }
        return schemas;
    }

    // utility fields

    /**
     * Equivalent to <code>ConjugationClasses.getSchema("a")</code>.
     * @return the standard "a" conjugation schema
     */
    public ConjugationSchema a_Conjugation() {
        return getSchema("a");
    }
    /**
     * Equivalent to <code>ConjugationClasses.getSchema("e")</code>.
     * @return the standard "e" conjugation schema
     */
    public ConjugationSchema e_Conjugation() {
        return getSchema("e");
    }
    /**
     * Equivalent to <code>ConjugationClasses.getSchema("ii")</code>.
     * @return the standard "ii" conjugation schema
     */
    public ConjugationSchema ii_Conjugation() {
        return getSchema("ii");
    }
    /**
     * Equivalent to <code>ConjugationClasses.getSchema("cons")</code>.
     * @return the standard "cons" conjugation schema
     */
    public ConjugationSchema cons_Conjugation() {
        return getSchema("cons");
    }
    /**
     * Equivalent to <code>ConjugationClasses.getSchema("i")</code>.
     * @return the standard "i" conjugation schema
     */
    public ConjugationSchema i_Conjugation() {
        return getSchema("i");
    }
}
