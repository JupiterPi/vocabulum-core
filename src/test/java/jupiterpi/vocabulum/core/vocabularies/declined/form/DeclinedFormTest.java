package jupiterpi.vocabulum.core.vocabularies.declined.form;

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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockDatabaseSetup.class)
class DeclinedFormTest {
    I18n i18n = Database.get().getI18ns().internal();

    @Nested
    @DisplayName("valid fromTokens()")
    class ValidFromTokens {

        @Test
        @DisplayName("with simple form")
        void simple() throws ParserException {
            DeclinedForm form = DeclinedForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.CASUS, "Nom", i18n),
                    new Token(Token.Type.NUMBER, "Sg", i18n),
                    new Token(Token.Type.GENDER, "m", i18n)
            ));
            assertEquals(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), form);
        }

        @Test
        @DisplayName("with harder form")
        void harder() throws ParserException {
            DeclinedForm form = DeclinedForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.CASUS, "Gen", i18n),
                    new Token(Token.Type.NUMBER, "Pl", i18n),
                    new Token(Token.Type.GENDER, "f", i18n)
            ));
            assertEquals(new DeclinedForm(Casus.GEN, NNumber.PL, Gender.FEM), form);
        }

    }


    @Nested
    @DisplayName("invalid fromTokens()")
    class InvalidFromTokens {

        @Test
        @DisplayName("wrong types")
        void wrongTypes() {
            assertThrows(ParserException.class, () -> {
                DeclinedForm form = DeclinedForm.fromTokens(new TokenSequence(
                        new Token(Token.Type.PERSON, "2", i18n),
                        new Token(Token.Type.VOICE, "Pass", i18n)
                ));
            });
        }

        @Test
        @DisplayName("additional token")
        void additionalToken() {
            assertThrows(ParserException.class, () -> {
                DeclinedForm form = DeclinedForm.fromTokens(new TokenSequence(
                        new Token(Token.Type.CASUS, "Nom", i18n),
                        new Token(Token.Type.NUMBER, "Sg", i18n),
                        new Token(Token.Type.GENDER, "m", i18n),
                        new Token(Token.Type.VOICE, "Pass", i18n)
                ));
            });
        }

    }


    @Test
    void normalizeGender() {
        DeclinedForm form = new DeclinedForm(Casus.DAT, NNumber.PL);
        form.normalizeGender(Gender.FEM);
        assertEquals(new DeclinedForm(Casus.DAT, NNumber.PL, Gender.FEM), form);
    }


    @Nested
    @DisplayName("fits()")
    class Fits {

        @Test
        @DisplayName("with non-gendered target")
        void withNonGenderedTarget() {
            DeclinedForm form = new DeclinedForm(Casus.DAT, NNumber.PL, Gender.FEM);
            DeclinedForm target = new DeclinedForm(Casus.DAT, NNumber.PL);
            assertTrue(form.fits(target));
        }

        @Test
        @DisplayName("with same-gender target")
        void withSameGenderTarget() {
            DeclinedForm form = new DeclinedForm(Casus.DAT, NNumber.PL, Gender.FEM);
            DeclinedForm target = new DeclinedForm(Casus.DAT, NNumber.PL, Gender.FEM);
            assertTrue(form.fits(target));
        }

        @Test
        @DisplayName("with different-gender target")
        void withDifferentGenderTarget() {
            DeclinedForm form = new DeclinedForm(Casus.DAT, NNumber.PL, Gender.FEM);
            DeclinedForm target = new DeclinedForm(Casus.DAT, NNumber.PL, Gender.MASC);
            assertFalse(form.fits(target));
        }

    }


    @Nested
    @DisplayName("formToString()")
    class FormToString {

        @Test
        @DisplayName("without gender")
        void formToString() {
            DeclinedForm form = new DeclinedForm(Casus.DAT, NNumber.PL);
            assertEquals("Dat. Pl.", form.formToString(i18n));
        }

        @Test
        @DisplayName("with gender")
        void withGender() {
            DeclinedForm form = new DeclinedForm(Casus.DAT, NNumber.PL, Gender.FEM);
            assertEquals("Dat. Pl. f.", form.formToString(i18n));
        }

    }
}