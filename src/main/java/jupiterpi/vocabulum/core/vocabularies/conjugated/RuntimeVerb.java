package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.db.Database;
import jupiterpi.vocabulum.core.interpreter.parser.ParserException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationSchema;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo.Pattern;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo.VerbInfo;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;

import java.util.List;

public class RuntimeVerb extends Verb {
    private ConjugationSchema conjugationSchema;
    private String infinitive;
    private String presentRoot;
    private String perfectRoot;

    public RuntimeVerb(ConjugationSchema conjugationSchema, String infinitive, String presentRoot, String perfectRoot, List<VocabularyTranslation> translations, String portion) {
        super(translations, portion);
        this.conjugationSchema = conjugationSchema;
        this.infinitive = infinitive;
        this.presentRoot = presentRoot;
        this.perfectRoot = perfectRoot;
    }

    public static RuntimeVerb fromBaseForms(String infinitive, String first_sg_present, String first_sg_perfect, List<VocabularyTranslation> translations, String portion) throws ParserException, VerbFormDoesNotExistException {
        ConjugationSchema conjugationSchema = Database.get().getConjugationClasses().a_Conjugation();

        String presentRoot = null;
        String perfectRoot = null;
        for (ConjugationSchema schema : Database.get().getConjugationClasses().getAll()) {
            Pattern pattern = schema.getPattern(VerbForm.get("1. Sg. Pres."));
            if (first_sg_present.endsWith(pattern.getSuffix())) {
                presentRoot = first_sg_present.substring(0, first_sg_present.length() - pattern.getSuffix().length());
                perfectRoot = first_sg_perfect.substring(0, first_sg_perfect.length() - schema.getPattern(VerbForm.get("1. Sg. Perf.")).getSuffix().length());
            }
        }
        if (presentRoot == null) {
            throw new ParserException("Couldn't read present root from 1. Sg. Pres. form: " + first_sg_present);
        }

        return new RuntimeVerb(conjugationSchema, infinitive, presentRoot, perfectRoot, translations, portion);
    }

    @Override
    public String getBaseForm() {
        return infinitive;
    }

    @Override
    public String makeForm(VerbForm form) throws VerbFormDoesNotExistException {
        Pattern pattern = conjugationSchema.getPattern(form);
        VerbInfo info = new VerbInfo(
                presentRoot, perfectRoot,
                "", "", "", "", "", ""
        );
        return pattern.make(info);
        //TODO implement actual
    }
}
