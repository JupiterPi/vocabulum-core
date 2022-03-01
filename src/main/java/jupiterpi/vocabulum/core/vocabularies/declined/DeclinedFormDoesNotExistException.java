package jupiterpi.vocabulum.core.vocabularies.declined;

import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.schemas.DeclensionSchema;

public class DeclinedFormDoesNotExistException extends Exception {
    private DeclinedForm form;

    public DeclinedFormDoesNotExistException(DeclinedForm form) {
        super("This declined form does not exist: " + form.toString());
        this.form = form;
    }

    private DeclinedFormDoesNotExistException(String message, DeclinedForm form) {
        super(message);
        this.form = form;
    }

    public static DeclinedFormDoesNotExistException forWord(DeclinedForm form, String wordToBeDeclined) {
        return new DeclinedFormDoesNotExistException("This declined form does not exist for word " + wordToBeDeclined + ": " + form.toString(), form);
    }

    public static DeclinedFormDoesNotExistException forDeclensionSchema(DeclinedForm form, DeclensionSchema declensionSchema) {
        return new DeclinedFormDoesNotExistException("This declined form does not exist for declension schema " + declensionSchema.getName() + ": " + form.toString(), form);
    }

    public DeclinedForm getForm() {
        return form;
    }
}