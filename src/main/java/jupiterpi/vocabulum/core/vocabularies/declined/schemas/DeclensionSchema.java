package jupiterpi.vocabulum.core.vocabularies.declined.schemas;

import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;

public abstract class DeclensionSchema {
    protected String name;
    protected String displayName;

    protected DeclensionSchema(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSuffix(DeclinedForm form) throws DeclinedFormDoesNotExistException {
        String suffix = getSuffixRaw(form);
        if (suffix.equals("-")) throw DeclinedFormDoesNotExistException.forDeclensionSchema(form, this);
        return suffix;
    }

    protected abstract String getSuffixRaw(DeclinedForm form) throws DeclinedFormDoesNotExistException;
}