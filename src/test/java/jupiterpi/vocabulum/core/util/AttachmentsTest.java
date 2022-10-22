package jupiterpi.vocabulum.core.util;

import org.bson.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttachmentsTest {
    @Test
    void fromDocument() {
        Attachments attachments = Attachments.fromDocument(Document.parse("""
                {
                  "a1": {
                    "key1": "value1",
                    "key2": "value2"
                  },
                  "a2": {
                    "key": "value"
                  }
                }
                """));
        assertAll(
            () -> assertEquals("value1", attachments.getAttachment("a1").getString("key1")),
            () -> assertEquals("value2", attachments.getAttachment("a1").getString("key2")),
            () -> assertEquals("value", attachments.getAttachment("a2").getString("key"))
        );
    }

    @Test
    void getDocument() {
        Attachments attachments = Attachments.empty();
        attachments.addAttachment("a1", new Document("key", "value"));
        attachments.addAttachment("a2", new Document("key", "value"));
        assertEquals(Document.parse("""
                {
                  "a1": {
                    "key": "value"
                  },
                  "a2": {
                    "key": "value"
                  }
                }
                """), attachments.getDocument());
    }

    @Test
    void consumeAttachment() {
        Attachments attachments = Attachments.empty();
        attachments.addAttachment("a1", new Document("key", "value"));
        Document consumedAttachment = attachments.consumeAttachment("a1");
        assertEquals(new Document("key", "value"), consumedAttachment);
        assertNull(attachments.getAttachment("a1"));
    }
}