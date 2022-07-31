package jupiterpi.vocabulum.core.i18n;

import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.bson.Document;

public interface I18n {
    String getName();

    Document getTexts();

    // casus
    String getCasusSymbol(Casus casus);

    Casus casusFromSymbol(String symbol) throws ParserException;

    // nNumber
    String getNumberSymbol(NNumber number);

    NNumber nNumberFromSymbol(String symbol) throws ParserException;

    // gender
    String getGenderSymbol(Gender gender);

    Gender genderFromSymbol(String symbol) throws ParserException;

    // comparative form
    String getComparativeFormSymbol(ComparativeForm comparativeForm);

    ComparativeForm comparativeFormFromSymbol(String symbol) throws ParserException;

    // adverb (flag)
    String getAdverbSymbol();

    // person
    String getPersonSymbol(Person person);

    Person personFromSymbol(String symbol) throws ParserException;

    // "person" (cosmetic)
    String getPersonCosmetic();

    // cNumber
    String getNumberSymbol(CNumber number);

    CNumber cNumberFromSymbol(String symbol) throws ParserException;

    // mode
    String getModeSymbol(Mode mode);

    Mode modeFromSymbol(String symbol) throws ParserException;

    // tense
    String getTenseSymbol(Tense tense);

    Tense tenseFromSymbol(String symbol) throws ParserException;

    // voice
    String getVoiceSymbol(Voice voice);

    Voice voiceFromSymbol(String symbol) throws ParserException;
}
