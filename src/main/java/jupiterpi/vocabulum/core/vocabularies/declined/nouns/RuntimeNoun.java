package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Number;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionClasses;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionSchema;

public class RuntimeNoun extends Noun {
    private DeclensionSchema declensionSchema;
    private String nom_sg;
    private String root;
    private Gender gender;

    public RuntimeNoun(DeclensionSchema declensionSchema, String nom_sg, String root, Gender gender) {
        this.declensionSchema = declensionSchema;
        this.nom_sg = nom_sg;
        this.root = root;
        this.gender = gender;
    }

    private RuntimeNoun() {}
    public static RuntimeNoun fromGenitive(String nom_sg, String gen_sg, Gender gender) throws DeclinedFormDoesNotExistException, ParserException {
        RuntimeNoun noun = new RuntimeNoun();
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

    @Override
    public String getBaseForm() {
        return nom_sg;
    }

    @Override
    public String makeForm(NounForm form) throws DeclinedFormDoesNotExistException {
        DeclinedForm declinedForm = form.getDeclinedForm();
        if (declinedForm.hasGender() && declinedForm.getGender() != this.gender) {
            throw DeclinedFormDoesNotExistException.forWord(declinedForm, nom_sg);
        }
        declinedForm.normalizeGender(gender);
        if (declinedForm.isCasus(Casus.NOM) && declinedForm.isNumber(Number.SG)) {
            return nom_sg;
        }
        return root + declensionSchema.getSuffix(declinedForm);
    }
}