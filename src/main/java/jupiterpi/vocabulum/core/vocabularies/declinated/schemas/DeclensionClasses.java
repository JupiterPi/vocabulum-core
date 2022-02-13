package jupiterpi.vocabulum.core.vocabularies.declinated.schemas;

import jupiterpi.vocabulum.core.Database;
import jupiterpi.vocabulum.core.vocabularies.declinated.LoadingDataException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeclensionClasses {
    private static Map<String, Document> declensionSchemasRaw;
    private static Map<String, DeclensionSchema> declensionSchemas;

    public static void loadDeclensionSchemas() throws LoadingDataException {
        declensionSchemasRaw = new HashMap<>();
        for (Document document : Database.declension_schemas.find()) {
            String name = document.getString("name");
            declensionSchemasRaw.put(name, document);
        }

        declensionSchemas = new HashMap<>();
        for (Document document : Database.declension_schemas.find()) {
            String name = document.getString("name");
            declensionSchemas.put(name, makeSchema(document));
        }

        assignUtilityFields();
    }

    public static DeclensionSchema makeSchema(Document document) throws LoadingDataException {
        String schema = document.getString("schema");
        return switch (schema) {
            case "simple" -> SimpleDeclensionSchema.readFromDocument(document);
            case "gender_dependant" -> GenderDependantDeclensionSchema.readFromDocument(document);
            default -> null;
        };
    }

    public static Document getRaw(String name) {
        return declensionSchemasRaw.get(name);
    }

    public static DeclensionSchema get(String name) {
        return declensionSchemas.get(name);
    }

    public static List<DeclensionSchema> getAll() {
        List<DeclensionSchema> schemas = new ArrayList<>();
        for (String key : declensionSchemas.keySet()) {
            schemas.add(declensionSchemas.get(key));
        }
        return schemas;
    }

    // utility fields

    public static DeclensionSchema a_Declension;
    public static DeclensionSchema o_Declension;
    public static DeclensionSchema cons_Declension;
    public static DeclensionSchema e_Declension;
    public static DeclensionSchema u_Declension;

    public static DeclensionSchema cons_adjectives_Declension;

    private static void assignUtilityFields() {
        a_Declension = get("a");
        o_Declension = get("o");
        cons_Declension = get("cons");
        e_Declension = get("e");
        u_Declension = get("u");

        cons_adjectives_Declension = get("cons_adjectives");
    }
}