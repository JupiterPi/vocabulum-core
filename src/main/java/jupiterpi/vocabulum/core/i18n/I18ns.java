package jupiterpi.vocabulum.core.i18n;

import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class I18ns {
    private Map<String, I18n> i18ns;

    public void loadI18ns(Iterable<Document> documents) {
        i18ns = new HashMap<>();
        for (Document document : documents) {
            String name = document.getString("language");
            Document texts = (Document) document.get("texts");
            List<String> translationArticles = document.getList("translation_articles", String.class);
            Document str_texts = (Document) document.get("str-texts");
            i18ns.put(name, new I18n(name, texts, translationArticles, str_texts));
        }
    }

    // utility fields

    public I18n internal() {
        return getI18n("int");
    }

    public I18n en() {
        return getI18n("en");
    }

    public I18n de() {
        return getI18n("de");
    }

    public I18n getI18n(String language) {
        return i18ns.get(language);
    }
}