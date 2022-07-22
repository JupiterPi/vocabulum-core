package jupiterpi.vocabulum.core.vocabularies.conjugated.form;

import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationSchema;

public class VerbFormDoesNotExistException extends Exception {
    private VerbForm form;

    public VerbFormDoesNotExistException(VerbForm form) {
        super("This conjugated form does not exist: " + form.toString());
        this.form = form;
    }

    private VerbFormDoesNotExistException(String message, VerbForm form) {
        super(message);
        this.form = form;
    }

    public static VerbFormDoesNotExistException forWord(VerbForm form, String word) {
        return new VerbFormDoesNotExistException("This conjugated form does not exist for word " + word + ": " + form.toString(), form);
    }

    public static VerbFormDoesNotExistException forConjugationSchema(VerbForm form, ConjugationSchema conjugationSchema) {
        return new VerbFormDoesNotExistException("This conjugated form does not exist for conjugation schema " + conjugationSchema.getName() + ": " + form.toString(), form);
    }
}
