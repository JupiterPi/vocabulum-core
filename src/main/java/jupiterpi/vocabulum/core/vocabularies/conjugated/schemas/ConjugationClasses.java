package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas;

import jupiterpi.vocabulum.core.Database;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class ConjugationClasses {
    private static Map<String, Document> conjugationSchemasRaw;
    private static Map<String, ConjugationSchema> conjugationSchemas;

    public static void loadConjugationSchemas() {
        conjugationSchemasRaw = new HashMap<>();
        conjugationSchemas = new HashMap<>();
        for (Document document : Database.conjugation_schemas.find()) {
            String name = document.getString("name");
            conjugationSchemasRaw.put(name, document);
            conjugationSchemas.put(name, makeSchema(document));
        }

        assignUtilityFields();
    }

    public static ConjugationSchema makeSchema(Document document) {
        //TODO implement
        return null;
    }

    // utility fields

    public static ConjugationSchema a_Conjugation;
    public static ConjugationSchema e_Conjugation;
    public static ConjugationSchema ii_Conjugation;
    public static ConjugationSchema cons_Conjugation;
    public static ConjugationSchema i_Conjugation;

    private static void assignUtilityFields() {
        a_Conjugation = get("a");
        e_Conjugation = get("e");
        ii_Conjugation = get("ii");
        cons_Conjugation = get("cons");
        i_Conjugation = get("i");
    }
}
