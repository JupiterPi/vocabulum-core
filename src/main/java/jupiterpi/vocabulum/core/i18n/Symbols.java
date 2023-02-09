package jupiterpi.vocabulum.core.i18n;

import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;

/**
 * Provides methods for retrieving static strings for tokens etc.
 * The standard implementation provides them in German language for Vocabulum.
 * @see SymbolsImpl
 */
public interface Symbols {
    // --- nouns ---

    // casus
    String getCasusSymbol(Casus casus);
    Casus casusFromSymbol(String symbol);

    // number (NNumber)
    String getNumberSymbol(NNumber number);
    NNumber nNumberFromSymbol(String symbol);

    // gender
    String getGenderSymbol(Gender gender);
    Gender genderFromSymbol(String symbol);

    // --- adjectives ---

    // comparative form
    String getComparativeFormSymbol(ComparativeForm comparativeForm);
    ComparativeForm comparativeFormFromSymbol(String symbol);

    // adverb (flag)
    String getAdverbFlag();

    // --- verbs ---

    // person
    String getPersonSymbol(Person person);
    Person personFromSymbol(String symbol);

    // "person" (cosmetic)
    String getPersonCosmetic();

    // number (CNumber)
    String getNumberSymbol(CNumber number);
    CNumber cNumberFromSymbol(String symbol);

    // mode
    String getModeSymbol(Mode mode);
    Mode modeFromSymbol(String symbol);

    // tense
    String getTenseSymbol(Tense tense);
    Tense tenseFromSymbol(String symbol);

    // voice
    String getVoiceSymbol(Voice voice);
    Voice voiceFromSymbol(String symbol);

    // imperative (flag)
    String getImperativeFlag();

    // infinitive (flag)
    String getInfinitiveFlag();

    // infinitive tense
    String getInfinitiveTenseSymbol(InfinitiveTense infinitiveTense);
    InfinitiveTense infinitiveTenseFromSymbol(String symbol);

    // noun like form
    String getNounLikeFormSymbol(NounLikeForm nounLikeForm);
    NounLikeForm nounLikeFormFromSymbol(String symbol);
}