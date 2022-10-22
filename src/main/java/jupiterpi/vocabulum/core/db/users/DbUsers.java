package jupiterpi.vocabulum.core.db.users;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.sessions.SessionConfiguration;
import jupiterpi.vocabulum.core.users.User;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DbUsers implements Users {
    private Database database;

    private List<User> users;
    private Class<? extends User> userClass;
    private Class<? extends SessionConfiguration> sessionConfigurationClass;

    public DbUsers(Database database, Class<? extends User> userClass, Class<? extends SessionConfiguration> sessionConfigurationClass) throws ReflectiveOperationException {
        this.database = database;
        this.userClass = userClass;
        loadUsers();
    }

    public void loadUsers() throws ReflectiveOperationException {
        users = new ArrayList<>();
        for (Document document : database.collection_users.find()) {
            User user = User.readFromDocument(document, userClass, sessionConfigurationClass);
            users.add(user);
        }
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public User getUser(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) return user;
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(user.getName()) || u.getEmail().equalsIgnoreCase(user.getEmail())) return false;
        }
        database.collection_users.insertOne(user.toDocument());
        users.add(user);
        return true;
    }

    @Override
    public boolean modifyUser(String name, User user) {
        if (getUser(user.getName()) != null) {
            database.collection_users.replaceOne(new Document("name", name), user.toDocument());

            int index = -1;
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);
                if (u.getName().equals(name)) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                users.remove(index);
                users.add(index, user);
            }
            return true;
        } else {
            return false;
        }
    }
}
