package jupiterpi.vocabulum.core.vocabularies.declined.nouns;

import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;

import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import org.bson.Document;

public class WordbaseNoun extends Noun {
    private String baseForm;
    private Document forms;

    public WordbaseNoun(String baseForm, Document forms) {
        this.baseForm = baseForm;
        this.forms = forms;
    }

    public static WordbaseNoun readFromDocument(Document document) {
        return new WordbaseNoun(document.getString("base_form"), (Document) document.get("forms"));
    }

    @Override
    public String getBaseForm() {
        return baseForm;
    }

    @Override
    public String makeForm(NounForm form) throws DeclinedFormDoesNotExistException {
        try {
            DeclinedForm declinedForm = form.getDeclinedForm();
            Document genderForms = (Document) forms.get(declinedForm.getGender().toString().toLowerCase());
            Document numberForms = (Document) genderForms.get(declinedForm.getNumber().toString().toLowerCase());
            return numberForms.getString(declinedForm.getCasus().toString().toLowerCase());
        } catch (NullPointerException e) {
            throw DeclinedFormDoesNotExistException.forWord(form.getDeclinedForm(), baseForm);
        }
    }
}