package jupiterpi.vocabulum.core.i18n;

import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.bson.Document;

public class DbI18n implements I18n {
    private String name;
    private Document texts;
    private Document str_texts;

    public DbI18n(String name, Document texts, Document str_texts) {
        this.name = name;
        this.texts = texts;
        this.str_texts = str_texts;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Document getTexts() {
        return this.texts;
    }

    // casus
    @Override
    public String getCasusSymbol(Casus casus) {
        Document document = (Document) str_texts.get("casus");
        return document.getString(casus.toString().toLowerCase());
    }
    @Override
    public Casus casusFromSymbol(String symbol) throws ParserException {
        for (Casus casus : Casus.values()) {
            if (getCasusSymbol(casus).equals(symbol)) return casus;
        }
        throw new ParserException("Invalid casus: " + symbol);
    }

    // nNumber
    @Override
    public String getNumberSymbol(NNumber number) { // is duplicate with cNumber
        Document document = (Document) str_texts.get("number");
        return document.getString(number.toString().toLowerCase());
    }
    @Override
    public NNumber nNumberFromSymbol(String symbol) throws ParserException {
        for (NNumber number : NNumber.values()) {
            if (getNumberSymbol(number).equals(symbol)) return number;
        }
        throw new ParserException("Invalid nNumber: " + symbol);
    }

    // gender
    @Override
    public String getGenderSymbol(Gender gender) {
        Document document = (Document) str_texts.get("gender");
        return document.getString(gender.toString().toLowerCase());
    }
    @Override
    public Gender genderFromSymbol(String symbol) throws ParserException {
        for (Gender gender : Gender.values()) {
            if (getGenderSymbol(gender).equals(symbol)) return gender;
        }
        throw new ParserException("Invalid gender: " + symbol);
    }

    // comparative form
    @Override
    public String getComparativeFormSymbol(ComparativeForm comparativeForm) {
        Document document = (Document) str_texts.get("comparative_form");
        return document.getString(comparativeForm.toString().toLowerCase());
    }
    @Override
    public ComparativeForm comparativeFormFromSymbol(String symbol) throws ParserException {
        for (ComparativeForm comparativeForm : ComparativeForm.values()) {
            if (getComparativeFormSymbol(comparativeForm).equals(symbol)) return comparativeForm;
        }
        throw new ParserException("Invalid comparative form: " + symbol);
    }

    // adverb (flag)
    @Override
    public String getAdverbSymbol() {
        return str_texts.getString("adverb");
    }

    // person
    @Override
    public String getPersonSymbol(Person person) {
        Document document = (Document) str_texts.get("person");
        return document.getString(person.toString().toLowerCase());
    }
    @Override
    public Person personFromSymbol(String symbol) throws ParserException {
        for (Person person : Person.values()) {
            if (getPersonSymbol(person).equals(symbol)) return person;
        }
        throw new ParserException("Invalid person: " + symbol);
    }

    // "person" (cosmetic)
    @Override
    public String getPersonCosmetic() {
        return str_texts.getString("person_cosmetic");
    }

    // cNumber
    @Override
    public String getNumberSymbol(CNumber number) { // is duplicate with nNumber
        Document document = (Document) str_texts.get("number");
        return document.getString(number.toString().toLowerCase());
    }
    @Override
    public CNumber cNumberFromSymbol(String symbol) throws ParserException {
        for (CNumber number : CNumber.values()) {
            if (getNumberSymbol(number).equals(symbol)) return number;
        }
        throw new ParserException("Invalid cNumber: " + symbol);
    }

    // mode
    @Override
    public String getModeSymbol(Mode mode) {
        Document document = (Document) str_texts.get("mode");
        return document.getString(mode.toString().toLowerCase());
    }
    @Override
    public Mode modeFromSymbol(String symbol) throws ParserException {
        for (Mode mode : Mode.values()) {
            if (getModeSymbol(mode).equals(symbol)) return mode;
        }
        throw new ParserException("Invalid mode: " + symbol);
    }

    // tense
    @Override
    public String getTenseSymbol(Tense tense) {
        Document document = (Document) str_texts.get("tense");
        return document.getString(tense.toString().toLowerCase());
    }
    @Override
    public Tense tenseFromSymbol(String symbol) throws ParserException {
        for (Tense tense : Tense.values()) {
            if (getTenseSymbol(tense).equals(symbol)) return tense;
        }
        throw new ParserException("Invalid tense: " + symbol + " in i18n: " + getName());
    }

    // voice
    @Override
    public String getVoiceSymbol(Voice voice) {
        Document document = (Document) str_texts.get("voice");
        return document.getString(voice.toString().toLowerCase());
    }
    @Override
    public Voice voiceFromSymbol(String symbol) throws ParserException {
        for (Voice voice : Voice.values()) {
            if (getVoiceSymbol(voice).equals(symbol)) return voice;
        }
        throw new ParserException("Invalid voice: " + symbol);
    }

    @Override
    public String toString() {
        return "I18n{name=" + name + "}";
    }
}