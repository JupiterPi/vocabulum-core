package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.vocabularies.Vocabulary;
import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.translations.VocabularyTranslation;
import org.bson.Document;

import java.util.List;

public class WordbaseAdjective extends Adjective {
    private String baseForm;
    private Document forms;

    public WordbaseAdjective(String baseForm, Document forms, List<VocabularyTranslation> translations, String portion) {
        super(translations, portion);
        this.baseForm = baseForm;
        this.forms = forms;
    }

    public static WordbaseAdjective readFromDocument(Document document) {
        return new WordbaseAdjective(
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
    public String makeForm(AdjectiveForm form) throws DeclinedFormDoesNotExistException {
        Document comparativeFormForms = (Document) forms.get(form.getComparativeForm().toString().toLowerCase());
        DeclinedForm declinedForm = form.getDeclinedForm();
        Document genderForms = (Document) comparativeFormForms.get(declinedForm.getGender().toString().toLowerCase());
        Document numberForms = (Document) genderForms.get(declinedForm.getNumber().toString().toLowerCase());
        String generatedForm = numberForms.getString(declinedForm.getCasus().toString().toLowerCase());
        if (generatedForm.equals("-")) throw DeclinedFormDoesNotExistException.forWord(declinedForm, baseForm);
        return generatedForm;
    }
}
