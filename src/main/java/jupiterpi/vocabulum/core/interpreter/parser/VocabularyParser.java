package jupiterpi.vocabulum.core.interpreter.parser;

import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.symbols.Symbols;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.Adjective;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.Noun;
import jupiterpi.vocabulum.core.vocabularies.inflexible.Inflexible;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;

/**
 * Part of the interpreter that transforms a token sequence, translations and portion to a vocabulary.
 * Instantiate and get the vocabulary using <code>getVocabulary()</code>.
 * @see jupiterpi.vocabulum.core.interpreter.lexer.Lexer
 */
public class VocabularyParser {
    private Vocabulary vocabulary;

    /**
     * Constructs this parser with the given token sequence that makes up the definition, translations and portion.
     * @param tokens          the token sequence that makes up the definition
     * @param punctuationSign the punctuation contained in the original expression, or <code>null</code> if there is none
     * @param translations    the translations
     * @param portion         the portion that the vocabulary belongs to
     * @throws ParserException when the given token sequence is not a valid vocabulary
     */
    public VocabularyParser(TokenSequence tokens, String punctuationSign, TranslationSequence translations, String portion) throws ParserException, DeclinedFormDoesNotExistException, VerbFormDoesNotExistException {
        this.vocabulary = parseVocabulary(tokens, punctuationSign, translations, portion);
    }

    /**
     * @return the resulting vocabulary
     */
    public Vocabulary getVocabulary() {
        return vocabulary;
    }

    /* parser */

    private Vocabulary parseVocabulary(TokenSequence tokens, String punctuationSign, TranslationSequence translations, String portion) throws ParserException, DeclinedFormDoesNotExistException, VerbFormDoesNotExistException {

        // nouns
        if (tokens.size() == 4 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD,
                Token.Type.GENDER))) {
            return Noun.fromGenitive(
                    tokens.get(0).getContent(),
                    tokens.get(2).getContent(),
                    Symbols.get().genderFromSymbol(tokens.get(3).getContent()),
                    punctuationSign, translations, portion
            );
        }

        // adjectives (2-/3-ended: from base forms)
        if (tokens.size() == 5 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD,
                Token.Type.COMMA,
                Token.Type.WORD))) {
            return Adjective.fromBaseForms(
                    tokens.get(0).getContent(),
                    tokens.get(2).getContent(),
                    tokens.get(4).getContent(),
                    punctuationSign, translations, portion
            );
        }

        // adjectives (1-ended: from genitive)
        if (tokens.size() == 4 && tokens.fitsStartsWith(new TokenSequence(
                new Token(Token.Type.WORD),
                new Token(Token.Type.COMMA),
                new Token(Token.Type.CASUS, Symbols.get().getCasusSymbol(Casus.GEN)),
                new Token(Token.Type.WORD)
        ))) {
            return Adjective.fromGenitive(
                    tokens.get(0).getContent(),
                    tokens.get(3).getContent(),
                    punctuationSign, translations, portion
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
            return Verb.fromBaseForms(
                    tokens.get(0).getContent(),
                    tokens.get(2).getContent(),
                    tokens.get(4).getContent(),
                    tokens.get(6).getContent(),
                    punctuationSign, translations, portion
            );
        }

        // inflexibles
        if (tokens.size() == 1 && tokens.fitsStartsWith(TokenSequence.fromTypes(
                Token.Type.WORD
        ))) {
            return new Inflexible(tokens.get(0).getContent(), punctuationSign, translations, portion);
        }

        throw new ParserException("Could not parse vocabulary: " + tokens);
    }
}