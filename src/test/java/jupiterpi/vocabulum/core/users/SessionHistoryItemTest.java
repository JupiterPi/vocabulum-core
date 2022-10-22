package jupiterpi.vocabulum.core.users;

import jupiterpi.vocabulum.core.sessions.SessionConfiguration;
import jupiterpi.vocabulum.core.sessions.selection.PortionBasedVocabularySelection;
import jupiterpi.vocabulum.core.sessions.selection.VocabularySelection;
import jupiterpi.vocabulum.core.sessions.selection.VocabularySelections;
import jupiterpi.vocabulum.core.util.Attachments;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionHistoryItemTest {

    @Test
    void fromDocument() throws ReflectiveOperationException {
        SessionHistoryItem sessionHistoryItem = SessionHistoryItem.fromDocument(Document.parse("""
                {
                    "time": NumberLong(0),
                    "sessionConfiguration": {
                        "selection": "sol+villa",
                        "attachments": {}
                    },
                    "firstResult": 30,
                    "totalAttempts": 10
                }
                """), SessionConfiguration.class);
        assertAll(
            () -> assertEquals(new Date(0), sessionHistoryItem.getTime()),
            () -> assertEquals("sol+villa", VocabularySelections.getPortionBasedString(sessionHistoryItem.getSessionConfiguration().getSelection())),
            () -> assertEquals(0.3f, sessionHistoryItem.getFirstResult()),
            () -> assertEquals(10, sessionHistoryItem.getTotalAttempts())
        );
    }

    @Test
    void getDocument() {
        SessionHistoryItem sessionHistoryItem = new SessionHistoryItem(
                new Date(0),
                new SessionConfiguration(PortionBasedVocabularySelection.fromString("sol+villa")),
                0.3f, 10);
        Document e = Document.parse("""
                {
                    "time": NumberLong(0),
                    "sessionConfiguration": {
                        "selection": "sol+villa",
                        "attachments": {}
                    },
                    "firstResult": 30,
                    "totalAttempts": 10
                }
                """);
        assertEquals(e, sessionHistoryItem.getDocument());
    }

    static class MySessionConfiguration extends SessionConfiguration {
        private String myString;

        public MySessionConfiguration(VocabularySelection selection, String myString) {
            super(selection);
            this.myString = myString;
        }

        public MySessionConfiguration(Attachments attachments) {
            super(attachments);
            myString = attachments.consumeAttachment("myattachment").getString("mykey");
        }

        public String getMyString() {
            return myString;
        }

        @Override
        protected Attachments generateAttachments() {
            Attachments attachments = super.generateAttachments();
            attachments.addAttachment("myattachment", new Document("mykey", myString));
            return attachments;
        }
    }
    
    @Test
    @DisplayName("with extended SessionConfiguration")
    void withExtendedSessionConfiguration() throws ReflectiveOperationException {
        SessionHistoryItem sessionHistoryItem = SessionHistoryItem.fromDocument(Document.parse("""
                {
                    "time": NumberLong(0),
                    "sessionConfiguration": {
                      "selection": "sol+villa",
                      "attachments": {
                        "myattachment": {
                          "mykey": "mystring"
                        }
                      }
                    },
                    "firstResult": 30,
                    "totalAttempts": 10
                }
                """), MySessionConfiguration.class);
        MySessionConfiguration sessionConfiguration = (MySessionConfiguration) sessionHistoryItem.getSessionConfiguration();
        assertEquals("mystring", sessionConfiguration.getMyString());
    }

}