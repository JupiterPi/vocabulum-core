package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas;

import jupiterpi.vocabulum.core.vocabularies.conjugated.form.NounLikeForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo.Pattern;

public abstract class ConjugationSchema {
    protected String name;
    protected String displayName;

    protected ConjugationSchema(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public abstract Pattern getPattern(VerbForm verbForm) throws VerbFormDoesNotExistException;

    public abstract Pattern getNounLikeFormRootPattern(NounLikeForm nounLikeForm);
}
