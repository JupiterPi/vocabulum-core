package jupiterpi.vocabulum.core.interpreter.lexer;

import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.util.StringSet;

public class Lexer {
    private TokenSequence tokens = new TokenSequence();

    public Lexer(String expr) throws LexerException {
        generateTokens(expr);
    }

    public TokenSequence getTokens() {
        return tokens;
    }

    /* lexer */

    // character types
    private final StringSet whitespaces = new StringSet(" ", "\t");
    private final StringSet letters = StringSet.getCharacters("abcdefghijklmnopqrstuvwxyz");
    private final String comma = ",";
    private final String dot = ".";

    // other string sets
    private final StringSet genderSymbols = new StringSet("m", "f", "n");

    // buffer
    private String buffer = "";
    private BufferType bufferType = null;
    private enum BufferType {
        WORD, ABBREVIATION, COMMA
    }

    private void generateTokens(String expr) throws LexerException {
        for (String c : expr.split("")) {
            // comma
            if (c.equals(comma)) {
                flushBuffer();
                buffer = c;
                bufferType = BufferType.COMMA;
                flushBuffer();
                continue;
            }

            // dot
            if (c.equals(dot)) {
                bufferType = BufferType.ABBREVIATION;
                flushBuffer();
                continue;
            }

            // whitespace
            if (whitespaces.contains(c)) {
                flushBuffer();
                continue;
            }

            // letter
            if (letters.contains(c)) {
                buffer += c;
                bufferType = BufferType.WORD;
                continue;
            }

            throw new LexerException("Invalid character: " + c);
        }
        flushBuffer();
    }

    private void flushBuffer() throws LexerException {
        if (bufferType == BufferType.WORD) {
            tokens.add(new Token(Token.Type.WORD, buffer));
        } else if (bufferType == BufferType.ABBREVIATION) {
            if (genderSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.GENDER, buffer));
            } else {
                throw new LexerException("Invalid abbreviation: " + buffer);
            }
        } else if (bufferType == BufferType.COMMA) {
            tokens.add(new Token(Token.Type.COMMA, buffer));
        }
        buffer = "";
        bufferType = null;
    }
}