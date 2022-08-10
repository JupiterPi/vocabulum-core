package jupiterpi.vocabulum.core.vocabularies.conjugated.schemas;

import jupiterpi.vocabulum.core.db.LoadingDataException;
import jupiterpi.vocabulum.core.vocabularies.conjugated.Verb;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo.Pattern;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class SimpleConjugationSchema extends ConjugationSchema {
    public static SimpleConjugationSchema readFromDocument(Document document) throws LoadingDataException {
        String name = document.getString("name");
        SimpleConjugationSchema schema = new SimpleConjugationSchema(name);

        // Kind.IMPERATIVE
        Document imperativeFormsDocument = (Document) document.get("imperative");
        for (CNumber number : CNumber.values()) {
            VerbForm form = new VerbForm(number);
            String pattern = imperativeFormsDocument.getString(number.toString().toLowerCase());
            schema.patterns.put(form, Pattern.fromString(pattern));
        }

        // Kind.INFINITIVE
        Document infinitiveFormsDocument = (Document) document.get("infinitive");
        for (InfinitiveTense infinitiveTense : InfinitiveTense.values()) {
            Document infinitiveTenseDocument = (Document) infinitiveFormsDocument.get(infinitiveTense.toString().toLowerCase());
            for (Voice voice : Voice.values()) {
                String pattern = infinitiveTenseDocument.getString(voice.toString().toLowerCase());
                VerbForm form = new VerbForm(infinitiveTense, voice);
                schema.patterns.put(form, Pattern.fromString(pattern));
            }
        }

        // Kind.BASIC
        Document basicFormsDocument = (Document) document.get("basic");
        for (Voice voice : Voice.values()) {
            Document voiceDocument = (Document) basicFormsDocument.get(voice.toString().toLowerCase());
            for (Tense tense : Tense.values()) {
                Document tenseDocument = (Document) voiceDocument.get(tense.toString().toLowerCase());
                for (Mode mode : Mode.values()) {
                    Document modeDocument = (Document) tenseDocument.get(mode.toString().toLowerCase());
                    for (CNumber number : CNumber.values()) {
                        Document numberDocument = (Document) modeDocument.get(number.toString().toLowerCase());
                        for (Person person : Person.values()) {
                            String pattern = numberDocument.getString(person.toString().toLowerCase());
                            VerbForm form = new VerbForm(new ConjugatedForm(person, number), mode, tense, voice);
                            schema.patterns.put(form, Pattern.fromString(pattern));
                        }
                    }
                }
            }
        }

        // Kind.NOUN_LIKE
        Document nounLikeFormsDocument = (Document) document.get("noun_like");
        for (NounLikeForm nounLikeForm : NounLikeForm.values()) {
            String pattern = nounLikeFormsDocument.getString(nounLikeForm.toString().toLowerCase());
            schema.nounLikeFormRootPatterns.put(nounLikeForm, Pattern.fromString(pattern));
        }

        return schema;
    }

    private SimpleConjugationSchema(String name) {
        super(name);
        patterns = new HashMap<>();
        nounLikeFormRootPatterns = new HashMap<>();
    }

    private Map<VerbForm, Pattern> patterns;
    private Map<NounLikeForm, Pattern> nounLikeFormRootPatterns;

    @Override
    public Pattern getPattern(VerbForm verbForm) throws VerbFormDoesNotExistException {
        Pattern formInfo = patterns.get(verbForm);
        if (!formInfo.exists()) throw VerbFormDoesNotExistException.forConjugationSchema(verbForm, this);
        return formInfo;
    }

    @Override
    public Pattern getNounLikeFormRootPattern(NounLikeForm nounLikeForm) {
        return nounLikeFormRootPatterns.get(nounLikeForm);
    }
}
