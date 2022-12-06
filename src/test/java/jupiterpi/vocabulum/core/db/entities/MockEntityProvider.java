package jupiterpi.vocabulum.core.db.entities;

import org.bson.Document;

import java.util.List;

public class MockEntityProvider {
    public static EntityProvider withSingleDocument(Document document) {
        return new EntityProvider() {
            @Override
            public List<String> getAvailableDocumentIds() {
                return List.of("");
            }

            @Override
            public Document readDocument(String documentId) {
                return document;
            }

            @Override
            public String insertDocument(Document document) {
                return "";
            }

            @Override
            public void modifyDocument(String documentId, Document document) {}
        };
    }
}