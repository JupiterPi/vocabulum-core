package jupiterpi.vocabulum.core.i18n;

import jupiterpi.vocabulum.core.db.Database;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class I18nManager {
    public I18n internal;
    public I18n en;
    public I18n de;

    private Map<String, I18n> i18ns = new HashMap<>();

    public I18nManager() {
        internal = readI18n("int");
        en = readI18n("en");
        de = readI18n("de");

        i18ns.put("internal", internal);
        i18ns.put("en", en);
        i18ns.put("de", de);
    }

    private I18n readI18n(String language) {
        String name = language;
        Document texts = (Document) Database.texts.find(new Document("language", language)).first().get("texts");
        Document str_texts = (Document) Database.texts.find(new Document("language", language)).first().get("str-texts");
        return new DbI18n(name, texts, str_texts);
    }

    public I18n get(String language) {
        I18n i18n = i18ns.get(language);
        if (i18n == null) {
            i18n = readI18n(language);
            i18ns.put(language, i18n);
        }
        return i18n;
    }
}