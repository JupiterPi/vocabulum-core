package jupiterpi.vocabulum.core.users;

import jupiterpi.vocabulum.core.sessions.SessionConfiguration;
import jupiterpi.vocabulum.core.util.Attachments;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("fits")
    void fits() {
        User user = User.createUser("name", "email@example.com", "password123");
        assertAll(
                () -> assertTrue(user.passwordFits("password123")),
                () -> assertFalse(user.passwordFits("PASSWORD123")),
                () -> assertTrue(user.fits("name", "password123")),
                () -> assertFalse(user.fits("name", "PASSWORD123")),
                () -> assertFalse(user.fits("NAME", "password123"))
        );
    }

    @Test
    void readFromDocument() throws ReflectiveOperationException {
        User user = User.readFromDocument(Document.parse("""
                {
                  "name": "Adam01",
                  "email": "a.andrews@email.com",
                  "password": "ILoveVocabulum<3",
                  "sessionHistory": [
                    {
                      "time": NumberLong(0),
                      "sessionConfiguration": {
                          "selection": "sol+villa",
                          "attachments": {}
                      },
                      "firstResult": 30,
                      "totalAttempts": 10
                    }
                  ],
                  "attachments": {}
                }
                """), User.class, SessionConfiguration.class);
        assertAll(
            () -> assertEquals("Adam01", user.getName()),
            () -> assertEquals("a.andrews@email.com", user.getEmail()),
            () -> assertEquals("ILoveVocabulum<3", user.getPassword()),
            () -> assertEquals(1, user.getSessionHistory().size())
        );
    }

    @Test
    void toDocument() {
        User user = User.createUser("Adam01", "a.andrews@email.com", "ILoveVocabulum<3");
        Document e = Document.parse("""
                {
                  "name": "Adam01",
                  "email": "a.andrews@email.com",
                  "password": "ILoveVocabulum<3",
                  "sessionHistory": [],
                  "attachments": {}
                }
                """);
        assertEquals(e, user.toDocument());
    }

    @Nested
    @DisplayName("with attachments")
    class WithAttachments {

        static class MyUser extends User {
            private String myString;

            public MyUser(String username, String email, String password, String myString) {
                super(username, email, password);
                this.myString = myString;
            }

            public MyUser(Attachments attachments) {
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
            MyUser myUser = User.readFromDocument(Document.parse("""
                    {
                      "name": "Adam01",
                      "email": "a.andrews@email.com",
                      "password": "ILoveVocabulum<3",
                      "sessionHistory": [
                        {
                          "time": NumberLong(0),
                          "sessionConfiguration": {
                              "selection": "sol+villa",
                              "attachments": {}
                          },
                          "firstResult": 30,
                          "totalAttempts": 10
                        }
                      ],
                      "attachments": {
                        "myattachment": {
                          "mykey": "mystring"
                        }
                      }
                    }
                    """), MyUser.class, SessionConfiguration.class);
            assertAll(
                    () -> assertEquals("Adam01", myUser.getName()),
                    () -> assertEquals("a.andrews@email.com", myUser.getEmail()),
                    () -> assertEquals("ILoveVocabulum<3", myUser.getPassword()),
                    () -> assertEquals(1, myUser.getSessionHistory().size()),
                    () -> assertEquals("mystring", myUser.getMyString())
            );
        }

        @Test
        @DisplayName("generate document")
        void generateDocument() {
            MyUser user = new MyUser("Adam01", "a.andrews@email.com", "ILoveVocabulum<3", "mystring");
            Document e = Document.parse("""
                    {
                      "name": "Adam01",
                      "email": "a.andrews@email.com",
                      "password": "ILoveVocabulum<3",
                      "sessionHistory": [],
                      "attachments": {
                        "myattachment": {
                          "mykey": "mystring"
                        }
                      }
                    }
                    """);
            assertEquals(e, user.toDocument());
        }

        @Test
        @DisplayName("remaining attachments")
        void remainingAttachments() throws ReflectiveOperationException {
            Document e = Document.parse("""
                    {
                      "name": "Adam01",
                      "email": "a.andrews@email.com",
                      "password": "ILoveVocabulum<3",
                      "sessionHistory": [],
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
            MyUser user = User.readFromDocument(e, MyUser.class, SessionConfiguration.class);
            assertEquals(e, user.toDocument());
        }

    }

}