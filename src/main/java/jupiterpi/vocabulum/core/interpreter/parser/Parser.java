package jupiterpi.vocabulum.core.interpreter.parser;

import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.RuntimeVerb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.RuntimeAdjective;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.RuntimeNoun;
import jupiterpi.vocabulum.core.vocabularies.inflexible.Inflexible;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;

import java.util.List;

public class Parser {
    private Vocabulary vocabulary;

    public Parser(TokenSequence tokens, List<VocabularyTranslation> translations) throws ParserException, DeclinedFormDoesNotExistException, I18nException, VerbFormDoesNotExistException {
        this.vocabulary = parseVocabulary(tokens, translations);
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    /* parser */

    private Vocabulary parseVocabulary(TokenSequence tokens, List<VocabularyTranslation> translations) throws ParserException, DeclinedFormDoesNotExistException, VerbFormDoesNotExistException {

        // nouns
        if (tokens.size() == 4 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD,
                Token.Type.GENDER))) {
            return RuntimeNoun.fromGenitive(
                    tokens.get(0).getContent(),
                    tokens.get(2).getContent(),
                    tokens.getI18n().genderFromSymbol(tokens.get(3).getContent()),
                    translations);
        }

        // adjectives (2-/3-ended)
        if (tokens.size() == 5 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD))) {
            return RuntimeAdjective.fromBaseForms(
                    tokens.get(0).getContent(),
                    tokens.get(2).getContent(),
                    tokens.get(4).getContent(),
                    translations
            );
        }

        // adjectives (1-ended)
        if (tokens.size() == 4 && tokens.fitsStartsWith(new TokenSequence(
                new Token(Token.Type.WORD),
                new Token(Token.Type.COMMA),
                new Token(Token.Type.CASUS, tokens.getI18n().getCasusSymbol(Casus.GEN), tokens.getI18n()),
                new Token(Token.Type.WORD)
        ))) {
            return RuntimeAdjective.fromBaseForm(
                    tokens.get(0).getContent(),
                    tokens.get(3).getContent(),
                    translations
            );
        }

        // verbs
        if (tokens.size() == 7 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD
        ))) {
            return RuntimeVerb.fromBaseForms(
                    tokens.get(0).getContent(),
                    tokens.get(2).getContent(),
                    tokens.get(4).getContent(),
                    translations
            );
        }

        // inflexibles
        if (tokens.size() == 1 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.WORD
        ))) {
            return new Inflexible(tokens.get(0).getContent(), translations);
        }

        throw new ParserException("Could not parse token sequence: " + tokens);
    }
}