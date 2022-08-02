package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.Main;
import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;

import java.util.Objects;

public class VerbForm implements VocabularyForm {
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

    public static VerbForm get(String expr) {
        try {
            return fromString(expr, Database.get().getI18ns().internal());
        } catch (ParserException | LexerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final Mode DEFAULT_MODE = Mode.INDICATIVE;
    public static final Tense DEFAULT_TENSE = Tense.PRESENT;
    public static final Voice DEFAULT_VOICE = Voice.ACTIVE;

    public static VerbForm fromTokens(TokenSequence tokens) throws ParserException {
        int voiceIndex = tokens.indexOf(new Token(Token.Type.VOICE));
        Voice voice = DEFAULT_VOICE;
        if (voiceIndex >= 0) {
            voice = tokens.getI18n().voiceFromSymbol(tokens.get(voiceIndex).getContent());
            tokens.remove(voiceIndex);
        }

        int tenseIndex = tokens.indexOf(new Token(Token.Type.TENSE));
        Tense tense = DEFAULT_TENSE;
        if (tenseIndex >= 0) {
            tense = tokens.getI18n().tenseFromSymbol(tokens.get(tenseIndex).getContent());
            tokens.remove(tenseIndex);
        }

        int modeIndex = tokens.indexOf(new Token(Token.Type.MODE));
        Mode mode = DEFAULT_MODE;
        if (modeIndex >= 0) {
            mode = tokens.getI18n().modeFromSymbol(tokens.get(modeIndex).getContent());
            tokens.remove(modeIndex);
        }

        ConjugatedForm conjugatedForm = ConjugatedForm.fromTokens(tokens);

        return new VerbForm(conjugatedForm, mode, tense, voice);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerbForm verbForm = (VerbForm) o;
        return Objects.equals(conjugatedForm, verbForm.conjugatedForm) && mode == verbForm.mode && tense == verbForm.tense && voice == verbForm.voice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(conjugatedForm, mode, tense, voice);
    }

    // to string

    @Override
    public String formToString(I18n i18n) {
        String str = "";
        str += conjugatedForm.formToString(i18n, true) + " ";
        str += mode != DEFAULT_MODE ? i18n.getModeSymbol(mode) + ". " : "";
        str += tense != DEFAULT_TENSE ? i18n.getTenseSymbol(tense) + ". " : "";
        str += voice != DEFAULT_VOICE ? i18n.getVoiceSymbol(voice) + ". " : "";
        return str.substring(0, str.length()-1);
    }

    @Override
    public String toString() {
        return "Verb{form=" + conjugatedForm.toString() + ",mode=" + mode.toString().toLowerCase() + ",tense=" + tense.toString().toLowerCase() + ",voice=" + voice.toString().toLowerCase() + "}";
    }
}