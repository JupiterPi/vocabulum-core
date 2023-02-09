package jupiterpi.vocabulum.core.interpreter.lexer;

import jupiterpi.vocabulum.core.i18n.Symbols;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.util.StringSet;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;

public class Lexer {
    private TokenSequence tokens = new TokenSequence();

    public Lexer(String expr) throws LexerException {
        casusSymbols = new StringSet(
                Symbols.get().getCasusSymbol(Casus.NOM),
                Symbols.get().getCasusSymbol(Casus.GEN),
                Symbols.get().getCasusSymbol(Casus.DAT),
                Symbols.get().getCasusSymbol(Casus.ACC),
                Symbols.get().getCasusSymbol(Casus.ABL)
        );
        numberSymbols = new StringSet(
                Symbols.get().getNumberSymbol(NNumber.SG),
                Symbols.get().getNumberSymbol(NNumber.PL)
        );
        genderSymbols = new StringSet(
                Symbols.get().getGenderSymbol(Gender.MASC),
                Symbols.get().getGenderSymbol(Gender.FEM),
                Symbols.get().getGenderSymbol(Gender.NEUT)
        );
        comparativeFormSymbols = new StringSet(
                Symbols.get().getComparativeFormSymbol(ComparativeForm.POSITIVE),
                Symbols.get().getComparativeFormSymbol(ComparativeForm.COMPARATIVE),
                Symbols.get().getComparativeFormSymbol(ComparativeForm.SUPERLATIVE)
        );
        adverbFlag = Symbols.get().getAdverbFlag();
        personSymbols = new StringSet(
                Symbols.get().getPersonSymbol(Person.FIRST),
                Symbols.get().getPersonSymbol(Person.SECOND),
                Symbols.get().getPersonSymbol(Person.THIRD)
        );
        personCosmetic = Symbols.get().getPersonCosmetic();
        modeSymbols = new StringSet(
                Symbols.get().getModeSymbol(Mode.INDICATIVE),
                Symbols.get().getModeSymbol(Mode.CONJUNCTIVE)
        );
        tenseSymbols = new StringSet(
                Symbols.get().getTenseSymbol(Tense.PRESENT),
                Symbols.get().getTenseSymbol(Tense.IMPERFECT),
                Symbols.get().getTenseSymbol(Tense.PERFECT),
                Symbols.get().getTenseSymbol(Tense.PLUPERFECT),
                Symbols.get().getTenseSymbol(Tense.FUTURE_I),
                Symbols.get().getTenseSymbol(Tense.FUTURE_II)
        );
        voiceSymbols = new StringSet(
                Symbols.get().getVoiceSymbol(Voice.ACTIVE),
                Symbols.get().getVoiceSymbol(Voice.PASSIVE)
        );
        imperativeFlag = Symbols.get().getImperativeFlag();
        infinitiveFlag = Symbols.get().getInfinitiveFlag();
        infinitiveTenseSymbols = new StringSet(
                Symbols.get().getInfinitiveTenseSymbol(InfinitiveTense.PRESENT),
                Symbols.get().getInfinitiveTenseSymbol(InfinitiveTense.PERFECT),
                Symbols.get().getInfinitiveTenseSymbol(InfinitiveTense.FUTURE)
        );
        nounLikeForms = new StringSet(
                Symbols.get().getNounLikeFormSymbol(NounLikeForm.PPP),
                Symbols.get().getNounLikeFormSymbol(NounLikeForm.PPA),
                Symbols.get().getNounLikeFormSymbol(NounLikeForm.GERUNDIUM),
                Symbols.get().getNounLikeFormSymbol(NounLikeForm.GERUNDIVUM)
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
    private final String imperativeFlag;
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
            tokens.add(new Token(Token.Type.WORD, buffer));
        } else if (bufferType == BufferType.ABBREVIATION) {
            if (casusSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.CASUS, buffer));
            } else if (numberSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.NUMBER, buffer));
            } else if (genderSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.GENDER, buffer));
            } else if (comparativeFormSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.COMPARATIVE_FORM, buffer));
            } else if (adverbFlag.equals(buffer)) {
                tokens.add(new Token(Token.Type.ADV_FLAG, buffer));
            } else if (personSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.PERSON, buffer));
            } else if (personCosmetic.equals(buffer)) {
                // do nothing, the cosmetic should be ignored completely
            } else if (modeSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.MODE, buffer));
            } else if (tenseSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.TENSE, buffer));
            } else if (voiceSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.VOICE, buffer));
            } else if (imperativeFlag.equals(buffer)) {
                tokens.add(new Token(Token.Type.IMPERATIVE_FLAG, buffer));
            } else if (infinitiveFlag.equals(buffer)) {
                tokens.add(new Token(Token.Type.INFINITIVE_FLAG, buffer));
            } else if (infinitiveTenseSymbols.contains(buffer)) {
                tokens.add(new Token(Token.Type.TENSE, buffer));
            } else if (nounLikeForms.contains(buffer)) {
                tokens.add(new Token(Token.Type.NOUN_LIKE_FORM, buffer));
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