package jupiterpi.vocabulum.core.users;

import jupiterpi.vocabulum.core.util.Attachments;
import org.bson.Document;

import java.lang.reflect.Constructor;

public class User {
    // unique identifier
    protected String name;
    // has to be unique too
    protected String email;
    protected String password;

    protected User(String username, String email, String password) {
        this.name = username;
        this.email = email;
        this.password = password;
    }

    public static User createUser(String username, String email, String password) {
        return new User(username, email, password);
    }

    /* Documents */

    protected User(Attachments attachments) {}

    public static <T extends User> T readFromDocument(Document document, Class<T> userClass) throws ReflectiveOperationException {
        Constructor<T> constructor = userClass.getDeclaredConstructor(Attachments.class);
        constructor.setAccessible(true);
        T user = constructor.newInstance(Attachments.fromDocument((Document) document.get("attachments")));
        user.name = document.getString("name");
        user.email = document.getString("email");
        user.password = document.getString("password");
        return user;
    }

    public Document toDocument() {
        Document document = new Document();
        document.put("name", name);
        document.put("email", email);
        document.put("password", password);
        document.put("attachments", generateAttachments().getDocument());
        return document;
    }
    protected Attachments generateAttachments() { return Attachments.empty(); }

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

    /* setters (changers) */

    public void changeName(String newName) {
        this.name = newName;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /* other getters */

    public boolean passwordFits(String password) {
        return password.equals(this.password);
    }

    public boolean fits(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }
}