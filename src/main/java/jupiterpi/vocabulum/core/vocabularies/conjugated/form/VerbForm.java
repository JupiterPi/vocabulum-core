package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.i18n.Symbols;
import jupiterpi.vocabulum.core.interpreter.lexer.Lexer;
import jupiterpi.vocabulum.core.interpreter.lexer.LexerException;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.interpreter.tokens.TokenSequence;
import jupiterpi.vocabulum.core.vocabularies.VocabularyForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class VerbForm implements VocabularyForm {
    // for Kind.IMPERATIVE
    private boolean imperative;
    private CNumber imperativeNumber;

    // for Kind.INFINITIVE
    private boolean infinitive;
    private InfinitiveTense infinitiveTense;
    private Voice infinitiveVoice;

    // for Kind.BASIC
    private ConjugatedForm conjugatedForm;
    private Mode mode;
    private Tense tense;
    private Voice voice;

    // for Kind.NOUN_LIKE
    private boolean nounLike;
    private NounLikeForm nounLikeForm;
    private DeclinedForm nounLikeDeclinedForm;


    /* constructors */

    // for Kind.IMPERATIVE
    public VerbForm(CNumber imperativeNumber) {
        this.imperative = true;
        this.imperativeNumber = imperativeNumber;
    }

    // for Kind.INFINITIVE
    public VerbForm(InfinitiveTense infinitiveTense, Voice infinitiveVoice) {
        this.infinitive = true;
        this.infinitiveTense = infinitiveTense;
        this.infinitiveVoice = infinitiveVoice;
    }

    // for Kind.BASIC
    public VerbForm(ConjugatedForm conjugatedForm, Mode mode, Tense tense, Voice voice) {
        this.conjugatedForm = conjugatedForm;
        this.mode = mode;
        this.tense = tense;
        this.voice = voice;
    }

    // for Kind.NOUN_LIKE
    public VerbForm(NounLikeForm nounLikeForm, DeclinedForm nounLikeDeclinedForm) {
        this.nounLike = true;
        this.nounLikeForm = nounLikeForm;
        this.nounLikeDeclinedForm = nounLikeDeclinedForm;
    }


    /* parser */

    public static VerbForm fromString(String expr) throws ParserException, LexerException {
        return fromTokens(new Lexer(expr).getTokens());
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
        IMPERATIVE, INFINITIVE, BASIC, NOUN_LIKE
    }

    public Kind getKind() {
        if (imperative) return Kind.IMPERATIVE;
        if (infinitive) return Kind.INFINITIVE;
        if (nounLike) return Kind.NOUN_LIKE;
        return Kind.BASIC;
    }


    /* getters, equals, toString */

    // getters

    public boolean isImperative() {
        return imperative;
    }

    public CNumber getImperativeNumber() {
        return imperativeNumber;
    }

    public boolean isInfinitive() {
        return infinitive;
    }

    public InfinitiveTense getInfinitiveTense() {
        return infinitiveTense;
    }

    public Voice getInfinitiveVoice() {
        return infinitiveVoice;
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

    public DeclinedForm getNounLikeDeclinedForm() {
        return nounLikeDeclinedForm;
    }

    // equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VerbForm verbForm = (VerbForm) o;
        return imperative == verbForm.imperative && infinitive == verbForm.infinitive && nounLike == verbForm.nounLike && imperativeNumber == verbForm.imperativeNumber && infinitiveTense == verbForm.infinitiveTense && infinitiveVoice == verbForm.infinitiveVoice && Objects.equals(conjugatedForm, verbForm.conjugatedForm) && mode == verbForm.mode && tense == verbForm.tense && voice == verbForm.voice && nounLikeForm == verbForm.nounLikeForm && Objects.equals(nounLikeDeclinedForm, verbForm.nounLikeDeclinedForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imperative, imperativeNumber, infinitive, infinitiveTense, infinitiveVoice, conjugatedForm, mode, tense, voice, nounLike, nounLikeForm, nounLikeDeclinedForm);
    }

    // to string

    @Override
    public String formToString() {
        if (getKind() == Kind.BASIC) {
            String str = "";
            str += conjugatedForm.formToString(true) + " ";
            str += mode != DEFAULT_MODE ? Symbols.get().getModeSymbol(mode) + ". " : "";
            str += tense != DEFAULT_TENSE ? Symbols.get().getTenseSymbol(tense) + ". " : "";
            str += voice != DEFAULT_VOICE ? Symbols.get().getVoiceSymbol(voice) + ". " : "";
            return str.substring(0, str.length() - 1);
        } else {
            return switch (getKind()) {
                case BASIC -> null;
                case IMPERATIVE -> Symbols.get().getImperativeFlag() + ". " + Symbols.get().getNumberSymbol(imperativeNumber) + ".";
                case INFINITIVE -> Symbols.get().getInfinitiveFlag() + ". " + Symbols.get().getInfinitiveTenseSymbol(infinitiveTense) + ". " + Symbols.get().getVoiceSymbol(infinitiveVoice) + ".";
                case NOUN_LIKE -> Symbols.get().getNounLikeFormSymbol(nounLikeForm) + ". " + nounLikeDeclinedForm.formToString();
            };
        }
    }

    @Override
    public String toString() {
        return switch (getKind()) {
            case BASIC -> "Verb{form=" + conjugatedForm.toString() + ",mode=" + mode.toString().toLowerCase() + ",tense=" + tense.toString().toLowerCase() + ",voice=" + voice.toString().toLowerCase() + "}";
            case IMPERATIVE -> "Verb{imperative, number=" + imperativeNumber.toString().toLowerCase() + "}";
            case INFINITIVE -> "Verb{infinitive, tense=" + infinitiveTense.toString().toLowerCase() + ", voice=" + infinitiveVoice.toString().toLowerCase() + "}";
            case NOUN_LIKE -> "Verb{" + nounLikeForm.toString().toLowerCase() + ", form=" + nounLikeDeclinedForm.toString() + "}";
        };
    }

    // compare

    /*@Override
    public int old_compareTo(VerbForm o) {
        List<Kind> kindsInOrder = List.of(Kind.INFINITIVE, Kind.BASIC, Kind.NOUN_LIKE, Kind.IMPERATIVE);
        int compareKind = (kindsInOrder.indexOf(getKind()) - kindsInOrder.indexOf(o.getKind()));
        if (compareKind == 0) {
            switch (getKind()) {
                case INFINITIVE -> {
                    return (infinitiveTense.compareTo(o.getInfinitiveTense()) * Voice.values().length)
                            + (infinitiveVoice.compareTo(o.getInfinitiveVoice()));
                }
                case BASIC -> {
                    return (getVoice().compareTo(o.getVoice()) * Tense.values().length)
                           + (getTense().compareTo(o.getTense()) * Mode.values().length)
                           + (getMode().compareTo(o.getMode()) * CNumber.values().length)
                           + getConjugatedForm().compareTo(o.getConjugatedForm());
                }
                case NOUN_LIKE -> {
                    return (getNounLikeForm().compareTo(o.getNounLikeForm()) * Casus.values().length)
                           + getNounLikeDeclinedForm().compareTo(o.getNounLikeDeclinedForm());
                }
                case IMPERATIVE -> {
                    return getImperativeNumber().compareTo(o.getImperativeNumber());
                }
            }
        } else {
            return compareKind * 1000;
        }
        return 0;
    }

    @Override
    public int compareTo(VerbForm o) {
        List<Kind> kindsInOrder = List.of(Kind.INFINITIVE, Kind.BASIC, Kind.NOUN_LIKE, Kind.IMPERATIVE);
        int compareKind = (kindsInOrder.indexOf(getKind()) - kindsInOrder.indexOf(o.getKind()));
        if (compareKind == 0) {
            switch (getKind()) {
                case INFINITIVE -> {
                    List<InfinitiveTense> infinitiveTenses = List.of(InfinitiveTense.values());
                    infinitiveTenses.indexOf(getInfinitiveTense()) - infinitiveTenses.indexOf(o.getInfinitiveTense())
                    if (infinitiveTenses.indexOf(getInfinitiveTense()) - infinitiveTenses.indexOf(o.getInfinitiveTense()) >)
                }
            }
        } else {
            return compareKind;
        }
    }*/

    public static Comparator<VerbForm> comparator() {
        return (o1, o2) -> {
            List<Kind> kindsInOrder = List.of(Kind.INFINITIVE, Kind.BASIC, Kind.NOUN_LIKE, Kind.IMPERATIVE);
            int compareKind = (kindsInOrder.indexOf(o1.getKind()) - kindsInOrder.indexOf(o2.getKind()));
            if (compareKind == 0) {
                switch (o1.getKind()) {
                    case INFINITIVE -> {
                        return Comparator
                                .comparing(VerbForm::getInfinitiveVoice)
                                .thenComparing(VerbForm::getInfinitiveTense)
                                .compare(o1, o2);
                    }
                    case BASIC -> {
                        return Comparator
                                .comparing(VerbForm::getVoice)
                                .thenComparing(VerbForm::getMode)
                                .thenComparing(VerbForm::getTense)
                                .thenComparing(VerbForm::getConjugatedForm)
                                .compare(o1, o2);
                    }
                    case NOUN_LIKE -> {
                        return Comparator
                                .comparing(VerbForm::getNounLikeForm)
                                .thenComparing(o -> o.getNounLikeDeclinedForm().getCasus())
                                .thenComparing(o -> o.getNounLikeDeclinedForm().getNumber())
                                .thenComparing(o -> o.getNounLikeDeclinedForm().getGender())
                                .compare(o1, o2);
                    }
                    case IMPERATIVE -> {
                        return Comparator
                                .comparing(VerbForm::getImperativeNumber)
                                .compare(o1, o2);
                    }
                }
            } else {
                return compareKind;
            }
            return 0;
        };
    }
}