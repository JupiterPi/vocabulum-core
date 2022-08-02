package jupiterpi.vocabulum.core.db.classes;

import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationSchema;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.SimpleConjugationSchema;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbConjugationClasses implements ConjugationClasses {
    private Map<String, ConjugationSchema> conjugationSchemas;

    @Override
    public void loadConjugationSchemas(Iterable<Document> documents) throws LoadingDataException {
        conjugationSchemas = new HashMap<>();
        for (Document document : documents) {
            String name = document.getString("name");
            conjugationSchemas.put(name, makeSchema(document));
        }
    }

    @Override
    public ConjugationSchema makeSchema(Document document) throws LoadingDataException {
        return SimpleConjugationSchema.readFromDocument(document);
    }

    @Override
    public ConjugationSchema getSchema(String name) {
        return conjugationSchemas.get(name);
    }

    @Override
    public List<ConjugationSchema> getAll() {
        List<ConjugationSchema> schemas = new ArrayList<>();
        for (String key : conjugationSchemas.keySet()) {
            schemas.add(conjugationSchemas.get(key));
        }
        return schemas;
    }

    // utility fields


    @Override
    public ConjugationSchema a_Conjugation() {
        return getSchema("a");
    }

    @Override
    public ConjugationSchema e_Conjugation() {
        return getSchema("e");
    }

    @Override
    public ConjugationSchema ii_Conjugation() {
        return getSchema("ii");
    }

    @Override
    public ConjugationSchema cons_Conjugation() {
        return getSchema("cons");
    }

    @Override
    public ConjugationSchema i_Conjugation() {
        return getSchema("i");
    }
}
