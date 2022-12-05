package jupiterpi.vocabulum.core.users;

import org.bson.Document;

public class User {
    // unique but mutable
    private String name;
    // unique identifier
    private String email;
    private String password;
    private boolean isProUser;
    private String discordUsername;

    public User(String name, String email, String password, boolean isProUser, String discordUsername) {
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

    /* Documents */

    public static User readFromDocument(Document document) throws ReflectiveOperationException {
        return new User(
                document.getString("name"),
                document.getString("email"),
                document.getString("password"),
                document.getBoolean("isProUser"),
                document.getString("discordUsername")
        );
    }

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

    /* setters (changers) */

    public void changeName(String newName) {
        this.name = newName;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeIsProUser(boolean newIsProUser) {
        this.isProUser = newIsProUser;
    }

    public void changeDiscordUsername(String newDiscordUsername) {
        this.discordUsername = newDiscordUsername;
    }

    /* other getters */

    public boolean passwordFits(String password) {
        return password.equals(this.password);
    }

    public boolean fits(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }
}