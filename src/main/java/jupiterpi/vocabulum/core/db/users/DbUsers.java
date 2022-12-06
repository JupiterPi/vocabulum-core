package jupiterpi.vocabulum.core.db.users;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.entities.EntityCollection;
import jupiterpi.vocabulum.core.db.entities.EntityProvider;
import jupiterpi.vocabulum.core.users.User;

import java.util.ArrayList;
import java.util.List;

public class DbUsers extends EntityCollection<User> implements Users {

    public DbUsers(Database database) {
        super(EntityProvider.fromMongoCollection(database.collection_users));
        load();
    }

    @Override
    protected User createEntityWithDocumentId(String documentId) {
        return User.readEntity(entityProvider, documentId);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(entities);
    }

    @Override
    public User getUser(String email) {
        for (User user : entities) {
            if (user.getEmail().equals(email)) return user;
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        for (User u : entities) {
            if (u.getName().equalsIgnoreCase(user.getName()) || u.getEmail().equalsIgnoreCase(user.getEmail())) return false;
        }
        addEntity(user);
        return true;
    }

    @Override
    public void save() {
        saveEntities();
    }
}
