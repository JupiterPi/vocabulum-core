package jupiterpi.vocabulum.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Database {
    public static MongoClient mongoClient;

    public static MongoDatabase vocabulum_data;
    public static MongoCollection<Document> declension_schemas;
    public static MongoCollection<Document> other;
    public static MongoCollection<Document> texts;
    public static MongoCollection<Document> portions;

    static {
        Database.mongoClient = MongoClients.create("mongodb://localhost");

        vocabulum_data = mongoClient.getDatabase("vocabulum_data");
        declension_schemas = vocabulum_data.getCollection("declension_schemas");
        other = vocabulum_data.getCollection("other");
        texts = vocabulum_data.getCollection("texts");
        portions = vocabulum_data.getCollection("portions");
    }
}