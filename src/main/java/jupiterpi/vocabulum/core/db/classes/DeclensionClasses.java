package jupiterpi.vocabulum.core.db.classes;

import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionSchema;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.SimpleDeclensionSchema;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Document getRaw(String name) {
        return declensionSchemasRaw.get(name);
    }

    public DeclensionSchema getSchema(String name) {
        return declensionSchemas.get(name);
    }

    public List<DeclensionSchema> getAll() {
        List<DeclensionSchema> schemas = new ArrayList<>();
        for (String key : declensionSchemas.keySet()) {
            schemas.add(declensionSchemas.get(key));
        }
        return schemas;
    }

    public List<DeclensionSchema> getAllForNouns() {
        List<DeclensionSchema> schemasForNouns = new ArrayList<>();
        for (DeclensionSchema declensionSchema : getAll()) {
            if (declensionSchema.getName().equals("cons_adjectives")) continue;
            schemasForNouns.add(declensionSchema);
        }
        return schemasForNouns;
    }

    // utility fields

    public DeclensionSchema a_Declension() {
        return getSchema("a");
    }
    public DeclensionSchema o_Declension() {
        return getSchema("o");
    }
    public DeclensionSchema cons_Declension() {
        return getSchema("cons");
    }
    public DeclensionSchema e_Declension() {
        return getSchema("e");
    }
    public DeclensionSchema u_Declension() {
        return getSchema("u");
    }
    public DeclensionSchema cons_adjectives_Declension() {
        return getSchema("cons_adjectives");
    }
}