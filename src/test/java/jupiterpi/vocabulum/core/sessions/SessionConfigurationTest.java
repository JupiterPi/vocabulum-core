package jupiterpi.vocabulum.core.sessions;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.sessions.selection.PortionBasedVocabularySelection;
import jupiterpi.vocabulum.core.sessions.selection.VocabularySelection;
import jupiterpi.vocabulum.core.sessions.selection.VocabularySelections;
import jupiterpi.vocabulum.core.util.Attachments;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockDatabaseSetup.class)
class SessionConfigurationTest {
    @Test
    void fromDocument() throws ReflectiveOperationException {
        SessionConfiguration sessionConfiguration = SessionConfiguration.fromDocument(Document.parse("""
                {
                    "selection": "sol+villa",
                    "attachments": {}
                }
                """), SessionConfiguration.class);
        assertEquals("sol+villa", VocabularySelections.getPortionBasedString(sessionConfiguration.getSelection()));
    }

    @Test
    void getDocument() {
        SessionConfiguration sessionConfiguration = new SessionConfiguration(PortionBasedVocabularySelection.fromString("sol+villa"));
        Document document = sessionConfiguration.getDocument();
        assertAll(
            () -> assertEquals("sol+villa", document.getString("selection")),
            () -> assertEquals(new Document(), document.get("attachments"))
        );
    }
    
    @Nested
    @DisplayName("with attachments")
    class WithAttachments {
        
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
        @DisplayName("read")
        void read() throws ReflectiveOperationException {
            MySessionConfiguration sessionConfiguration = SessionConfiguration.fromDocument(Document.parse("""
                    {
                      "selection": "sol+villa",
                      "attachments": {
                        "myattachment": {
                          "mykey": "mystring"
                        }
                      }
                    }
                    """), MySessionConfiguration.class);
            assertAll(
                    () -> assertEquals("sol+villa", VocabularySelections.getPortionBasedString(sessionConfiguration.getSelection())),
                    () -> assertEquals("mystring", sessionConfiguration.getMyString())
            );
        }

        @Test
        @DisplayName("generate document")
        void generateDocument() {
            MySessionConfiguration sessionConfiguration = new MySessionConfiguration(PortionBasedVocabularySelection.fromString("sol+villa"), "mystring");
            Document e = Document.parse("""
                    {
                      "selection": "sol+villa",
                      "attachments": {
                        "myattachment": {
                          "mykey": "mystring"
                        }
                      }
                    }
                    """);
            assertEquals(e, sessionConfiguration.getDocument());
        }

        @Test
        @DisplayName("remaining attachments")
        void remainingAttachments() throws ReflectiveOperationException {
            Document e = Document.parse("""
                    {
                      "selection": "sol+villa",
                      "attachments": {
                        "myattachment": {
                          "mykey": "mystring"
                        },
                        "unknown_attachment": {
                          "key": "value"
                        }
                      }
                    }
                    """);
            MySessionConfiguration sessionConfiguration = SessionConfiguration.fromDocument(e, MySessionConfiguration.class);
            assertEquals(e, sessionConfiguration.getDocument());
        }
        
    }
}