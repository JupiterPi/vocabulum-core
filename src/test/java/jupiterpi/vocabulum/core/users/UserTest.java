package jupiterpi.vocabulum.core.users;

import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
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
                () -> assertFalse(user.fits("NAME", "password123")),
                () -> assertFalse(user.isProUser()),
                () -> assertEquals("", user.getDiscordUsername())
        );
    }

    @Test
    void readFromDocument() throws ReflectiveOperationException {
        User user = User.readFromDocument(Document.parse("""
                {
                  "name": "Adam01",
                  "email": "a.andrews@email.com",
                  "password": "ILoveVocabulum<3",
                  "isProUser": true,
                  "discordUsername": "Adam01#0000"
                }
                """));
        assertAll(
            () -> assertEquals("Adam01", user.getName()),
            () -> assertEquals("a.andrews@email.com", user.getEmail()),
            () -> assertEquals("ILoveVocabulum<3", user.getPassword()),
            () -> assertTrue(user.isProUser()),
            () -> assertEquals("Adam01#0000", user.getDiscordUsername())
        );
    }

    @Test
    void toDocument() {
        User user = new User("Adam01", "a.andrews@email.com", "ILoveVocabulum<3", true, "Adam01#0000");
        Document e = Document.parse("""
                {
                  "name": "Adam01",
                  "email": "a.andrews@email.com",
                  "password": "ILoveVocabulum<3",
                  "isProUser": true,
                  "discordUsername": "Adam01#0000"
                }
                """);
        assertEquals(e, user.toDocument());
    }

}