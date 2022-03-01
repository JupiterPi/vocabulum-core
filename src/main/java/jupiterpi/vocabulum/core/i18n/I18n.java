package jupiterpi.vocabulum.core.i18n;

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

    public String getString(Casus casus) {
        Document document = (Document) str_texts.get("casus");
        return document.getString(casus.toString().toLowerCase());
    }

    public String getString(Number number) {
        Document document = (Document) str_texts.get("number");
        return document.getString(number.toString().toLowerCase());
    }

    public String getString(Gender gender) {
        Document document = (Document) str_texts.get("gender");
        return document.getString(gender.toString().toLowerCase());
    }

    @Override
    public String toString() {
        return "I18n{name=" + name + "}";
    }
}