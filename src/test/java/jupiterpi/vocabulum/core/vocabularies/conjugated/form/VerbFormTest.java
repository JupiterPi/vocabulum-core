package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.i18n.I18n;
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
    I18n i18n = Database.get().getI18ns().internal();
    
    @Nested
    @DisplayName("valid fromTokens()")
    class ValidFromTokens {

        @Test
        @DisplayName("with simple, short form")
        void simpleShort() throws ParserException {
            VerbForm form = VerbForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.PERSON, "1", i18n),
                    new Token(Token.Type.NUMBER, "Sg", i18n)
            ));
            assertEquals(new VerbForm(new ConjugatedForm(Person.FIRST, CNumber.SG), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE), form);
        }

        @Test
        @DisplayName("with fully specified form")
        void fullySpecified() throws ParserException {
            VerbForm form = VerbForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.PERSON, "2", i18n),
                    new Token(Token.Type.NUMBER, "Pl", i18n),
                    new Token(Token.Type.MODE, "Conj", i18n),
                    new Token(Token.Type.TENSE, "Imperf", i18n),
                    new Token(Token.Type.VOICE, "Pass", i18n)
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
        @DisplayName("=> Kind.INFINITIVE")
        void infinitive() {
            VerbForm form = new VerbForm(InfinitiveTense.PERFECT);
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
                        new Token(Token.Type.CASUS, "Acc", i18n),
                        new Token(Token.Type.GENDER, "Masc", i18n)
                ));
            });
        }

        @Test
        @DisplayName("additional token")
        void additionalToken() {
            assertThrows(ParserException.class, () -> {
                VerbForm.fromTokens(new TokenSequence(
                        new Token(Token.Type.PERSON, "2", i18n),
                        new Token(Token.Type.NUMBER, "Pl", i18n),
                        new Token(Token.Type.MODE, "Conj", i18n),
                        new Token(Token.Type.TENSE, "Imperf", i18n),
                        new Token(Token.Type.VOICE, "Pass", i18n),
                        new Token(Token.Type.CASUS, "Masc", i18n)
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
                assertEquals("1. Pers. Sg.", form.formToString(i18n));
            }

            @Test
            @DisplayName("fully specified form")
            void fullySpecified() {
                VerbForm form = new VerbForm(new ConjugatedForm(Person.SECOND, CNumber.PL), Mode.CONJUNCTIVE, Tense.IMPERFECT, Voice.PASSIVE);
                assertEquals("2. Pers. Pl. Conj. Imperf. Pass.", form.formToString(i18n));
            }

        }

        @Test
        @DisplayName("Kind.INFINITIVE")
        void infinitiveKind() {
            VerbForm form = new VerbForm(InfinitiveTense.PERFECT);
            assertEquals("Inf. Perf.", form.formToString(i18n));
        }

        @Test
        @DisplayName("Kind.NOUN_LIKE")
        void nounLikeKind() {
            VerbForm form = new VerbForm(NounLikeForm.PPA, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC));
            assertEquals("PPA. Nom. Sg. m.", form.formToString(i18n));
        }

    }
}