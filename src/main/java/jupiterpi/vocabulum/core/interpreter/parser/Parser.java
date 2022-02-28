package jupiterpi.vocabulum.core.interpreter.parser;

import jupiterpi.vocabulum.core.i18n.I18nException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.DeclinedFormAspects;
import jupiterpi.vocabulum.core.vocabularies.declinated.nouns.Noun;

public class Parser {
    private Vocabulary vocabulary;

    public Parser(TokenSequence tokens) throws ParserException, DeclinedFormDoesNotExistException, I18nException {
        this.vocabulary = parseVocabulary(tokens);
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    /* parser */

    private Vocabulary parseVocabulary(TokenSequence tokens) throws ParserException, DeclinedFormDoesNotExistException, I18nException {
        // nouns
        if (tokens.size() == 4 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD,
                Token.Type.GENDER))) {
            return Noun.fromGenitive(
                    tokens.get(0).getContent(),
                    tokens.get(2).getContent(),
                    DeclinedFormAspects.genderFromString(tokens.get(3).getContent(), tokens.getI18n()));
        }
        // adjectives (2-/3-ended)
        if (tokens.size() == 5 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD))) {
            return Adjective.fromBaseForms(
                    tokens.get(0).getContent(),
                    tokens.get(2).getContent(),
                    tokens.get(4).getContent()
            );
        }
        // adjectives (1-ended)
        if (tokens.size() == 4 && tokens.fitsStartsWith(new TokenSequence(
                new Token(Token.Type.WORD),
                new Token(Token.Type.COMMA),
                new Token(Token.Type.CASUS, tokens.getI18n().getString(Casus.GEN), tokens.getI18n()),
                new Token(Token.Type.WORD)
        ))) {
            return Adjective.fromBaseForm(
                    tokens.get(0).getContent(),
                    tokens.get(3).getContent()
            );
        }
        throw new ParserException("Could not parse token sequence: " + tokens);
    }
}