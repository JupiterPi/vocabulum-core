package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.i18n.I18ns;
import org.bson.Document;

public class MockI18ns implements I18ns {
    //TODO implement

    @Override
    public void loadI18ns(Iterable<Document> documents) {

    }

    @Override
    public I18n getI18n(String language) {
        return null;
    }

    // utility fields

    @Override
    public I18n internal() {
        return null;
    }

    @Override
    public I18n en() {
        return null;
    }

    @Override
    public I18n de() {
        return null;
    }
}
