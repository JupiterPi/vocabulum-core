package jupiterpi.vocabulum.core.interpreter.parser;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.MockDatabaseSetup;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.RuntimeVerb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.RuntimeAdjective;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.Noun;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.RuntimeNoun;
import jupiterpi.vocabulum.core.vocabularies.inflexible.Inflexible;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockDatabaseSetup.class)
class VocabularyParserTest {
    I18n i18n = Database.get().getI18ns().internal();

    @Nested
    @DisplayName("valid")
    class Valid {

        private TranslationSequence generateTranslations(String... translations) {
            TranslationSequence vocabularyTranslations = new TranslationSequence();
            for (String translation : translations) {
                vocabularyTranslations.add(VocabularyTranslation.fromString(translation).get(0));
            }
            return vocabularyTranslations;
        }

        @Test
        @DisplayName("noun")
        void noun() throws ParserException, DeclinedFormDoesNotExistException, VerbFormDoesNotExistException {
            TranslationSequence translations = generateTranslations("*der Sklave*", "der Diener");
            Noun e = RuntimeNoun.fromGenitive(
                    "servus", "servi", Gender.MASC,
                    translations, "test"
            );
            Vocabulary vocabulary = new VocabularyParser(new TokenSequence(
                    new Token(Token.Type.WORD, "servus", i18n),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.WORD, "servi", i18n),
                    new Token(Token.Type.GENDER, "m", i18n)
            ), translations, "test").getVocabulary();
            assertEquals(e.toString(), vocabulary.toString());
        }

        @Test
        @DisplayName("adjective (2/3-ended: form base forms)")
        void adjectiveFromBaseForms() throws ParserException, DeclinedFormDoesNotExistException, VerbFormDoesNotExistException {
            TranslationSequence translations = generateTranslations("*hart*", "*scharf*");
            Adjective e = RuntimeAdjective.fromBaseForms(
                    "acer", "acris", "acre",
                    translations, "test"
            );
            Vocabulary vocabulary = new VocabularyParser(new TokenSequence(
                    new Token(Token.Type.WORD, "acer", i18n),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.WORD, "acris", i18n),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.WORD, "acre", i18n)
            ), translations, "test").getVocabulary();
            assertEquals(e.toString(), vocabulary.toString());
        }

        @Test
        @DisplayName("adjective (1-ended: form genitive)")
        void adjectiveFromGenitive() throws ParserException, DeclinedFormDoesNotExistException, VerbFormDoesNotExistException {
            TranslationSequence translations = generateTranslations("*glücklich*");
            Adjective e = RuntimeAdjective.fromGenitive(
                    "felix", "felicis",
                    translations, "test"
            );
            Vocabulary vocabulary = new VocabularyParser(new TokenSequence(
                    new Token(Token.Type.WORD, "felix", i18n),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.CASUS, "Gen", i18n),
                    new Token(Token.Type.WORD, "felicis", i18n)
            ), translations, "test").getVocabulary();
            assertEquals(e.toString(), vocabulary.toString());
        }

        @Test
        @DisplayName("verb")
        void verb() throws ParserException, DeclinedFormDoesNotExistException, VerbFormDoesNotExistException {
            TranslationSequence translations = generateTranslations("*rufen*", "nennen");
            Verb e = RuntimeVerb.fromBaseForms(
                    "vocare", "voco", "vocavi", "vocatum",
                    translations, "test"
            );
            Vocabulary vocabulary = new VocabularyParser(new TokenSequence(
                    new Token(Token.Type.WORD, "vocare", i18n),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.WORD, "voco", i18n),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.WORD, "vocavi", i18n),
                    new Token(Token.Type.COMMA, ",", i18n),
                    new Token(Token.Type.WORD, "vocatum", i18n)
            ), translations, "test").getVocabulary();
            assertEquals(e.toString(), vocabulary.toString());
        }

        @Test
        @DisplayName("inflexible")
        void inflexible() throws ParserException, DeclinedFormDoesNotExistException, VerbFormDoesNotExistException {
            TranslationSequence translations = generateTranslations("*und*");
            Inflexible e = new Inflexible("et", translations, "test");
            Vocabulary vocabulary = new VocabularyParser(new TokenSequence(
                    new Token(Token.Type.WORD, "et", i18n)
            ), translations, "test").getVocabulary();
            assertEquals(e, vocabulary);
        }

    }


    @Nested
    @DisplayName("invalid")
    class Invalid {

        @Test
        @DisplayName("invalid token sequence")
        void invalidTokenSequence() {
            assertThrows(ParserException.class, () -> {
                new VocabularyParser(
                        new TokenSequence(
                                new Token(Token.Type.CASUS, "Acc", i18n),
                                new Token(Token.Type.GENDER, "m", i18n)
                        ),
                        new TranslationSequence(), "test"
                );
            });
        }

    }

}