package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockDatabaseSetup.class)
class VerbFormParserTest {
    @Test
    @DisplayName("imperative")
    void imperative() throws ParserException {
        TokenSequence tokens = new TokenSequence(
                new Token(Token.Type.IMPERATIVE_FLAG, "Imp"),
                new Token(Token.Type.NUMBER, "Pl")
        );
        VerbForm verbForm = new VerbFormParser(tokens).getVerbForm();
        VerbForm e = new VerbForm(CNumber.PL);
        assertEquals(e, verbForm);
    }

    @Test
    @DisplayName("infinitive")
    void infinitive() throws ParserException {
        TokenSequence tokens = new TokenSequence(
                new Token(Token.Type.INFINITIVE_FLAG, "Inf"),
                new Token(Token.Type.TENSE, "Perf"),
                new Token(Token.Type.VOICE, "Pass")
        );
        VerbForm verbForm = new VerbFormParser(tokens).getVerbForm();
        VerbForm e = new VerbForm(InfinitiveTense.PERFECT, Voice.PASSIVE);
        assertEquals(e, verbForm);
    }

    @Test
    @DisplayName("noun-like form")
    void nounLikeForm() throws ParserException {
        TokenSequence tokens = new TokenSequence(
                new Token(Token.Type.NOUN_LIKE_FORM, "PPA"),
                new Token(Token.Type.CASUS, "Akk"),
                new Token(Token.Type.NUMBER, "Sg")
        );
        VerbForm verbForm = new VerbFormParser(tokens).getVerbForm();
        VerbForm e = new VerbForm(NounLikeForm.PPA, new DeclinedForm(Casus.ACC, NNumber.SG, Gender.MASC));
        assertEquals(e, verbForm);
    }

    @Nested
    @DisplayName("basic")
    class Basic {

        @Test
        @DisplayName("fully specified")
        void fullySpecified() throws ParserException {
            TokenSequence tokens = new TokenSequence(
                    new Token(Token.Type.PERSON, "2"),
                    new Token(Token.Type.NUMBER, "Pl"),
                    new Token(Token.Type.MODE, "Konj"),
                    new Token(Token.Type.TENSE, "Perf"),
                    new Token(Token.Type.VOICE, "Pass")
            );
            VerbForm verbForm = new VerbFormParser(tokens).getVerbForm();
            VerbForm e = new VerbForm(new ConjugatedForm(Person.SECOND, CNumber.PL), Mode.CONJUNCTIVE, Tense.PERFECT, Voice.PASSIVE);
            assertEquals(e, verbForm);
        }

        @Test
        @DisplayName("minimal")
        void minimal() throws ParserException {
            TokenSequence tokens = new TokenSequence(
                    new Token(Token.Type.PERSON, "2"),
                    new Token(Token.Type.NUMBER, "Pl")
            );
            VerbForm verbForm = new VerbFormParser(tokens).getVerbForm();
            VerbForm e = new VerbForm(new ConjugatedForm(Person.SECOND, CNumber.PL), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE);
            assertEquals(e, verbForm);
        }

    }

    @Nested
    @DisplayName("invalid")
    class Invalid {

        @Test
        @DisplayName("wrong size")
        void wrongSize() {
            assertThrows(ParserException.class, () -> {
                new VerbFormParser(TokenSequence.fromTypes(Token.Type.CASUS));
            });
        }

        @Test
        @DisplayName("wrong types")
        void wrongTypes() {
            assertThrows(ParserException.class, () -> {
                new VerbFormParser(TokenSequence.fromTypes(Token.Type.INFINITIVE_FLAG, Token.Type.CASUS));
            });
        }

        @Test
        @DisplayName("wrong conjugated form")
        void wrongConjugatedForm() {
            assertThrows(ParserException.class, () -> {
                new VerbFormParser(TokenSequence.fromTypes(Token.Type.CASUS, Token.Type.NUMBER, Token.Type.GENDER));
            });
        }

    }
}
