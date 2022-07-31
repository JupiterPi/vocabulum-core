package jupiterpi.vocabulum.core.i18n;

import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.bson.Document;

public class MockI18n implements I18n {
    @Override
    public String getName() {
        return "mock-internal";
    }

    @Override
    public Document getTexts() {
        throw new RuntimeException("Not supposed to get texts from mocked I18n");
    }

    @Override
    public String getCasusSymbol(Casus casus) {
        return switch (casus) {
            case NOM -> "Nom";
            case GEN -> "Gen";
            case DAT -> "Dat";
            case ACC -> "Acc";
            case ABL -> "Abl";
        };
    }

    @Override
    public Casus casusFromSymbol(String symbol) throws ParserException {
        return switch (symbol) {
            case "Nom" -> Casus.NOM;
            case "Gen" -> Casus.GEN;
            case "Dat" -> Casus.DAT;
            case "Acc" -> Casus.ACC;
            case "Abl" -> Casus.ABL;
            default -> throw new ParserException("Invalid casus: " + symbol);
        };
    }

    @Override
    public String getNumberSymbol(NNumber number) {
        return switch (number) {
            case SG -> "Sg";
            case PL -> "Pl";
        };
    }

    @Override
    public NNumber nNumberFromSymbol(String symbol) throws ParserException {
        return switch (symbol) {
            case "Sg" -> NNumber.SG;
            case "Pl" -> NNumber.PL;
            default -> throw new ParserException("Invalid number: " + symbol);
        };
    }

    @Override
    public String getGenderSymbol(Gender gender) {
        return switch (gender) {
            case MASC -> "m";
            case FEM -> "f";
            case NEUT -> "n";
        };
    }

    @Override
    public Gender genderFromSymbol(String symbol) throws ParserException {
        return switch (symbol) {
            case "m" -> Gender.MASC;
            case "f" -> Gender.FEM;
            case "n" -> Gender.NEUT;
            default -> throw new ParserException("Invalid gender: " + symbol);
        };
    }

    @Override
    public String getComparativeFormSymbol(ComparativeForm comparativeForm) {
        return switch (comparativeForm) {
            case POSITIVE -> "Pos";
            case COMPARATIVE -> "Comp";
            case SUPERLATIVE -> "Sup";
        };
    }

    @Override
    public ComparativeForm comparativeFormFromSymbol(String symbol) throws ParserException {
        return switch (symbol) {
            case "Pos" -> ComparativeForm.POSITIVE;
            case "Comp" -> ComparativeForm.COMPARATIVE;
            case "Sup" -> ComparativeForm.SUPERLATIVE;
            default -> throw new ParserException("Invalid comparative form: " + symbol);
        };
    }

    @Override
    public String getAdverbSymbol() {
        return "Adv";
    }

    @Override
    public String getPersonSymbol(Person person) {
        return switch (person) {
            case FIRST -> "1";
            case SECOND -> "2";
            case THIRD -> "3";
        };
    }

    @Override
    public Person personFromSymbol(String symbol) throws ParserException {
        return switch (symbol) {
            case "1" -> Person.FIRST;
            case "2" -> Person.SECOND;
            case "3" -> Person.THIRD;
            default -> throw new ParserException("Invalid person: " + symbol);
        };
    }

    @Override
    public String getPersonCosmetic() {
        return "Pers";
    }

    @Override
    public String getNumberSymbol(CNumber number) {
        return switch (number) {
            case SG -> "Sg";
            case PL -> "Pl";
        };
    }

    @Override
    public CNumber cNumberFromSymbol(String symbol) throws ParserException {
        return switch (symbol) {
            case "Sg" -> CNumber.SG;
            case "Pl" -> CNumber.PL;
            default -> throw new ParserException("Invalid number: " + symbol);
        };
    }

    @Override
    public String getModeSymbol(Mode mode) {
        return switch (mode) {
            case INDICATIVE -> "Ind";
            case CONJUNCTIVE -> "Conj";
        };
    }

    @Override
    public Mode modeFromSymbol(String symbol) throws ParserException {
        return switch (symbol) {
            case "Ind" -> Mode.INDICATIVE;
            case "Conj" -> Mode.CONJUNCTIVE;
            default -> throw new ParserException("Invalid mode: " + symbol);
        };
    }

    @Override
    public String getTenseSymbol(Tense tense) {
        return switch (tense) {
            case PRESENT -> "Pres";
            case IMPERFECT -> "Imperf";
            case PERFECT -> "Perf";
            case FUTURE_I -> "FutI";
            case FUTURE_II -> "FutII";
            case PLUPERFECT -> "Pluperf";
        };
    }

    @Override
    public Tense tenseFromSymbol(String symbol) throws ParserException {
        return switch (symbol) {
            case "Pres" -> Tense.PRESENT;
            case "Imperf" -> Tense.IMPERFECT;
            case "Perf" -> Tense.PERFECT;
            case "FutI" -> Tense.FUTURE_I;
            case "FutII" -> Tense.FUTURE_II;
            case "Pluperf" -> Tense.PLUPERFECT;
            default -> throw new ParserException("Invalid tense: " + symbol);
        };
    }

    @Override
    public String getVoiceSymbol(Voice voice) {
        return switch (voice) {
            case ACTIVE -> "Act";
            case PASSIVE -> "Pass";
        };
    }

    @Override
    public Voice voiceFromSymbol(String symbol) throws ParserException {
        return switch (symbol) {
            case "Act" -> Voice.ACTIVE;
            case "Pass" -> Voice.PASSIVE;
            default -> throw new ParserException("Invalid voice: " + symbol);
        };
    }
}