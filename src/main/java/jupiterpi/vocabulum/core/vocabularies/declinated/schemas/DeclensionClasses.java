package jupiterpi.vocabulum.core.vocabularies.declinated.schemas;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DeclensionClasses {
    public static DeclensionSchema a_Declension;

    public static void loadDeclensionSchemas(MongoClient client) {
        MongoDatabase vocabulum_data = client.getDatabase("vocabulum_data");
        MongoCollection<Document> declension_schemas = vocabulum_data.getCollection("declension_schemas");

        a_Declension = SimpleDeclensionSchema.readFromDocument(
                declension_schemas.find(new Document("name", "a")).first()
        );
    }
}