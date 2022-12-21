package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.users.Users;
import jupiterpi.vocabulum.core.users.User;

import java.util.ArrayList;
import java.util.List;

public class MockUsers implements Users {
    private List<User> users;
    public MockUsers() {
        users = new ArrayList<>();
        users.add(User.createUser("Adam01", "a.andrews@email.com", "ILoveVocabulum<3"));
        users.add(User.createUser("Admine", "admine@email.com", "IAmDaAdmin"));
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
        if (getUser(user.getName()) != null) return false;
        users.add(user);
        return true;
    }

    @Override
    public void load() {}
    @Override
    public void save() {}
}
