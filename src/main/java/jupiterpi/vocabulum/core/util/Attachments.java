package jupiterpi.vocabulum.core.util;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class Attachments {
    private Map<String, Document> attachments;

    public Attachments(Map<String, Document> attachments) {
        this.attachments = attachments;
    }

    public static Attachments empty() {
        return new Attachments(new HashMap<>());
    }

    /* access for provider */

    public static Attachments fromDocument(Document attachmentsDocument) {
        Map<String, Document> attachments = new HashMap<>();
        for (String key : attachmentsDocument.keySet()) {
            attachments.put(key, (Document) attachmentsDocument.get(key));
        }
        return new Attachments(attachments);
    }

    public Document getDocument() {
        Document document = new Document();
        for (String key : attachments.keySet()) {
            document.put(key, attachments.get(key));
        }
        return document;
    }

    /* access for consumers */

    public Document getAttachment(String name) {
        return attachments.get(name);
    }

    public void removeAttachment(String name) {
        attachments.remove(name);
    }

    public Document consumeAttachment(String name) {
        Document attachment = getAttachment(name);
        removeAttachment(name);
        return attachment;
    }

    public void addAttachment(String name, Document attachment) {
        this.attachments.put(name, attachment);
    }

    public void addAttachments(Attachments attachments) {
        this.attachments.putAll(attachments.attachments);
    }
}