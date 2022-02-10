package jupiterpi.vocabulum.core;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.Noun;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionClasses;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Number;

public class Main {
    public static MongoClient mongoClient;

    public static void main(String[] args) {
        System.out.println("----- Vocabulum Core -----");

        mongoClient = MongoClients.create("mongodb://localhost");

        DeclensionClasses.loadDeclensionSchemas(mongoClient);

        test1();
    }

    private static void test1() {
        try {
            Noun noun1 = new Noun(DeclensionClasses.a_Declension, "amica", "amic", Gender.FEM);
            System.out.println("Nom. Sg. f. = " + noun1.getForm(new DeclinedForm(Casus.NOM, Number.SG, Gender.FEM)));
            System.out.println("Gen. Pl. f. = " + noun1.getForm(new DeclinedForm(Casus.GEN, Number.PL, Gender.FEM)));

            Noun noun2 = new Noun(DeclensionClasses.o_Declension, "amicus", "amic", Gender.MASC);
            System.out.println("Nom. Sg. f. = " + noun2.getForm(new DeclinedForm(Casus.NOM, Number.SG, Gender.MASC)));
            System.out.println("Gen. Pl. f. = " + noun2.getForm(new DeclinedForm(Casus.ABL, Number.PL, Gender.MASC)));
        } catch (DeclinedFormDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }
}
