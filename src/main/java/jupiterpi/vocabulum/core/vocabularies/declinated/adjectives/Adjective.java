package jupiterpi.vocabulum.core.vocabularies.declinated.adjectives;

import jupiterpi.vocabulum.core.Database;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionClasses;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionSchema;
import org.bson.Document;

public class Adjective extends Vocabulary {
    private static final DeclensionSchema masculineDeclensionSchema = DeclensionClasses.o_Declension;
    private static final DeclensionSchema feminineDeclensionSchema = DeclensionClasses.a_Declension;
    private static final DeclensionSchema neuterDeclensionSchema = DeclensionClasses.o_Declension;
    private static final DeclensionSchema consonantalDeclensionSchema = DeclensionClasses.cons_adjectives_Declension;

    private static final Document adjectivesData = Database.other.find(new Document("id", "adjectives")).first();

    private String nom_sg_masc;
    private String nom_sg_fem;
    private String nom_sg_neut;
    private Kind kind;
    public enum Kind {
        AO, CONS
    }
    private String root;

    public Adjective(String nom_sg_masc, String nom_sg_fem, String nom_sg_neut, String root) {
        this.nom_sg_masc = nom_sg_masc;
        this.nom_sg_fem = nom_sg_fem;
        this.nom_sg_neut = nom_sg_neut;
        this.root = root;
    }

    private Adjective() {}
    public static Adjective fromBaseForms(String nom_sg_masc, String nom_sg_fem, String nom_sg_neut) throws DeclinedFormDoesNotExistException {
        Adjective adjective = new Adjective();
        adjective.nom_sg_masc = nom_sg_masc;
        adjective.nom_sg_fem = nom_sg_fem;
        adjective.nom_sg_neut = nom_sg_neut;

        if (
                /*nom_sg_masc.endsWith(masculineDeclensionSchema.getSuffix(DeclinedForm.get("Nom. Sg. m.")))      // incorrect, only depends on f. and n. ending
                &&*/ nom_sg_fem.endsWith(feminineDeclensionSchema.getSuffix(DeclinedForm.get("Nom. Sg. f.")))
                && nom_sg_neut.endsWith(neuterDeclensionSchema.getSuffix(DeclinedForm.get("Nom. Sg. n.")))
        ) {
            adjective.kind = Kind.AO;
            adjective.root = nom_sg_fem.substring(0, nom_sg_fem.length() - feminineDeclensionSchema.getSuffix(DeclinedForm.get("Nom. Sg. f.")).length());
        } else {
            adjective.kind = Kind.CONS;
            adjective.root = "";
            for (int i = 0; i < nom_sg_fem.length() && i < nom_sg_neut.length(); i++) {
                String fem_c = nom_sg_fem.split("")[i];
                String neut_c = nom_sg_neut.split("")[i];
                if (fem_c.equals(neut_c)) adjective.root += fem_c;
                else break;
            }
        }

        return adjective;
    }
    public static Adjective fromBaseForm(String nom_sg, String gen_sg) throws DeclinedFormDoesNotExistException, ParserException {
        Adjective adjective = new Adjective();
        adjective.nom_sg_masc = nom_sg;
        adjective.nom_sg_fem = nom_sg;
        adjective.nom_sg_neut = nom_sg;

        String gen_sg_suffix = consonantalDeclensionSchema.getSuffix(DeclinedForm.get("Gen. Sg. m."));
        if (gen_sg.endsWith(gen_sg_suffix)) {
            adjective.root = gen_sg.substring(0, gen_sg.length() - gen_sg_suffix.length());
        } else {
            throw new ParserException("Could not determine root from Gen. Sg. form: " + gen_sg);
        }

        adjective.kind = Kind.CONS;
        return adjective;
    }

    public String makeForm(DeclinedForm form) throws DeclinedFormDoesNotExistException {
        return makeForm(new AdjectiveForm(form, ComparativeForm.POSITIVE));
    }
    public String makeForm(AdjectiveForm form) throws DeclinedFormDoesNotExistException {
        if (form.isAdverb()) {
            Document adverbsData = (Document) adjectivesData.get("adverbs");
            if (kind == Kind.AO) {
                return root + adverbsData.getString("ao_suffix");
            } else {
                Document exceptionData = (Document) adverbsData.get("cons_exception");
                if (nom_sg_masc.endsWith(exceptionData.getString("nom_sg_masc_suffix"))) {
                    return root + exceptionData.getString("suffix");
                } else {
                    return root + adverbsData.getString("cons_suffix");
                }
            }
        } else {
            DeclinedForm declinedForm = form.getDeclinedForm();
            declinedForm.normalizeGender();
            if (declinedForm.fits(DeclinedForm.get("Nom. Sg."))) {
                return switch (declinedForm.getGender()) {
                    case MASC -> nom_sg_masc;
                    case FEM -> nom_sg_fem;
                    case NEUT -> nom_sg_neut;
                };
            }
            if (kind == Kind.AO) {
                return root + switch (declinedForm.getGender()) {
                    case MASC -> masculineDeclensionSchema.getSuffix(declinedForm);
                    case FEM -> feminineDeclensionSchema.getSuffix(declinedForm);
                    case NEUT -> neuterDeclensionSchema.getSuffix(declinedForm);
                };
            } else {
                return root + consonantalDeclensionSchema.getSuffix(declinedForm);
            }
        }
    }

    @Override
    public String getBaseForm() {
        return nom_sg_masc;
    }

    @Override
    public Vocabulary.Kind getKind() {
        return Vocabulary.Kind.ADJECTIVE;
    }
}