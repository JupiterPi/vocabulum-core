package jupiterpi.vocabulum.core.interpreter.parser;

import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
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
        if (tokens.get(0).getType() == Token.Type.WORD) {
            if (tokens.get(1).getType() == Token.Type.COMMA) {
                if (tokens.get(2).getType() == Token.Type.WORD) {
                    if (tokens.get(3).getType() == Token.Type.GENDER) {
                        return Noun.fromGenitive(tokens.get(0).getContent(), tokens.get(2).getContent(), Genders.fromSymbol(tokens.get(3).getContent()));
                    }
                }
            }
        }
        throw new ParserException("Could not parse token sequence: " + tokens);
    }
}