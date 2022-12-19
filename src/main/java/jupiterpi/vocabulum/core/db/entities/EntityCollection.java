package jupiterpi.vocabulum.core.db.entities;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityCollection<T extends Entity> {
    protected EntityProvider entityProvider;
    protected List<T> entities = new ArrayList<>();

    protected EntityCollection(EntityProvider entityProvider) {
        this.entityProvider = entityProvider;
    }

    protected void addEntity(T entity) {
        if (!entity.hasProvider()) entity.attachProvider(entityProvider);
        entities.add(entity);
    }

    protected void removeEntity(T entity) {
        entities.remove(entity);
        entity.deleteEntity();
    }

    public void load() {
        for (T entity : entities) {
            entity.loadEntity();
        }
        for (String documentId : entityProvider.getAvailableDocumentIds()) {
            boolean exists = false;
            for (T entity : entities) {
                if (entity.getDocumentId().equals(documentId)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                addEntity(createEntityWithDocumentId(documentId));
            }
        }
    }

    protected abstract T createEntityWithDocumentId(String documentId);

    public void loadEntities() {
        entities.forEach(Entity::loadEntity);
    }

    public void saveEntities() {
        entities.forEach(Entity::saveEntity);
    }
}