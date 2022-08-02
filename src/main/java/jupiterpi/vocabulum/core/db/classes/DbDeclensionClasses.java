package jupiterpi.vocabulum.core.db.classes;

import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionSchema;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.GenderDependantDeclensionSchema;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.SimpleDeclensionSchema;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbDeclensionClasses implements DeclensionClasses {
    private Map<String, Document> declensionSchemasRaw;
    private Map<String, DeclensionSchema> declensionSchemas;

    @Override
    public void loadDeclensionSchemas(Iterable<Document> documents) throws LoadingDataException {
        declensionSchemasRaw = new HashMap<>();
        for (Document document : documents) {
            String name = document.getString("name");
            declensionSchemasRaw.put(name, document);
        }

        declensionSchemas = new HashMap<>();
        for (Document document : documents) {
            String name = document.getString("name");
            declensionSchemas.put(name, makeSchema(document));
        }
    }

    @Override
    public DeclensionSchema makeSchema(Document document) throws LoadingDataException {
        String schema = document.getString("schema");
        return switch (schema) {
            case "simple" -> SimpleDeclensionSchema.readFromDocument(document);
            case "gender_dependant" -> GenderDependantDeclensionSchema.readFromDocument(document);
            default -> null;
        };
    }

    @Override
    public Document getRaw(String name) {
        return declensionSchemasRaw.get(name);
    }

    @Override
    public DeclensionSchema getSchema(String name) {
        return declensionSchemas.get(name);
    }

    @Override
    public List<DeclensionSchema> getAll() {
        List<DeclensionSchema> schemas = new ArrayList<>();
        for (String key : declensionSchemas.keySet()) {
            schemas.add(declensionSchemas.get(key));
        }
        return schemas;
    }

    // utility fields

    @Override
    public DeclensionSchema a_Declension() {
        return getSchema("a");
    }
    @Override
    public DeclensionSchema o_Declension() {
        return getSchema("o");
    }
    @Override
    public DeclensionSchema cons_Declension() {
        return getSchema("cons");
    }
    @Override
    public DeclensionSchema e_Declension() {
        return getSchema("e");
    }
    @Override
    public DeclensionSchema u_Declension() {
        return getSchema("u");
    }
    @Override
    public DeclensionSchema cons_adjectives_Declension() {
        return getSchema("cons_adjectives");
    }
}