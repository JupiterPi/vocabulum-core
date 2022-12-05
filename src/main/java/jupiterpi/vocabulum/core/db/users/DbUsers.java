package jupiterpi.vocabulum.core.db.users;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.users.User;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DbUsers implements Users {
    private Database database;

    private List<User> users;

    public DbUsers(Database database) throws ReflectiveOperationException {
        this.database = database;
        loadUsers();
    }

    public void loadUsers() throws ReflectiveOperationException {
        users = new ArrayList<>();
        for (Document document : database.collection_users.find()) {
            User user = User.readFromDocument(document);
            users.add(user);
        }
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }

    @Override
    public User getUser(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) return user;
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
    public boolean modifyUser(User user) {
        if (getUser(user.getEmail()) != null) {
            database.collection_users.replaceOne(new Document("email", user.getEmail()), user.toDocument());

            int index = -1;
            for (int i = 0; i < users.size(); i++) {
                User u = users.get(i);
                if (u.getEmail().equals(user.getEmail())) {
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
