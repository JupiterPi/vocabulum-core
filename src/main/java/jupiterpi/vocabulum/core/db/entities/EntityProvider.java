package jupiterpi.vocabulum.core.db.entities;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityProvider {
    public abstract List<String> getAvailableDocumentIds();

    public abstract Document readDocument(String documentId);

    public abstract String insertDocument(Document document);

    public abstract void modifyDocument(String documentId, Document document);

    public abstract void deleteDocument(String documentId);

    public static EntityProvider fromMongoCollection(MongoCollection<Document> collection) {
        return new EntityProvider() {
            @Override
            public List<String> getAvailableDocumentIds() {
                List<String> documentIds = new ArrayList<>();
                for (String id : collection.distinct("_id", ObjectId.class).map(objectId -> objectId.toString())) {
                    documentIds.add(id);
                }
                return documentIds;
            }

            @Override
            public Document readDocument(String documentId) {
                return collection.find(new Document("_id", new ObjectId(documentId))).first();
            }

            @Override
            public String insertDocument(Document document) {
                return collection.insertOne(document).getInsertedId().toString();
            }

            @Override
            public void modifyDocument(String documentId, Document document) {
                collection.replaceOne(new Document("_id", new ObjectId(documentId)), document);
            }

            @Override
            public void deleteDocument(String documentId) {
                collection.findOneAndDelete(new Document("_id", new ObjectId(documentId)));
            }
        };
    }
}
