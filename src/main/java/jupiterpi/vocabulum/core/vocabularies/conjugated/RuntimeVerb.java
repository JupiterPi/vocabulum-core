package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationSchema;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.FormInfo;

public class RuntimeVerb extends Verb {
    private ConjugationSchema conjugationSchema;
    private String infinitive;
    private String presentRoot;
    private String perfectRoot;

    public RuntimeVerb(ConjugationSchema conjugationSchema, String infinitive, String presentRoot, String perfectRoot) {
        this.conjugationSchema = conjugationSchema;
        this.infinitive = infinitive;
        this.presentRoot = presentRoot;
        this.perfectRoot = perfectRoot;
    }

    @Override
    public String getBaseForm() {
        return infinitive;
    }

    @Override
    public String makeForm(VerbForm form) {
        FormInfo formInfo = conjugationSchema.getFormInfo(form);
        String root = switch (formInfo.getRoot()) {
            case PRESENT -> presentRoot;
            case PERFECT -> perfectRoot;
        };
        String suffix = formInfo.getSuffix();
        return root + suffix;
    }
}
