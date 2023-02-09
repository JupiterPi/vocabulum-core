package jupiterpi.vocabulum.core.interpreter.tokens;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockDatabaseSetup.class)
class TokenTest {
    @Nested
    @DisplayName("valid fits()")
    class ValidFits {

        @Test
        @DisplayName("target with content")
        void targetWithContent() {
            Token token = new Token(Token.Type.CASUS, "m");
            Token target = new Token(Token.Type.CASUS, "m");
            assertTrue(token.fits(target));
        }

        @Test
        @DisplayName("target without content")
        void targetWithoutContent() {
            Token token = new Token(Token.Type.CASUS, "m");
            Token target = new Token(Token.Type.CASUS);
            assertTrue(token.fits(target));
        }

        @Test
        @DisplayName("both without content")
        void bothWithoutContent() {
            Token token = new Token(Token.Type.CASUS);
            Token target = new Token(Token.Type.CASUS);
            assertTrue(token.fits(target));
        }

    }


    @Nested
    @DisplayName("invalid fits()")
    class InvalidFits {

        @Test
        @DisplayName("wrong type")
        void wrongType() {
            Token token = new Token(Token.Type.CASUS, "m");
            Token target = new Token(Token.Type.PERSON);
            assertFalse(token.fits(target));
        }

        @Test
        @DisplayName("wrong content")
        void wrongContent() {
            Token token = new Token(Token.Type.CASUS, "m");
            Token target = new Token(Token.Type.CASUS, "f");
            assertFalse(token.fits(target));
        }

    }
}