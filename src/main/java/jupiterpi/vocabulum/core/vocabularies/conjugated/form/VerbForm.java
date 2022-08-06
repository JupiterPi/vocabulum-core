package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.Token;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;

import java.util.Objects;

public class VerbForm implements VocabularyForm {
    // for Kind.INFINITIVE
    private boolean infinitive;
    private InfinitiveTense infinitiveTense;

    // for Kind.BASIC
    private ConjugatedForm conjugatedForm;
    private Mode mode;
    private Tense tense;
    private Voice voice;

    // for Kind.NOUN_LIKE
    private boolean nounLike;
    private NounLikeForm nounLikeForm;
    private DeclinedForm declinedForm;


    /* constructors */

    // for Kind.INFINITIVE
    public VerbForm(InfinitiveTense infinitiveTense) {
        this.infinitive = true;
        this.infinitiveTense = infinitiveTense;
    }

    // for Kind.BASIC
    public VerbForm(ConjugatedForm conjugatedForm, Mode mode, Tense tense, Voice voice) {
        this.conjugatedForm = conjugatedForm;
        this.mode = mode;
        this.tense = tense;
        this.voice = voice;
    }

    // for Kind.NOUN_LIKE
    public VerbForm(NounLikeForm nounLikeForm, DeclinedForm declinedForm) {
        this.nounLike = true;
        this.nounLikeForm = nounLikeForm;
        this.declinedForm = declinedForm;
    }


    /* parser */

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
        VerbFormParser parser = new VerbFormParser(tokens);
        return parser.getVerbForm();
    }


    /* kinds */

    public enum Kind {
        INFINITIVE, BASIC, NOUN_LIKE
    }

    public Kind getKind() {
        if (infinitive) return Kind.INFINITIVE;
        if (nounLike) return Kind.NOUN_LIKE;
        return Kind.BASIC;
    }


    /* getters, equals, toString */

    // getters

    public boolean isInfinitive() {
        return infinitive;
    }

    public InfinitiveTense getInfinitiveTense() {
        return infinitiveTense;
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

    public boolean isNounLike() {
        return nounLike;
    }

    public NounLikeForm getNounLikeForm() {
        return nounLikeForm;
    }

    public DeclinedForm getDeclinedForm() {
        return declinedForm;
    }

    // equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerbForm verbForm = (VerbForm) o;
        return infinitive == verbForm.infinitive && nounLike == verbForm.nounLike && infinitiveTense == verbForm.infinitiveTense && Objects.equals(conjugatedForm, verbForm.conjugatedForm) && mode == verbForm.mode && tense == verbForm.tense && voice == verbForm.voice && nounLikeForm == verbForm.nounLikeForm && Objects.equals(declinedForm, verbForm.declinedForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(infinitive, infinitiveTense, conjugatedForm, mode, tense, voice, nounLike, nounLikeForm, declinedForm);
    }

    // to string

    @Override
    public String formToString(I18n i18n) {
        if (getKind() == Kind.BASIC) {
            String str = "";
            str += conjugatedForm.formToString(i18n, true) + " ";
            str += mode != DEFAULT_MODE ? i18n.getModeSymbol(mode) + ". " : "";
            str += tense != DEFAULT_TENSE ? i18n.getTenseSymbol(tense) + ". " : "";
            str += voice != DEFAULT_VOICE ? i18n.getVoiceSymbol(voice) + ". " : "";
            return str.substring(0, str.length() - 1);
        } else {
            return switch (getKind()) {
                case BASIC -> null;
                case INFINITIVE -> i18n.getInfinitiveFlag() + ". " + i18n.getInfinitiveTenseSymbol(infinitiveTense) + ".";
                case NOUN_LIKE -> i18n.getNounLikeFormSymbol(nounLikeForm) + ". " + declinedForm.formToString(i18n);
            };
        }
    }

    @Override
    public String toString() {
        return switch (getKind()) {
            case BASIC -> "Verb{form=" + conjugatedForm.toString() + ",mode=" + mode.toString().toLowerCase() + ",tense=" + tense.toString().toLowerCase() + ",voice=" + voice.toString().toLowerCase() + "}";
            case INFINITIVE -> "Verb{infinitive, tense=" + infinitiveTense.toString().toLowerCase() + "}";
            case NOUN_LIKE -> "Verb{" + nounLikeForm.toString().toLowerCase() + ", form=" + declinedForm.toString() + "}";
        };
    }
}