package jupiterpi.vocabulum.core.db.entities;

import org.bson.Document;

public abstract class Entity {
    private EntityProvider entityProvider;
    private String documentId;

    protected Entity() {}

    protected Entity(EntityProvider entityProvider, String documentId) {
        this.entityProvider = entityProvider;
        this.documentId = documentId;
        loadEntity();
    }

    public String getDocumentId() {
        return documentId;
    }

    public boolean hasProvider() {
        return entityProvider != null;
    }

    public boolean usesProvider() {
        return entityProvider != null && documentId != null;
    }

    public void attachProvider(EntityProvider entityProvider) {
        this.entityProvider = entityProvider;
    }

    /* entity provider interaction */

    public void loadEntity() {
        if (usesProvider()) {
            loadFromDocument(entityProvider.readDocument(documentId));
        }
    }

    protected abstract void loadFromDocument(Document document);

    public void saveEntity() {
        if (hasProvider()) {
            if (usesProvider()) {
                entityProvider.modifyDocument(documentId, toDocument());
            } else {
                documentId = entityProvider.insertDocument(toDocument());
            }
        }
    }

    protected abstract Document toDocument();

    public void deleteEntity() {
        if (usesProvider()) {
            entityProvider.deleteDocument(documentId);
        }
    }
}