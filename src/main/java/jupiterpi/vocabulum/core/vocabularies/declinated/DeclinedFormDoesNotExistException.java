package jupiterpi.vocabulum.core.vocabularies.declinated;

import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.DeclinedForm;

public class DeclinedFormDoesNotExistException extends Exception {
    public DeclinedFormDoesNotExistException(DeclinedForm form) {
        super("This declined form does not exist: " + form.toString());
    }

    public DeclinedFormDoesNotExistException(DeclinedForm form, String wordToBeDeclined) {
        super("This declined form does not exist for word " + wordToBeDeclined + ": " + form.toString());
    }
}
