package jupiterpi.vocabulum.core.vocabularies.declinated.schemas;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.Number;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeclensionClasses {
    private static Map<String, Document> declensionSchemasRaw;
    private static Map<String, DeclensionSchema> declensionSchemas;

    public static void loadDeclensionSchemas(MongoClient client) throws LoadingDataException {
        MongoDatabase vocabulum_data = client.getDatabase("vocabulum_data");
        MongoCollection<Document> declension_schemas = vocabulum_data.getCollection("declension_schemas");

        declensionSchemasRaw = new HashMap<>();
        for (Document document : declension_schemas.find()) {
            String name = document.getString("name");
            declensionSchemasRaw.put(name, document);
        }

        declensionSchemas = new HashMap<>();
        for (Document document : declension_schemas.find()) {
            String name = document.getString("name");
            declensionSchemas.put(name, makeSchema(document));
        }

        assignUtilityFields();

        /*try {

            System.out.println("NOM, SG, MASC = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.NOM, Number.SG, Gender.MASC)));
            System.out.println("GEN, SG, MASC = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.GEN, Number.SG, Gender.MASC)));
            System.out.println("DAT, SG, MASC = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.DAT, Number.SG, Gender.MASC)));
            System.out.println("ACC, SG, MASC = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.ACC, Number.SG, Gender.MASC)));
            System.out.println("ABL, SG, MASC = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.ABL, Number.SG, Gender.MASC)));

            System.out.println("NOM, PL, MASC = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.NOM, Number.PL, Gender.MASC)));
            System.out.println("GEN, PL, MASC = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.GEN, Number.PL, Gender.MASC)));
            System.out.println("DAT, PL, MASC = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.DAT, Number.PL, Gender.MASC)));
            System.out.println("ACC, PL, MASC = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.ACC, Number.PL, Gender.MASC)));
            System.out.println("ABL, PL, MASC = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.ABL, Number.PL, Gender.MASC)));

            System.out.println();


            System.out.println("NOM, SG, FEM = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.NOM, Number.SG, Gender.FEM)));
            System.out.println("GEN, SG, FEM = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.GEN, Number.SG, Gender.FEM)));
            System.out.println("DAT, SG, FEM = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.DAT, Number.SG, Gender.FEM)));
            System.out.println("ACC, SG, FEM = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.ACC, Number.SG, Gender.FEM)));
            System.out.println("ABL, SG, FEM = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.ABL, Number.SG, Gender.FEM)));

            System.out.println("NOM, PL, FEM = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.NOM, Number.PL, Gender.FEM)));
            System.out.println("GEN, PL, FEM = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.GEN, Number.PL, Gender.FEM)));
            System.out.println("DAT, PL, FEM = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.DAT, Number.PL, Gender.FEM)));
            System.out.println("ACC, PL, FEM = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.ACC, Number.PL, Gender.FEM)));
            System.out.println("ABL, PL, FEM = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.ABL, Number.PL, Gender.FEM)));

            System.out.println();


            System.out.println("NOM, SG, NEUT = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.NOM, Number.SG, Gender.NEUT)));
            System.out.println("GEN, SG, NEUT = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.GEN, Number.SG, Gender.NEUT)));
            System.out.println("DAT, SG, NEUT = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.DAT, Number.SG, Gender.NEUT)));
            System.out.println("ACC, SG, NEUT = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.ACC, Number.SG, Gender.NEUT)));
            System.out.println("ABL, SG, NEUT = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.ABL, Number.SG, Gender.NEUT)));

            System.out.println("NOM, PL, NEUT = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.NOM, Number.PL, Gender.NEUT)));
            System.out.println("GEN, PL, NEUT = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.GEN, Number.PL, Gender.NEUT)));
            System.out.println("DAT, PL, NEUT = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.DAT, Number.PL, Gender.NEUT)));
            System.out.println("ACC, PL, NEUT = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.ACC, Number.PL, Gender.NEUT)));
            System.out.println("ABL, PL, NEUT = " + cons_adjectives_Declension.getSuffix(new DeclinedForm(Casus.ABL, Number.PL, Gender.NEUT)));

        } catch (DeclinedFormDoesNotExistException e) {
            e.printStackTrace();
        }*/
    }

    public static DeclensionSchema makeSchema(Document document) throws LoadingDataException {
        String schema = document.getString("schema");
        return switch (schema) {
            case "simple" -> SimpleDeclensionSchema.readFromDocument(document);
            case "gender_dependant" -> GenderDependantDeclensionSchema.readFromDocument(document);
            default -> null;
        };
    }

    public static Document getRaw(String name) {
        return declensionSchemasRaw.get(name);
    }

    public static DeclensionSchema get(String name) {
        return declensionSchemas.get(name);
    }

    public static List<DeclensionSchema> getAll() {
        List<DeclensionSchema> schemas = new ArrayList<>();
        for (String key : declensionSchemas.keySet()) {
            schemas.add(declensionSchemas.get(key));
        }
        return schemas;
    }

    // utility fields

    public static DeclensionSchema a_Declension;
    public static DeclensionSchema o_Declension;
    public static DeclensionSchema cons_Declension;
    public static DeclensionSchema e_Declension;
    public static DeclensionSchema u_Declension;

    public static DeclensionSchema cons_adjectives_Declension;

    private static void assignUtilityFields() {
        a_Declension = get("a");
        o_Declension = get("o");
        cons_Declension = get("cons");
        e_Declension = get("e");
        u_Declension = get("u");

        cons_adjectives_Declension = get("cons_adjectives");
    }
}