package jupiterpi.vocabulum.core.sessions;

import jupiterpi.vocabulum.core.sessions.selection.PortionBasedVocabularySelection;
import jupiterpi.vocabulum.core.sessions.selection.VocabularySelection;
import jupiterpi.vocabulum.core.sessions.selection.VocabularySelections;
import jupiterpi.vocabulum.core.util.Attachments;
import org.bson.Document;

import java.lang.reflect.Constructor;

public class SessionConfiguration {
    protected VocabularySelection selection;
    protected Attachments remainingAttachments = Attachments.empty();

    public SessionConfiguration(VocabularySelection selection) {
        this.selection = selection;
    }

    public VocabularySelection getSelection() {
        return selection;
    }

    /* Documents */

    protected SessionConfiguration(Attachments attachments) {}

    public static <T extends SessionConfiguration> T fromDocument(Document document, Class<T> sessionConfigurationClass) throws ReflectiveOperationException {
        Constructor<T> constructor = sessionConfigurationClass.getDeclaredConstructor(Attachments.class);
        constructor.setAccessible(true);
        Attachments attachments = Attachments.fromDocument((Document) document.get("attachments"));
        T sessionConfiguration = constructor.newInstance(attachments);
        sessionConfiguration.selection = PortionBasedVocabularySelection.fromString(document.getString("selection"));
        sessionConfiguration.remainingAttachments = attachments;
        return sessionConfiguration;
    }

    public Document getDocument() {
        Document document = new Document();
        document.put("selection", VocabularySelections.getPortionBasedString(selection));

        Attachments attachments = generateAttachments();
        attachments.addAttachments(remainingAttachments);
        document.put("attachments", attachments.getDocument());
        return document;
    }
    protected Attachments generateAttachments() { return Attachments.empty(); }
}