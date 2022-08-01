package jupiterpi.vocabulum.core.vocabularies.declined.schemas;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeclensionClasses {
    private static DeclensionClasses instance = null;
    public static DeclensionClasses get() {
        if (instance == null) {
            instance = new DeclensionClasses();
        }
        return instance;
    }

    /////

    private Map<String, Document> declensionSchemasRaw;
    private Map<String, DeclensionSchema> declensionSchemas;

    public void loadDeclensionSchemas() throws LoadingDataException {
        declensionSchemasRaw = new HashMap<>();
        declensionSchemas = new HashMap<>();
        for (Document document : Database.declension_schemas.find()) {
            String name = document.getString("name");
            declensionSchemasRaw.put(name, document);
            declensionSchemas.put(name, makeSchema(document));
        }

        assignUtilityFields();
    }

    public DeclensionSchema makeSchema(Document document) throws LoadingDataException {
        String schema = document.getString("schema");
        return switch (schema) {
            case "simple" -> SimpleDeclensionSchema.readFromDocument(document);
            case "gender_dependant" -> GenderDependantDeclensionSchema.readFromDocument(document);
            default -> null;
        };
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

    // utility fields

    public DeclensionSchema a_Declension;
    public DeclensionSchema o_Declension;
    public DeclensionSchema cons_Declension;
    public DeclensionSchema e_Declension;
    public DeclensionSchema u_Declension;

    public DeclensionSchema cons_adjectives_Declension;

    private void assignUtilityFields() {
        a_Declension = getSchema("a");
        o_Declension = getSchema("o");
        cons_Declension = getSchema("cons");
        e_Declension = getSchema("e");
        u_Declension = getSchema("u");

        cons_adjectives_Declension = getSchema("cons_adjectives");
    }
}