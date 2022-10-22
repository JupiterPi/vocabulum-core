package jupiterpi.vocabulum.core.users;

import jupiterpi.vocabulum.core.sessions.SessionConfiguration;
import org.bson.Document;

import java.util.Date;

public class SessionHistoryItem<C extends SessionConfiguration> {
    private Date time;
    private C sessionConfiguration;
    private float firstResult;
    private int totalAttempts;

    public SessionHistoryItem(Date time, C sessionConfiguration, float firstResult, int totalAttempts) {
        this.time = time;
        this.sessionConfiguration = sessionConfiguration;
        this.firstResult = firstResult;
        this.totalAttempts = totalAttempts;
    }

    /* Documents */

    public static <C extends SessionConfiguration> SessionHistoryItem<C> fromDocument(Document document, Class<C> sessionConfigurationClass) throws ReflectiveOperationException {
        Date time = new Date(document.getLong("time"));
        float firstResult = document.getInteger("firstResult") / 100f;
        int totalAttempts = document.getInteger("totalAttempts");
        C sessionConfiguration = SessionConfiguration.fromDocument((Document) document.get("sessionConfiguration"), sessionConfigurationClass);
        return new SessionHistoryItem<>(time, sessionConfiguration, firstResult, totalAttempts);
    }

    public Document getDocument() {
        Document document = new Document();
        document.put("time", time.getTime());
        document.put("sessionConfiguration", sessionConfiguration.getDocument());
        document.put("firstResult", (int) Math.floor(firstResult * 100));
        document.put("totalAttempts", totalAttempts);
        return document;
    }

    /* getters, setters */

    public Date getTime() {
        return time;
    }

    public C getSessionConfiguration() {
        return sessionConfiguration;
    }

    public float getFirstResult() {
        return firstResult;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }
}