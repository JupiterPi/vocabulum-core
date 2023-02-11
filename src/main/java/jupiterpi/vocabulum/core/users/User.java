package jupiterpi.vocabulum.core.users;

import jupiterpi.vocabulum.core.db.entities.Entity;
import jupiterpi.vocabulum.core.db.entities.EntityProvider;
import org.bson.Document;

import java.util.Date;
import java.util.Objects;

/**
 * Represents a Vocabulum user.
 * The unique, immutable identifier for users is their email.
 * The name is unique too, but mutable.
 */
public class User extends Entity {
    // unique but mutable
    private String name;
    // unique identifier
    private String email;
    private String password;
    private Date proExpiration;
    private String discordUsername;
    private boolean isAdmin;

    private User(String name, String email, String password, Date proExpiration, String discordUsername, boolean isAdmin) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.proExpiration = proExpiration;
        this.discordUsername = discordUsername;
        this.isAdmin = isAdmin;
    }

    public static User createUser(String name, String email, String password) {
        return new User(
                name, email, password,
                null, "", false
        );
    }

    /* Entity */

    private User(EntityProvider entityProvider, String documentId) {
        super(entityProvider, documentId);
    }
    public static User readEntity(EntityProvider entityProvider, String documentId) {
        return new User(entityProvider, documentId);
    }
    @Override
    protected void loadFromDocument(Document document) {
        name = document.getString("name");
        email = document.getString("email");
        password = document.getString("password");
        proExpiration = document.getDate("proExpiration");
        discordUsername = document.getString("discordUsername");
        isAdmin = document.getBoolean("isAdmin");
    }

    @Override
    public Document toDocument() {
        Document document = new Document();
        document.put("name", name);
        document.put("email", email);
        document.put("password", password);
        document.put("proExpiration", proExpiration);
        document.put("discordUsername", discordUsername);
        document.put("isAdmin", isAdmin);
        return document;
    }

    /* getters */

    /**
     * @return this user's name, which is unique but mutable
     * @see #fits(String, String)
     */
    public String getName() {
        return name;
    }

    /**
     * @return this user's email, which is their unique, immutable identifier
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return this user's password
     * @see #passwordFits(String)
     * @see #fits(String, String)
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns when this user's pro subscription expires.
     * Can be before the current date, in which case it means when it has expired last.
     * @return the expiration of this user's pro subscription
     * @see #isProUser()
     */
    public Date getProExpiration() {
        return proExpiration;
    }

    /**
     * @return this user's connected Discord username
     */
    public String getDiscordUsername() {
        return discordUsername;
    }

    /**
     * @return whether this user is an admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /* setters */

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setProExpiration(Date proExpiration) {
        this.proExpiration = proExpiration;
        return this;
    }

    public User setDiscordUsername(String discordUsername) {
        this.discordUsername = discordUsername;
        return this;
    }

    /* other getters */

    /**
     * Checks whether the password provided for login is correct.
     * @param password the password provided for login
     * @return whether the password is correct
     */
    public boolean passwordFits(String password) {
        return password.equals(this.password);
    }

    /**
     * Checks whether the username and password provided for login is correct / fit this user.
     * @param name the name provided for login
     * @param password the password provided for login
     * @return whether the username and password fit this user
     */
    public boolean fits(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }

    /**
     * @return whether this user's pro subscription has not expired
     * @see #getProExpiration()
     */
    public boolean isProUser() {
        if (proExpiration == null) return false;
        return new Date().getTime() <= proExpiration.getTime();
    }

    /* equals */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isAdmin == user.isAdmin && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(proExpiration, user.proExpiration) && Objects.equals(discordUsername, user.discordUsername);
    }
}