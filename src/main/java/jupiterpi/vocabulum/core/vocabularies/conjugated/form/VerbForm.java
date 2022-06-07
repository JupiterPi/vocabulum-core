package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;

public class VerbForm {
    private ConjugatedForm conjugatedForm;
    private Mode mode;
    private Tense tense;
    private Voice voice;

    public VerbForm(ConjugatedForm conjugatedForm, Mode mode, Tense tense, Voice voice) {
        this.conjugatedForm = conjugatedForm;
        this.mode = mode;
        this.tense = tense;
        this.voice = voice;
    }

    public VerbForm(ConjugatedForm conjugatedForm) {
        this.conjugatedForm = conjugatedForm;
        this.mode = Mode.INDICATIVE;
        this.tense = Tense.PRESENT;
        this.voice = Voice.ACTIVE;
    }

    public static VerbForm fromString(String expr, I18n i18n) throws ParserException, LexerException {
        return fromTokens(new Lexer(expr, i18n).getTokens());
    }

    public static VerbForm fromTokens(TokenSequence tokens) throws ParserException {
        return null;
        //TODO implement
    }

    public ConjugatedForm getConjugatedForm() {
        return conjugatedForm;
    }

    public Mode getMode() {
        return mode;
    }

    public Tense getTense() {
        return tense;
    }

    public Voice getVoice() {
        return voice;
    }

    @Override
    public String toString() {
        return "Verb{form=" + conjugatedForm.toString() + ",mode=" + mode.toString().toLowerCase() + ",tense=" + tense.toString().toLowerCase() + ",voice=" + voice.toString().toLowerCase() + "}";
    }
}