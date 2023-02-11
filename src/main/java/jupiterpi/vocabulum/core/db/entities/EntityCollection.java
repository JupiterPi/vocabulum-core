package jupiterpi.vocabulum.core.db.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for using whole collections of entities.
 * @param <T> the entity type of this collection
 * @see Entity
 */
public abstract class EntityCollection<T extends Entity> {
    /**
     * The entity provider to be used with this collection.
     */
    protected EntityProvider entityProvider;
    /**
     * The currently loaded entities in this collection.
     */
    protected List<T> entities = new ArrayList<>();

    /**
     * Constructs a new entity collection with an entity provider
     * @param entityProvider the entity provider
     */
    protected EntityCollection(EntityProvider entityProvider) {
        this.entityProvider = entityProvider;
    }

    /**
     * Adds an entity to the managed entities in this collection.
     * Attaches the used entity provider to the entity.
     * @param entity the entity to add
     */
    protected void addEntity(T entity) {
        if (!entity.hasProvider()) entity.attachProvider(entityProvider);
        entities.add(entity);
    }

    /**
     * Deletes an entity by removing it from the managed entities in this collection and deleting it.
     * @param entity the entity to delete
     */
    protected void removeEntity(T entity) {
        entities.remove(entity);
        entity.deleteEntity();
    }

    /**
     * (Re)loads the set of managed entities (adds new ones added and removes ones deleted in the database)
     * and (re)loads all managed entities.
     */
    public void load() {
        for (T entity : entities) {
            entity.loadEntity();
        }

        List<T> unlistedEntities = new ArrayList<>(entities);
        for (String documentId : entityProvider.getAvailableDocumentIds()) {
            boolean exists = false;
            for (T entity : entities) {
                if (entity.getDocumentId().equals(documentId)) {
                    exists = true;
                    break;
                }
                unlistedEntities.remove(entity);
            }
            if (!exists) {
                addEntity(createEntityWithDocumentId(documentId));
            }
        }
        for (T entity : unlistedEntities) {
            entity.attachProvider(null);
            entities.remove(entity);
        }
    }

    /**
     * Creates an instance of the entity from the provided document.
     * @param documentId the id of the provided document
     * @return the instance of the entity
     */
    protected abstract T createEntityWithDocumentId(String documentId);

    /**
     * Saves all managed entities.
     */
    public void saveEntities() {
        entities.forEach(Entity::saveEntity);
    }
}