package jupiterpi.vocabulum.core.i18n;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class DbI18ns implements I18ns {
    private Map<String, I18n> i18ns;

    @Override
    public void loadI18ns(Iterable<Document> documents) {
        i18ns = new HashMap<>();
        for (Document document : documents) {
            String name = document.getString("language");
            Document texts = (Document) document.get("texts");
            Document str_texts = (Document) document.get("str-texts");
            i18ns.put(name, new DbI18n(name, texts, str_texts));
        }
    }

    // utility fields


    @Override
    public I18n internal() {
        return getI18n("int");
    }

    @Override
    public I18n en() {
        return getI18n("en");
    }

    @Override
    public I18n de() {
        return getI18n("de");
    }

    @Override
    public I18n getI18n(String language) {
        return i18ns.get(language);
    }
}