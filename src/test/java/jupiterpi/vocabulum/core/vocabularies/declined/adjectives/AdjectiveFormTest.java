package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

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

@ExtendWith(MockDatabaseSetup.class)
class AdjectiveFormTest {
    I18n i18n = Database.get().getI18ns().internal();
    
    @Nested
    @DisplayName("valid fromTokens()")
    class ValidFromTokens {

        @Test
        @DisplayName("simple form")
        void simple() throws ParserException {
            AdjectiveForm form = AdjectiveForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.CASUS, "Nom", i18n),
                    new Token(Token.Type.NUMBER, "Sg", i18n),
                    new Token(Token.Type.GENDER, "m", i18n)
            ));
            assertEquals(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE), form);
        }

        @Test
        @DisplayName("with comparative form")
        void withComparativeForm() throws ParserException {
            AdjectiveForm form = AdjectiveForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.CASUS, "Nom", i18n),
                    new Token(Token.Type.NUMBER, "Sg", i18n),
                    new Token(Token.Type.GENDER, "m", i18n),
                    new Token(Token.Type.COMPARATIVE_FORM, "Comp", i18n)
            ));
            assertEquals(new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.COMPARATIVE), form);
        }

        @Test
        @DisplayName("with adverb form")
        void adverb() throws ParserException {
            AdjectiveForm form = AdjectiveForm.fromTokens(new TokenSequence(
                    new Token(Token.Type.CASUS, "Nom", i18n),
                    new Token(Token.Type.NUMBER, "Sg", i18n),
                    new Token(Token.Type.GENDER, "m", i18n),
                    new Token(Token.Type.COMPARATIVE_FORM, "Comp", i18n),
                    new Token(Token.Type.ADV_FLAG, "Adv", i18n)
            ));
            assertEquals(new AdjectiveForm(true, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.COMPARATIVE), form);
        }

    }


    @Nested
    @DisplayName("formToString()")
    class FormToString {

        @Test
        @DisplayName("simple form")
        void simple() {
            AdjectiveForm form = new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.POSITIVE);
            assertEquals("Nom. Sg. m.", form.formToString(i18n));
        }

        @Test
        @DisplayName("with comparative form")
        void withComparativeForm() {
            AdjectiveForm form = new AdjectiveForm(new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.COMPARATIVE);
            assertEquals("Nom. Sg. m. Comp.", form.formToString(i18n));
        }

        @Test
        @DisplayName("with adverb form")
        void adverb() {
            AdjectiveForm form = new AdjectiveForm(true, new DeclinedForm(Casus.NOM, NNumber.SG, Gender.MASC), ComparativeForm.COMPARATIVE);
            assertEquals("Adv. Comp.", form.formToString(i18n));
        }

    }
}