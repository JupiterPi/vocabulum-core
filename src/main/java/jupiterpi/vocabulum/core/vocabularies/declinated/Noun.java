package jupiterpi.vocabulum.core.vocabularies.declinated;

import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.DeclensionSchema;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.Number;

public class Noun {
    private DeclensionSchema declensionSchema;
    private String nom_sg;
    private String root;

    public Noun(DeclensionSchema declensionSchema, String nom_sg, String root) {
        this.declensionSchema = declensionSchema;
        this.nom_sg = nom_sg;
        this.root = root;
    }

    public String getForm(DeclinedForm form) {
        if (form.isCasus(Casus.NOM) && form.isNumber(Number.SG)) {
            return nom_sg;
        }
        return root + declensionSchema.getSuffix(form);
    }
}