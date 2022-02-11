package jupiterpi.vocabulum.core.vocabularies.declinated;

import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionClasses;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionSchema;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Number;

public class Noun extends Vocabulary {
    private DeclensionSchema declensionSchema;
    private String nom_sg;
    private String root;
    private Gender gender;

    public Noun(DeclensionSchema declensionSchema, String nom_sg, String root, Gender gender) {
        this.declensionSchema = declensionSchema;
        this.nom_sg = nom_sg;
        this.root = root;
        this.gender = gender;
    }

    private Noun() {}
    public static Noun fromGenitive(String nom_sg, String gen_sg, Gender gender) throws DeclinedFormDoesNotExistException, ParserException {
        Noun noun = new Noun();
        noun.nom_sg = nom_sg;
        noun.gender = gender;
        for (DeclensionSchema declensionSchema : DeclensionClasses.getAll()) {
            String suffix = declensionSchema.getSuffix(new DeclinedForm(Casus.GEN, Number.SG, gender));
            if (gen_sg.endsWith(suffix)) {
                noun.declensionSchema = declensionSchema;
                noun.root = gen_sg.substring(0, gen_sg.length()-suffix.length());
                break;
            }
        }
        if (noun.declensionSchema == null) {
            throw new ParserException("Could not determine declension schema for Gen. Sg. form: " + gen_sg);
        }
        return noun;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String getBaseForm() {
        return nom_sg;
    }

    public String getForm(DeclinedForm form) throws DeclinedFormDoesNotExistException {
        if (form.getGender() != this.gender) {
            throw new DeclinedFormDoesNotExistException(form, nom_sg);
        }
        if (form.isCasus(Casus.NOM) && form.isNumber(Number.SG)) {
            return nom_sg;
        }
        return root + declensionSchema.getSuffix(form);
    }
}