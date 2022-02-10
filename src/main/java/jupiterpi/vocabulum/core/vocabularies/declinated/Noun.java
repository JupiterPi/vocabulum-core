package jupiterpi.vocabulum.core.vocabularies.declinated;

import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionSchema;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Number;

public class Noun {
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