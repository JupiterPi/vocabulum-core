package jupiterpi.vocabulum.core.vocabularies.declinated.schemas;

import jupiterpi.vocabulum.core.vocabularies.declinated.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declinated.form.DeclinedForm;

public abstract class DeclensionSchema {
    protected String name;

    protected DeclensionSchema(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSuffix(DeclinedForm form) throws DeclinedFormDoesNotExistException {
        if (!form.hasGender() && isGenderDependant()) throw new DeclinedFormDoesNotExistException(form);
        String suffix = getSuffixRaw(form);
        if (suffix.equals("-")) throw new DeclinedFormDoesNotExistException(form);
        return suffix;
    }

    protected abstract String getSuffixRaw(DeclinedForm form);

    protected abstract boolean isGenderDependant();
}