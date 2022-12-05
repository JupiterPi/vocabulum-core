package jupiterpi.vocabulum.core.users;

import jupiterpi.vocabulum.core.sessions.SessionConfiguration;
import jupiterpi.vocabulum.core.util.Attachments;
import org.bson.Document;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class User {
    // unique but mutable
    protected String name;
    // unique identifier
    protected String email;
    protected String password;

    protected List<SessionHistoryItem> sessionHistory = new ArrayList<>();

    protected Attachments remainingAttachments = Attachments.empty();

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

    public static <T extends User> T readFromDocument(Document document, Class<T> userClass, Class<? extends SessionConfiguration> sessionConfigurationClass) throws ReflectiveOperationException {
        Constructor<T> constructor = userClass.getDeclaredConstructor(Attachments.class);
        constructor.setAccessible(true);
        Attachments attachments = Attachments.fromDocument((Document) document.get("attachments"));
        T user = constructor.newInstance(attachments);
        user.name = document.getString("name");
        user.email = document.getString("email");
        user.password = document.getString("password");
        user.remainingAttachments = attachments;

        user.sessionHistory = new ArrayList<>();
        for (Document d : (List<Document>) document.get("sessionHistory")) {
            user.sessionHistory.add(SessionHistoryItem.fromDocument(d, sessionConfigurationClass));
        }

        return user;
    }

    public Document toDocument() {
        Document document = new Document();
        document.put("name", name);
        document.put("email", email);
        document.put("password", password);

        List<Document> sessionHistoryDocuments = new ArrayList<>();
        for (SessionHistoryItem sessionHistoryItem : sessionHistory) {
            sessionHistoryDocuments.add(sessionHistoryItem.getDocument());
        }
        document.put("sessionHistory", sessionHistoryDocuments);

        Attachments attachments = generateAttachments();
        attachments.addAttachments(remainingAttachments);
        document.put("attachments", attachments.getDocument());
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

    public List<SessionHistoryItem> getSessionHistory() {
        return sessionHistory;
    }

    /* setters (changers) */

    public void changeName(String newName) {
        this.name = newName;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void setSessionHistory(List<SessionHistoryItem> sessionHistory) {
        this.sessionHistory = sessionHistory;
    }

    /* other getters */

    public boolean passwordFits(String password) {
        return password.equals(this.password);
    }

    public boolean fits(String name, String password) {
        return this.name.equals(name) && this.password.equals(password);
    }
}