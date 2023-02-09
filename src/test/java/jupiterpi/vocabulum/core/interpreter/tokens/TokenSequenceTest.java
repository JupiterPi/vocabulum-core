package jupiterpi.vocabulum.core.interpreter.tokens;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class TokenSequenceTest {
    @Nested
    @DisplayName("creation")
    class Creation {

        @Test
        void fromTypes() {
            Token.Type[] types = {Token.Type.WORD, Token.Type.COMMA, Token.Type.GENDER};
            TokenSequence s = TokenSequence.fromTypes(types);
            for (int i = 0; i < s.size(); i++) {
                Token token = s.get(i);
                assertEquals(types[i], token.getType());
                assertNull(token.getContent());
            }
        }

    }


    @Nested
    @DisplayName("operations")
    class Operations {

        TokenSequence s;

        @BeforeEach
        void init() {
            s = new TokenSequence(
                    new Token(Token.Type.WORD, "amicus"),
                    new Token(Token.Type.COMMA, ","),
                    new Token(Token.Type.GENDER, "m")
            );
        }

        @Test
        @DisplayName("subsequence(fromIndex, toIndex)")
        void subsequence1() {
            TokenSequence e = new TokenSequence(new Token(Token.Type.COMMA, ","));
            assertEquals(e, s.subsequence(1, 2));
        }

        @Test
        @DisplayName("subsequence(formIndex)")
        void subsequence2() {
            TokenSequence e = new TokenSequence(
                    new Token(Token.Type.COMMA, ","),
                    new Token(Token.Type.GENDER, "m")
            );
            assertEquals(e, s.subsequence(1));
        }

        @Nested
        @DisplayName("indexOf()")
        class IndexOf {

            @Test
            @DisplayName("with content")
            void withContent() {
                assertEquals(2, s.indexOf(new Token(Token.Type.GENDER, "m")));
            }

            @Test
            @DisplayName("without content")
            void withoutContent() {
                assertEquals(2, s.indexOf(new Token(Token.Type.GENDER)));
            }

            @Test
            @DisplayName("doesn't contain")
            void doesntContain() {
                assertEquals(-1, s.indexOf(new Token(Token.Type.CASUS)));
            }

        }

        @Nested
        @DisplayName("fitsStartsWith()")
        class FitsStartsWith {

            @Test
            @DisplayName("=> true, with content")
            void withContent() {
                TokenSequence f = new TokenSequence(
                        new Token(Token.Type.WORD, "amicus"),
                        new Token(Token.Type.COMMA, ",")
                );
                assertTrue(s.fitsStartsWith(f));
            }

            @Test
            @DisplayName("=> true, without content")
            void withoutContent() {
                TokenSequence f = new TokenSequence(
                        new Token(Token.Type.WORD),
                        new Token(Token.Type.COMMA)
                );
                assertTrue(s.fitsStartsWith(f));
            }

            @Test
            @DisplayName("=> false")
            void shouldFalse() {
                TokenSequence f = new TokenSequence(
                        new Token(Token.Type.CASUS)
                );
                assertFalse(s.fitsStartsWith(f));
            }

        }

    }
}