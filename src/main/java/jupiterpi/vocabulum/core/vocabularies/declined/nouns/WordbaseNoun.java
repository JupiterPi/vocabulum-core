package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;

import java.util.List;

public class WordbaseNoun extends Noun {
    private String baseForm;
    private Gender gender;
    private Document forms;

    public WordbaseNoun(String baseForm, Gender gender, Document forms, List<VocabularyTranslation> translations, String portion) {
        super(translations, portion);
        this.baseForm = baseForm;
        this.gender = gender;
        this.forms = forms;
    }

    public static WordbaseNoun readFromDocument(Document document) {
        return new WordbaseNoun(
                document.getString("base_form"),
                Gender.valueOf(document.getString("gender").toUpperCase()),
                (Document) document.get("forms"),
                readTranslations(document),
                document.getString("portion"));
    }

    @Override
    public String getBaseForm() {
        return baseForm;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public String makeForm(NounForm form) throws DeclinedFormDoesNotExistException {
        DeclinedForm declinedForm = form.getDeclinedForm();
        if (declinedForm.hasGender() && declinedForm.getGender() != this.gender) {
            throw DeclinedFormDoesNotExistException.forWord(declinedForm, baseForm);
        }

        Gender gender = declinedForm.hasGender() ? declinedForm.getGender() : this.gender;

        Document genderForms = (Document) forms.get(gender.toString().toLowerCase());
        Document numberForms = (Document) genderForms.get(declinedForm.getNumber().toString().toLowerCase());
        String generatedForm = numberForms.getString(declinedForm.getCasus().toString().toLowerCase());
        if (generatedForm.equals("-")) throw DeclinedFormDoesNotExistException.forWord(declinedForm, baseForm);
        return generatedForm;
    }
}