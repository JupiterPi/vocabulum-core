package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.declined.form.Gender;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;

public class WordbaseNoun extends Noun {
    private String baseForm;
    private Gender gender;
    private String declensionSchema;
    private Document forms;

    public WordbaseNoun(String baseForm, Gender gender, String declensionSchema, Document forms, TranslationSequence translations, String portion) {
        super(translations, portion);
        this.baseForm = baseForm;
        this.gender = gender;
        this.declensionSchema = declensionSchema;
        this.forms = forms;
    }

    public static WordbaseNoun readFromDocument(Document document) {
        return new WordbaseNoun(
                document.getString("base_form"),
                Gender.valueOf(document.getString("gender").toUpperCase()),
                document.getString("declension_schema"),
                (Document) document.get("forms"),
                TranslationSequence.readFromDocument(document),
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
    public String getDeclensionSchema() {
        return declensionSchema;
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