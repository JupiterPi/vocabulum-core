package jupiterpi.vocabulum.core.db.users;

import jupiterpi.vocabulum.core.users.User;

import java.util.List;

public interface Users {
    List<User> getAll();

    User getUser(String name);

    boolean addUser(User user);

    void load();

    void save();
}
