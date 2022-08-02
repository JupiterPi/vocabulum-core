package jupiterpi.vocabulum.core.db.classes;

import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationSchema;
import org.bson.Document;

import java.util.List;

public interface ConjugationClasses {
    void loadConjugationSchemas(Iterable<Document> documents) throws LoadingDataException;

    ConjugationSchema makeSchema(Document document) throws LoadingDataException;

    ConjugationSchema getSchema(String name);

    List<ConjugationSchema> getAll();

    // utility fields

    ConjugationSchema a_Conjugation();
    ConjugationSchema e_Conjugation();
    ConjugationSchema ii_Conjugation();
    ConjugationSchema cons_Conjugation();
    ConjugationSchema i_Conjugation();
}
