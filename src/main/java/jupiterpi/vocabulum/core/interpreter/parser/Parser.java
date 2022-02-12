package jupiterpi.vocabulum.core.interpreter.parser;

import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declinated.nouns.Noun;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.Genders;

public class Parser {
    private Vocabulary vocabulary;

    public Parser(TokenSequence tokens) throws ParserException, DeclinedFormDoesNotExistException {
        this.vocabulary = parseVocabulary(tokens);
    }

    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    /* parser */

    private Vocabulary parseVocabulary(TokenSequence tokens) throws ParserException, DeclinedFormDoesNotExistException {
        // nouns
        if (tokens.size() == 4 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD,
                Token.Type.GENDER))) {
            return Noun.fromGenitive(
                    tokens.get(0).getContent(),
                    tokens.get(2).getContent(),
                    Genders.fromSymbol(tokens.get(3).getContent()));
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
                new Token(Token.Type.CASUS, "Gen"),
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