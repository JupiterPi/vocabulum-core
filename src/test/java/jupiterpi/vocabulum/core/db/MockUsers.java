package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.users.Users;
import jupiterpi.vocabulum.core.users.User;

import java.util.ArrayList;
import java.util.List;

public class MockUsers implements Users {
    private List<User> users;

    public MockUsers() {
        users = new ArrayList<>();
        users.add(User.createUser("adam", "a.andrews@email.com", "ILoveVocabulum<3"));
    }

    public MockUsers(List<User> users) {
        this.users = users;
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
        if (getUser(user.getName()) != null) return false;
        users.add(user);
        return true;
    }

    @Override
    public boolean modifyUser(String name, User user) {
        User originalUser = getUser(name);
        if (originalUser == null) return false;
        int index = users.indexOf(originalUser);
        users.remove(originalUser);
        users.add(index, user);
        return true;
    }
}
