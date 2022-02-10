package jupiterpi.vocabulum.core.vocabularies.declinated.schemas;

import jupiterpi.vocabulum.core.vocabularies.declinated.schemas.form.DeclinedForm;

public interface DeclensionSchema {
    String getSuffix(DeclinedForm form);
}