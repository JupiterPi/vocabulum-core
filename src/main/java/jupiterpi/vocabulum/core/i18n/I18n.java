package jupiterpi.vocabulum.core.i18n;

import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.bson.Document;

public class I18n {
    private String name;
    private Document texts;
    private Document str_texts;

    public I18n(String name, Document texts, Document str_texts) {
        this.name = name;
        this.texts = texts;
        this.str_texts = str_texts;
    }

    public String getName() {
        return name;
    }

    public Document getTexts() {
        return this.texts;
    }

    // casus
    public String getCasusSymbol(Casus casus) {
        Document document = (Document) str_texts.get("casus");
        return document.getString(casus.toString().toLowerCase());
    }
    public Casus casusFromSymbol(String symbol) throws ParserException {
        for (Casus casus : Casus.values()) {
            if (getCasusSymbol(casus).equals(symbol)) return casus;
        }
        throw new ParserException("Invalid casus: " + symbol);
    }

    // nNumber
    public String getNumberSymbol(NNumber number) { // is duplicate with cNumber
        Document document = (Document) str_texts.get("number");
        return document.getString(number.toString().toLowerCase());
    }
    public NNumber nNumberFromSymbol(String symbol) throws ParserException {
        for (NNumber number : NNumber.values()) {
            if (getNumberSymbol(number).equals(symbol)) return number;
        }
        throw new ParserException("Invalid nNumber: " + symbol);
    }

    // gender
    public String getGenderSymbol(Gender gender) {
        Document document = (Document) str_texts.get("gender");
        return document.getString(gender.toString().toLowerCase());
    }
    public Gender genderFromSymbol(String symbol) throws ParserException {
        for (Gender gender : Gender.values()) {
            if (getGenderSymbol(gender).equals(symbol)) return gender;
        }
        throw new ParserException("Invalid gender: " + symbol);
    }

    // comparative form
    public String getComparativeFormSymbol(ComparativeForm comparativeForm) {
        Document document = (Document) str_texts.get("comparative_form");
        return document.getString(comparativeForm.toString().toLowerCase());
    }
    public ComparativeForm comparativeFormFromSymbol(String symbol) throws ParserException {
        for (ComparativeForm comparativeForm : ComparativeForm.values()) {
            if (getComparativeFormSymbol(comparativeForm).equals(symbol)) return comparativeForm;
        }
        throw new ParserException("Invalid comparative form: " + symbol);
    }

    // adverb (flag)
    public String getAdverbFlag() {
        return str_texts.getString("adverb");
    }

    // person
    public String getPersonSymbol(Person person) {
        Document document = (Document) str_texts.get("person");
        return document.getString(person.toString().toLowerCase());
    }
    public Person personFromSymbol(String symbol) throws ParserException {
        for (Person person : Person.values()) {
            if (getPersonSymbol(person).equals(symbol)) return person;
        }
        throw new ParserException("Invalid person: " + symbol);
    }

    // "person" (cosmetic)
    public String getPersonCosmetic() {
        return str_texts.getString("person_cosmetic");
    }

    // cNumber
    public String getNumberSymbol(CNumber number) { // is duplicate with nNumber
        Document document = (Document) str_texts.get("number");
        return document.getString(number.toString().toLowerCase());
    }
    public CNumber cNumberFromSymbol(String symbol) throws ParserException {
        for (CNumber number : CNumber.values()) {
            if (getNumberSymbol(number).equals(symbol)) return number;
        }
        throw new ParserException("Invalid cNumber: " + symbol);
    }

    // mode
    public String getModeSymbol(Mode mode) {
        Document document = (Document) str_texts.get("mode");
        return document.getString(mode.toString().toLowerCase());
    }
    public Mode modeFromSymbol(String symbol) throws ParserException {
        for (Mode mode : Mode.values()) {
            if (getModeSymbol(mode).equals(symbol)) return mode;
        }
        throw new ParserException("Invalid mode: " + symbol);
    }

    // tense
    public String getTenseSymbol(Tense tense) {
        Document document = (Document) str_texts.get("tense");
        return document.getString(tense.toString().toLowerCase());
    }
    public Tense tenseFromSymbol(String symbol) throws ParserException {
        for (Tense tense : Tense.values()) {
            if (getTenseSymbol(tense).equals(symbol)) return tense;
        }
        throw new ParserException("Invalid tense: " + symbol + " in i18n: " + getName());
    }

    // voice
    public String getVoiceSymbol(Voice voice) {
        Document document = (Document) str_texts.get("voice");
        return document.getString(voice.toString().toLowerCase());
    }
    public Voice voiceFromSymbol(String symbol) throws ParserException {
        for (Voice voice : Voice.values()) {
            if (getVoiceSymbol(voice).equals(symbol)) return voice;
        }
        throw new ParserException("Invalid voice: " + symbol);
    }

    // imperative (flag)
    public String getImperativeFlag() {
        return str_texts.getString("imperative");
    }

    // infinitive (flag)
    public String getInfinitiveFlag() {
        return str_texts.getString("infinitive");
    }

    // infinitive tense
    public String getInfinitiveTenseSymbol(InfinitiveTense infinitiveTense) {
        Document document = (Document) str_texts.get("infinitive_tense");
        return document.getString(infinitiveTense.toString().toLowerCase());
    }
    public InfinitiveTense infinitiveTenseFromSymbol(String symbol) throws ParserException {
        for (InfinitiveTense infinitiveTense : InfinitiveTense.values()) {
            if (getInfinitiveTenseSymbol(infinitiveTense).equals(symbol)) return infinitiveTense;
        }
        throw new ParserException("Invalid infinitive tense: " + symbol);
    }

    // noun-like form
    public String getNounLikeFormSymbol(NounLikeForm nounLikeForm) {
        Document document = (Document) str_texts.get("noun_like_form");
        return document.getString(nounLikeForm.toString().toLowerCase());
    }
    public NounLikeForm nounLikeFormFromSymbol(String symbol) throws ParserException {
        for (NounLikeForm nounLikeForm : NounLikeForm.values()) {
            if (getNounLikeFormSymbol(nounLikeForm).equals(symbol)) return nounLikeForm;
        }
        throw new ParserException("Invalid noun-like form: " + symbol);
    }
    
    public String toString() {
        return "I18n{name=" + name + "}";
    }
}