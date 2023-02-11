package jupiterpi.vocabulum.core.db.users;

import jupiterpi.vocabulum.core.users.User;

import java.util.List;

/**
 * Collection of users.
 * @see User
 */
public interface Users {
    /**
     * @return all available users
     */
    List<User> getAll();

    /**
     * @param name the email (not username) of the user
     * @return the user with that email
     */
    User getUser(String name);

    /**
     * Adds a user to the database.
     * @param user the user to add
     * @return whether the user could be added (not possible when the username or email is already taken)
     */
    boolean addUser(User user);

    /**
     * (Re)loads all users.
     */
    void load();

    /**
     * Saves all users.
     */
    void save();
}
