package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.classes.DeclensionClasses;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionSchema;
import org.bson.Document;

import java.util.List;

public class MockDeclensionClasses implements DeclensionClasses {
    //TODO implement

    @Override
    public void loadDeclensionSchemas(Iterable<Document> documents) throws LoadingDataException {

    }

    @Override
    public DeclensionSchema makeSchema(Document document) throws LoadingDataException {
        return null;
    }

    @Override
    public Document getRaw(String name) {
        return null;
    }

    @Override
    public DeclensionSchema getSchema(String name) {
        return null;
    }

    @Override
    public List<DeclensionSchema> getAll() {
        return null;
    }

    @Override
    public DeclensionSchema a_Declension() {
        return null;
    }

    @Override
    public DeclensionSchema o_Declension() {
        return null;
    }

    @Override
    public DeclensionSchema cons_Declension() {
        return null;
    }

    @Override
    public DeclensionSchema e_Declension() {
        return null;
    }

    @Override
    public DeclensionSchema u_Declension() {
        return null;
    }

    @Override
    public DeclensionSchema cons_adjectives_Declension() {
        return null;
    }
}
