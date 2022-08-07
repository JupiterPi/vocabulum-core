package jupiterpi.vocabulum.core.vocabularies.conjugated;

import jupiterpi.vocabulum.core.i18n.I18n;
import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.conjugated.form.*;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Casus;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.declined.form.NNumber;
import jupiterpi.vocabulum.core.vocabularies.declined.nouns.NounForm;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public abstract class Verb extends Vocabulary {
    protected Verb(List<VocabularyTranslation> translations, String portion) {
        super(translations, portion);
    }

    public abstract String makeForm(VerbForm form) throws VerbFormDoesNotExistException;

    public String makeFormOrDash(VerbForm form) {
        try {
            return makeForm(form);
        } catch (VerbFormDoesNotExistException e) {
            return "-";
        }
    }

    @Override
    public Kind getKind() {
        return Kind.VERB;
    }

    @Override
    public String getDefinition(I18n i18n) {
        String first_sg_pres = makeFormOrDash(new VerbForm(ConjugatedForm.get("1. Sg."), Mode.INDICATIVE, Tense.PRESENT, Voice.ACTIVE));
        String first_sg_perfect = makeFormOrDash(new VerbForm(ConjugatedForm.get("1. Sg."), Mode.INDICATIVE, Tense.PERFECT, Voice.ACTIVE));
        return getBaseForm() + ", " + first_sg_pres + ", " + first_sg_perfect;
        //TODO update for full form making -> write test
    }

    public abstract String getBaseForm();

    @Override
    public Document generateWordbaseEntrySpecificPart() {
        Document formsDocument = new Document();

        // Kind.INFINITIVE
        Document infinitiveFormsDocument = new Document();
        for (InfinitiveTense infinitiveTense : InfinitiveTense.values()) {
            String form = makeFormOrDash(new VerbForm(infinitiveTense));
            infinitiveFormsDocument.put(infinitiveTense.toString().toLowerCase(), form);
        }
        formsDocument.put("infinitive", infinitiveFormsDocument);

        // Kind.BASIC
        Document basicFormsDocument = new Document();
        for (Voice voice : Voice.values()) {
            Document voiceDocument = new Document();
            for (Tense tense : Tense.values()) {
                Document tenseDocument = new Document();
                for (Mode mode : Mode.values()) {
                    Document modeDocument = new Document();
                    for (CNumber number : CNumber.values()) {
                        Document numberDocument = new Document();
                        for (Person person : Person.values()) {
                            String form = makeFormOrDash(new VerbForm(new ConjugatedForm(person, number), mode, tense, voice));
                            numberDocument.put(person.toString().toLowerCase(), form);
                        }
                        modeDocument.put(number.toString().toLowerCase(), numberDocument);
                    }
                    tenseDocument.put(mode.toString().toLowerCase(), modeDocument);
                }
                voiceDocument.put(tense.toString().toLowerCase(), tenseDocument);
            }
            basicFormsDocument.put(voice.toString().toLowerCase(), voiceDocument);
        }
        formsDocument.put("basic", basicFormsDocument);

        // Kind.NOUN_LIKE
        Document nounLikeFormsDocument = new Document();
        for (NounLikeForm nounLikeForm : NounLikeForm.values()) {
            Document nounLikeFormDocument = new Document();
            for (Gender gender : Gender.values()) {
                Document genderDocument = new Document();
                for (NNumber number : NNumber.values()) {
                    Document numberDocument = new Document();
                    for (Casus casus : Casus.values()) {
                        String form = makeFormOrDash(new VerbForm(nounLikeForm, new DeclinedForm(casus, number, gender)));
                        numberDocument.put(casus.toString().toLowerCase(), form);
                    }
                    genderDocument.put(number.toString().toLowerCase(), numberDocument);
                }
                nounLikeFormDocument.put(gender.toString().toLowerCase(), genderDocument);
            }
            nounLikeFormsDocument.put(nounLikeForm.toString().toLowerCase(), nounLikeFormDocument);
        }
        formsDocument.put("noun_like", nounLikeFormsDocument);

        Document document = new Document();
        document.put("forms", formsDocument);
        return document;
    }

    public List<VerbForm> identifyForm(String word) {
        List<VerbForm> forms = new ArrayList<>();

        // Kind.INFINITIVE
        for (InfinitiveTense infinitiveTense : InfinitiveTense.values()) {
            VerbForm form = new VerbForm(infinitiveTense);
            try {
                if (makeForm(form).equalsIgnoreCase(word)) forms.add(form);
            } catch (VerbFormDoesNotExistException ignored) {}
        }

        // Kind.BASIC
        for (Voice voice : Voice.values()) {
            for (Tense tense : Tense.values()) {
                for (Mode mode : Mode.values()) {
                    for (CNumber number : CNumber.values()) {
                        for (Person person : Person.values()) {
                            VerbForm form = new VerbForm(new ConjugatedForm(person, number), mode, tense, voice);
                            try {
                                if (makeForm(form).equalsIgnoreCase(word)) forms.add(form);
                            } catch (VerbFormDoesNotExistException ignored) {}
                        }
                    }
                }
            }
        }

        // Kind.NOUN_LIKE
        for (NounLikeForm nounLikeForm : NounLikeForm.values()) {
            for (Gender gender : Gender.values()) {
                for (NNumber number : NNumber.values()) {
                    for (Casus casus : Casus.values()) {
                        VerbForm form = new VerbForm(nounLikeForm, new DeclinedForm(casus, number, gender));
                        try {
                            if (makeForm(form).equalsIgnoreCase(word)) forms.add(form);
                        } catch (VerbFormDoesNotExistException ignored) {}
                    }
                }
            }
        }

        return forms;
    }
}