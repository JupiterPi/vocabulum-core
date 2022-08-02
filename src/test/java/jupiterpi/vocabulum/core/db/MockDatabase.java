package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.i18n.I18ns;
import org.bson.Document;

public class MockDatabase extends Database {
    public static void inject() {
        Database.inject(new MockDatabase());
    }

    /////

    public MockDatabase() {
        this.i18ns = new MockI18ns();
        this.declensionClasses = new MockDeclensionClasses();
        this.conjugationClasses = new MockConjugationClasses();
        this.portions = new MockPortions();

        this.wordbase = new MockWordbase();
    }

    @Override
    public void connectAndLoad(String mongoConnectUrl) {
        // do nothing
    }

    @Override
    public void prepareWordbase() {
        // do nothing
    }

    @Override
    public Document getAdjectivesDocument() {
        return super.getAdjectivesDocument();
    }

    @Override
    public I18ns getI18ns() {
        return super.getI18ns();
    }
}