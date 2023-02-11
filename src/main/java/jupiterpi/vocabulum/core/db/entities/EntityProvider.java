package jupiterpi.vocabulum.core.db.entities;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction for a MongoDB collection that provides basic methods to read and write documents.
 * @see Entity
 */
public abstract class EntityProvider {
    /**
     * @return The ids of all documents available in the collection
     */
    public abstract List<String> getAvailableDocumentIds();

    /**
     * @param documentId the document id
     * @return the raw document read in from the collection
     */
    public abstract Document readDocument(String documentId);

    /**
     * Inserts a document into the collection.
     * @param document the document to insert
     * @return the assigned document id
     */
    public abstract String insertDocument(Document document);

    /**
     * Modifies a document inside the collection.
     * @param documentId the id of the document to modify
     * @param document   the modified document
     */
    public abstract void modifyDocument(String documentId, Document document);

    /**
     * Deletes a document in the collection.
     * @param documentId the id of the document to delete
     */
    public abstract void deleteDocument(String documentId);

    /**
     * Constructs an entity provider from a MongoDB collection.
     * @param collection the MongoDB collection
     * @return the corresponding entity provider
     */
    public static EntityProvider fromMongoCollection(MongoCollection<Document> collection) {
        return new EntityProvider() {
            @Override
            public List<String> getAvailableDocumentIds() {
                List<String> documentIds = new ArrayList<>();
                for (String id : collection.distinct("_id", ObjectId.class).map(objectId -> objectId.toHexString())) {
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
