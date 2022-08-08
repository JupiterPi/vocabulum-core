package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas;

import jupiterpi.vocabulum.core.vocabularies.conjugated.form.NounLikeForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo.Pattern;

public abstract class ConjugationSchema {
    protected String name;

    protected ConjugationSchema(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Pattern getPattern(VerbForm verbForm) throws VerbFormDoesNotExistException;

    public abstract Pattern getNounLikeFormRootPattern(NounLikeForm nounLikeForm);
}
