package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.conjugated.schemas.forminfo.Pattern;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordbaseVerb extends Verb {
    //TODO write test

    private String baseForm;
    private Map<VerbForm, String> forms;

    public WordbaseVerb(String baseForm, Document forms, List<VocabularyTranslation> translations, String portion) {
        super(translations, portion);
        this.baseForm = baseForm;

        this.forms = new HashMap<>();

        // Kind.IMPERATIVE
        Document imperativeFormsDocument = (Document) forms.get("imperative");
        for (CNumber number : CNumber.values()) {
            VerbForm form = new VerbForm(number);
            this.forms.put(form, imperativeFormsDocument.getString(number.toString().toLowerCase()));
        }

        // Kind.INFINITIVE
        Document infinitiveFormsDocument = (Document) forms.get("infinitive");
        for (InfinitiveTense infinitiveTense : InfinitiveTense.values()) {
            Document infinitiveTenseDocument = (Document) infinitiveFormsDocument.get(infinitiveTense.toString().toLowerCase());
            for (Voice voice : Voice.values()) {
                VerbForm form = new VerbForm(infinitiveTense, voice);
                this.forms.put(form, infinitiveTenseDocument.getString(voice.toString().toLowerCase()));
            }
        }

        // Kind.BASIC
        Document basicFormsDocument = (Document) forms.get("basic");
        for (Voice voice : Voice.values()) {
            Document voiceDocument = (Document) forms.get(voice.toString().toLowerCase());
            for (Tense tense : Tense.values()) {
                Document tenseDocument = (Document) voiceDocument.get(tense.toString().toLowerCase());
                for (Mode mode : Mode.values()) {
                    Document modeDocument = (Document) tenseDocument.get(mode.toString().toLowerCase());
                    for (CNumber number : CNumber.values()) {
                        Document numberDocument = (Document) modeDocument.get(number.toString().toLowerCase());
                        for (Person person : Person.values()) {
                            VerbForm form = new VerbForm(new ConjugatedForm(person, number), mode, tense, voice);
                            this.forms.put(form, numberDocument.getString(person.toString().toLowerCase()));
                        }
                    }
                }
            }
        }

        // Kind.NOUN_LIKE
        Document nounLikeFormsDocument = (Document) forms.get("noun_like");
        for (NounLikeForm nounLikeForm : NounLikeForm.values()) {
            Document nounLikeFormDocument = (Document) nounLikeFormsDocument.get(nounLikeForm.toString().toLowerCase());
            for (Gender gender : Gender.values()) {
                Document genderDocument = (Document) nounLikeFormDocument.get(gender.toString().toLowerCase());
                for (NNumber number : NNumber.values()) {
                    Document numberDocument = (Document) genderDocument.get(number.toString().toLowerCase());
                    for (Casus casus : Casus.values()) {
                        VerbForm form = new VerbForm(nounLikeForm, new DeclinedForm(casus, number, gender));
                        this.forms.put(form, numberDocument.getString(casus.toString().toLowerCase()));
                    }
                }
            }
        }
    }

    public static WordbaseVerb readFromDocument(Document document) {
        return new WordbaseVerb(
                document.getString("base_form"),
                (Document) document.get("forms"),
                readTranslations(document),
                document.getString("portion"));
    }

    @Override
    public String getBaseForm() {
        return baseForm;
    }

    @Override
    public String makeForm(VerbForm form) {
        return forms.get(form);
    }
}