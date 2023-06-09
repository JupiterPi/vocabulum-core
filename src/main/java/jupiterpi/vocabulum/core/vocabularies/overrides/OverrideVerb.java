package jupiterpi.vocabulum.core.vocabularies.overrides;

import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.InfinitiveTense;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbForm;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.VerbFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.Voice;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.ConjugationSchema;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.SimpleConjugationSchema;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo.VerbInfo;
import jupiterpi.vocabulum.core.vocabularies.formresult.FormResult;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;

import java.util.List;

public class OverrideVerb extends Verb {
    private String definition;
    private List<OverrideVocabularies.TemplatePart> template;
    private ConjugationSchema overrideSchema;

    public OverrideVerb(String portion, String definition, List<OverrideVocabularies.TemplatePart> template, String punctuationSign, TranslationSequence translations, Document override) {
        super(punctuationSign, translations, portion);
        this.definition = definition;
        this.template = template;

        String base_form = definition.split(",")[0].trim();
        overrideSchema = SimpleConjugationSchema.readFromDocument("override:" + base_form, base_form, (Document) override.get("overrides"));
    }

    @Override
    public String getBaseForm() {
        return makeForm(new VerbForm(InfinitiveTense.PRESENT, Voice.ACTIVE)).toString();
    }

    @Override
    public String getDefinition() {
        return definition;
    }

    @Override
    public FormResult makeForm(VerbForm form) {
        //TODO implement noun-like OverrideVerb forms
        if (form.isNounLike()) return FormResult.doesNotExist();

        try {
            String result = "";
            VerbInfo verbInfo = new VerbInfo(
                    "", "", "", "", "", "", "", "", true, true
            );
            for (OverrideVocabularies.TemplatePart part : template) {
                if (part instanceof OverrideVocabularies.TextTemplatePart) {
                    result += ((OverrideVocabularies.TextTemplatePart) part).getText();
                } else {
                    result += overrideSchema.getPattern(form).make(verbInfo);
                }
            }
            return FormResult.withPrimaryForm(result);
        } catch (VerbFormDoesNotExistException e) {
            return FormResult.doesNotExist();
        }
    }

    @Override
    public ConjugationSchema getConjugationSchema() {
        return null;
    }
}
