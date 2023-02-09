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
class VerbFormTest {
    @Nested
    @DisplayName("valid fromTokens()")
    class ValidFromTokens {

        @Test
        @DisplayName("with simple, short form")
        void simpleShort() throws ParserException {
            VerbForm form = VerbForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.PERSON, "1"),
                    new Token(Token.Type.NUMBER, "Sg")
            ));
            assertEquals(new VerbForm(new ConjugatedForm(Person.FIRST, CNumber.SG), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE), form);
        }

        @Test
        @DisplayName("with fully specified form")
        void fullySpecified() throws ParserException {
            VerbForm form = VerbForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.PERSON, "2"),
                    new Token(Token.Type.NUMBER, "Pl"),
                    new Token(Token.Type.MODE, "Konj"),
                    new Token(Token.Type.TENSE, "Imperf"),
                    new Token(Token.Type.VOICE, "Pass")
            ));
            assertEquals(new VerbForm(new ConjugatedForm(Person.SECOND, CNumber.PL), Mode.CONJUNCTIVE, Tense.IMPERFECT, Voice.PASSIVE), form);
        }

    }

    @Nested
    @DisplayName("getKind()")
    class GetKind {

        @Test
        @DisplayName("=> Kind.BASIC")
        void basic() {
            VerbForm form = new VerbForm(new ConjugatedForm(Person.SECOND, CNumber.PL), Mode.CONJUNCTIVE, Tense.IMPERFECT, Voice.PASSIVE);
            assertEquals(VerbForm.Kind.BASIC, form.getKind());
        }

        @Test
        @DisplayName("=> Kind.IMPERATIVE")
        void imperative() {
            VerbForm form = new VerbForm(CNumber.PL);
            assertEquals(VerbForm.Kind.IMPERATIVE, form.getKind());
        }

        @Test
        @DisplayName("=> Kind.INFINITIVE")
        void infinitive() {
            VerbForm form = new VerbForm(InfinitiveTense.PERFECT, Voice.PASSIVE);
            assertEquals(VerbForm.Kind.INFINITIVE, form.getKind());
        }

        @Test
        @DisplayName("=> Kind.NOUN_LIKE")
        void nounLike() {
            VerbForm form = new VerbForm(NounLikeForm.PPA, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC));
            assertEquals(VerbForm.Kind.NOUN_LIKE, form.getKind());
        }

    }

    @Nested
    @DisplayName("invalid fromTokens()")
    class InvalidFromTokens {

        @Test
        @DisplayName("wrong types")
        void wrongTypes() {
            assertThrows(ParserException.class, () -> {
                VerbForm.fromTokens(new TokenSequence(
                        new Token(Token.Type.CASUS, "Acc"),
                        new Token(Token.Type.GENDER, "Masc")
                ));
            });
        }

        @Test
        @DisplayName("additional token")
        void additionalToken() {
            assertThrows(ParserException.class, () -> {
                VerbForm.fromTokens(new TokenSequence(
                        new Token(Token.Type.PERSON, "2"),
                        new Token(Token.Type.NUMBER, "Pl"),
                        new Token(Token.Type.MODE, "Conj"),
                        new Token(Token.Type.TENSE, "Imperf"),
                        new Token(Token.Type.VOICE, "Pass"),
                        new Token(Token.Type.CASUS, "Masc")
                ));
            });
        }

    }

    @Nested
    @DisplayName("formToString()")
    class FormToString {

        @Nested
        @DisplayName("Kind.BASIC")
        class BasicKind {

            @Test
            @DisplayName("simple, short form")
            void simpleShort() {
                VerbForm form = new VerbForm(new ConjugatedForm(Person.FIRST, CNumber.SG), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE);
                assertEquals("1. Pers. Sg.", form.formToString());
            }

            @Test
            @DisplayName("fully specified form")
            void fullySpecified() {
                VerbForm form = new VerbForm(new ConjugatedForm(Person.SECOND, CNumber.PL), Mode.CONJUNCTIVE, Tense.IMPERFECT, Voice.PASSIVE);
                assertEquals("2. Pers. Pl. Konj. Imperf. Pass.", form.formToString());
            }

        }

        @Test
        @DisplayName("Kind.IMPERATIVE")
        void imperativeKind() {
            VerbForm form = new VerbForm(CNumber.PL);
            assertEquals("Imp. Pl.", form.formToString());
        }

        @Test
        @DisplayName("Kind.INFINITIVE")
        void infinitiveKind() {
            VerbForm form = new VerbForm(InfinitiveTense.PERFECT, Voice.PASSIVE);
            assertEquals("Inf. Perf. Pass.", form.formToString());
        }

        @Test
        @DisplayName("Kind.NOUN_LIKE")
        void nounLikeKind() {
            VerbForm form = new VerbForm(NounLikeForm.PPA, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC));
            assertEquals("PPA. Nom. Sg. m.", form.formToString());
        }

    }

    // no specific test for compareTo(): harmless bugs will be seen in production
}