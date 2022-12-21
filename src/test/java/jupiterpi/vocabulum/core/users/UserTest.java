package jupiterpi.vocabulum.core.users;

import jupiterpi.vocabulum.core.db.entities.MockEntityProvider;
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
                () -> assertEquals("", user.getDiscordUsername()),
                () -> assertFalse(user.isAdmin())
        );
    }

    @Test
    void readFromDocument() {
        User user = User.readEntity(MockEntityProvider.withSingleDocument(Document.parse("""
                        {
                          "name": "Adam01",
                          "email": "a.andrews@email.com",
                          "password": "ILoveVocabulum<3",
                          "isProUser": true,
                          "discordUsername": "Adam01#0000",
                          "isAdmin": true
                        }
                        """)), "");
        assertAll(
            () -> assertEquals("Adam01", user.getName()),
            () -> assertEquals("a.andrews@email.com", user.getEmail()),
            () -> assertEquals("ILoveVocabulum<3", user.getPassword()),
            () -> assertTrue(user.isProUser()),
            () -> assertEquals("Adam01#0000", user.getDiscordUsername()),
            () -> assertTrue(user.isAdmin())
        );
    }

    @Test
    void toDocument() {
        User user = User.createUser("Adam01", "a.andrews@email.com", "ILoveVocabulum<3");
        user.setIsProUser(true);
        user.setDiscordUsername("Adam01#0000");
        Document e = Document.parse("""
                {
                  "name": "Adam01",
                  "email": "a.andrews@email.com",
                  "password": "ILoveVocabulum<3",
                  "isProUser": true,
                  "discordUsername": "Adam01#0000",
                  "isAdmin": false
                }
                """);
        assertEquals(e, user.toDocument());
    }

}