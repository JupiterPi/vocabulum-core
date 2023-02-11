package jupiterpi.vocabulum.core.db.classes;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionSchema;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.SimpleDeclensionSchema;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An ordered collection of all available <code>DeclensionSchema</code>s.
 * @see Database#getDeclensionClasses()
 * @see DeclensionSchema
 */
public class DeclensionClasses {
    private Map<String, Document> declensionSchemasRaw;
    private Map<String, DeclensionSchema> declensionSchemas;

    public void loadDeclensionSchemas(Iterable<Document> documents) throws LoadingDataException {
        declensionSchemasRaw = new HashMap<>();
        for (Document document : documents) {
            String name = document.getString("name");
            declensionSchemasRaw.put(name, document);
        }

        declensionSchemas = new HashMap<>();
        for (Document document : documents) {
            String name = document.getString("name");
            try {
                declensionSchemas.put(name, SimpleDeclensionSchema.readFromDocument(document));
            } catch (Exception e) {
                throw new LoadingDataException(String.format("Could not read declension schema %s: %s \"%s\"", name, e.getClass().getSimpleName(), e.getMessage()));
            }
        }
    }

    /**
     * @param name the name of the declension schema
     * @return the raw bson document for the declension schema
     */
    public Document getRaw(String name) {
        return declensionSchemasRaw.get(name);
    }

    /**
     * @param name the name of the declension schema
     * @return the declension schema with the specified name
     */
    public DeclensionSchema getSchema(String name) {
        return declensionSchemas.get(name);
    }

    /**
     * @return all available declension schemas
     */
    public List<DeclensionSchema> getAll() {
        List<DeclensionSchema> schemas = new ArrayList<>();
        for (String key : declensionSchemas.keySet()) {
            schemas.add(declensionSchemas.get(key));
        }
        return schemas;
    }

    /**
     * @return only those declension schemas that are used for <code>Noun</code>s
     */
    public List<DeclensionSchema> getAllForNouns() {
        List<DeclensionSchema> schemasForNouns = new ArrayList<>();
        for (DeclensionSchema declensionSchema : getAll()) {
            if (declensionSchema.getName().equals("cons_adjectives")) continue;
            schemasForNouns.add(declensionSchema);
        }
        return schemasForNouns;
    }

    // utility fields

    /**
     * Equivalent to <code>DeclensionClasses.getSchema("a")</code>
     * @return the standard "a" declension schema
     */
    public DeclensionSchema a_Declension() {
        return getSchema("a");
    }
    /**
     * Equivalent to <code>DeclensionClasses.getSchema("o")</code>
     * @return the standard "o" declension schema
     */
    public DeclensionSchema o_Declension() {
        return getSchema("o");
    }
    /**
     * Equivalent to <code>DeclensionClasses.getSchema("cons")</code>
     * @return the standard "cons" declension schema
     */
    public DeclensionSchema cons_Declension() {
        return getSchema("cons");
    }
    /**
     * Equivalent to <code>DeclensionClasses.getSchema("e")</code>
     * @return the standard "e" declension schema
     */
    public DeclensionSchema e_Declension() {
        return getSchema("e");
    }
    /**
     * Equivalent to <code>DeclensionClasses.getSchema("u")</code>
     * @return the standard "u" declension schema
     */
    public DeclensionSchema u_Declension() {
        return getSchema("u");
    }
    /**
     * Equivalent to <code>DeclensionClasses.getSchema("a")</code>
     * @return the standard "a" declension schema
     */
    public DeclensionSchema cons_adjectives_Declension() {
        return getSchema("cons_adjectives");
    }
}