package jupiterpi.vocabulum.core.i18n;

import org.bson.Document;

public interface I18ns {
    void loadI18ns(Iterable<Document> documents);

    I18n getI18n(String language);

    // utility fields

    I18n internal();
    I18n en();
    I18n de();
}
