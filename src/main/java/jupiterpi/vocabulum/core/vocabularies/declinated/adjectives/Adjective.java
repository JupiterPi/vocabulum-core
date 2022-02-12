package jupiterpi.vocabulum.core.vocabularies.declinated.adjectives;

import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionClasses;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionSchema;

public class Adjective extends Vocabulary {
    private static final DeclensionSchema masculineDeclensionSchema = DeclensionClasses.o_Declension;
    private static final DeclensionSchema feminineDeclensionSchema = DeclensionClasses.a_Declension;
    private static final DeclensionSchema neuterDeclensionSchema = DeclensionClasses.o_Declension;
    private static final DeclensionSchema consonantalDeclensionSchema = DeclensionClasses.cons_Declension;

    private String nom_sg_masc;
    private String nom_sg_fem;
    private String nom_sg_neut;
    private String root;

    public Adjective(String nom_sg_masc, String nom_sg_fem, String nom_sg_neut, String root) {
        this.nom_sg_masc = nom_sg_masc;
        this.nom_sg_fem = nom_sg_fem;
        this.nom_sg_neut = nom_sg_neut;
        this.root = root;
    }

    private Adjective() {}
    public static Adjective fromBaseForms(String nom_sg_masc, String nom_sg_fem, String nom_sg_neut) {
        Adjective adjective = new Adjective();
        adjective.nom_sg_masc = nom_sg_masc;
        adjective.nom_sg_fem = nom_sg_fem;
        adjective.nom_sg_neut = nom_sg_neut;

        adjective.root = "";
        for (int i = 0; i < nom_sg_fem.length() && i < nom_sg_neut.length(); i++) {
            String fem_c = nom_sg_fem.split("")[i];
            String neut_c = nom_sg_neut.split("")[i];
            if (fem_c.equals(neut_c)) adjective.root += fem_c;
            else break;
        }

        return adjective;
    }
    public static Adjective fromBaseForm(String nom_sg, String gen_sg) throws DeclinedFormDoesNotExistException, ParserException {
        Adjective adjective = new Adjective();
        adjective.nom_sg_masc = nom_sg;
        adjective.nom_sg_fem = nom_sg;
        adjective.nom_sg_neut = nom_sg;

        String gen_sg_suffix = consonantalDeclensionSchema.getSuffix(DeclinedForm.get("Gen. Sg."));
        if (gen_sg.endsWith(gen_sg_suffix)) {
            adjective.root = gen_sg.substring(0, gen_sg.length() - gen_sg_suffix.length());
        } else {
            throw new ParserException("Could not determine root from Gen. Sg. form: " + gen_sg);
        }

        return adjective;
    }

    public String getForm(DeclinedForm form) throws DeclinedFormDoesNotExistException {
        if (form.fits(DeclinedForm.get("Nom. Sg."))) {
            return switch (form.getGender()) {
                case MASC -> nom_sg_masc;
                case FEM -> nom_sg_fem;
                case NEUT -> nom_sg_neut;
            };
        }
        form.normalizeGender();
        return root + switch (form.getGender()) {
            case MASC -> consonantalDeclensionSchema.getSuffix(form);
            case FEM -> consonantalDeclensionSchema.getSuffix(form);
            case NEUT -> consonantalDeclensionSchema.getSuffix(form);
        };
    }

    @Override
    public String getBaseForm() {
        return nom_sg_masc;
    }

    @Override
    public Kind getKind() {
        return Kind.ADJECTIVE;
    }
}