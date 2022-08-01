package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.db.LoadingDataException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConjugationClasses {
    private static ConjugationClasses instance = null;
    public static ConjugationClasses get() {
        if (instance == null) instance = new ConjugationClasses();
        return instance;
    }

    /////

    private Map<String, ConjugationSchema> conjugationSchemas;

    public void loadConjugationSchemas() throws LoadingDataException {
        conjugationSchemas = new HashMap<>();
        for (Document document : Database.conjugation_schemas.find()) {
            String name = document.getString("name");
            conjugationSchemas.put(name, makeSchema(document));
        }

        assignUtilityFields();
    }

    public ConjugationSchema makeSchema(Document document) throws LoadingDataException {
        return SimpleConjugationSchema.readFromDocument(document);
    }

    public ConjugationSchema get(String name) {
        return conjugationSchemas.get(name);
    }

    public List<ConjugationSchema> getAll() {
        List<ConjugationSchema> schemas = new ArrayList<>();
        for (String key : conjugationSchemas.keySet()) {
            schemas.add(conjugationSchemas.get(key));
        }
        return schemas;
    }

    // utility fields

    public ConjugationSchema a_Conjugation;
    public ConjugationSchema e_Conjugation;
    public ConjugationSchema ii_Conjugation;
    public ConjugationSchema cons_Conjugation;
    public ConjugationSchema i_Conjugation;

    private void assignUtilityFields() {
        a_Conjugation = get("a");
        e_Conjugation = get("e");
        ii_Conjugation = get("ii");
        cons_Conjugation = get("cons");
        i_Conjugation = get("i");
    }
}
