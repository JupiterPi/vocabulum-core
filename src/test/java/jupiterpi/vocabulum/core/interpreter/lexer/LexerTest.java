package jupiterpi.vocabulum.core.interpreter.lexer;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockDatabaseSetup.class)
class LexerTest {
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
                Lexer lexer = new Lexer(content);
                TokenSequence s = lexer.getTokens();
                assertEquals(1, s.size());
                assertEquals(new Token(expectedType, content), s.get(0));
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
                Lexer lexer = new Lexer(content + ".");
                TokenSequence s = lexer.getTokens();
                assertEquals(1, s.size());
                assertEquals(new Token(expectedType, content), s.get(0));
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
                        Arguments.of("Präs", Token.Type.TENSE),
                        Arguments.of("Akt", Token.Type.VOICE),
                        Arguments.of("Imp", Token.Type.IMPERATIVE_FLAG),
                        Arguments.of("Inf", Token.Type.INFINITIVE_FLAG)
                );
            }

        }

        @Test
        @DisplayName("Person Decorator should be ignored")
        void personDecoratorShouldBeIgnored() throws LexerException {
            assertEquals(new TokenSequence(), new Lexer("Pers.").getTokens());
        }

        @Test
        @DisplayName("sequence of tokens")
        void sequenceOfTokens() throws LexerException {
            String expr = "word , Nom. Sg. m. Pos. Adv. 1. Ind. word Präs. Akt. Imp. Inf.";
            //                  ^ standalone comma               ^ word between abbreviations
            TokenSequence e = new TokenSequence(
                    new Token(Token.Type.WORD, "word"),
                    new Token(Token.Type.COMMA, ","),
                    new Token(Token.Type.CASUS, "Nom"),
                    new Token(Token.Type.NUMBER, "Sg"),
                    new Token(Token.Type.GENDER, "m"),
                    new Token(Token.Type.COMPARATIVE_FORM, "Pos"),
                    new Token(Token.Type.ADV_FLAG, "Adv"),
                    new Token(Token.Type.PERSON, "1"),
                    new Token(Token.Type.MODE, "Ind"),
                    new Token(Token.Type.WORD, "word"),
                    new Token(Token.Type.TENSE, "Präs"),
                    new Token(Token.Type.VOICE, "Akt"),
                    new Token(Token.Type.IMPERATIVE_FLAG, "Imp"),
                    new Token(Token.Type.INFINITIVE_FLAG, "Inf")
            );
            assertEquals(e, new Lexer(expr).getTokens());
        }

        @Test
        @DisplayName("commas should be possible after word")
        void commasShouldBePossibleAfterWord() throws LexerException {
            String expr = "word, word , word";
            TokenSequence e = new TokenSequence(
                    new Token(Token.Type.WORD, "word"),
                    new Token(Token.Type.COMMA, ","),
                    new Token(Token.Type.WORD, "word"),
                    new Token(Token.Type.COMMA, ","),
                    new Token(Token.Type.WORD, "word")
            );
            assertEquals(e, new Lexer(expr).getTokens());
        }

        @Test
        @DisplayName("InfinitiveTenses should work correctly")
        void infinitiveTensesShouldWorkCorrectly() throws LexerException {
            String expr = "Perf. FutI. Fut.";
            TokenSequence e = new TokenSequence(
                    new Token(Token.Type.TENSE, "Perf"),
                    new Token(Token.Type.TENSE, "FutI"),
                    new Token(Token.Type.TENSE, "Fut")
            );
            assertEquals(e, new Lexer(expr).getTokens());
        }

    }


    @Nested
    @DisplayName("invalid")
    class Invalid {

        @Test
        @DisplayName("invalid abbreviation")
        void invalidAbbreviation() {
            assertThrows(LexerException.class, () -> {
                new Lexer("Invalid.");
            });
        }

        @Test
        @DisplayName("invalid character")
        void invalidCharacter() {
            assertThrows(LexerException.class, () -> {
                new Lexer("ó");
            });
        }

    }

}