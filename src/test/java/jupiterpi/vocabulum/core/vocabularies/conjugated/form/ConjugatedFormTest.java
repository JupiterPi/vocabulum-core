package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockDatabaseSetup.class)
class ConjugatedFormTest {
    I18n i18n = Database.get().getI18ns().internal();

    @Nested
    @DisplayName("valid fromTokens()")
    class ValidFromTokens {

        @Test
        @DisplayName("with simple form")
        void simple() throws ParserException {
            ConjugatedForm form = ConjugatedForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.PERSON, "1", i18n),
                    new Token(Token.Type.NUMBER, "Sg", i18n)
            ));
            assertEquals(new ConjugatedForm(Person.FIRST, CNumber.SG), form);
        }

        @Test
        @DisplayName("with harder form")
        void harder() throws ParserException {
            ConjugatedForm form = ConjugatedForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.PERSON, "2", i18n),
                    new Token(Token.Type.NUMBER, "Pl", i18n)
            ));
            assertEquals(new ConjugatedForm(Person.SECOND, CNumber.PL), form);
        }

    }


    @Nested
    @DisplayName("invalid fromTokens()")
    class InvalidFromTokens {

        @Test
        @DisplayName("wrong types")
        void wrongTypes() {
            assertThrows(ParserException.class, () -> {
                ConjugatedForm form = ConjugatedForm.fromTokens(new TokenSequence(
                        new Token(Token.Type.CASUS, "Acc", i18n),
                        new Token(Token.Type.GENDER, "Masc", i18n)
                ));
            });
        }

        @Test
        @DisplayName("additional token")
        void additionalToken() {
            assertThrows(ParserException.class, () -> {
                ConjugatedForm form = ConjugatedForm.fromTokens(new TokenSequence(
                        new Token(Token.Type.PERSON, "2", i18n),
                        new Token(Token.Type.NUMBER, "Pl", i18n),
                        new Token(Token.Type.CASUS, "Acc", i18n)
                ));
            });
        }

    }


    @Nested
    @DisplayName("formToString()")
    class FormToString {

        @Test
        @DisplayName("userFriendly = false")
        void userFriendly_false() {
            ConjugatedForm form = new ConjugatedForm(Person.THIRD, CNumber.PL);
            assertEquals("3. Pl.", form.formToString(i18n, false));
        }

        @Test
        @DisplayName("userFriendly = true")
        void userFriendly_true() {
            ConjugatedForm form = new ConjugatedForm(Person.THIRD, CNumber.PL);
            assertEquals("3. Pers. Pl.", form.formToString(i18n, true));
        }

    }

    // no specific test for compareTo(): harmless bugs will be seen in production
}