package jupiterpi.vocabulum.core.vocabularies.declinated.schemas;

import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.DeclinedForm;

public abstract class DeclensionSchema {
    public String getSuffix(DeclinedForm form) throws DeclinedFormDoesNotExistException {
        String suffix = getSuffixRaw(form);
        if (suffix.equals("-")) throw new DeclinedFormDoesNotExistException(form);
        return suffix;
    }

    protected abstract String getSuffixRaw(DeclinedForm form);
}