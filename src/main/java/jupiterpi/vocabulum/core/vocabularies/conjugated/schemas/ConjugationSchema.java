package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas;

import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;

public abstract class ConjugationSchema {
    protected String name;

    protected ConjugationSchema(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract FormInfo getFormInfo(VerbForm verbForm) throws VerbFormDoesNotExistException;
}
