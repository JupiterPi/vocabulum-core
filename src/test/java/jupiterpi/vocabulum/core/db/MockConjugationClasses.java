package jupiterpi.vocabulum.core.db;

import jupiterpi.vocabulum.core.db.classes.ConjugationClasses;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationSchema;
import org.bson.Document;

import java.util.List;

public class MockConjugationClasses implements ConjugationClasses {
    //TODO implement

    @Override
    public void loadConjugationSchemas(Iterable<Document> documents) throws LoadingDataException {

    }

    @Override
    public ConjugationSchema makeSchema(Document document) throws LoadingDataException {
        return null;
    }

    @Override
    public ConjugationSchema getSchema(String name) {
        return null;
    }

    @Override
    public List<ConjugationSchema> getAll() {
        return null;
    }

    @Override
    public ConjugationSchema a_Conjugation() {
        return null;
    }

    @Override
    public ConjugationSchema e_Conjugation() {
        return null;
    }

    @Override
    public ConjugationSchema ii_Conjugation() {
        return null;
    }

    @Override
    public ConjugationSchema cons_Conjugation() {
        return null;
    }

    @Override
    public ConjugationSchema i_Conjugation() {
        return null;
    }
}
