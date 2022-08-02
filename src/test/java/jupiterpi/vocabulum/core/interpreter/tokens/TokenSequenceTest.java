package jupiterpi.vocabulum.core.interpreter.tokens;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.i18n.I18n;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class TokenSequenceTest {
    I18n i18n = Database.get().getI18ns().internal();

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
    @DisplayName("getI18n()")
    class GetI18n {

        @Test
        @DisplayName("empty")
        void empty() {
            TokenSequence s = new TokenSequence();
            assertNull(s.getI18n());
        }

        @Test
        @DisplayName("simple")
        void simple() {
            TokenSequence s = new TokenSequence(
                    new Token(Token.Type.WORD, "amicus", i18n),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.GENDER, "m", i18n)
            );
            assertSame(i18n, s.getI18n());
        }

        @Test
        @DisplayName("first item without i18n")
        void firstItemWithoutI18n() {
            TokenSequence s = new TokenSequence(
                    new Token(Token.Type.WORD),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.GENDER, "m", i18n)
            );
            assertSame(i18n, s.getI18n());
        }

    }


    @Nested
    @DisplayName("operations")
    class Operations {

        TokenSequence s;

        @BeforeEach
        void init() {
            s = new TokenSequence(
                    new Token(Token.Type.WORD, "amicus", i18n),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.GENDER, "m", i18n)
            );
        }

        @Test
        @DisplayName("subsequence(fromIndex, toIndex)")
        void subsequence1() {
            TokenSequence e = new TokenSequence(new Token(Token.Type.COMMA, ",", i18n));
            assertEquals(e, s.subsequence(1, 2));
        }

        @Test
        @DisplayName("subsequence(formIndex)")
        void subsequence2() {
            TokenSequence e = new TokenSequence(
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.GENDER, "m", i18n)
            );
            assertEquals(e, s.subsequence(1));
        }

        @Nested
        @DisplayName("indexOf()")
        class IndexOf {

            @Test
            @DisplayName("with content")
            void withContent() {
                assertEquals(2, s.indexOf(new Token(Token.Type.GENDER, "m", i18n)));
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
                        new Token(Token.Type.WORD, "amicus", i18n),
                        new Token(Token.Type.COMMA, ",", i18n)
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