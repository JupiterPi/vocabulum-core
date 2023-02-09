package jupiterpi.vocabulum.core.i18n;

import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;

/**
 * Provides methods for retrieving static strings for tokens etc.
 * This standard implementation provides them in German language for Vocabulum.
 */
public class Symbols {
    private static Symbols instance = null;
    public static Symbols get() {
        if (instance == null) {
            instance = new Symbols();
        }
        return instance;
    }

    /////

    // --- nouns ---

    // casus

    public String getCasusSymbol(Casus casus) {
        return switch (casus) {
            case NOM -> "Nom";
            case GEN -> "Gen";
            case DAT -> "Dat";
            case ACC -> "Akk";
            case ABL -> "Abl";
        };
    }

    public Casus casusFromSymbol(String symbol) {
        return switch (symbol) {
            case "Nom" -> Casus.NOM;
            case "Gen" -> Casus.GEN;
            case "Dat" -> Casus.DAT;
            case "Akk" -> Casus.ACC;
            case "Abl" -> Casus.ABL;
            default -> null;
        };
    }

    // number (NNumber)

    public String getNumberSymbol(NNumber number) {
        return switch (number) {
            case SG -> "Sg";
            case PL -> "Pl";
        };
    }

    public NNumber nNumberFromSymbol(String symbol) {
        return switch (symbol) {
            case "Sg" -> NNumber.SG;
            case "Pl" -> NNumber.PL;
            default -> null;
        };
    }

    // gender

    public String getGenderSymbol(Gender gender) {
        return switch (gender) {
            case MASC -> "m";
            case FEM -> "f";
            case NEUT -> "n";
        };
    }

    public Gender genderFromSymbol(String symbol) {
        return switch (symbol) {
            case "m" -> Gender.MASC;
            case "f" -> Gender.FEM;
            case "n" -> Gender.NEUT;
            default -> null;
        };
    }

    // --- adjectives ---

    // comparative form

    public String getComparativeFormSymbol(ComparativeForm comparativeForm) {
        return switch (comparativeForm) {
            case POSITIVE -> "Pos";
            case COMPARATIVE -> "Komp";
            case SUPERLATIVE -> "Sup";
        };
    }

    public ComparativeForm comparativeFormFromSymbol(String symbol) {
        return switch (symbol) {
            case "Pos" -> ComparativeForm.POSITIVE;
            case "Komp" -> ComparativeForm.COMPARATIVE;
            case "Sup" -> ComparativeForm.SUPERLATIVE;
            default -> null;
        };
    }

    // adverb (flag)

    public String getAdverbFlag() {
        return "Adv";
    }

    // person

    public String getPersonSymbol(Person person) {
        return switch (person) {
            case FIRST -> "1";
            case SECOND -> "2";
            case THIRD -> "3";
        };
    }

    public Person personFromSymbol(String symbol) {
        return switch (symbol) {
            case "1" -> Person.FIRST;
            case "2" -> Person.SECOND;
            case "3" -> Person.THIRD;
            default -> null;
        };
    }

    // "person" (cosmetic)

    public String getPersonCosmetic() {
        return "Pers";
    }

    // number (CNumber)

    public String getNumberSymbol(CNumber number) {
        return switch (number) {
            case SG -> "Sg";
            case PL -> "Pl";
        };
    }

    public CNumber cNumberFromSymbol(String symbol) {
        return switch (symbol) {
            case "Sg" -> CNumber.SG;
            case "Pl" -> CNumber.PL;
            default -> null;
        };
    }

    // mode

    public String getModeSymbol(Mode mode) {
        return switch (mode) {
            case INDICATIVE -> "Ind";
            case CONJUNCTIVE -> "Konj";
        };
    }

    public Mode modeFromSymbol(String symbol) {
        return switch (symbol) {
            case "Ind" -> Mode.INDICATIVE;
            case "Konj" -> Mode.CONJUNCTIVE;
            default -> null;
        };
    }

    // tense

    public String getTenseSymbol(Tense tense) {
        return switch (tense) {
            case PRESENT -> "Präs";
            case IMPERFECT -> "Imperf";
            case PERFECT -> "Perf";
            case PLUPERFECT -> "Plusq";
            case FUTURE_I -> "FutI";
            case FUTURE_II -> "FutII";
        };
    }

    public Tense tenseFromSymbol(String symbol) {
        return switch (symbol) {
            case "Präs" -> Tense.PRESENT;
            case "Imperf" -> Tense.IMPERFECT;
            case "Perf" -> Tense.PERFECT;
            case "Plusq" -> Tense.PLUPERFECT;
            case "FutI" -> Tense.FUTURE_I;
            case "FutII" -> Tense.FUTURE_II;
            default -> null;
        };
    }

    // voice

    public String getVoiceSymbol(Voice voice) {
        return switch (voice) {
            case ACTIVE -> "Akt";
            case PASSIVE -> "Pass";
        };
    }

    public Voice voiceFromSymbol(String symbol) {
        return switch (symbol) {
            case "Akt" -> Voice.ACTIVE;
            case "Pass" -> Voice.PASSIVE;
            default -> null;
        };
    }

    // imperative (flag)

    public String getImperativeFlag() {
        return "Imp";
    }

    // infinitive (flag)

    public String getInfinitiveFlag() {
        return "Inf";
    }

    // infinitive tense

    public String getInfinitiveTenseSymbol(InfinitiveTense infinitiveTense) {
        return switch (infinitiveTense) {
            case PRESENT -> "Präs";
            case PERFECT -> "Perf";
            case FUTURE -> "Fut";
        };
    }

    public InfinitiveTense infinitiveTenseFromSymbol(String symbol) {
        return switch (symbol) {
            case "Präs" -> InfinitiveTense.PRESENT;
            case "Perf" -> InfinitiveTense.PERFECT;
            case "Fut" -> InfinitiveTense.FUTURE;
            default -> null;
        };
    }

    // noun like form

    public String getNounLikeFormSymbol(NounLikeForm nounLikeForm) {
        return switch (nounLikeForm) {
            case PPP -> "PPP";
            case PPA -> "PPA";
            case PFA -> "PFA";
            case GERUNDIUM -> "Gerund";
            case GERUNDIVUM -> "Gerundv";
        };
    }

    public NounLikeForm nounLikeFormFromSymbol(String symbol) {
        return switch (symbol) {
            case "PPP" -> NounLikeForm.PPP;
            case "PPA" -> NounLikeForm.PPA;
            case "PFA" -> NounLikeForm.PFA;
            case "Gerund" -> NounLikeForm.GERUNDIUM;
            case "Gerundv" -> NounLikeForm.GERUNDIVUM;
            default -> null;
        };
    }
}
