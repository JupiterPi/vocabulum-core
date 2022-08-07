package jupiterpi.vocabulum.core.interpreter.lexer;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.util.StringSet;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;

public class Lexer {
    private I18n i18n;
    private TokenSequence tokens = new TokenSequence();

    public Lexer(String expr, I18n i18n) throws LexerException {
        this.i18n = i18n;

        casusSymbols = new StringSet(
                i18n.getCasusSymbol(Casus.NOM),
                i18n.getCasusSymbol(Casus.GEN),
                i18n.getCasusSymbol(Casus.DAT),
                i18n.getCasusSymbol(Casus.ACC),
                i18n.getCasusSymbol(Casus.ABL)
        );
        numberSymbols = new StringSet(
                i18n.getNumberSymbol(NNumber.SG),
                i18n.getNumberSymbol(NNumber.PL)
        );
        genderSymbols = new StringSet(
                i18n.getGenderSymbol(Gender.MASC),
                i18n.getGenderSymbol(Gender.FEM),
                i18n.getGenderSymbol(Gender.NEUT)
        );
        comparativeFormSymbols = new StringSet(
                i18n.getComparativeFormSymbol(ComparativeForm.POSITIVE),
                i18n.getComparativeFormSymbol(ComparativeForm.COMPARATIVE),
                i18n.getComparativeFormSymbol(ComparativeForm.SUPERLATIVE)
        );
        adverbFlag = i18n.getAdverbFlag();
        personSymbols = new StringSet(
                i18n.getPersonSymbol(Person.FIRST),
                i18n.getPersonSymbol(Person.SECOND),
                i18n.getPersonSymbol(Person.THIRD)
        );
        personCosmetic = i18n.getPersonCosmetic();
        modeSymbols = new StringSet(
                i18n.getModeSymbol(Mode.INDICATIVE),
                i18n.getModeSymbol(Mode.CONJUNCTIVE)
        );
        tenseSymbols = new StringSet(
                i18n.getTenseSymbol(Tense.PRESENT),
                i18n.getTenseSymbol(Tense.IMPERFECT),
                i18n.getTenseSymbol(Tense.PERFECT),
                i18n.getTenseSymbol(Tense.PLUPERFECT),
                i18n.getTenseSymbol(Tense.FUTURE_I),
                i18n.getTenseSymbol(Tense.FUTURE_II)
        );
        voiceSymbols = new StringSet(
                i18n.getVoiceSymbol(Voice.ACTIVE),
                i18n.getVoiceSymbol(Voice.PASSIVE)
        );
        infinitiveFlag = i18n.getInfinitiveFlag();
        infinitiveTenseSymbols = new StringSet(
                i18n.getInfinitiveTenseSymbol(InfinitiveTense.PRESENT),
                i18n.getInfinitiveTenseSymbol(InfinitiveTense.PERFECT),
                i18n.getInfinitiveTenseSymbol(InfinitiveTense.FUTURE)
        );
        nounLikeForms = new StringSet(
                i18n.getNounLikeFormSymbol(NounLikeForm.PPP),
                i18n.getNounLikeFormSymbol(NounLikeForm.PPA),
                i18n.getNounLikeFormSymbol(NounLikeForm.GERUNDIUM),
                i18n.getNounLikeFormSymbol(NounLikeForm.GERUNDIVUM)
        );

        generateTokens(expr);
    }

    public TokenSequence getTokens() {
        return tokens;
    }

    /* lexer */

    // character types
    private final StringSet whitespaces = new StringSet(" ", "\t");
    private final StringSet letters = StringSet.getCharacters("abcdefghijklmnopqrstuvwxyzäöüß" + "abcdefghijklmnopqrstuvwxyzäöü".toUpperCase() + "123");
    private final String comma = ",";
    private final String dot = ".";

    // other string sets                   // all set in constructor
    private final StringSet casusSymbols;
    private final StringSet numberSymbols;
    private final StringSet genderSymbols;
    private final StringSet comparativeFormSymbols;
    private final String adverbFlag;
    private final StringSet personSymbols;
    private final String personCosmetic;
    private final StringSet modeSymbols;
    private final StringSet tenseSymbols;
    private final StringSet voiceSymbols;
    private final String infinitiveFlag;
    private final StringSet infinitiveTenseSymbols;
    private final StringSet nounLikeForms;

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
            } else if (comparativeFormSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.COMPARATIVE_FORM, buffer, i18n));
            } else if (adverbFlag.equals(buffer)) {
                tokens.add(new Token(Token.Type.ADV_FLAG, buffer, i18n));
            } else if (personSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.PERSON, buffer, i18n));
            } else if (personCosmetic.equals(buffer)) {
                // do nothing, the cosmetic should be ignored completely
            } else if (modeSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.MODE, buffer, i18n));
            } else if (tenseSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.TENSE, buffer, i18n));
            } else if (voiceSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.VOICE, buffer, i18n));
            } else if (infinitiveFlag.equals(buffer)) {
                tokens.add(new Token(Token.Type.INFINITIVE_FLAG, buffer, i18n));
            } else if (infinitiveTenseSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.TENSE, buffer, i18n));
            } else if (nounLikeForms.contains(buffer)) {
                tokens.add(new Token(Token.Type.NOUN_LIKE_FORM, buffer, i18n));
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