package jupiterpi.vocabulum.core.users;

import jupiterpi.vocabulum.core.sessions.SessionConfiguration;
import jupiterpi.vocabulum.core.sessions.selection.PortionBasedVocabularySelection;
import jupiterpi.vocabulum.core.sessions.selection.VocabularySelections;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionHistoryItemTest {

    @Test
    void fromDocument() throws ReflectiveOperationException {
        SessionHistoryItem<SessionConfiguration> sessionHistoryItem = SessionHistoryItem.fromDocument(Document.parse("""
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
        SessionHistoryItem<SessionConfiguration> sessionHistoryItem = new SessionHistoryItem<>(
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
        Document document = sessionHistoryItem.getDocument();
        assertEquals(e, sessionHistoryItem.getDocument());
    }

}