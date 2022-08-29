package jupiterpi.vocabulum.core.vocabularies.declined.adjectives;

import jupiterpi.vocabulum.core.vocabularies.declined.DeclinedFormDoesNotExistException;
import jupiterpi.vocabulum.core.vocabularies.declined.form.DeclinedForm;
import jupiterpi.vocabulum.core.vocabularies.translations.TranslationSequence;
import org.bson.Document;

public class WordbaseAdjective extends Adjective {
    private String baseForm;
    private AdjectiveDefinitionType definitionType;
    private Document adjectiveForms;
    private Document adverbForms;

    public WordbaseAdjective(String baseForm, Document adjectiveForms, Document adverbForms, TranslationSequence translations, String portion, AdjectiveDefinitionType definitionType) {
        super(translations, portion, definitionType);
        this.baseForm = baseForm;
        this.definitionType = definitionType;
        this.adjectiveForms = adjectiveForms;
        this.adverbForms = adverbForms;
    }

    public static WordbaseAdjective readFromDocument(Document document) {
        return new WordbaseAdjective(
                document.getString("base_form"),
                (Document) ((Document) document.get("forms")).get("adjectives"),
                (Document) ((Document) document.get("forms")).get("adverbs"),
                TranslationSequence.readFromDocument(document),
                document.getString("portion"),
                AdjectiveDefinitionType.valueOf(document.getString("definition_type").toUpperCase()));
    }

    @Override
    public String getBaseForm() {
        return baseForm;
    }

    @Override
    public String makeForm(AdjectiveForm form) throws DeclinedFormDoesNotExistException {
        //TODO remake similar to WordbaseVerbs

        String generatedForm;
        if (form.isAdverb()) {
            generatedForm = adverbForms.getString(form.getComparativeForm().toString().toLowerCase());
        } else {
            Document comparativeFormForms = (Document) adjectiveForms.get(form.getComparativeForm().toString().toLowerCase());
            DeclinedForm declinedForm = form.getDeclinedForm();
            Document genderForms = (Document) comparativeFormForms.get(declinedForm.getGender().toString().toLowerCase());
            Document numberForms = (Document) genderForms.get(declinedForm.getNumber().toString().toLowerCase());
            generatedForm = numberForms.getString(declinedForm.getCasus().toString().toLowerCase());
            if (generatedForm.equals("-")) throw DeclinedFormDoesNotExistException.forWord(declinedForm, baseForm);
        }
        return generatedForm;
    }
}
