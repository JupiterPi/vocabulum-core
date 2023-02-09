package jupiterpi.vocabulum.core.i18n;

import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;

public class SymbolsImpl implements Symbols {
    // --- nouns ---

    // casus

    @Override
    public String getCasusSymbol(Casus casus) {
        return switch (casus) {
            case NOM -> "Nom";
            case GEN -> "Gen";
            case DAT -> "Dat";
            case ACC -> "Akk";
            case ABL -> "Abl";
        };
    }

    @Override
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

    @Override
    public String getNumberSymbol(NNumber number) {
        return switch (number) {
            case SG -> "Sg";
            case PL -> "Pl";
        };
    }

    @Override
    public NNumber nNumberFromSymbol(String symbol) {
        return switch (symbol) {
            case "Sg" -> NNumber.SG;
            case "Pl" -> NNumber.PL;
            default -> null;
        };
    }

    // gender

    @Override
    public String getGenderSymbol(Gender gender) {
        return switch (gender) {
            case MASC -> "m";
            case FEM -> "f";
            case NEUT -> "n";
        };
    }

    @Override
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

    @Override
    public String getComparativeFormSymbol(ComparativeForm comparativeForm) {
        return switch (comparativeForm) {
            case POSITIVE -> "Pos";
            case COMPARATIVE -> "Komp";
            case SUPERLATIVE -> "Sup";
        };
    }

    @Override
    public ComparativeForm comparativeFormFromSymbol(String symbol) {
        return switch (symbol) {
            case "Pos" -> ComparativeForm.POSITIVE;
            case "Komp" -> ComparativeForm.COMPARATIVE;
            case "Sup" -> ComparativeForm.SUPERLATIVE;
            default -> null;
        };
    }

    // adverb (flag)

    @Override
    public String getAdverbFlag() {
        return "Adv";
    }

    // person

    @Override
    public String getPersonSymbol(Person person) {
        return switch (person) {
            case FIRST -> "1";
            case SECOND -> "2";
            case THIRD -> "3";
        };
    }

    @Override
    public Person personFromSymbol(String symbol) {
        return switch (symbol) {
            case "1" -> Person.FIRST;
            case "2" -> Person.SECOND;
            case "3" -> Person.THIRD;
            default -> null;
        };
    }

    // "person" (cosmetic)

    @Override
    public String getPersonCosmetic() {
        return "Pers";
    }

    // number (CNumber)

    @Override
    public String getNumberSymbol(CNumber number) {
        return switch (number) {
            case SG -> "Sg";
            case PL -> "Pl";
        };
    }

    @Override
    public CNumber cNumberFromSymbol(String symbol) {
        return switch (symbol) {
            case "Sg" -> CNumber.SG;
            case "Pl" -> CNumber.PL;
            default -> null;
        };
    }

    // mode

    @Override
    public String getModeSymbol(Mode mode) {
        return switch (mode) {
            case INDICATIVE -> "Ind";
            case CONJUNCTIVE -> "Konj";
        };
    }

    @Override
    public Mode modeFromSymbol(String symbol) {
        return switch (symbol) {
            case "Ind" -> Mode.INDICATIVE;
            case "Konj" -> Mode.CONJUNCTIVE;
            default -> null;
        };
    }

    // tense

    @Override
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

    @Override
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

    @Override
    public String getVoiceSymbol(Voice voice) {
        return switch (voice) {
            case ACTIVE -> "Akt";
            case PASSIVE -> "Pass";
        };
    }

    @Override
    public Voice voiceFromSymbol(String symbol) {
        return switch (symbol) {
            case "Akt" -> Voice.ACTIVE;
            case "Pass" -> Voice.PASSIVE;
            default -> null;
        };
    }

    // imperative (flag)

    @Override
    public String getImperativeFlag() {
        return "Imp";
    }

    // infinitive (flag)

    @Override
    public String getInfinitiveFlag() {
        return "Inf";
    }

    // infinitive tense

    @Override
    public String getInfinitiveTenseSymbol(InfinitiveTense infinitiveTense) {
        return switch (infinitiveTense) {
            case PRESENT -> "Präs";
            case PERFECT -> "Perf";
            case FUTURE -> "Fut";
        };
    }

    @Override
    public InfinitiveTense infinitiveTenseFromSymbol(String symbol) {
        return switch (symbol) {
            case "Präs" -> InfinitiveTense.PRESENT;
            case "Perf" -> InfinitiveTense.PERFECT;
            case "Fut" -> InfinitiveTense.FUTURE;
            default -> null;
        };
    }

    // noun like form

    @Override
    public String getNounLikeFormSymbol(NounLikeForm nounLikeForm) {
        return switch (nounLikeForm) {
            case PPP -> "PPP";
            case PPA -> "PPA";
            case PFA -> "PFA";
            case GERUNDIUM -> "Gerund";
            case GERUNDIVUM -> "Gerundv";
        };
    }

    @Override
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
