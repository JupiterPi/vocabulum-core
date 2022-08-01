package jupiterpi.vocabulum.core.i18n;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class I18ns {
    private Map<String, I18n> i18ns;

    public void loadI18ns(Iterable<Document> documents) {
        i18ns = new HashMap<>();
        for (Document document : documents) {
            String name = document.getString("language");
            Document texts = (Document) document.get("texts");
            Document str_texts = (Document) document.get("str-texts");
            i18ns.put(name, new DbI18n(name, texts, str_texts));
        }
        assignUtilityFields();
    }

    public I18n internal;
    public I18n en;
    public I18n de;

    private void assignUtilityFields() {
        internal = getI18n("int");
        en = getI18n("en");
        de = getI18n("de");
    }

    public I18n getI18n(String language) {
        return i18ns.get(language);
    }
}