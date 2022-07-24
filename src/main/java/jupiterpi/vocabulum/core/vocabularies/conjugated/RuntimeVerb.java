package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationClasses;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationSchema;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.FormInfo;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;

import java.util.List;

public class RuntimeVerb extends Verb {
    private ConjugationSchema conjugationSchema;
    private String infinitive;
    private String presentRoot;
    private String perfectRoot;

    public RuntimeVerb(ConjugationSchema conjugationSchema, String infinitive, String presentRoot, String perfectRoot, List<VocabularyTranslation> translations) {
        super(translations);
        this.conjugationSchema = conjugationSchema;
        this.infinitive = infinitive;
        this.presentRoot = presentRoot;
        this.perfectRoot = perfectRoot;
    }

    public static RuntimeVerb fromBaseForms(String infinitive, String first_sg_present, String first_sg_perfect, List<VocabularyTranslation> translations) throws ParserException, VerbFormDoesNotExistException {
        ConjugationSchema conjugationSchema = ConjugationClasses.a_Conjugation;

        String presentRoot = null;
        String perfectRoot = null;
        for (ConjugationSchema schema : ConjugationClasses.getAll()) {
            FormInfo info = schema.getFormInfo(VerbForm.get("1. Sg. Pres."));
            if (first_sg_present.endsWith(info.getSuffix())) {
                presentRoot = first_sg_present.substring(0, first_sg_present.length() - info.getSuffix().length());
                perfectRoot = first_sg_perfect.substring(0, first_sg_perfect.length() - schema.getFormInfo(VerbForm.get("1. Sg. Perf.")).getSuffix().length());
            }
        }
        if (presentRoot == null) {
            throw new ParserException("Couldn't read present root from 1. Sg. Pres. form: " + first_sg_present);
        }

        return new RuntimeVerb(conjugationSchema, infinitive, presentRoot, perfectRoot, translations);
    }

    @Override
    public String getBaseForm() {
        return infinitive;
    }

    @Override
    public String makeForm(VerbForm form) throws VerbFormDoesNotExistException {
        FormInfo formInfo = conjugationSchema.getFormInfo(form);
        if (!formInfo.exists()) {

        }
        String root = switch (formInfo.getRoot()) {
            case PRESENT -> presentRoot;
            case PERFECT -> perfectRoot;
        };
        String suffix = formInfo.getSuffix();
        return root + suffix;
    }
}
