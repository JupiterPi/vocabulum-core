package jupiterpi.vocabulum.core.sessions;

import jupiterpi.vocabulum.core.sessions.selection.PortionBasedVocabularySelection;
import jupiterpi.vocabulum.core.sessions.selection.VocabularySelection;
import jupiterpi.vocabulum.core.sessions.selection.VocabularySelections;
import org.bson.Document;

public class SessionConfiguration {
    private VocabularySelection selection;

    public SessionConfiguration(VocabularySelection selection) {
        this.selection = selection;
    }

    public VocabularySelection getSelection() {
        return selection;
    }

    /* Documents */

    private SessionConfiguration() {}
    public static SessionConfiguration fromDocument(Document document) {
        SessionConfiguration sessionConfiguration = new SessionConfiguration();
        sessionConfiguration.selection = PortionBasedVocabularySelection.fromString(document.getString("selection"));
        return sessionConfiguration;
    }

    public Document getDocument() {
        Document document = new Document();
        document.put("selection", VocabularySelections.getPortionBasedString(selection));
        document.putAll(getCustomDataDocument());
        return document;
    }

    protected Document getCustomDataDocument() {
        return new Document();
    }
}