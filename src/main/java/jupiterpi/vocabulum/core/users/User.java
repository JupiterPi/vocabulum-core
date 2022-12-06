package jupiterpi.vocabulum.core.users;

import jupiterpi.vocabulum.core.db.entities.Entity;
import jupiterpi.vocabulum.core.db.entities.EntityProvider;
import org.bson.Document;

public class User extends Entity {
    // unique but mutable
    private String name;
    // unique identifier
    private String email;
    private String password;
    private boolean isProUser;
    private String discordUsername;

    private User(String name, String email, String password, boolean isProUser, String discordUsername) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.isProUser = isProUser;
        this.discordUsername = discordUsername;
    }

    public static User createUser(String name, String email, String password) {
        return new User(
                name, email, password,
                false, ""
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
        isProUser = document.getBoolean("isProUser");
        discordUsername = document.getString("discordUsername");
    }

    @Override
    public Document toDocument() {
        Document document = new Document();
        document.put("name", name);
        document.put("email", email);
        document.put("password", password);
        document.put("isProUser", isProUser);
        document.put("discordUsername", discordUsername);
        return document;
    }

    /* getters */

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isProUser() {
        return isProUser;
    }

    public String getDiscordUsername() {
        return discordUsername;
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

    public User setIsProUser(boolean isProUser) {
        this.isProUser = isProUser;
        return this;
    }

    public User setDiscordUsername(String discordUsername) {
        this.discordUsername = discordUsername;
        return this;
    }

    /* other getters */

    public boolean passwordFits(String password) {
        return password.equals(this.password);
    }

    public boolean fits(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }
}