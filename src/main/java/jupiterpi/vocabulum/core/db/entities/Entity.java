package jupiterpi.vocabulum.core.db.entities;

import org.bson.Document;

/**
 * Base class used for objects that resemble individual bson documents on the database.
 * It can be used to (re)load and save specific documents and to (de)serialize them.
 * Before you can do (re)load and save operations, you need to hydrate it by attaching an entity provider and setting a document id.
 * @see EntityProvider
 * @see EntityCollection
 * @see #hydrate(EntityProvider, String)
 */
public abstract class Entity {
    private EntityProvider entityProvider;
    private String documentId;

    protected Entity() {}

    /**
     * Creates and immediately hydrates an entity.
     * Equivalent to calling <code>Entity.hydrate(entityProvider, documentId)</code>
     * @param entityProvider the entity provider to attach
     * @param documentId     the id of the bson document that this entity resembles
     * @see #hydrate(EntityProvider, String)
     */
    protected Entity(EntityProvider entityProvider, String documentId) {
        hydrate(entityProvider, documentId);
    }

    /**
     * Hydrates the entity by attaching an entity provider and setting a document id.
     * @param entityProvider the entity provider to attach
     * @param documentId     the id of the bson document that this entity resembles
     */
    protected void hydrate(EntityProvider entityProvider, String documentId) {
        this.entityProvider = entityProvider;
        this.documentId = documentId;
        loadEntity();
    }

    /**
     * @return the id of the bson document that this entity resembles
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * @return whether this entity has an entity provider attached
     */
    public boolean hasProvider() {
        return entityProvider != null;
    }

    /**
     * @return whether this entity has an entity provider attached and the document id set
     */
    public boolean usesProvider() {
        return entityProvider != null && documentId != null;
    }

    /**
     * Attaches an entity provider.
     * @param entityProvider the entity provider to attach
     */
    public void attachProvider(EntityProvider entityProvider) {
        this.entityProvider = entityProvider;
    }

    /* entity provider interaction */

    /**
     * (Re)loads this entity from the entity provider using the specified document id.
     */
    public void loadEntity() {
        if (usesProvider()) {
            loadFromDocument(entityProvider.readDocument(documentId));
        }
    }

    /**
     * Deserializes the raw document from the entity provider into this entity.
     * @param document the raw document from the entity provider
     */
    protected abstract void loadFromDocument(Document document);

    /**
     * Saves this entity to the entity provider using the specified document id.
     */
    public void saveEntity() {
        if (hasProvider()) {
            if (usesProvider()) {
                entityProvider.modifyDocument(documentId, toDocument());
            } else {
                documentId = entityProvider.insertDocument(toDocument());
            }
        }
    }

    /**
     * Serializes this entity into a raw document
     * @return the raw document representation of this entity
     */
    protected abstract Document toDocument();

    /**
     * Deletes this entity in the entity provider.
     */
    public void deleteEntity() {
        if (usesProvider()) {
            entityProvider.deleteDocument(documentId);
        }
    }
}