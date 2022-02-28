package jupiterpi.vocabulum.core.i18n;

import jupiterpi.vocabulum.core.Database;
import jupiterpi.vocabulum.core.Main;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.Number;
import org.bson.Document;

public class I18n {
    public static I18n internal = new I18n("int");
    public static I18n en = new I18n("en");
    public static I18n de = new I18n("de");
    public static I18n get() { return Main.i18n; }

    private String name;
    private Document texts;

    public I18n(String language) {
        name = language;
        texts = (Document) Database.texts.find(new Document("language", language)).first().get("str-texts");
    }

    public String getName() {
        return name;
    }

    public String getString(Casus casus) {
        Document document = (Document) texts.get("casus");
        return document.getString(casus.toString().toLowerCase());
    }

    public String getString(Number number) {
        Document document = (Document) texts.get("number");
        return document.getString(number.toString().toLowerCase());
    }

    public String getString(Gender gender) {
        Document document = (Document) texts.get("gender");
        return document.getString(gender.toString().toLowerCase());
    }

    @Override
    public String toString() {
        return "I18n{name=" + name + "}";
    }
}