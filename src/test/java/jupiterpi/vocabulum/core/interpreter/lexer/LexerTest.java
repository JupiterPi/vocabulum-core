package jupiterpi.vocabulum.core.interpreter.lexer;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class LexerTest {
    I18n i18n = Database.get().getI18ns().internal();

    @Nested
    @DisplayName("valid")
    class Valid {

        @Nested
        @DisplayName("single tokens")
        class SingleTokens {

            @ParameterizedTest
            @MethodSource("wordAndComma_args")
            @DisplayName("word, comma")
            void wordAndComma(String content, Token.Type expectedType) throws LexerException {
                Lexer lexer = new Lexer(content, i18n);
                TokenSequence s = lexer.getTokens();
                assertEquals(1, s.size());
                assertEquals(new Token(expectedType, content, i18n), s.get(0));
            }
            static Stream<Arguments> wordAndComma_args() {
                return Stream.of(
                        Arguments.of("word", Token.Type.WORD),
                        Arguments.of(",", Token.Type.COMMA)
                );
            }

            @ParameterizedTest
            @MethodSource("abbreviations_args")
            @DisplayName("abbreviations")
            void abbreviations(String content, Token.Type expectedType) throws LexerException {
                Lexer lexer = new Lexer(content + ".", i18n);
                TokenSequence s = lexer.getTokens();
                assertEquals(1, s.size());
                assertEquals(new Token(expectedType, content, i18n), s.get(0));
            }
            static Stream<Arguments> abbreviations_args() {
                return Stream.of(
                        Arguments.of("Nom", Token.Type.CASUS),
                        Arguments.of("Sg", Token.Type.NUMBER),
                        Arguments.of("m", Token.Type.GENDER),
                        Arguments.of("Pos", Token.Type.COMPARATIVE_FORM),
                        Arguments.of("Adv", Token.Type.ADV_FLAG),
                        Arguments.of("1", Token.Type.PERSON),
                        Arguments.of("Ind", Token.Type.MODE),
                        Arguments.of("Pres", Token.Type.TENSE),
                        Arguments.of("Act", Token.Type.VOICE)
                );
            }

        }

        @Test
        @DisplayName("Person Decorator should be ignored")
        void personDecoratorShouldBeIgnored() throws LexerException {
            assertEquals(new TokenSequence(), new Lexer("Pers.", i18n).getTokens());
        }

        @Test
        @DisplayName("sequence of tokens")
        void sequenceOfTokens() throws LexerException {
            String expr = "word , Nom. Sg. m. Pos. Adv. 1. Ind. word Pres. Act.";
            //                  ^ standalone comma               ^ word between abbreviations
            TokenSequence e = new TokenSequence(
                    new Token(Token.Type.WORD, "word", i18n),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.CASUS, "Nom", i18n),
                    new Token(Token.Type.NUMBER, "Sg", i18n),
                    new Token(Token.Type.GENDER, "m", i18n),
                    new Token(Token.Type.COMPARATIVE_FORM, "Pos", i18n),
                    new Token(Token.Type.ADV_FLAG, "Adv", i18n),
                    new Token(Token.Type.PERSON, "1", i18n),
                    new Token(Token.Type.MODE, "Ind", i18n),
                    new Token(Token.Type.WORD, "word", i18n),
                    new Token(Token.Type.TENSE, "Pres", i18n),
                    new Token(Token.Type.VOICE, "Act", i18n)
            );
            assertEquals(e, new Lexer(expr, i18n).getTokens());
        }

        @Test
        @DisplayName("commas should be possible after word")
        void commasShouldBePossibleAfterWord() throws LexerException {
            String expr = "word, word , word";
            TokenSequence e = new TokenSequence(
                    new Token(Token.Type.WORD, "word", i18n),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.WORD, "word", i18n),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.WORD, "word", i18n)
            );
            assertEquals(e, new Lexer(expr, i18n).getTokens());
        }

        @Test
        @DisplayName("InfinitiveTenses should work correctly")
        void infinitiveTensesShouldWorkCorrectly() throws LexerException {
            String expr = "Perf. FutI. Fut.";
            TokenSequence e = new TokenSequence(
                    new Token(Token.Type.TENSE, "Perf", i18n),
                    new Token(Token.Type.TENSE, "FutI", i18n),
                    new Token(Token.Type.TENSE, "Fut", i18n)
            );
            assertEquals(e, new Lexer(expr, i18n).getTokens());
        }

    }


    @Nested
    @DisplayName("invalid")
    class Invalid {

        @Test
        @DisplayName("invalid abbreviation")
        void invalidAbbreviation() {
            assertThrows(LexerException.class, () -> {
                new Lexer("Invalid.", i18n);
            });
        }

        @Test
        @DisplayName("invalid character")
        void invalidCharacter() {
            assertThrows(LexerException.class, () -> {
                new Lexer("ó", i18n);
            });
        }

    }

}