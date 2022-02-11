package jupiterpi.vocabulum.core.vocabularies.declinated.schemas;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class DeclensionClasses {
    private static Map<String, DeclensionSchema> declensionSchemas;

    public static void loadDeclensionSchemas(MongoClient client) {
        MongoDatabase vocabulum_data = client.getDatabase("vocabulum_data");
        MongoCollection<Document> declension_schemas = vocabulum_data.getCollection("declension_schemas");

        declensionSchemas = new HashMap<>();
        for (Document document : declension_schemas.find()) {
            String name = document.getString("name");
            String schema = document.getString("schema");
            declensionSchemas.put(name, switch (schema) {
                case "simple" -> SimpleDeclensionSchema.readFromDocument(document);
                case "gender_dependant" -> GenderDependantDeclensionSchema.readFromDocument(document);
                default -> null;
            });
        }

        assignUtilityFields();
    }

    public static DeclensionSchema get(String name) {
        return declensionSchemas.get(name);
    }

    // utility fields

    public static DeclensionSchema a_Declension;
    public static DeclensionSchema o_Declension;

    private static void assignUtilityFields() {
        a_Declension = get("a");
        o_Declension = get("o");
    }
}