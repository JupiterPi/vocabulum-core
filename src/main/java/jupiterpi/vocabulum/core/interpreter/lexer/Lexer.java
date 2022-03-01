package jupiterpi.vocabulum.core.interpreter.lexer;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.util.StringSet;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Number;

public class Lexer {
    private I18n i18n;
    private TokenSequence tokens = new TokenSequence();

    public Lexer(String expr, I18n i18n) throws LexerException {
        this.i18n = i18n;

        casusSymbols = new StringSet(
                i18n.getString(Casus.NOM),
                i18n.getString(Casus.GEN),
                i18n.getString(Casus.DAT),
                i18n.getString(Casus.ACC),
                i18n.getString(Casus.ABL)
        );
        numberSymbols = new StringSet(
                i18n.getString(Number.SG),
                i18n.getString(Number.PL)
        );
        genderSymbols = new StringSet(
                i18n.getString(Gender.MASC),
                i18n.getString(Gender.FEM),
                i18n.getString(Gender.NEUT)
        );

        generateTokens(expr);
    }

    public TokenSequence getTokens() {
        return tokens;
    }

    /* lexer */

    // character types
    private final StringSet whitespaces = new StringSet(" ", "\t");
    private final StringSet letters = StringSet.getCharacters("abcdefghijklmnopqrstuvwxyz" + "abcdefghijklmnopqrstuvwxyz".toUpperCase());
    private final String comma = ",";
    private final String dot = ".";

    // other string sets
    private final StringSet casusSymbols;  // set in constructor
    private final StringSet numberSymbols; // set in constructor
    private final StringSet genderSymbols; // set in constructor

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
            tokens.add(new Token(Token.Type.WORD, buffer, i18n));
        } else if (bufferType == BufferType.ABBREVIATION) {
            if (casusSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.CASUS, buffer, i18n));
            } else if (numberSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.NUMBER, buffer, i18n));
            } else if (genderSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.GENDER, buffer, i18n));
            } else {
                throw new LexerException("Invalid abbreviation: " + buffer);
            }
        } else if (bufferType == BufferType.COMMA) {
            tokens.add(new Token(Token.Type.COMMA, buffer, i18n));
        }
        buffer = "";
        bufferType = null;
    }
}