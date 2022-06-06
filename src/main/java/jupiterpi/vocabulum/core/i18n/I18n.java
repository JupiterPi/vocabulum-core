package jupiterpi.vocabulum.core.i18n;

import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.declined.adjectives.ComparativeForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Number;
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
        return texts;
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

    // number
    public String getNumberSymbol(Number number) {
        Document document = (Document) str_texts.get("number");
        return document.getString(number.toString().toLowerCase());
    }
    public Number numberFromSymbol(String symbol) throws ParserException {
        for (Number number : Number.values()) {
            if (getNumberSymbol(number).equals(symbol)) return number;
        }
        throw new ParserException("Invalid number: " + symbol);
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
    public String getAdverbSymbol() {
        return str_texts.getString("adverb");
    }

    @Override
    public String toString() {
        return "I18n{name=" + name + "}";
    }
}