package jupiterpi.vocabulum.core.vocabularies.declined.form;

import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
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
    @Nested
    @DisplayName("valid fromTokens()")
    class ValidFromTokens {

        @Test
        @DisplayName("with simple form")
        void simple() throws ParserException {
            DeclinedForm form = DeclinedForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.CASUS, "Nom"),
                    new Token(Token.Type.NUMBER, "Sg"),
                    new Token(Token.Type.GENDER, "m")
            ));
            assertEquals(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), form);
        }

        @Test
        @DisplayName("with harder form")
        void harder() throws ParserException {
            DeclinedForm form = DeclinedForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.CASUS, "Gen"),
                    new Token(Token.Type.NUMBER, "Pl"),
                    new Token(Token.Type.GENDER, "f")
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
                        new Token(Token.Type.PERSON, "2"),
                        new Token(Token.Type.VOICE, "Pass")
                ));
            });
        }

        @Test
        @DisplayName("additional token")
        void additionalToken() {
            assertThrows(ParserException.class, () -> {
                DeclinedForm form = DeclinedForm.fromTokens(new TokenSequence(
                        new Token(Token.Type.CASUS, "Nom"),
                        new Token(Token.Type.NUMBER, "Sg"),
                        new Token(Token.Type.GENDER, "m"),
                        new Token(Token.Type.VOICE, "Pass")
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
            assertEquals("Dat. Pl.", form.formToString());
        }

        @Test
        @DisplayName("with gender")
        void withGender() {
            DeclinedForm form = new DeclinedForm(Casus.DAT, NNumber.PL, Gender.FEM);
            assertEquals("Dat. Pl. f.", form.formToString());
        }

    }

    @Nested
    @DisplayName("compare")
    class Compare {

        @Test
        @DisplayName("only positive")
        void onlyPositive() {
            DeclinedForm f1 = new DeclinedForm(Casus.GEN, NNumber.SG, Gender.MASC);
            DeclinedForm f2 = new DeclinedForm(Casus.ABL, NNumber.PL, Gender.FEM);
            assertTrue(f2.compareTo(f1) > 0);
        }

        @Test
        @DisplayName("decided by lower level")
        void decidedByLowerLevel() {
            DeclinedForm f1 = new DeclinedForm(Casus.ABL, NNumber.PL, Gender.FEM);
            DeclinedForm f2 = new DeclinedForm(Casus.ABL, NNumber.PL, Gender.NEUT);
            assertTrue(f2.compareTo(f1) > 0);
        }

        @Test
        @DisplayName("with negatives")
        void withNegatives() {
            DeclinedForm f1 = new DeclinedForm(Casus.ABL, NNumber.SG, Gender.MASC);
            DeclinedForm f2 = new DeclinedForm(Casus.GEN, NNumber.PL, Gender.FEM);
            assertTrue(f2.compareTo(f1) < 0);
        }

    }
}